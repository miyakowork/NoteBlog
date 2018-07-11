package me.wuwenbin.noteblog.v4.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.wuwenbin.modules.sql.annotation.GeneralType;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

import static java.lang.Boolean.TRUE;

/**
 * created by Wuwenbin on 2018/1/22 at 11:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLTable("t_keyword")
public class Keyword implements Serializable {

    @GeneralType
    @SQLColumn(pk = true)
    private Long id;
    @NotEmpty
    private String words;
    private Boolean enable = TRUE;
}
