package me.wuwenbin.blog.v201801.web.management;

import me.wuwenbin.blog.v201801.common.SessionParam;
import me.wuwenbin.blog.v201801.model.Article;
import me.wuwenbin.blog.v201801.model.Note;
import me.wuwenbin.blog.v201801.model.User;
import me.wuwenbin.blog.v201801.repository.ArticleRepository;
import me.wuwenbin.blog.v201801.repository.CateRepository;
import me.wuwenbin.blog.v201801.repository.CommentRepository;
import me.wuwenbin.blog.v201801.repository.NoteRepository;
import me.wuwenbin.blog.v201801.web.BaseController;
import me.wuwenbin.modules.utils.http.CookieUtils;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.valdiation.template.RTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static me.wuwenbin.modules.utils.http.R.error;
import static me.wuwenbin.modules.utils.web.Controllers.builder;

/**
 * created by Wuwenbin on 2018/1/14 at 14:38
 */
@Controller("managementIndexController")
@RequestMapping("/management")
public class IndexController extends BaseController {

    @Autowired
    private RTemplate rTemplate;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CateRepository cateRepository;

    @GetMapping("/index")
    public String index(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute(SessionParam.LOGIN_USER);
        model.addAttribute("avatar", user.getAvatar());
        model.addAttribute("nickname", user.getNickname());
        return "management/index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("articleCnt", articleRepository.count());
        model.addAttribute("noteCnt", noteRepository.count());
        model.addAttribute("commentCnt", commentRepository.count());
        model.addAttribute("latestArticle", articleRepository.findLatestArticle());
        model.addAttribute("latestNote", noteRepository.findLatestNote());
        model.addAttribute("latestComment", commentRepository.findLatestComment());
        model.addAttribute("cateList", cateRepository.findAll());
        return "management/home";
    }

    @PostMapping("/simple/post/article")
    @ResponseBody
    public R simplePostArticle(@Valid Article article, BindingResult result) {
        if (article.getContent().length() > 300) {
            return error("草稿字数不宜过多！");
        }
        article.setId(System.currentTimeMillis() / 1000);
        article.setSummary(article.getContent());
        return validResponse(result, rTemplate, article,
                simpleArticle -> builder("发布文章草稿").exec(() -> articleRepository.saveSimpleArticle(simpleArticle) == 1));
    }

    @PostMapping("/simple/post/note")
    @ResponseBody
    public R simplePostNote(@Valid Note note, BindingResult result) {
        if (note.getContent().length() > 300) {
            return error("字数不宜过多！");
        }
        return validResponse(result, rTemplate, note,
                simpleNote -> builder("发布随笔").exec(() -> noteRepository.saveSimpleNote(simpleNote) == 1));
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, String from) {
        request.getSession().removeAttribute(SessionParam.LOGIN_USER);
        request.getSession().invalidate();
        CookieUtils.deleteCookie(request, response, SessionParam.COOKIE_NAME);
        if (StringUtils.isEmpty(from)) {
            return "redirect:/";
        } else {
            return "redirect:" + SessionParam.MANAGEMENT_INDEX;
        }
    }
}
