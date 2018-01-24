package me.wuwenbin.blog.v201801.repository;

import me.wuwenbin.blog.v201801.model.TagRefer;
import me.wuwenbin.modules.repository.annotation.field.SQL;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IBaseCrudRepository;
import me.wuwenbin.modules.repository.provider.find.annotation.PrimitiveCollection;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * created by Wuwenbin on 2018/1/16 at 17:00
 */
@Repository
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public interface TagReferRepository extends IBaseCrudRepository<TagRefer, Long> {

    int countByTagId(long tagId);

    @SQL("SELECT tt.`name` FROM t_tag tt WHERE tt.id IN (SELECT tag_id FROM t_tag_refer WHERE refer_id = ?)")
    @PrimitiveCollection
    List<String> findNameByArticleId(long articleId);

    void deleteByReferId(long referId) throws Exception;

}
