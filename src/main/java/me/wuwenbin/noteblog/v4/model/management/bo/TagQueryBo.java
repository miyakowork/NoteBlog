package me.wuwenbin.noteblog.v4.model.management.bo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.pagination.query.model.layui.LayTableQuery;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryTable;

/**
 * created by Wuwenbin on 2018/1/21 at 11:29
 */
@Getter
@Setter
@QueryTable(name = "t_tag")
public class TagQueryBo extends LayTableQuery {

    private String name;
}
