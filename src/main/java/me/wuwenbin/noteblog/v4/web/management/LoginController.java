package me.wuwenbin.noteblog.v4.web.management;

import me.wuwenbin.modules.utils.http.CookieUtils;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.utils.security.Encrypt;
import me.wuwenbin.noteblog.v4.common.SessionParam;
import me.wuwenbin.noteblog.v4.common.blog.BlogContext;
import me.wuwenbin.noteblog.v4.model.User;
import me.wuwenbin.noteblog.v4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static me.wuwenbin.modules.utils.http.R.error;
import static me.wuwenbin.modules.utils.http.R.ok;
import static me.wuwenbin.modules.utils.security.Encrypt.base64;

/**
 * created by Wuwenbin on 2018/1/14 at 12:47
 */
@Controller
public class LoginController {

    @Autowired
    private BlogContext blogContext;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login(@CookieValue(value = SessionParam.SESSION_ID_COOKIE, required = false) String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            return "management/login";
        }
        User u = blogContext.getSessionUser(uuid);
        if (u != null && u.getDefaultRoleId() == 1) {
            return "management/index";
        }
        return "management/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public R login(HttpServletRequest request, HttpServletResponse response, String bmyName, String bmyPass, Boolean remember) {
        String md52Pass = Encrypt.digest.md5Hex(bmyPass);
        User findUser = userRepository.findByUsernameAndPasswordAndEnable(bmyName, md52Pass, true);
        if (findUser != null) {
            if (remember != null && remember.equals(Boolean.TRUE)) {
                String cookieValue = base64.encode(findUser.getUsername()).concat(SessionParam.COOKIE_SPLIT).concat(findUser.getPassword());
                CookieUtils.setCookie(response, SessionParam.REMEMBER_COOKIE_NAME, cookieValue, 15 * 24 * 60 * 60);
            }
            blogContext.setSessionUser(request, response, findUser);
            return ok("登陆成功！", SessionParam.MANAGEMENT_INDEX);
        } else {
            return error("用户名或密码错误！");
        }
    }
}
