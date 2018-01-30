package me.wuwenbin.blog.v201801.web.management;

import me.wuwenbin.blog.v201801.common.SessionParam;
import me.wuwenbin.blog.v201801.model.User;
import me.wuwenbin.blog.v201801.repository.UserRepository;
import me.wuwenbin.modules.utils.http.CookieUtils;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.utils.security.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static me.wuwenbin.blog.v201801.common.SessionParam.COOKIE_NAME;
import static me.wuwenbin.blog.v201801.common.SessionParam.COOKIE_SPLIT;
import static me.wuwenbin.modules.utils.http.R.error;
import static me.wuwenbin.modules.utils.http.R.ok;
import static me.wuwenbin.modules.utils.security.Encrypt.base64;

/**
 * created by Wuwenbin on 2018/1/14 at 12:47
 */
@Controller
public class LoginController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        User u = (User) request.getSession().getAttribute(SessionParam.LOGIN_USER);
        if (u != null && u.getDefaultRoleId() == 1) {
            return "management/index";
        }
        return "management/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public R login(HttpServletResponse response, String bmyName, String bmyPass, Boolean remember) {
        String md52Pass = Encrypt.digest.md5Hex(bmyPass);
        User findUser = userRepository.findByUsernameAndPasswordAndEnable(bmyName, md52Pass, true);
        if (findUser != null) {
            if (remember != null && remember.equals(Boolean.TRUE)) {
                String cookieValue = base64.encode(findUser.getUsername()).concat(COOKIE_SPLIT).concat(findUser.getPassword());
                CookieUtils.setCookie(response, COOKIE_NAME, cookieValue, 15 * 24 * 60 * 60);
            }
            request.getSession().setAttribute(SessionParam.LOGIN_USER, findUser);
            request.getSession().setMaxInactiveInterval(30 * 60);
            return ok("登陆成功！", SessionParam.MANAGEMENT_INDEX);
        } else {
            return error("用户名或密码错误！");
        }
    }
}
