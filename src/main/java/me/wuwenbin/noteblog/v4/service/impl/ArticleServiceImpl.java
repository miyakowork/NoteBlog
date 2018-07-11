package me.wuwenbin.noteblog.v4.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import me.wuwenbin.modules.utils.http.WebUtils;
import me.wuwenbin.modules.utils.lang.LangUtils;
import me.wuwenbin.modules.utils.util.function.TemplateSupplier;
import me.wuwenbin.noteblog.v4.common.FileType;
import me.wuwenbin.noteblog.v4.common.TagType;
import me.wuwenbin.noteblog.v4.model.Article;
import me.wuwenbin.noteblog.v4.model.Tag;
import me.wuwenbin.noteblog.v4.model.TagRefer;
import me.wuwenbin.noteblog.v4.model.Upload;
import me.wuwenbin.noteblog.v4.repository.ArticleRepository;
import me.wuwenbin.noteblog.v4.repository.TagReferRepository;
import me.wuwenbin.noteblog.v4.repository.TagRepository;
import me.wuwenbin.noteblog.v4.repository.UploadRepository;
import me.wuwenbin.noteblog.v4.service.ArticleService;
import me.wuwenbin.noteblog.v4.service.support.NkUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static java.time.LocalDateTime.now;

/**
 * created by Wuwenbin on 2018/1/16 at 16:17
 */
@Slf4j
@Service
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private DataSourceTransactionManager tm;
    @Autowired
    private Environment environment;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TagReferRepository tagReferRepository;
    @Autowired
    private UploadRepository uploadRepository;

    @Override
    public boolean postArticle(Article article, String tagNames) {
        if (LangUtils.string.isEmpty(tagNames)) {
            throw new RuntimeException("tagName不能为空！");
        }
        String[] tagNameArray = tagNames.split(",");
        DefaultTransactionDefinition def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = tm.getTransaction(def);
        try {
            //博客没啥并发吧，那就按秒来吧
            long articleId = System.currentTimeMillis() / 1000;
            article.setId(articleId);
            getSumByFilterContent(article);
            int savePostArticle = articleRepository.savePk(article);
            if (savePostArticle == 0) {
                throw new RuntimeException("博文未发布成功...");
            }
            int cnt = 0;
            for (String name : Arrays.asList(tagNameArray)) {
                long tagId;
                if (tagRepository.countByName(name) == 0) {
                    tagId = tagRepository.save(Tag.builder().name(name).build()).getId();
                } else {
                    tagId = tagRepository.findIdByName(name);
                }
                tagReferRepository.save(TagRefer.builder().referId(articleId).tagId(tagId).show(cnt < 4).type(TagType.article.name()).build());
                cnt++;
            }
            tm.commit(status);
            return true;
        } catch (Exception e) {
            tm.rollback(status);
            log.error("发布博文出错，回滚数据...", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateArticle(Article article, String tagNames) {
        String[] tagNameArray = tagNames.split(",");
        DefaultTransactionDefinition def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = tm.getTransaction(def);
        try {
            article.setModify(now());
            getSumByFilterContent(article);
            if (articleRepository.updateById(article) == 1) {
                tagReferRepository.deleteByReferId(article.getId());
                int cnt = 0;
                for (String name : Arrays.asList(tagNameArray)) {
                    long tagId;
                    if (tagRepository.countByName(name) == 0) {
                        tagId = tagRepository.save(Tag.builder().name(name).build()).getId();
                    } else {
                        tagId = tagRepository.findIdByName(name);
                    }
                    tagReferRepository.save(TagRefer.builder().referId(article.getId()).tagId(tagId).show(cnt < 4).type(TagType.article.name()).build());
                    cnt++;
                }
                tm.commit(status);
                return true;
            } else return false;
        } catch (Exception e) {
            tm.rollback(status);
            log.error("发布博文出错，回滚数据...", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> upload(MultipartFile file) {
        log.info("上传 [{}] 类型文件", file.getContentType());
        String path = file.getContentType().contains("image/") ? FileType.IMAGE : FileType.FILE;
        String fileName = file.getOriginalFilename();
        //扩展名，包括点符号
        String ext = fileName.substring(fileName.lastIndexOf("."));
        fileName = LangUtils.random.uuidCool().concat(ext);
        return upload(path, fileName, file::getBytes, file.getContentType());
    }

    @Override
    public Map<String, Object> upload(String type, String base64Data) {
        log.info("上传 [{}] 类型文件", type);
        String path = type.contains("image/") ? FileType.IMAGE : FileType.FILE;
        String ext = type.split("/")[1].replace(";", "");
        String fileName = LangUtils.random.uuidCool().concat(".").concat(ext);
        return upload(path, fileName, () -> Base64Utils.decodeFromString(base64Data), type);
    }

    @Override
    public boolean updateTopById(long articleId, boolean top) throws Exception {
        if (top) {
            int maxTop = articleRepository.findMaxTop();
            return articleRepository.updateTopById(maxTop + 1, articleId) == 1;
        } else {
            int currentTop = articleRepository.findTopById(articleId);
            int isModifyTops = articleRepository.updateTopsByTop(currentTop);
            return articleRepository.updateTopById(0, articleId) == 1;
        }
    }

    private Map<String, Object> upload(String path, String fileName, TemplateSupplier<byte[]> fileBytes, String fileType) {
        try {
            File targetFile = new File(path);
            boolean m = true;
            if (!targetFile.exists()) {
                m = targetFile.mkdirs();
            }
            if (m) {
                String prefix = environment.getProperty("spring.resources.static-locations");
                String filePath = prefix + path + "/" + fileName;
                filePath = FileUtil.getAbsolutePath(filePath);
                FileOutputStream out = new FileOutputStream(filePath);
                out.write(fileBytes.get());
                out.flush();
                out.close();
                String virtualPath = "/upload" + path + "/" + fileName;
                uploadRepository.save(Upload.builder().diskPath(filePath).virtualPath(virtualPath).type(fileType).upload(now()).build());
                return NkUpload.ok(virtualPath);
            } else {
                return NkUpload.err("文件创建失败！");
            }
        } catch (IOException e) {
            log.error("文件IO出错，出错信息：{}", e.getLocalizedMessage());
            return NkUpload.err("文件IO出错，出错信息：" + e.getLocalizedMessage());
        } catch (Exception e) {
            return NkUpload.err("上传出错，出错信息：" + e.getLocalizedMessage());
        }
    }

    private void getSumByFilterContent(Article article) {
        String clearContent = WebUtils.filterHtml(StrUtil.trim(article.getContent()));
        clearContent = StringUtils.trimAllWhitespace(clearContent);
        clearContent = clearContent.substring(0, clearContent.length() < 243 ? clearContent.length() : 243);
        int allStandardLength = clearContent.length();
        int fullAngelLength = LangUtils.string.fullAngelWords(clearContent);
        int finalLength = allStandardLength - fullAngelLength / 2;
        article.setSummary(clearContent.substring(0, finalLength < 243 ? finalLength : 243));
        article.setTextContent(clearContent);
    }
}
