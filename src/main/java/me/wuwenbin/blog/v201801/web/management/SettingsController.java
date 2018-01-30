package me.wuwenbin.blog.v201801.web.management;

import me.wuwenbin.blog.v201801.common.ParamKey;
import me.wuwenbin.blog.v201801.model.XParam;
import me.wuwenbin.blog.v201801.repository.ParamRepository;
import me.wuwenbin.blog.v201801.service.UploadService;
import me.wuwenbin.blog.v201801.service.support.LayUpload;
import me.wuwenbin.blog.v201801.web.BaseController;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.valdiation.template.RTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private UploadService uploadService;

    @GetMapping
    public String index(Model model) {
        List<XParam> params = paramRepository.findAll();
        Map<String, Object> attributeMap = params.stream().collect(Collectors.toMap(XParam::getName, XParam::getValue));
        model.addAllAttributes(attributeMap);
        return "management/settings";
    }

    @GetMapping("/qrcode")
    public String qrcode(Model model) {
        model.addAttribute("alipay", paramRepository.findValueByName(ParamKey.ALIPAY));
        model.addAttribute("wechat", paramRepository.findValueByName(ParamKey.WECHAT_PAY));
        return "management/qrcode";
    }

    @PostMapping("/edit")
    @ResponseBody
    public R editSettings(@Valid XParam xParam, BindingResult result) {
        if (!result.hasErrors()) {
            return validResponse(result, rTemplate, xParam,
                    editParam -> builder("修改").exec(() -> paramRepository.updateValueByName(editParam) == 1));
        } else {
            return check(rTemplate, result);
        }
    }


    @PostMapping("/upload")
    @ResponseBody
    public Map<String, Object> upload(@RequestParam(value = "file", required = false) MultipartFile file, String payType) {
        if (file != null) {
            return uploadService.uploadQrCode(file, payType);
        } else {
            return LayUpload.err("二维码不能为空！");
        }
    }
}
