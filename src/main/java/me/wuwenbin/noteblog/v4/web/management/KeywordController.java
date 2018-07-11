package me.wuwenbin.noteblog.v4.web.management;

import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.pagination.model.layui.LayuiTable;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.valdiation.template.RTemplate;
import me.wuwenbin.noteblog.v4.model.Keyword;
import me.wuwenbin.noteblog.v4.model.management.bo.KeywordQueryBo;
import me.wuwenbin.noteblog.v4.repository.KeywordRepository;
import me.wuwenbin.noteblog.v4.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

import static me.wuwenbin.modules.utils.web.Controllers.builder;

/**
 * created by Wuwenbin on 2018/1/22 at 11:12
 */
@Controller
@RequestMapping("/management/keyword")
public class KeywordController extends BaseController {

    @Autowired
    private RTemplate rTemplate;
    @Autowired
    private KeywordRepository keywordRepository;

    @GetMapping
    public String index() {
        return "management/keyword";
    }

    @GetMapping("/list")
    @ResponseBody
    public LayuiTable<Keyword> cateList(Page<Keyword> keywordPage, KeywordQueryBo keywordQueryBo) {
        keywordPage = keywordRepository.findPagination(keywordPage, Keyword.class, keywordQueryBo);
        return layuiTable(keywordPage);
    }

    @PostMapping("/add")
    @ResponseBody
    public R add(@Valid Keyword keyword, BindingResult result) {
        return validResponse(result, rTemplate, keyword,
                (kw) -> builder("新增分类").execLight(
                        () -> keywordRepository.countByWords(keyword.getWords()) == 0
                        , kw, (obj) -> keywordRepository.save(obj)
                        , "关键字[" + keyword.getWords() + "]已存在！"));
    }

    @PostMapping("/delete")
    @ResponseBody
    public R delete(long id) {
        return builder("删除分类").execLight(id, (delId) -> keywordRepository.delete(delId));
    }

    @PostMapping("/edit/words")
    @ResponseBody
    public R editWords(@Valid Keyword keyword, BindingResult result) {
        return validResponse(result, rTemplate, keyword,
                (kw) -> builder("编辑关键字")
                        .exec(() -> keywordRepository.updateWordsById(kw) == 1));
    }

    @PostMapping("/edit/enable")
    @ResponseBody
    public R editEnable(Keyword keyword) {
        return builder("编辑关键字状态")
                .exec(() -> keywordRepository.updateEnableById(keyword) == 1);
    }
}
