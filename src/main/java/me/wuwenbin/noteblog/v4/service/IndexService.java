package me.wuwenbin.noteblog.v4.service;

import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.pagination.query.model.layui.LayTableQuery;
import me.wuwenbin.noteblog.v4.model.frontend.vo.ArticleVo;
import me.wuwenbin.noteblog.v4.model.frontend.vo.IndexArticleVo;
import me.wuwenbin.noteblog.v4.model.frontend.vo.IndexVo;

/**
 * created by Wuwenbin on 2018/1/31 at 19:03
 */
public interface IndexService {

    IndexVo findIndexVo();

    IndexArticleVo next(Page<ArticleVo> nextPage, LayTableQuery layTableQuery);
}
