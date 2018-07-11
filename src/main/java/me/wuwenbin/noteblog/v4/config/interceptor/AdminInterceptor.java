package me.wuwenbin.noteblog.v4.config.interceptor;

import jodd.json.JsonSerializer;
import me.wuwenbin.modules.utils.http.CookieUtils;
import me.wuwenbin.modules.utils.http.WebUtils;
import me.wuwenbin.noteblog.v4.common.SessionParam;
import me.wuwenbin.noteblog.v4.common.blog.BlogContext;
import me.wuwenbin.noteblog.v4.model.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static me.wuwenbin.modules.utils.http.R.error;

/**
 * created by Wuwenbin on 2018/1/23 at 13:41
 */
public class AdminInterceptor extends HandlerInterceptorAdapter {

    private static final int SA = 1;

    private BlogContext blogContext;

    public AdminInterceptor(BlogContext blogContext) {
        this.blogContext = blogContext;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie cookie = CookieUtils.getCookie(request, SessionParam.SESSION_ID_COOKIE);
        if (cookie != null) {
            String sessionId = cookie.getValue();
            User sessionUser = blogContext.getSessionUser(sessionId);
            if (sessionUser == null) {
                if (WebUtils.isAjaxRequest(request)) {
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(JsonSerializer.create().serialize(error("用户未登录或登录时效过期，请重新登录！", SessionParam.LOGIN_URL)));
                } else {
                    response.sendRedirect(SessionParam.LOGIN_URL);
                }
                return false;
            } else if (sessionUser.getDefaultRoleId() == SA) {
                return true;
            } else {
                if (WebUtils.isAjaxRequest(request)) {
                    response.getWriter().write(JsonSerializer.create().serialize(error("非法访问，即将跳转首页！", "/")));
                } else {
                    response.sendRedirect("/");
                }
                return false;
            }
        }
        return false;
    }
}
