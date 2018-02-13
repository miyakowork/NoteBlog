package me.wuwenbin.noteblog.v3.model.frontend.bo;

import lombok.Getter;
import me.wuwenbin.modules.pagination.query.model.BasicTableQuery;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryColumn;
import me.wuwenbin.modules.utils.http.Url;
import me.wuwenbin.modules.utils.web.Injection;

/**
 * created by Wuwenbin on 2018/2/9 at 14:16
 */
@Getter
public class NoteQueryBo extends BasicTableQuery {


    @QueryColumn(column = "title")
    private String t;
    @QueryColumn(column = "clear_content", constraint = "OR")
    private String cc;

    public void setT(String t) {
        t = Url.urlDecode(t, "UTF-8");
        t = Injection.stripSqlXSS(t);
        this.t = t;
    }

    public void setCc(String cc) {
        cc = Url.urlDecode(cc, "UTF-8");
        cc = Injection.stripSqlXSS(cc);
        this.cc = cc;
    }

}