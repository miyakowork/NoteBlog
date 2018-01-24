package me.wuwenbin.blog.v201801.repository;

import me.wuwenbin.blog.v201801.model.Keyword;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.modules.repository.provider.update.annotation.Modify;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by Wuwenbin on 2018/1/22 at 11:11
 */
@Repository
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public interface KeywordRepository extends IPageAndSortRepository<Keyword, Long> {

    int countByWords(String words);

    int updateWordsById(Keyword keyword) throws Exception;

    int updateEnableById(Keyword keyword)throws Exception;
}
