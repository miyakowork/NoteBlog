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

/**
 * created by Wuwenbin on 2018/1/17 at 20:08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLTable("t_upload")
public class Upload implements Serializable {

    @GeneralType
    @SQLColumn(pk = true)
    private Long id;
    private String diskPath;
    private String virtualPath;
    private LocalDateTime upload;
    private String type;
}
