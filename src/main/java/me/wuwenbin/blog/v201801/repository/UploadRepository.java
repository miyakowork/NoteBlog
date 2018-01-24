package me.wuwenbin.blog.v201801.repository;

import me.wuwenbin.blog.v201801.model.Upload;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by Wuwenbin on 2018/1/17 at 20:11
 */
@Repository
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public interface UploadRepository extends IPageAndSortRepository<Upload,Long>{
}
