package me.wuwenbin.blog.v201801.model.vo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.blog.v201801.model.Article;
import me.wuwenbin.blog.v201801.model.Cate;
import me.wuwenbin.modules.repository.annotation.sql.SQLPkRefer;

/**
 * created by Wuwenbin on 2018/1/18 at 11:58
 */
@Getter
@Setter
public class ArticleVo extends Article {

    @SQLPkRefer(targetClass = Cate.class, targetColumn = "cn_name", joinColumn = "cate_id")
    private String cateName;
}
