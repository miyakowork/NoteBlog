package me.wuwenbin.noteblog.v4.web.management;

import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.valdiation.template.RTemplate;
import me.wuwenbin.noteblog.v4.model.About;
import me.wuwenbin.noteblog.v4.repository.AboutRepository;
import me.wuwenbin.noteblog.v4.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

import static me.wuwenbin.modules.utils.web.Controllers.builder;
import static me.wuwenbin.modules.valdiation.assertion.Assert.check;

/**
 * created by Wuwenbin on 2018/1/20 at 16:33
 */
@Controller("managementAboutController")
@RequestMapping("/management/about")
public class AboutController extends BaseController {

    @Autowired
    private RTemplate rTemplate;
    @Autowired
    private AboutRepository aboutRepository;

    @GetMapping
    public String about(Model model) {
        model.addAttribute("me", aboutRepository.findByTab("about_me"));
        model.addAttribute("website", aboutRepository.findByTab("about_website"));
        model.addAttribute("blog", aboutRepository.findByTab("about_blog"));
        return "management/about";
    }

    @PostMapping("/post")
    @ResponseBody
    public R post(@Valid About about, BindingResult result) {
        if (!result.hasErrors()) {
            return validResponse(result, rTemplate, about,
                    editAbout -> builder("编辑关于内容").exec(() -> aboutRepository.updateByTab(editAbout) == 1));
        } else {
            return check(rTemplate, result);
        }
    }
}
