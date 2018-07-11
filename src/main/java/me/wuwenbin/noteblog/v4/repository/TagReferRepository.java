package me.wuwenbin.noteblog.v4.repository;

import me.wuwenbin.modules.repository.annotation.field.SQL;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IBaseCrudRepository;
import me.wuwenbin.modules.repository.provider.find.annotation.ListMap;
import me.wuwenbin.modules.repository.provider.find.annotation.PrimitiveCollection;
import me.wuwenbin.noteblog.v4.model.TagRefer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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

    @SQL("SELECT tt.id,tt.name,count(ttr.tag_id) AS cnt FROM t_tag_refer ttr LEFT JOIN t_tag tt ON  tt.id = ttr.tag_id GROUP BY ttr.tag_id ORDER BY cnt DESC LIMIT 30")
    @ListMap
    List<Map<String, Object>> findTagsTab();
}
