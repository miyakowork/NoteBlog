package me.wuwenbin.blog.v201801.repository;

import me.wuwenbin.blog.v201801.model.Note;
import me.wuwenbin.modules.repository.annotation.field.SQL;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.modules.repository.provider.save.annotation.SaveSQL;
import me.wuwenbin.modules.repository.provider.update.annotation.Modify;
import org.springframework.transaction.annotation.Transactional;

import static me.wuwenbin.modules.sql.constant.Router.DEFAULT;

/**
 * created by Wuwenbin on 2018/1/17 at 20:29
 */
@Repository
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public interface NoteRepository extends IPageAndSortRepository<Note, Long> {

    @Modify(DEFAULT)
    int updateById(Note note) throws Exception;

    @Modify(Note.Router.update_show)
    int updateById(Boolean show, Long id) throws Exception;

    @SQL("SELECT * FROM t_note ORDER BY post DESC LIMIT 1")
    Note findLatestNote();

    @SaveSQL(columns = {"title", "content"})
    int saveSimpleNote(Note note) throws Exception;
}
