package me.wuwenbin.noteblog.v4.model.management.vo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.repository.annotation.sql.SQLPkRefer;
import me.wuwenbin.noteblog.v4.model.Article;
import me.wuwenbin.noteblog.v4.model.Comment;
import me.wuwenbin.noteblog.v4.model.User;

/**
 * created by Wuwenbin on 2018/1/25 at 14:25
 */
@Getter
@Setter
public class CommentVo extends Comment {

    @SQLPkRefer(targetClass = User.class, targetColumn = "nickname", joinColumn = "user_id")
    private String nickname;
    @SQLPkRefer(targetClass = Article.class, targetColumn = "title", joinColumn = "article_id")
    private String articleTitle;
}
