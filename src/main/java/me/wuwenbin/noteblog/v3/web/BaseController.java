package me.wuwenbin.noteblog.v3.web;

import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.pagination.model.layui.LayuiTable;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.valdiation.template.Template;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Function;

import static me.wuwenbin.modules.valdiation.assertion.Assert.check;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * created by Wuwenbin on 2018/1/15 at 11:42
 */
public abstract class BaseController {

    //TODO:替换注释内容
    protected static String basePath(HttpServletRequest request) {
//        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        return "http://wx.wuwenbin.me/";
    }

    protected static <T> LayuiTable<T> layuiTable(Page<T> page) {
        return new LayuiTable<>(page.getTotalCount(), page.getTResult());
    }

    protected static <T> R validResponse(BindingResult result, Template<R> template, T t, Function<T, R> operation) {
        if (!result.hasErrors()) {
            return operation.apply(t);
        } else {
            return check(template, result);
        }
    }

    protected boolean isAjax(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equalsIgnoreCase(header);
    }

    protected boolean isJson(HttpServletRequest request) {
        String headerAccept = request.getHeader("Accept");
        return !isEmpty(headerAccept) && headerAccept.contains("application/json");
    }

    protected boolean isRouter(HttpServletRequest request) {
        String headerAccept = request.getHeader("Accept");
        return !isEmpty(headerAccept) && headerAccept.contains("text/html") && !isJson(request) && isAjax(request) && isGet(request);
    }

    protected boolean isGet(HttpServletRequest request) {
        String method = request.getMethod();
        return "GET".equalsIgnoreCase(method);
    }

}
