package me.wuwenbin.noteblog.v4.model.management.vo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.repository.annotation.sql.SQLPkRefer;
import me.wuwenbin.noteblog.v4.model.Article;
import me.wuwenbin.noteblog.v4.model.Cate;

/**
 * created by Wuwenbin on 2018/1/18 at 11:58
 */
@Getter
@Setter
public class ArticleVo extends Article {

    @SQLPkRefer(targetClass = Cate.class, targetColumn = "cn_name", joinColumn = "cate_id")
    private String cateName;
}
