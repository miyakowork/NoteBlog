package me.wuwenbin.noteblog.v4.repository;

import me.wuwenbin.modules.repository.annotation.field.SQL;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IBaseCrudRepository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.modules.repository.provider.find.annotation.ListMap;
import me.wuwenbin.modules.repository.provider.find.annotation.Primitive;
import me.wuwenbin.modules.repository.provider.save.annotation.SaveSQL;
import me.wuwenbin.modules.repository.provider.update.annotation.Modify;
import me.wuwenbin.modules.sql.constant.Router;
import me.wuwenbin.noteblog.v4.model.Article;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * created by Wuwenbin on 2018/1/15 at 12:17
 */
@Repository
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public interface ArticleRepository extends IPageAndSortRepository<Article, Long>, IBaseCrudRepository<Article, Long> {

    int countByCateId(long cateId);

    @Modify(Router.DEFAULT)
    int updateById(Article article) throws Exception;

    int updateAppreciableById(boolean appreciable, long id) throws Exception;

    int updateCommentedById(boolean commented, long id) throws Exception;

    @SQL("SELECT max(top) FROM t_article")
    @Primitive(Integer.class)
    Integer findMaxTop();

    @Primitive
    Integer findTopById(long articleId);

    @SQL("UPDATE t_article SET top = top - 1 WHERE top > ?")
    int updateTopsByTop(int currentTop) throws Exception;

    int updateTopById(int top, long articleId) throws Exception;

    @SQL("SELECT * FROM t_article WHERE draft = 0 ORDER BY post DESC LIMIT 1")
    Article findLatestArticle();

    @SaveSQL(columns = {"id", "title", "cate_id", "content"})
    int saveSimpleArticle(Article article) throws Exception;

    /**
     * 数据不会太多，此处直接用rand()随机
     *
     * @return
     */
    @ListMap
    @SQL("SELECT id,title FROM t_article ORDER BY rand() LIMIT ?")
    List<Map<String, Object>> findRandomArticles(int limit);

    @ListMap
    @SQL("SELECT id,title FROM t_article WHERE cate_id = ? ORDER BY rand() LIMIT ?")
    List<Map<String, Object>> findSimilarArticles(long cateId, int limit);

    @SQL("UPDATE t_article SET approve_cnt = approve_cnt + 1 WHERE id = ?")
    int updateApproveCntById(long articleId) throws Exception;

    @SQL("UPDATE t_article SET views = views + 1 WHERE id = ?")
    int updateViewsById(long articleId) throws Exception;
}
