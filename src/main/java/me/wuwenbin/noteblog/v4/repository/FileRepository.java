package me.wuwenbin.noteblog.v4.repository;

import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IBaseCrudRepository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.modules.repository.provider.update.annotation.Modify;
import me.wuwenbin.modules.sql.constant.Router;
import me.wuwenbin.noteblog.v4.model.XFile;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by Wuwenbin on 2018/2/11 at 14:26
 */
@Repository
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public interface FileRepository extends IPageAndSortRepository<XFile, Long>, IBaseCrudRepository<XFile, Long> {

    @Modify(Router.DEFAULT)
    int updateById(XFile file) throws Exception;
}
