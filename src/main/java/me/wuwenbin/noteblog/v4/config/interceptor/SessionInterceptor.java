package me.wuwenbin.noteblog.v4.config.interceptor;

import jodd.json.JsonSerializer;
import lombok.extern.slf4j.Slf4j;
import me.wuwenbin.modules.repository.api.repository.RepositoryFactory;
import me.wuwenbin.modules.utils.http.CookieUtils;
import me.wuwenbin.modules.utils.http.WebUtils;
import me.wuwenbin.noteblog.v4.common.SessionParam;
import me.wuwenbin.noteblog.v4.common.blog.BlogContext;
import me.wuwenbin.noteblog.v4.model.User;
import me.wuwenbin.noteblog.v4.repository.UserRepository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static me.wuwenbin.modules.utils.http.R.error;
import static me.wuwenbin.modules.utils.security.Encrypt.base64;
import static me.wuwenbin.noteblog.v4.common.SessionParam.*;

/**
 * created by Wuwenbin on 2018/1/23 at 13:36
 */
@Slf4j
public class SessionInterceptor extends HandlerInterceptorAdapter {

    private UserRepository userRepository = RepositoryFactory.get(UserRepository.class);

    private BlogContext blogContext;

    public SessionInterceptor(BlogContext blogContext) {
        this.blogContext = blogContext;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie cookie = CookieUtils.getCookie(request, SessionParam.SESSION_ID_COOKIE);
        if (cookie != null) {
            String sessionId = cookie.getValue();
            User sessionUser = blogContext.getSessionUser(sessionId);
            if (sessionUser == null) {
                Cookie rememberCookie = CookieUtils.getCookie(request, REMEMBER_COOKIE_NAME);
                if (rememberCookie != null) {
                    String userString = rememberCookie.getValue();
                    try {
                        String username = userString.split(COOKIE_SPLIT)[0];
                        String password = userString.split(COOKIE_SPLIT)[1];
                        User cookieUser = userRepository.findByUsernameAndPasswordAndEnable(base64.decodeStr(username), password, true);
                        if (cookieUser != null) {
                            blogContext.setSessionUser(request, response, cookieUser);
                            log.info("[已登录用户]");
                            return true;
                        }
                    } catch (Exception ignore) {
                    }
                }
                log.info("[未登录用户]");
                if (WebUtils.isAjaxRequest(request)) {
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(JsonSerializer.create().serialize(error("用户未登录或登录时效过期，请重新登录！", LOGIN_URL)));
                } else {
                    response.sendRedirect(LOGIN_URL);
                }
                return false;
            } else {
                log.info("[已登录用户]");
                return true;
            }
        } else {
            response.sendRedirect(LOGIN_URL);
            return false;
        }
    }
}

