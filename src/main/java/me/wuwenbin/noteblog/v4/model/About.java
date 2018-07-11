package me.wuwenbin.noteblog.v4.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.wuwenbin.modules.sql.annotation.GeneralType;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;
import me.wuwenbin.modules.sql.annotation.not.NotInsert;
import me.wuwenbin.modules.sql.annotation.not.NotUpdate;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * created by Wuwenbin on 2018/1/20 at 16:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLTable("t_about")
public class About implements Serializable {

    @GeneralType
    @SQLColumn(pk = true)
    @NotUpdate
    private Long id;
    @NotUpdate
    @NotInsert
    private String tab;
    @NotUpdate
    @NotInsert
    private String name;
    @NotEmpty
    private String content;
}
