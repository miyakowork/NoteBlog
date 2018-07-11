package me.wuwenbin.noteblog.v4.web.frontend;

import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.utils.web.Controllers;
import me.wuwenbin.noteblog.v4.common.blog.BlogUtils;
import me.wuwenbin.noteblog.v4.model.Article;
import me.wuwenbin.noteblog.v4.model.XParam;
import me.wuwenbin.noteblog.v4.model.frontend.bo.CommentListBo;
import me.wuwenbin.noteblog.v4.model.frontend.vo.CommentVo;
import me.wuwenbin.noteblog.v4.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * created by Wuwenbin on 2018/2/6 at 14:28
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ParamRepository paramRepository;
    @Autowired
    private CateRepository cateRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/{id}")
    public String index(Model model, @PathVariable("id") Long id, Page<CommentVo> commentVoPage, CommentListBo commentListBo) {
        try {
            articleRepository.updateViewsById(id);
        } catch (Exception ignore) {
        }
        List<XParam> xParams = paramRepository.findAll();
        Map<String, Object> settings = BlogUtils.settingMap(xParams);
        Article article = articleRepository.findOne(id);
        model.addAttribute("settings", settings);
        model.addAttribute("cateList", cateRepository.findAll());
        model.addAttribute("article", article);
        model.addAttribute("tags", tagRepository.findArticleTags(article.getId(), true));
        model.addAttribute("author", userRepository.findNicknameById(article.getAuthorId()));
        model.addAttribute("articles", BlogUtils.toLowerKeyListMap(articleRepository.findSimilarArticles(article.getCateId(), 10)));
        model.addAttribute("similars", BlogUtils.toLowerKeyListMap(articleRepository.findSimilarArticles(article.getCateId(), 6)));
        model.addAttribute("comments", commentRepository.findPagination(commentVoPage, CommentVo.class, commentListBo));
        return "frontend/article";
    }

    @PostMapping("/comments")
    @ResponseBody
    public Page<CommentVo> comments(Page<CommentVo> commentVoPage, CommentListBo commentListBo) {
        return commentRepository.findPagination(commentVoPage, CommentVo.class, commentListBo);
    }

    @PostMapping("/approve")
    @ResponseBody
    public R approve(@RequestParam Long articleId) {
        return Controllers.builder("点赞").exec(() -> articleRepository.updateApproveCntById(articleId) == 1);
    }
}
