package me.wuwenbin.noteblog.v4.model.frontend.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.wuwenbin.modules.pagination.query.model.layui.LayTableQuery;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryColumn;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryTable;
import me.wuwenbin.modules.pagination.sort.Sorting;
import me.wuwenbin.modules.pagination.sort.direction.Direction;
import me.wuwenbin.modules.utils.http.Url;
import me.wuwenbin.modules.utils.web.Injection;

import java.util.ArrayList;
import java.util.List;

import static me.wuwenbin.modules.pagination.query.support.operator.Operator.EQ;

/**
 * 先按置顶排序再按时间排序
 * created by Wuwenbin on 2018/2/4 at 14:14
 */
@ToString
@QueryTable(name = "t_article")
public class IndexArticleBo extends LayTableQuery {

    @Getter
    private String title;
    @QueryColumn(constraint = "OR")
    @Getter
    private String textContent;
    @Getter
    @Setter
    @QueryColumn(operator = EQ)
    private Long cateId;
    @Getter
    @QueryColumn(column = "text_content")
    private String tagSearch;


    public void setTitle(String title) {
        title = Url.urlDecode(title, "UTF-8");
        this.title = Injection.stripSqlXSS(title);
    }

    public void setTextContent(String textContent) {
        textContent = Url.urlDecode(textContent, "UTF-8");
        this.textContent = Injection.stripSqlXSS(textContent);
    }

    public void setTagSearch(String tagSearch) {
        tagSearch = Url.urlDecode(tagSearch, "UTF-8");
        this.tagSearch = tagSearch;
    }

    @Override
    public List<Sorting> getSortingInformation() {
        String multiSorts = "top,desc;post,desc";
        String[] sorts = multiSorts.split(";");
        List<Sorting> multiSortList = new ArrayList<>(sorts.length);
        for (String sort : sorts) {
            String[] singleSort = sort.split(",");
            Sorting ms = new Sorting();
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
