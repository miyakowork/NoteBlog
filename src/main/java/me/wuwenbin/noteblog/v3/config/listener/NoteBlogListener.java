package me.wuwenbin.noteblog.v3.config.listener;

import lombok.extern.slf4j.Slf4j;
import me.wuwenbin.modules.utils.lang.LangUtils;
import me.wuwenbin.modules.utils.security.Encrypt;
import me.wuwenbin.noteblog.v3.model.User;
import me.wuwenbin.noteblog.v3.repository.ParamRepository;
import me.wuwenbin.noteblog.v3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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

    private void init() throws Exception {
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

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            if (!paramRepository.findValueByName("is_set_master").equals("1"))
                init();
            log.info("初始化设置管理员账号：[{}]，密码：[{}]", masterName, masterPass);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("初始化设置失败！", e);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
