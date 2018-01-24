package me.wuwenbin.blog.v201801.repository;

import me.wuwenbin.blog.v201801.model.Param;
import me.wuwenbin.modules.repository.annotation.field.SQL;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.modules.repository.provider.find.annotation.ListMap;
import me.wuwenbin.modules.repository.provider.find.annotation.Primitive;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * created by Wuwenbin on 2018/1/17 at 11:34
 */
@Repository
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public interface ParamRepository extends IPageAndSortRepository<Param, Long> {

    @Primitive
    String findValueByName(String name);

    int updateValueByName(Param param);
}
