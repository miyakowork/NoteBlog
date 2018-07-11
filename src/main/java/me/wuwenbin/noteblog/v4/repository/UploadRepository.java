package me.wuwenbin.noteblog.v4.repository;

import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IBaseCrudRepository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.noteblog.v4.model.Upload;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by Wuwenbin on 2018/1/17 at 20:11
 */
@Repository
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public interface UploadRepository extends IPageAndSortRepository<Upload, Long>, IBaseCrudRepository<Upload, Long> {
}
