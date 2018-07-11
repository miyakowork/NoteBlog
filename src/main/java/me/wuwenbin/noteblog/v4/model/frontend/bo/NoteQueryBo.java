package me.wuwenbin.noteblog.v4.model.frontend.bo;

import lombok.Getter;
import me.wuwenbin.modules.pagination.query.model.BasicTableQuery;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryColumn;
import me.wuwenbin.modules.pagination.sort.Sorting;
import me.wuwenbin.modules.pagination.sort.direction.Direction;
import me.wuwenbin.modules.utils.http.Url;
import me.wuwenbin.modules.utils.web.Injection;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Sorting> getSortingInformation() {
        String multiSorts = "top,desc;post,desc";
        String[] sorts = multiSorts.split(";");
        List<Sorting> multiSortList = new ArrayList<>(sorts.length);
        for (String sort : sorts) {
            Sorting ms = new Sorting();
            String[] singleSort = sort.split(",");
            ms.setSortName(singleSort[0]);
            ms.setSortDirection(Direction.getDirectionByString(singleSort[1]));
            multiSortList.add(ms);
        }
        return multiSortList;
    }

    @Override
    public boolean isSupportMultiSort() {
        return true;
    }

}