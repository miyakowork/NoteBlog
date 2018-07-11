package me.wuwenbin.noteblog.v4.config.interceptor;

import me.wuwenbin.modules.utils.http.CookieUtils;
import me.wuwenbin.noteblog.v4.common.SessionParam;
import me.wuwenbin.noteblog.v4.common.blog.BlogContext;
import me.wuwenbin.noteblog.v4.common.blog.BlogSession;
import me.wuwenbin.noteblog.v4.common.blog.BlogUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * created by Wuwenbin on 2018/1/23 at 13:41
 */

public class ValidateInterceptor extends HandlerInterceptorAdapter {
    private BlogContext blogContext;

    public ValidateInterceptor(BlogContext blogContext) {
        this.blogContext = blogContext;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        Cookie cookie = CookieUtils.getCookie(request, SessionParam.SESSION_ID_COOKIE);
        if (cookie != null) {
            String sessionId = cookie.getValue();
            BlogSession blogSession = blogContext.get(sessionId);
            if (blogSession != null) {
                blogSession.update();
                if (modelAndView != null)
                    modelAndView.getModelMap().addAttribute("su", BlogUtils.toMap(blogSession.getSessionUser()));
            }
        }

    }
}
