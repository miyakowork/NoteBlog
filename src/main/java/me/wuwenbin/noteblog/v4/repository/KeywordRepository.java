package me.wuwenbin.noteblog.v4.repository;

import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IBaseCrudRepository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.noteblog.v4.model.Keyword;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by Wuwenbin on 2018/1/22 at 11:11
 */
@Repository
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public interface KeywordRepository extends IPageAndSortRepository<Keyword, Long>, IBaseCrudRepository<Keyword, Long> {

    int countByWords(String words);

    int updateWordsById(Keyword keyword) throws Exception;

    int updateEnableById(Keyword keyword) throws Exception;
}
