package me.wuwenbin.noteblog.v4.web.management;

import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.pagination.model.layui.LayuiTable;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.valdiation.template.RTemplate;
import me.wuwenbin.noteblog.v4.model.Tag;
import me.wuwenbin.noteblog.v4.model.management.bo.TagQueryBo;
import me.wuwenbin.noteblog.v4.repository.TagReferRepository;
import me.wuwenbin.noteblog.v4.repository.TagRepository;
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
@RequestMapping("/management/tag")
public class TagController extends BaseController {

    @Autowired
    private RTemplate rTemplate;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TagReferRepository tagReferRepository;

    @RequestMapping
    public String cate() {
        return "management/tag";
    }

    @GetMapping("/list")
    @ResponseBody
    public LayuiTable<Tag> cateList(Page<Tag> tagPage, TagQueryBo tagQueryBo) {
        tagPage = tagRepository.findPagination(tagPage, Tag.class, tagQueryBo);
        return layuiTable(tagPage);
    }

    @PostMapping("/edit")
    @ResponseBody
    public R edit(@Valid Tag tag, BindingResult result) {
        return validResponse(result, rTemplate, tag,
                (editTag) -> builder("编辑标签")
                        .exec(() -> tagRepository.updateById(editTag) == 1));
    }

    @PostMapping("/delete")
    @ResponseBody
    public R delete(long id) {
        return builder("删除标签").execLight(
                () -> tagReferRepository.countByTagId(id) == 0
                , id, (delTagId) -> tagRepository.delete(delTagId)
                , "请删除标签相关文章/笔记后再删除！");
    }

}
