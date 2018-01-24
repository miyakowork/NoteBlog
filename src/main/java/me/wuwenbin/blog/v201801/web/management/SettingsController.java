package me.wuwenbin.blog.v201801.web.management;

import me.wuwenbin.blog.v201801.model.Param;
import me.wuwenbin.blog.v201801.repository.ParamRepository;
import me.wuwenbin.blog.v201801.web.BaseController;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.valdiation.template.RTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static me.wuwenbin.modules.utils.web.Controllers.builder;
import static me.wuwenbin.modules.valdiation.assertion.Assert.check;

/**
 * created by Wuwenbin on 2018/1/24 at 13:58
 */
@Controller
@RequestMapping("/management/settings")
public class SettingsController extends BaseController {

    @Autowired
    private RTemplate rTemplate;
    @Autowired
    private ParamRepository paramRepository;

    @GetMapping
    public String index(Model model) {
        List<Param> params = paramRepository.findAll();
        Map<String, Object> attributeMap = params.stream().collect(Collectors.toMap(Param::getName, Param::getValue));
        model.addAllAttributes(attributeMap);
        return "management/settings";
    }

    @PostMapping("/edit")
    public R editSettings(@Valid Param param, BindingResult result) {
        if (!result.hasErrors()) {
            return validResponse(result, rTemplate, param,
                    editParam -> builder("修改").exec(() -> paramRepository.updateValueByName(editParam) == 1));
        } else {
            return check(rTemplate, result);
        }
    }
}
