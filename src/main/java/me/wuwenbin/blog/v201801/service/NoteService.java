package me.wuwenbin.blog.v201801.service;

import me.wuwenbin.blog.v201801.model.Note;

/**
 * created by Wuwenbin on 2018/1/17 at 20:56
 */
public interface NoteService {

    boolean postNote(Note note, String tagNames) throws Exception;

    boolean updateNote(Note note, String tagNames) throws Exception;
}
