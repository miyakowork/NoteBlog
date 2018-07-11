package me.wuwenbin.noteblog.v4.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.wuwenbin.modules.sql.annotation.GeneralType;
import me.wuwenbin.modules.sql.annotation.SQLPk;
import me.wuwenbin.modules.sql.annotation.SQLTable;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

import static java.lang.Boolean.TRUE;
import static java.time.LocalDateTime.now;

/**
 * created by Wuwenbin on 2018/2/26 at 12:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLTable("t_message")
public class Message implements Serializable {

    @GeneralType
    @SQLPk
    private Long id;
    @NotNull
    private Long userId;
    private String ipCnAddr;
    private String ipAddr;
    @NotEmpty
    private String comment;
    private LocalDateTime post = now();
    private String userAgent;
    private Boolean enable = TRUE;
}
