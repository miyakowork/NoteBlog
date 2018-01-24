package me.wuwenbin.blog.v201801.web.management;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * created by Wuwenbin on 2018/1/14 at 14:38
 */
@Controller("managementIndexController")
@RequestMapping("/management")
public class IndexController {

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("", "");
        return "management/index";
    }

}
