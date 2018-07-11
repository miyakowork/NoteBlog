package me.wuwenbin.noteblog.v4.model.management.bo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.pagination.query.model.layui.LayTableQuery;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryTable;

/**
 * created by Wuwenbin on 2018/1/14 at 20:59
 */
@Setter
@Getter
@QueryTable(name = "t_cate")
public class CateQueryBo extends LayTableQuery {

    private String cnName;
}
