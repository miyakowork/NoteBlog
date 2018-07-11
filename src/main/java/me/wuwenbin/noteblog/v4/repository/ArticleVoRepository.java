package me.wuwenbin.noteblog.v4.repository;

import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IBaseCrudRepository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.noteblog.v4.model.frontend.vo.ArticleVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by Wuwenbin on 2018/1/16 at 17:00
 */
@Repository
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public interface ArticleVoRepository extends IPageAndSortRepository<ArticleVo, Long>, IBaseCrudRepository<ArticleVo, Long> {
}
