package me.wuwenbin.noteblog.v4.model.management.bo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.pagination.query.model.layui.LayTableQuery;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryTable;

/**
 * created by Wuwenbin on 2018/1/22 at 11:10
 */
@Getter
@Setter
@QueryTable(name = "t_keyword")
public class KeywordQueryBo extends LayTableQuery {

    private String words;
}
