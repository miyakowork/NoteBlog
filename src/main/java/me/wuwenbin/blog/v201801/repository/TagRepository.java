package me.wuwenbin.blog.v201801.repository;

import me.wuwenbin.blog.v201801.model.Tag;
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
public interface TagRepository extends IPageAndSortRepository<Tag, Long> {

    int countByName(String name);

    @Primitive(Long.class)
    long findIdByName(String name);

    @Modify(Router.DEFAULT)
    int updateById(Tag tag) throws Exception;
}
