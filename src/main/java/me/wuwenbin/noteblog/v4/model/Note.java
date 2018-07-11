package me.wuwenbin.noteblog.v4.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.wuwenbin.modules.sql.annotation.GeneralType;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;
import me.wuwenbin.modules.sql.annotation.not.NotUpdate;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.time.LocalDateTime;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.time.LocalDateTime.now;
import static me.wuwenbin.noteblog.v4.model.Note.Router.update_show;

/**
 * created by Wuwenbin on 2018/1/17 at 20:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLTable("t_note")
public class Note implements Serializable {

    public interface Router {
        int update_show = 1;
    }

    @GeneralType
    @SQLColumn(pk = true)
    private Long id;
    @NotEmpty
    private String title;
    private String clearContent;
    @NotEmpty
    private String content;
    private Boolean top = FALSE;
    @SQLColumn(value = "`show`", routers = update_show)
    private Boolean show = TRUE;
    @NotUpdate
    private LocalDateTime post = now();
    private LocalDateTime modify;
}
