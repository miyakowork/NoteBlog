package me.wuwenbin.noteblog.v4.web.management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * created by Wuwenbin on 2018/1/26 at 14:04
 */
@Controller
@RequestMapping("/management/noteblog")
public class NoteBlogController {

    @GetMapping
    public String index() {
        return "management/noteblog";
    }
}
