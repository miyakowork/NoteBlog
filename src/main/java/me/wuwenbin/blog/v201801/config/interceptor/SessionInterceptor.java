package me.wuwenbin.blog.v201801.config.interceptor;

import jodd.json.JsonSerializer;
import lombok.extern.slf4j.Slf4j;
import me.wuwenbin.blog.v201801.model.User;
import me.wuwenbin.blog.v201801.repository.UserRepository;
import me.wuwenbin.modules.repository.api.repository.RepositoryFactory;
import me.wuwenbin.modules.utils.http.CookieUtils;
import me.wuwenbin.modules.utils.http.WebUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static me.wuwenbin.blog.v201801.common.SessionParam.*;
import static me.wuwenbin.modules.utils.http.R.error;
import static me.wuwenbin.modules.utils.security.Encrypt.base64;

/**
 * created by Wuwenbin on 2018/1/23 at 13:36
 */
@Slf4j
public class SessionInterceptor extends HandlerInterceptorAdapter {

    private UserRepository userRepository = RepositoryFactory.get(UserRepository.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute(LOGIN_USER);
        if (sessionUser == null) {
            Cookie cookie = CookieUtils.getCookie(request, COOKIE_NAME);
            if (cookie != null) {
                String userString = cookie.getValue();
                try {
                    String username = userString.split(COOKIE_SPLIT)[0];
                    String password = userString.split(COOKIE_SPLIT)[1];
                    User cookieUser = userRepository.findByUsernameAndPasswordAndEnable(base64.decodeStr(username), password, true);
                    if (cookieUser != null) {
                        session.setAttribute(LOGIN_USER, cookieUser);
                        session.setMaxInactiveInterval(30 * 60);
                        log.info("[已登录用户]");
                        return true;
                    }
                } catch (Exception ignore) {
                }
            }
            log.info("[未登录用户]");
            if (WebUtils.isAjaxRequest(request)) {
                response.getWriter().write(JsonSerializer.create().serialize(error("用户未登录或登录时效过期，请重新登录！", LOGIN_URL)));
            } else {
                response.sendRedirect(LOGIN_URL);
            }
            return false;
        } else {
            log.info("[已登录用户]");
            return true;
        }
    }
}
