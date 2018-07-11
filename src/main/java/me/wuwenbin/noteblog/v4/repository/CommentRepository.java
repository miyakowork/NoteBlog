package me.wuwenbin.noteblog.v4.repository;

import me.wuwenbin.modules.repository.annotation.field.SQL;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IBaseCrudRepository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.noteblog.v4.model.Comment;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * created by Wuwenbin on 2018/1/25 at 14:28
 */
@Repository
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public interface CommentRepository extends IPageAndSortRepository<Comment, Long>, IBaseCrudRepository<Comment, Long> {

    int updateEnableById(boolean enable, long id) throws Exception;

    @SQL("SELECT tc.*,tu.nickname,ta.title FROM t_comment tc,t_user tu,t_article ta " +
            "WHERE tu.id=tc.user_id AND ta.id=tc.article_id ORDER BY tc.post DESC LIMIT 1")
    Map<String, Object> findLatestComment();

}
