package me.wuwenbin.noteblog.v4.model.management.bo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.pagination.query.model.layui.LayTableQuery;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryTable;

/**
 * created by Wuwenbin on 2018/1/18 at 11:32
 */
@Setter
@Getter
@QueryTable(name = "t_article")
public class ArticleQueryBo extends LayTableQuery {

    private String title;
}
