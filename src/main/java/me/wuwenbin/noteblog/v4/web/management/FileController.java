package me.wuwenbin.noteblog.v4.web.management;

import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.pagination.model.layui.LayuiTable;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.valdiation.template.RTemplate;
import me.wuwenbin.noteblog.v4.model.XFile;
import me.wuwenbin.noteblog.v4.model.management.bo.FileQueryBo;
import me.wuwenbin.noteblog.v4.repository.FileRepository;
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
@RequestMapping("/management/file")
public class FileController extends BaseController {

    @Autowired
    private RTemplate rTemplate;
    @Autowired
    private FileRepository fileRepository;

    @RequestMapping
    public String file() {
        return "management/file";
    }

    @GetMapping("/list")
    @ResponseBody
    public LayuiTable<XFile> cateList(Page<XFile> filePage, FileQueryBo fileQueryBo) {
        filePage = fileRepository.findPagination(filePage, XFile.class, fileQueryBo);
        return layuiTable(filePage);
    }

    @PostMapping("/add")
    @ResponseBody
    public R add(@Valid XFile xFile, BindingResult result) {
        return validResponse(result, rTemplate,
                xFile, (file -> builder("新增资源").execLight(xFile, (obj) -> fileRepository.save(obj))));
    }

    @PostMapping("/delete")
    @ResponseBody
    public R delete(long id) {
        return builder("删除资源").execLight(id, (pk) -> fileRepository.delete(pk));
    }

    @PostMapping("/edit")
    @ResponseBody
    public R edit(@Valid XFile xFile, BindingResult result) {
        return validResponse(result, rTemplate, xFile,
                (file) -> builder("编辑资源").exec(() -> fileRepository.updateById(file) == 1));
    }
}
