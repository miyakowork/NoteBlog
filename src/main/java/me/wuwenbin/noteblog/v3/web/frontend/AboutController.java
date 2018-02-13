package me.wuwenbin.noteblog.v3.web.frontend;

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

import static java.util.stream.Collectors.toMap;

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
        Map<String, Object> settings = xParams.stream()
                .filter(xParam -> !xParam.getName().equals("app_id") && !xParam.getName().equals("app_key"))
                .collect(toMap(XParam::getName, XParam::getValue));
        model.addAttribute("settings", settings);
        model.addAttribute("abouts", aboutRepository.findAll());
        return "frontend/about";
    }
}
