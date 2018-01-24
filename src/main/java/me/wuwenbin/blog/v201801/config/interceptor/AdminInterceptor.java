package me.wuwenbin.blog.v201801.config.interceptor;

import jodd.json.JsonSerializer;
import me.wuwenbin.blog.v201801.common.SessionParam;
import me.wuwenbin.blog.v201801.model.User;
import me.wuwenbin.modules.utils.http.WebUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static me.wuwenbin.modules.utils.http.R.error;

/**
 * created by Wuwenbin on 2018/1/23 at 13:41
 */
public class AdminInterceptor extends HandlerInterceptorAdapter {

    private static final int SA = 1;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute(SessionParam.LOGIN_USER);
        if (sessionUser == null) {
            if (WebUtils.isAjaxRequest(request)) {
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
}
