package me.wuwenbin.noteblog.v4.web.management;

import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.pagination.model.layui.LayuiTable;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.noteblog.v4.model.management.bo.MessageQueryBo;
import me.wuwenbin.noteblog.v4.model.management.vo.MessageVo;
import me.wuwenbin.noteblog.v4.repository.MessageRepository;
import me.wuwenbin.noteblog.v4.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static me.wuwenbin.modules.utils.web.Controllers.builder;

/**
 * created by Wuwenbin on 2018/1/25 at 14:28
 */
@Controller("managementMessageController")
@RequestMapping("/management/message")
public class MessageController extends BaseController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping
    public String index() {
        return "management/message";
    }

    @PostMapping("/list")
    @ResponseBody
    public LayuiTable<MessageVo> page(Page<MessageVo> messageVoPage, MessageQueryBo messageQueryBo) {
        messageVoPage = messageRepository.findPagination(messageVoPage, MessageVo.class, messageQueryBo);
        return layuiTable(messageVoPage);
    }

    @PostMapping("/edit/enable")
    @ResponseBody
    public R editEnable(Long id, Boolean enable) {
        return builder("修改留言状态").exec(() -> messageRepository.updateEnableById(enable, id) == 1);
    }

}
