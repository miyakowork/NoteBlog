package me.wuwenbin.noteblog.v4.config.listener;

import lombok.extern.slf4j.Slf4j;
import me.wuwenbin.modules.utils.lang.LangUtils;
import me.wuwenbin.modules.utils.security.Encrypt;
import me.wuwenbin.noteblog.v4.model.User;
import me.wuwenbin.noteblog.v4.model.XParam;
import me.wuwenbin.noteblog.v4.repository.ParamRepository;
import me.wuwenbin.noteblog.v4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

/**
 * created by Wuwenbin on 2018/2/13 at 18:53
 */
@Slf4j
@Component
public class NoteBlogListener implements ServletContextListener {

    @Autowired
    private Environment environment;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ParamRepository paramRepository;

    private String masterName;
    private String masterPass;


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            if (!paramRepository.findValueByName("is_set_master").equals("1")) {
                initMaster();
                log.info("初始化设置管理员账号：[{}]，密码：[{}]", masterName, masterPass);
                paramRepository.updateValueByName(XParam.builder().name("is_set_master").value("1").build());
            } else {
                log.info("已设定管理员账号，跳过初始化管理员账号设置");
            }
            initUploadPath();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("初始化设置失败！", e);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }


    private void initMaster() throws Exception {
        this.masterName = environment.getProperty("master.name");
        if (LangUtils.string.isEmpty(masterName)) {
            throw new IllegalArgumentException("管理员账户未设置！");
        } else {
            this.masterPass = environment.getProperty("master.pass", "123456");
            userRepository.save(User.builder()
                    .username(masterName)
                    .password(Encrypt.digest.md5Hex(Encrypt.digest.md5Hex(masterPass)))
                    .avatar("/static/assets/img/bmy.png")
                    .defaultRoleId(1L)
                    .nickname("管理员")
                    .build());
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void initUploadPath() throws Exception {
        String uploadPathKey = "spring.resources.static-locations";
        String path = environment.getProperty(uploadPathKey);
        if (!StringUtils.isEmpty(path)) {
            log.info("文件上传路径设置为：[{}]", path);
            path = path.replace("file:", "");
            File filePath = new File(path + "file/");
            File imgPath = new File(path + "img/");
            boolean f = false, i = false;
            if (!filePath.exists() && !filePath.isDirectory()) {
                f = filePath.mkdirs();
            }
            if (!imgPath.exists() && !imgPath.isDirectory()) {
                i = imgPath.mkdirs();
            }
            if (f && i) {
                log.info("成功创建目录：[{}] 和 [{}]", path + "file/", path + "img/");
            } else if (f) {
                log.info("目录 [{}] 已存在或创建失败", path + "img/");
            } else if (i) {
                log.info("目录 [{}] 已存在或创建 失败", path + "file/");
            } else {
                log.info("目录已存在或者创建目录失败！");
            }
        } else {
            Exception e = new IllegalArgumentException("上传路径未正确设置！");
            log.error("上传路径未正确设置", e);
            throw e;
        }
    }
}
