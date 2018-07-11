package me.wuwenbin.noteblog.v4.web.frontend;

import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.noteblog.v4.common.blog.BlogUtils;
import me.wuwenbin.noteblog.v4.model.XParam;
import me.wuwenbin.noteblog.v4.model.frontend.vo.SearchPageVo;
import me.wuwenbin.noteblog.v4.repository.ParamRepository;
import me.wuwenbin.noteblog.v4.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * created by Wuwenbin on 2018/2/11 at 15:40
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;
    @Autowired
    private ParamRepository paramRepository;

    @GetMapping
    public String index(Page<SearchPageVo> searchPage, String sp, Model model) {
        searchPage = searchService.findPagination(searchPage, sp);
        List<XParam> xParams = paramRepository.findAll();
        Map<String, Object> settings = BlogUtils.settingMap(xParams);
        long articleCount = searchPage.getTResult().stream().filter(obj -> obj.getResType().equals("article")).count();
        long noteCount = searchPage.getTResult().stream().filter(obj -> obj.getResType().equals("note")).count();
        long fileCount = searchPage.getTResult().stream().filter(obj -> obj.getResType().equals("file")).count();
        model.addAttribute("settings", settings);
        model.addAttribute("page", searchPage);
        model.addAttribute("ac", articleCount);
        model.addAttribute("nc", noteCount);
        model.addAttribute("fc", fileCount);
        return "frontend/search";
    }

}
