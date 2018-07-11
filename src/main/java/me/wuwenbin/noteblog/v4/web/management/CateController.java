package me.wuwenbin.noteblog.v4.web.management;

import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.pagination.model.layui.LayuiTable;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.valdiation.template.RTemplate;
import me.wuwenbin.noteblog.v4.model.Cate;
import me.wuwenbin.noteblog.v4.model.management.bo.CateQueryBo;
import me.wuwenbin.noteblog.v4.repository.ArticleRepository;
import me.wuwenbin.noteblog.v4.repository.CateRepository;
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
 * created by Wuwenbin on 2018/1/14 at 15:21
 */
@Controller
@RequestMapping("/management/cate")
public class CateController extends BaseController {

    @Autowired
    private RTemplate rTemplate;
    @Autowired
    private CateRepository cateRepository;
    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping
    public String cate() {
        return "management/cate";
    }

    @GetMapping("/list")
    @ResponseBody
    public LayuiTable<Cate> cateList(Page<Cate> catePage, CateQueryBo cateQueryBo) {
        catePage = cateRepository.findPagination(catePage, Cate.class, cateQueryBo);
        return layuiTable(catePage);
    }

    @PostMapping("/add")
    @ResponseBody
    public R add(@Valid Cate cate, BindingResult result) {
        return validResponse(result, rTemplate, cate,
                (category) -> builder("新增分类").execLight(
                        () -> cateRepository.countByName(cate.getName()) == 0
                        , category, (obj) -> cateRepository.save(obj)
                        , "分类名[" + cate.getName() + "]已存在！"));
    }

    @PostMapping("/delete")
    @ResponseBody
    public R delete(long id) {
        return builder("删除分类").execLight(
                () -> articleRepository.countByCateId(id) == 0
                , id, (pk) -> cateRepository.delete(pk), "请先删除分类下的相关文章！");
    }

    @PostMapping("/edit")
    @ResponseBody
    public R edit(@Valid Cate cate, BindingResult result) {
        return validResponse(result, rTemplate, cate,
                (category) -> builder("编辑分类")
                        .exec(() -> cateRepository.updateById(category) == 1));
    }
}
