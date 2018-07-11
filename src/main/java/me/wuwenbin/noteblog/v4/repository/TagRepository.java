package me.wuwenbin.noteblog.v4.repository;

import me.wuwenbin.modules.repository.annotation.field.SQL;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IBaseCrudRepository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.modules.repository.provider.find.annotation.Primitive;
import me.wuwenbin.modules.repository.provider.update.annotation.Modify;
import me.wuwenbin.modules.sql.constant.Router;
import me.wuwenbin.noteblog.v4.model.Tag;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * created by Wuwenbin on 2018/1/15 at 12:17
 */
@Repository
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public interface TagRepository extends IPageAndSortRepository<Tag, Long>, IBaseCrudRepository<Tag, Long> {

    int countByName(String name);

    @Primitive(Long.class)
    long findIdByName(String name);

    @Modify(Router.DEFAULT)
    int updateById(Tag tag) throws Exception;

    @SQL("SELECT * FROM t_tag WHERE  id IN (SELECT tag_id FROM t_tag_refer WHERE refer_id =? AND `show`= ? AND type = 'article')")
    List<Tag> findArticleTags(long referId, boolean show);

}
