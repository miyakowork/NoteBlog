package me.wuwenbin.noteblog.v3.service;

import me.wuwenbin.noteblog.v3.model.Note;

/**
 * created by Wuwenbin on 2018/1/17 at 20:56
 */
public interface NoteService {

    boolean postNote(Note note, String tagNames) throws Exception;

    boolean updateNote(Note note, String tagNames) throws Exception;
}
