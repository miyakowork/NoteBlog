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

import java.io.Serializable;

/**
 * created by Wuwenbin on 2018/1/17 at 11:32
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLTable("t_param")
public class XParam implements Serializable {

    @GeneralType
    @SQLColumn(pk = true)
    @NotUpdate
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String value;
    private String remark;

    @SuppressWarnings("unchecked")
    public <T> T getValue() {
        return (T) this.value;
    }

}
