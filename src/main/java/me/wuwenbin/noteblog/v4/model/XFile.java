package me.wuwenbin.noteblog.v4.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.wuwenbin.modules.sql.annotation.GeneralType;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;
import me.wuwenbin.modules.sql.annotation.not.NotInsert;
import me.wuwenbin.modules.sql.annotation.not.NotUpdate;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

/**
 * created by Wuwenbin on 2018/2/11 at 14:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLTable("t_file")
public class XFile implements Serializable {

    @GeneralType
    @SQLColumn(pk = true)
    @NotInsert
    @NotUpdate
    public Long id;
    @NotNull
    private String name;
    @NotNull
    private String url;
    private LocalDateTime post = now();
}
