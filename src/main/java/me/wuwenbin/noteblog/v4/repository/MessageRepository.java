package me.wuwenbin.noteblog.v4.repository;

import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IBaseCrudRepository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.noteblog.v4.model.Message;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by Wuwenbin on 2018/1/25 at 14:28
 */
@Repository
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public interface MessageRepository extends IPageAndSortRepository<Message, Long>, IBaseCrudRepository<Message, Long> {

    int updateEnableById(boolean enable, long id) throws Exception;

}
