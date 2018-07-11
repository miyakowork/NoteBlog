package me.wuwenbin.noteblog.v4.web.frontend;

import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.noteblog.v4.common.SessionParam;
import me.wuwenbin.noteblog.v4.common.blog.BlogContext;
import me.wuwenbin.noteblog.v4.model.User;
import me.wuwenbin.noteblog.v4.repository.ParamRepository;
import me.wuwenbin.noteblog.v4.service.QqLoginService;
import me.wuwenbin.noteblog.v4.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static me.wuwenbin.noteblog.v4.common.ParamKey.APP_ID;

/**
 * created by Wuwenbin on 2018/1/22 at 20:53
 */
@Controller
@RequestMapping("/api")
public class QqLoginController extends BaseController {

    @Autowired
    private BlogContext blogContext;
    @Autowired
    private ParamRepository paramRepository;
    @Autowired
    private QqLoginService qqLoginService;

    @GetMapping("/qq")
    public String qqLogin(HttpServletRequest request) {
        String callbackDomain = basePath(request).concat("api/qqCallback");
        String appId = paramRepository.findValueByName(APP_ID);
        return "redirect:https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=" + appId + "&redirect_uri=" + callbackDomain + "&state=" + System.currentTimeMillis();
    }

    @GetMapping("/qqCallback")
    public String qqCallback(HttpServletRequest request, HttpServletResponse response, String code) {
        String callbackDomain = basePath(request).concat("api/qqCallback");
        R r = qqLoginService.login(callbackDomain, code);
        if (r.get(R.CODE).equals(R.SUCCESS)) {
            blogContext.setSessionUser(request, response, (User) r.get(SessionParam.LOGIN_USER));
            return "redirect:" + r.get(R.DATA);
        } else {
            return "error/page";
        }
    }

}
