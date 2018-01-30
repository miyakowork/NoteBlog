package me.wuwenbin.blog.v201801.web.management;

import me.wuwenbin.blog.v201801.common.SessionParam;
import me.wuwenbin.blog.v201801.model.User;
import me.wuwenbin.blog.v201801.repository.UserRepository;
import me.wuwenbin.blog.v201801.service.UploadService;
import me.wuwenbin.blog.v201801.service.support.LayUpload;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.utils.security.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * created by Wuwenbin on 2018/1/27 at 19:53
 */
@Controller
@RequestMapping("/management/profile")
public class ProfileController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UploadService uploadService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("loginUser", request.getSession().getAttribute(SessionParam.LOGIN_USER));
        return "management/profile";
    }

    @PostMapping("/edit")
    @ResponseBody
    public R edit(String nickname, String password1, String password2) {
        String message = "";
        if (!StringUtils.isEmpty(nickname)) {
            User loginUser = (User) request.getSession().getAttribute(SessionParam.LOGIN_USER);
            message += userRepository.updateAdminNickname(nickname, loginUser.getUsername()) == 1 ? "修改昵称成功" : "修改昵称失败";
        }
        if (!StringUtils.isEmpty(password1)) {
            if (password1.equals(password2)) {
                String dbPass = Encrypt.digest.md5Hex(password1);
                User loginUser = (User) request.getSession().getAttribute(SessionParam.LOGIN_USER);
                message += userRepository.updateAdminPass(dbPass, loginUser.getUsername()) == 1 ? "，修改密码成功" : "修改密码失败";
            }
        }
        return R.ok(message + "，重新登陆后生效！");
    }

    @PostMapping("/upload")
    @ResponseBody
    public Map<String, Object> upload(@RequestParam(value = "file", required = false) MultipartFile file) {
        if (file != null) {
            User u = (User) request.getSession().getAttribute(SessionParam.LOGIN_USER);
            if (u != null && u.getDefaultRoleId() == 1) {
                return uploadService.uploadAvatar(file, u.getUsername());
            } else {
                return LayUpload.err("非法操作！");
            }
        } else {
            return LayUpload.err("二维码不能为空！");
        }
    }
}
