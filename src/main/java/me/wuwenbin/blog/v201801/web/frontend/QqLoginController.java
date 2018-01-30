package me.wuwenbin.blog.v201801.web.frontend;

import me.wuwenbin.blog.v201801.repository.ParamRepository;
import me.wuwenbin.blog.v201801.service.QqLoginService;
import me.wuwenbin.blog.v201801.web.BaseController;
import me.wuwenbin.modules.utils.http.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static me.wuwenbin.blog.v201801.common.ParamKey.APP_ID;

/**
 * created by Wuwenbin on 2018/1/22 at 20:53
 */
@Controller
@RequestMapping("/api")
public class QqLoginController extends BaseController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ParamRepository paramRepository;
    @Autowired
    private QqLoginService qqLoginService;

    @GetMapping("/qq")
    public String qqLogin() {
        String callbackDomain = basePath(request).concat("api/qqCallback");
        String appId = paramRepository.findValueByName(APP_ID);
        return "redirect:https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=" + appId + "&redirect_uri=" + callbackDomain + "&state=" + System.currentTimeMillis();
    }

    @GetMapping("/qqCallback")
    public String qqCallback(String code) {
        String callbackDomain = basePath(request).concat("api/qqCallback");
        R r = qqLoginService.login(callbackDomain, code);
        if (r.get(R.CODE).equals(R.SUCCESS)) {
            return "redirect:" + r.get(R.DATA);
        } else {
            return "error/page";
        }
    }

}
