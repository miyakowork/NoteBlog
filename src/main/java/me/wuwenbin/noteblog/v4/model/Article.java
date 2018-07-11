package me.wuwenbin.noteblog.v4.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.wuwenbin.modules.sql.annotation.GeneralType;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;
import me.wuwenbin.modules.sql.annotation.not.NotUpdate;
import me.wuwenbin.modules.sql.annotation.support.PkGenType;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.time.LocalDateTime.now;
import static me.wuwenbin.noteblog.v4.model.Article.Router.Article_Pk;

/**
 * created by Wuwenbin on 2018/1/15 at 12:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLTable("t_article")
public class Article implements Serializable {

    interface Router {
        int Article_Pk = 1;
    }

    @GeneralType(PkGenType.DEFINITION)
    @SQLColumn(pk = true, routers = Article_Pk)
    @NotUpdate
    private Long id;
    @NotEmpty
    private String title;
    @NotNull
    private Long cateId;
    private String cover;
    private String summary;
    @NotEmpty
    private String content;
    @NotNull
    private String textContent = "";
    private Long authorId;
    @NotUpdate
    private LocalDateTime post = now();
    private LocalDateTime modify;
    private Integer views = 0;
    private Integer approveCnt = 0;
    private Boolean commented = FALSE;
    private Boolean appreciable = FALSE;
    private Boolean draft = TRUE;
    private Integer top = 0;
}
