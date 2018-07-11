package me.wuwenbin.noteblog.v4.config;

import lombok.extern.slf4j.Slf4j;
import me.wuwenbin.modules.utils.lang.LangUtils;
import me.wuwenbin.noteblog.v4.common.blog.BlogContext;
import me.wuwenbin.noteblog.v4.common.blog.BlogSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Enumeration;

/**
 * created by Wuwenbin on 2018/2/7 at 22:40
 */
@Slf4j
@Component
@EnableScheduling
public class SessionConfig {

    @Autowired
    private BlogContext blogContext;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void sessionValidate() {
        log.info("validate session for task...");
        Enumeration<String> keys = blogContext.keys();
        while (keys.hasMoreElements()) {
            String uuid = keys.nextElement();
            BlogSession blogSession = blogContext.get(uuid);
            if (blogSession != null) {
                if (blogSession.isExpired()) {
                    String info = "delete session for id:[{}], at [{}]";
                    log.info(LangUtils.string.placeholder(info, blogSession.getId(), LocalDateTime.now()));
                    blogContext.remove(uuid);
                }
            }
        }
    }

}
