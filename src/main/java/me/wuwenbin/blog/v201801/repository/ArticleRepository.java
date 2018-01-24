package me.wuwenbin.blog.v201801.repository;

import me.wuwenbin.blog.v201801.model.Article;
import me.wuwenbin.modules.repository.annotation.field.SQL;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.modules.repository.provider.find.annotation.Primitive;
import me.wuwenbin.modules.repository.provider.update.annotation.Modify;
import me.wuwenbin.modules.sql.constant.Router;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by Wuwenbin on 2018/1/15 at 12:17
 */
@Repository
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public interface ArticleRepository extends IPageAndSortRepository<Article, Long> {

    int countByCateId(long cateId);

    @Modify(Router.DEFAULT)
    int updateById(Article article) throws Exception;

    int updateAppreciableById(boolean appreciable, long id) throws Exception;

    int updateCommentedById(boolean commented, long id) throws Exception;

    @SQL("SELECT max(top) FROM t_article")
    @Primitive(Integer.class)
    Integer findMaxTopById();

    int updateTopById(int top, long articleId) throws Exception;
}
