package me.wuwenbin.noteblog.v4.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.wuwenbin.modules.utils.lang.LangUtils;
import me.wuwenbin.noteblog.v4.common.TagType;
import me.wuwenbin.noteblog.v4.model.Note;
import me.wuwenbin.noteblog.v4.model.Tag;
import me.wuwenbin.noteblog.v4.model.TagRefer;
import me.wuwenbin.noteblog.v4.repository.NoteRepository;
import me.wuwenbin.noteblog.v4.repository.TagReferRepository;
import me.wuwenbin.noteblog.v4.repository.TagRepository;
import me.wuwenbin.noteblog.v4.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Arrays;

/**
 * created by Wuwenbin on 2018/1/17 at 20:56
 */
@Slf4j
@Service
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public class NoteServiceImpl implements NoteService {

    @Autowired
    private DataSourceTransactionManager tm;
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TagReferRepository tagReferRepository;

    @Override
    public boolean postNote(Note note, String tagNames) {
        if (LangUtils.string.isEmpty(tagNames)) {
            throw new RuntimeException("tagName不能为空！");
        }
        String[] tagNameArray = tagNames.split(",");
        DefaultTransactionDefinition def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = tm.getTransaction(def);
        try {
            note = noteRepository.save(note);
            if (note == null) {
                throw new RuntimeException("发布失败！");
            }
            saveNoteTag(note, tagNameArray);
            tm.commit(status);
            return true;
        } catch (Exception e) {
            tm.rollback(status);
            log.error("发布笔记出错，回滚数据...", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateNote(Note note, String tagNames) {
        String[] tagNameArray = tagNames.split(",");
        DefaultTransactionDefinition def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = tm.getTransaction(def);
        try {
            if (noteRepository.updateById(note) == 1) {
                tagReferRepository.deleteByReferId(note.getId());
                saveNoteTag(note, tagNameArray);
                tm.commit(status);
                return true;
            } else return false;
        } catch (Exception e) {
            tm.rollback(status);
            log.error("发布笔记出错，回滚数据...", e);
            throw new RuntimeException(e);
        }
    }

    private void saveNoteTag(Note note, String[] tagNameArray) throws Exception {
        for (String name : Arrays.asList(tagNameArray)) {
            long tagId;
            if (tagRepository.countByName(name) == 0) {
                tagId = tagRepository.save(Tag.builder().name(name).build()).getId();
            } else {
                tagId = tagRepository.findIdByName(name);
            }
            tagReferRepository.save(TagRefer.builder().referId(note.getId()).tagId(tagId).show(false).type(TagType.note.name()).build());
        }
    }
}
