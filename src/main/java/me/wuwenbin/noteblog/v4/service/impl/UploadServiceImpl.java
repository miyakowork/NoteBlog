package me.wuwenbin.noteblog.v4.service.impl;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import me.wuwenbin.modules.utils.lang.LangUtils;
import me.wuwenbin.modules.utils.util.function.TemplateSupplier;
import me.wuwenbin.noteblog.v4.common.FileType;
import me.wuwenbin.noteblog.v4.model.Upload;
import me.wuwenbin.noteblog.v4.model.XParam;
import me.wuwenbin.noteblog.v4.repository.ParamRepository;
import me.wuwenbin.noteblog.v4.repository.UploadRepository;
import me.wuwenbin.noteblog.v4.repository.UserRepository;
import me.wuwenbin.noteblog.v4.service.UploadService;
import me.wuwenbin.noteblog.v4.service.support.LayUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import static java.time.LocalDateTime.now;

/**
 * created by Wuwenbin on 2018/1/25 at 13:58
 */
@Slf4j
@Service
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public class UploadServiceImpl implements UploadService {
    @Autowired
    private Environment environment;
    @Autowired
    private UploadRepository uploadRepository;
    @Autowired
    private ParamRepository paramRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Map<String, Object> upload(MultipartFile qrCodeImg, String type) {
        String path = FileType.IMAGE;
        String fileName = qrCodeImg.getOriginalFilename();
        //扩展名，包括点符号
        String ext = fileName.substring(fileName.lastIndexOf("."));
        fileName = LangUtils.random.uuidCool().concat(ext);
        try {
            File targetFile = new File(path);
            boolean m = true;
            if (!targetFile.exists()) {
                m = targetFile.mkdirs();
            }
            if (m) {
                String filePath = saveFile(path, fileName, qrCodeImg::getBytes);
                String virtualPath = "/upload" + path + "/" + fileName;
                uploadRepository.save(Upload.builder().diskPath(filePath).virtualPath(virtualPath).type(qrCodeImg.getContentType()).upload(now()).build());
                if ("alipay".equals(type)) {
                    paramRepository.updateValueByName(XParam.builder().name("alipay").value(virtualPath).build());
                }
                if ("wechat".equals(type)) {
                    paramRepository.updateValueByName(XParam.builder().name("wechat_pay").value(virtualPath).build());
                }
                return LayUpload.ok("上传成功！", virtualPath);
            } else {
                return LayUpload.err("文件创建失败！");
            }
        } catch (IOException e) {
            log.error("文件IO出错，出错信息：{}", e.getLocalizedMessage());
            return LayUpload.err("文件IO出错，出错信息：" + e.getLocalizedMessage());
        } catch (Exception e) {
            return LayUpload.err("上传出错，出错信息：" + e.getLocalizedMessage());
        }
    }

    @Override
    public Map<String, Object> uploadAvatar(MultipartFile avatar, String username) {
        String path = FileType.IMAGE;
        String fileName = avatar.getOriginalFilename();
        //扩展名，包括点符号
        String ext = fileName.substring(fileName.lastIndexOf("."));
        fileName = LangUtils.random.uuidCool().concat(ext);
        try {
            File targetFile = new File(path);
            boolean m = true;
            if (!targetFile.exists()) {
                m = targetFile.mkdirs();
            }
            if (m) {
                String filePath = saveFile(path, fileName, avatar::getBytes);
                String virtualPath = "/upload" + path + "/" + fileName;
                uploadRepository.save(Upload.builder().diskPath(filePath).virtualPath(virtualPath).type(avatar.getContentType()).upload(now()).build());
                int uploadAvatar = userRepository.updateAdminAvatar(virtualPath, username);
                assert uploadAvatar == 1;
                return LayUpload.ok("上传头像成功，重新登陆后生效！", virtualPath);
            } else {
                return LayUpload.err("文件创建失败！");
            }
        } catch (IOException e) {
            log.error("文件IO出错，出错信息：{}", e.getLocalizedMessage());
            return LayUpload.err("文件IO出错，出错信息：" + e.getLocalizedMessage());
        } catch (Exception e) {
            return LayUpload.err("上传出错，出错信息：" + e.getLocalizedMessage());
        }
    }


    private String saveFile(String path, String fileName, TemplateSupplier<byte[]> fileBytes) throws Exception {
        String prefix = environment.getProperty("spring.resources.static-locations");
        String filePath = prefix + path + "/" + fileName;
        filePath = FileUtil.getAbsolutePath(filePath);
        FileOutputStream out = new FileOutputStream(filePath);
        out.write(fileBytes.get());
        out.flush();
        out.close();
        return filePath;
    }
}
