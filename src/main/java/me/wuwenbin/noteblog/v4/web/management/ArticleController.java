package me.wuwenbin.noteblog.v4.web.management;

import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.pagination.model.layui.LayuiTable;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.valdiation.template.RTemplate;
import me.wuwenbin.noteblog.v4.common.SessionParam;
import me.wuwenbin.noteblog.v4.common.blog.BlogContext;
import me.wuwenbin.noteblog.v4.model.Article;
import me.wuwenbin.noteblog.v4.model.management.bo.ArticleQueryBo;
import me.wuwenbin.noteblog.v4.model.management.vo.ArticleVo;
import me.wuwenbin.noteblog.v4.repository.ArticleRepository;
import me.wuwenbin.noteblog.v4.repository.CateRepository;
import me.wuwenbin.noteblog.v4.repository.TagReferRepository;
import me.wuwenbin.noteblog.v4.service.ArticleService;
import me.wuwenbin.noteblog.v4.service.UploadService;
import me.wuwenbin.noteblog.v4.service.support.LayUpload;
import me.wuwenbin.noteblog.v4.service.support.NkUpload;
import me.wuwenbin.noteblog.v4.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Map;

import static me.wuwenbin.modules.utils.web.Controllers.builder;
import static me.wuwenbin.modules.valdiation.assertion.Assert.check;

/**
 * created by Wuwenbin on 2018/1/14 at 15:18
 */
@Controller("managementArticleController")
@RequestMapping("/management/blog")
public class ArticleController extends BaseController {

    @Autowired
    private BlogContext blogContext;
    @Autowired
    private RTemplate rTemplate;
    @Autowired
    private CateRepository cateRepository;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private TagReferRepository tagReferRepository;
    @Autowired
    private UploadService uploadService;

    @GetMapping
    public String blog(Model model) {
        model.addAttribute("cateList", cateRepository.findAll());
        return "management/blog";
    }

    @GetMapping("/index")
    public String index() {
        return "management/blogs";
    }

    @PostMapping("/list")
    @ResponseBody
    public LayuiTable<ArticleVo> index(Page<ArticleVo> articlePage, ArticleQueryBo articleBo) {
        articlePage = articleRepository.findPagination(articlePage, ArticleVo.class, articleBo);
        return layuiTable(articlePage);
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("cateList", cateRepository.findAll());
        model.addAttribute("editArticle", articleRepository.findOne(id));
        String[] tagArray = tagReferRepository.findNameByArticleId(id).toArray(new String[]{});
        String tags = Arrays.toString(tagArray);
        model.addAttribute("articleTags", tags.substring(1, tags.length() - 1));
        return "management/blog_edit";
    }

    @PostMapping("/doEdit")
    @ResponseBody
    public R doEdit(@Valid Article article, String tagName, BindingResult result, @CookieValue(SessionParam.SESSION_ID_COOKIE) String uuid) {
        article.setAuthorId(blogContext.getSessionUser(uuid).getId());
        return validResponse(result, rTemplate, article,
                editArticle -> builder("编辑博文").exec(() -> articleService.updateArticle(editArticle, tagName)));
    }

    @PostMapping("/edit/appreciable/{id}")
    @ResponseBody
    public R appreciable(@PathVariable("id") Long id, Boolean appreciable) {
        return builder("修改打赏状态").exec(() -> articleRepository.updateAppreciableById(appreciable, id) == 1);
    }

    @PostMapping("/edit/commented/{id}")
    @ResponseBody
    public R commented(@PathVariable("id") Long id, Boolean commented) {
        return builder("修改评论状态").exec(() -> articleRepository.updateCommentedById(commented, id) == 1);
    }

    @PostMapping("/edit/top/{id}")
    @ResponseBody
    public R top(@PathVariable("id") Long id, Boolean top) {
        return builder("修改置顶状态").exec(() -> articleService.updateTopById(id, top));
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public R delete(@PathVariable("id") Long id) {
        return builder("删除博文").execLight(id, articleId -> articleRepository.delete(articleId));
    }


    @PostMapping("/post")
    @ResponseBody
    public R post(@Valid Article article, String tagName, BindingResult result, @CookieValue(SessionParam.SESSION_ID_COOKIE) String uuid) {
        if (!result.hasErrors()) {
            article.setAuthorId(blogContext.getSessionUser(uuid).getId());
            return builder("发布博文").exec(() -> articleService.postArticle(article, tagName));
        } else {
            return check(rTemplate, result);
        }
    }

    @PostMapping("/upload")
    @ResponseBody
    public Map<String, Object> upload(HttpServletRequest request, @RequestParam(value = "uploadFile", required = false) MultipartFile file) {
        String base64 = request.getParameter("base64");
        if (null != base64 && "1".equals(base64)) {
            String base64Data = request.getParameter("img_base64_data");
            String[] pureData = base64Data.split("base64,");
            if (pureData.length == 2) {
                String contentType = pureData[0];
                String pureBase64Data = pureData[1];
                return articleService.upload(contentType, pureBase64Data);
            } else {
                return NkUpload.err("数据不合法，上传失败！");
            }
        } else {
            NkUpload.err("数据为空，上传失败！");
        }
        return articleService.upload(file);
    }

    @PostMapping("/upload/cover")
    @ResponseBody
    public Map<String, Object> upload(@RequestParam(value = "file", required = false) MultipartFile file, String type) {
        if (file != null) {
            return uploadService.upload(file, type);
        } else {
            return LayUpload.err("上传文件为空！");
        }
    }

}
