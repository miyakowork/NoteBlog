package me.wuwenbin.noteblog.v4.model.management.bo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.pagination.query.model.layui.LayTableQuery;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryColumn;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryTable;
import me.wuwenbin.modules.pagination.query.support.operator.Operator;

/**
 * created by Wuwenbin on 2018/1/23 at 18:02
 */
@Getter
@Setter
@QueryTable(name = "t_user")
public class UserQueryBo extends LayTableQuery {

    @QueryColumn(operator = Operator.EQ)
    private Long defaultRoleId;

    private String nickname;

}
