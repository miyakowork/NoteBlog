package me.wuwenbin.noteblog.v4.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.wuwenbin.modules.sql.annotation.GeneralType;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;

import java.io.Serializable;

/**
 * created by Wuwenbin on 2018/1/16 at 16:58
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLTable("t_tag_refer")
public class TagRefer implements Serializable {

    @GeneralType
    @SQLColumn(pk = true)
    private Long id;
    private Long referId;
    private Long tagId;
    @SQLColumn("`show`")
    private Boolean show;
    private String type;
}
