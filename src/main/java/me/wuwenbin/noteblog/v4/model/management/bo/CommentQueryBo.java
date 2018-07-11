package me.wuwenbin.noteblog.v4.model.management.bo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.pagination.query.model.layui.LayTableQuery;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryColumn;

/**
 * created by Wuwenbin on 2018/1/25 at 14:30
 */
@Getter
@Setter
public class CommentQueryBo extends LayTableQuery {

    @QueryColumn(column = "title", tableName = "t_article")
    private String articleTitle;

    @QueryColumn(tableName = "t_user")
    private String nickname;

    @QueryColumn(tableName = "t_comment")
    private String comment;
}
