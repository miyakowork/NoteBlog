package me.wuwenbin.noteblog.v4.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.wuwenbin.modules.sql.annotation.GeneralType;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;
import me.wuwenbin.modules.sql.annotation.not.NotInsert;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

import static java.lang.Boolean.TRUE;
import static java.time.LocalDateTime.now;

/**
 * created by Wuwenbin on 2018/1/25 at 14:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLTable("t_comment")
public class Comment implements Serializable {

    @GeneralType
    @SQLColumn(pk = true)
    @NotInsert
    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private Long articleId;
    @NotEmpty
    private String comment;
    private LocalDateTime post = now();
    private String ipAddr;
    private String ipCnAddr;
    private String userAgent;
    private Boolean enable = TRUE;
}
