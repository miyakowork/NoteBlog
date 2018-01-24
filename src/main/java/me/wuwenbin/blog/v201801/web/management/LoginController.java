package me.wuwenbin.blog.v201801.web.management;

import me.wuwenbin.blog.v201801.common.SessionParam;
import me.wuwenbin.blog.v201801.model.User;
import me.wuwenbin.blog.v201801.repository.UserRepository;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.utils.security.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import static me.wuwenbin.modules.utils.http.R.error;
import static me.wuwenbin.modules.utils.http.R.ok;

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
        return "management/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public R login(String bmyName, String bmyPass) {
        String md52Pass = Encrypt.digest.md5Hex(bmyPass);
        User findUser = userRepository.findByUsernameAndPasswordAndEnable(bmyName, md52Pass, true);
        if (findUser != null) {
            request.getSession().setAttribute(SessionParam.LOGIN_USER, findUser);
            request.getSession().setMaxInactiveInterval(30 * 60);
            return ok("登陆成功！", SessionParam.MANAGEMENT_INDEX);
        } else {
            return error("用户名或密码错误！");
        }
    }
}
