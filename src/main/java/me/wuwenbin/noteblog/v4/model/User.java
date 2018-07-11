package me.wuwenbin.noteblog.v4.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.wuwenbin.modules.sql.annotation.GeneralType;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;

import java.io.Serializable;
import java.time.LocalDateTime;

import static java.lang.Boolean.TRUE;
import static java.time.LocalDateTime.now;

/**
 * created by Wuwenbin on 2018/1/13 at 21:07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLTable("t_user")
public class User implements Serializable {

    @GeneralType
    @SQLColumn(pk = true)
    private Long id;
    private String username;
    private String nickname;
    private String avatar;
    private String password;
    @Builder.Default
    @SQLColumn("`create`")
    private LocalDateTime create = now();
    private String qqNum;
    @Builder.Default
    private Long defaultRoleId = 2L;
    @Builder.Default
    private Boolean enable = TRUE;
    private String openId;

}
