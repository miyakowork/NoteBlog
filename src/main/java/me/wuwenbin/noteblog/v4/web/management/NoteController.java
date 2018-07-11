package me.wuwenbin.noteblog.v4.web.management;

import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.pagination.model.layui.LayuiTable;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.utils.http.WebUtils;
import me.wuwenbin.modules.valdiation.template.RTemplate;
import me.wuwenbin.noteblog.v4.model.Note;
import me.wuwenbin.noteblog.v4.model.management.bo.NoteQueryBo;
import me.wuwenbin.noteblog.v4.repository.NoteRepository;
import me.wuwenbin.noteblog.v4.repository.TagReferRepository;
import me.wuwenbin.noteblog.v4.service.NoteService;
import me.wuwenbin.noteblog.v4.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

import static java.time.LocalDateTime.now;
import static me.wuwenbin.modules.utils.web.Controllers.builder;
import static me.wuwenbin.modules.valdiation.assertion.Assert.check;

/**
 * created by Wuwenbin on 2018/1/17 at 20:44
 */
@Controller("managementNoteController")
@RequestMapping("/management/note")
public class NoteController extends BaseController {

    @Autowired
    private RTemplate rTemplate;
    @Autowired
    private NoteService noteService;
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private TagReferRepository tagReferRepository;

    @GetMapping
    public String note() {
        return "management/note";
    }

    @GetMapping("/index")
    public String index() {
        return "management/notes";
    }

    @PostMapping("/list")
    @ResponseBody
    public LayuiTable<Note> index(Page<Note> notePage, NoteQueryBo noteBo) {
        notePage = noteRepository.findPagination(notePage, Note.class, noteBo);
        return layuiTable(notePage);
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("editNote", noteRepository.findOne(id));
        String[] tagArray = tagReferRepository.findNameByArticleId(id).toArray(new String[]{});
        String tags = Arrays.toString(tagArray);
        model.addAttribute("noteTags", tags.substring(1, tags.length() - 1));
        return "management/note_edit";
    }

    @PostMapping("/doEdit")
    @ResponseBody
    public R doEdit(@Valid Note note, String tagNames, BindingResult result) {
        note.setModify(now());
        note.setClearContent(WebUtils.filterHtml(note.getContent()));
        return validResponse(result, rTemplate, note,
                editNote -> builder("编辑笔记").exec(() -> noteService.updateNote(editNote, tagNames)));
    }

    @PostMapping("/edit/show/{id}")
    @ResponseBody
    public R show(@PathVariable("id") Long id, Boolean show) {
        return builder("修改显示状态").exec(() -> noteRepository.updateById(show, id) == 1);
    }

    @PostMapping("/edit/top/{id}")
    @ResponseBody
    public R top(@PathVariable("id") Long id, Boolean top) {
        return builder("修改置顶状态").exec(() -> noteRepository.updateTopById(top, id) == 1);
    }


    @PostMapping("/delete/{id}")
    @ResponseBody
    public R delete(@PathVariable("id") Long id) {
        return builder("删除笔记").execLight(id, noteId -> noteRepository.delete(noteId));
    }


    @PostMapping("/post")
    @ResponseBody
    public R post(@Valid Note note, String tagName, BindingResult result) {
        if (!result.hasErrors()) {
            note.setClearContent(WebUtils.filterHtml(note.getContent()));
            return builder("发布笔记").exec(() -> noteService.postNote(note, tagName));
        } else {
            return check(rTemplate, result);
        }
    }

}
