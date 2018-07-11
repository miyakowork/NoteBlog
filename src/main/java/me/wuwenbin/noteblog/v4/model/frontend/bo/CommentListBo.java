package me.wuwenbin.noteblog.v4.model.frontend.bo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.pagination.query.model.BasicTableQuery;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryColumn;
import me.wuwenbin.modules.pagination.query.support.operator.Operator;
import me.wuwenbin.modules.pagination.sort.Sorting;

/**
 * created by Wuwenbin on 2018/2/8 at 17:08
 */
@Getter
@Setter
public class CommentListBo extends BasicTableQuery {

    @QueryColumn(column = "article_id", operator = Operator.EQ)
    private Long id;

    @Override
    public Sorting getSortingInfo() {
        return Sorting.desc("post");
    }
}
