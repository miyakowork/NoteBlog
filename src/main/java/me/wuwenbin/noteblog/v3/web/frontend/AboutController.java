package me.wuwenbin.noteblog.v3.web.frontend;

import me.wuwenbin.noteblog.v3.common.blog.BlogUtils;
import me.wuwenbin.noteblog.v3.model.XParam;
import me.wuwenbin.noteblog.v3.repository.AboutRepository;
import me.wuwenbin.noteblog.v3.repository.ParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * created by Wuwenbin on 2018/2/9 at 17:24
 */
@Controller
@RequestMapping("/profile")
public class AboutController {

    @Autowired
    private AboutRepository aboutRepository;
    @Autowired
    private ParamRepository paramRepository;

    @GetMapping
    public String index(Model model) {
        List<XParam> xParams = paramRepository.findAll();
        Map<String, Object> settings = BlogUtils.settingMap(xParams);
        model.addAttribute("settings", settings);
        model.addAttribute("abouts", aboutRepository.findAll());
        return "frontend/about";
    }
}
