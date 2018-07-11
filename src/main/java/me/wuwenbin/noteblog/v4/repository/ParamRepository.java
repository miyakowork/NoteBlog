package me.wuwenbin.noteblog.v4.repository;

import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IBaseCrudRepository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.modules.repository.provider.find.annotation.Primitive;
import me.wuwenbin.noteblog.v4.model.XParam;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by Wuwenbin on 2018/1/17 at 11:34
 */
@Repository
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public interface ParamRepository extends IPageAndSortRepository<XParam, Long>, IBaseCrudRepository<XParam, Long> {

    @Primitive
    String findValueByName(String name);

    int updateValueByName(XParam param);
}
