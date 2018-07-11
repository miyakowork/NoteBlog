package me.wuwenbin.noteblog.v4.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.wuwenbin.modules.sql.annotation.GeneralType;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;
import me.wuwenbin.modules.sql.annotation.not.NotUpdate;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * created by Wuwenbin on 2018/1/14 at 15:26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLTable("t_cate")
public class Cate implements Serializable {

    @GeneralType
    @SQLColumn(pk = true)
    @NotUpdate
    private Long id;
    @NotEmpty
    private String name;
    @NotNull
    private String cnName;

}
