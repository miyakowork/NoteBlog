package me.wuwenbin.noteblog.v4.model.management.bo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.pagination.query.model.layui.LayTableQuery;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryTable;

/**
 * created by Wuwenbin on 2018/1/19 at 18:56
 */
@Setter
@Getter
@QueryTable(name = "t_note")
public class NoteQueryBo extends LayTableQuery {

    private String title;

}
