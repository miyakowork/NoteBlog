package me.wuwenbin.blog.v201801.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.wuwenbin.modules.sql.annotation.GeneralType;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    private Long id;
    private Long userId;
    private Long articleId;
    private String comment;
    private LocalDateTime post;
    private String ipAddr;
    private String ipCnAddr;
    private String userAgent;
    private Boolean enable;
}
