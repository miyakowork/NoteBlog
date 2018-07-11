package me.wuwenbin.noteblog.v4.model.frontend.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.noteblog.v4.model.Cate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * created by Wuwenbin on 2018/1/30 at 13:21
 */
@Getter
@Setter
@Builder
public class IndexVo implements Serializable {

    private Map<String, Object> settings;
    private long blogCount;
    private List<Cate> cateList;
    private List<Map<String, Object>> randomArticles;
    private List<Map<String, Object>> tagList;

}
