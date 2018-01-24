package me.wuwenbin.blog.v201801.repository;

import me.wuwenbin.blog.v201801.model.Cate;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.modules.repository.provider.update.annotation.Modify;
import me.wuwenbin.modules.sql.constant.Router;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by Wuwenbin on 2018/1/14 at 15:37
 */
@Repository
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public interface CateRepository extends IPageAndSortRepository<Cate, Long> {

    @Modify(Router.DEFAULT)
    int updateById(Cate cate);

    int countByName(String name);
}
