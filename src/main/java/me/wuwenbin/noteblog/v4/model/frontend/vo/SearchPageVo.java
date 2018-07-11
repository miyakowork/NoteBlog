package me.wuwenbin.noteblog.v4.model.frontend.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * created by Wuwenbin on 2018/2/11 at 16:49
 */
@Getter
@Setter
public class SearchPageVo implements Serializable {
    private Long resId;
    private String resName;
    private String resTemp;
    private String resType;
    private String resUrl;
    private LocalDateTime post;
}
