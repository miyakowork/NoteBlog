package me.wuwenbin.noteblog.v4.web.frontend;

import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.utils.http.WebUtils;
import me.wuwenbin.modules.utils.lang.LangUtils;
import me.wuwenbin.modules.utils.web.Injection;
import me.wuwenbin.noteblog.v4.common.blog.BlogUtils;
import me.wuwenbin.noteblog.v4.model.Keyword;
import me.wuwenbin.noteblog.v4.model.Message;
import me.wuwenbin.noteblog.v4.model.XParam;
import me.wuwenbin.noteblog.v4.model.frontend.bo.MessageListBo;
import me.wuwenbin.noteblog.v4.model.frontend.vo.MessageVo;
import me.wuwenbin.noteblog.v4.repository.*;
import me.wuwenbin.noteblog.v4.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static me.wuwenbin.modules.utils.http.R.error;
import static me.wuwenbin.modules.utils.web.Controllers.builder;

/**
 * created by Wuwenbin on 2018/2/8 at 18:54
 */
@Controller
public class MessageController extends BaseController {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private KeywordRepository keywordRepository;
    @Autowired
    private ParamRepository paramRepository;
    @Autowired
    private CateRepository cateRepository;
    @Autowired
    private TagReferRepository tagReferRepository;

    @GetMapping("/message")
    public String index(Model model, Page<MessageVo> messageVoPage, MessageListBo messageListBo) {
        List<XParam> xParams = paramRepository.findAll();
        Map<String, Object> settings = BlogUtils.settingMap(xParams);
        model.addAttribute("settings", settings);
        model.addAttribute("cates", cateRepository.findAll());
        model.addAttribute("tags", BlogUtils.toLowerKeyListMap(tagReferRepository.findTagsTab()));
        model.addAttribute("messages", messageRepository.findPagination(messageVoPage, MessageVo.class, messageListBo));
        return "frontend/message";
    }


    @PostMapping("/token/message/sub")
    @ResponseBody
    public R sub(@Valid Message message, BindingResult bindingResult, HttpServletRequest request) {
        if (!bindingResult.hasErrors()) {
            message.setIpAddr(WebUtils.getRemoteAddr(request));
            message.setIpCnAddr(BlogUtils.getIpCnInfo(BlogUtils.getIpInfo(message.getIpAddr())));
            message.setUserAgent(request.getHeader("user-agent"));
            message.setComment(Injection.stripSqlXSS(message.getComment()));
            List<Keyword> keywords = keywordRepository.findAll();
            keywords.forEach(kw -> message.setComment(message.getComment().replace(kw.getWords(), LangUtils.string.repeat("*", kw.getWords().length()))));
            return builder("发表留言成功", "发表留言失败", "发表留言失败").exec(() -> messageRepository.save(message) != null);
        } else {
            return error("提交的留言内容不合法");
        }
    }


    @PostMapping("/message/lists")
    @ResponseBody
    public Page<MessageVo> comments(Page<MessageVo> messageVoPage, MessageListBo messageListBo) {
        return messageRepository.findPagination(messageVoPage, MessageVo.class, messageListBo);
    }
}
