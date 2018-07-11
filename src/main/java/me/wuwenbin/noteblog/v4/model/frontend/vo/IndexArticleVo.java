package me.wuwenbin.noteblog.v4.model.frontend.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.noteblog.v4.model.Tag;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * created by Wuwenbin on 2018/2/4 at 12:31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndexArticleVo implements Serializable {
    private Page<ArticleVo> page;
    private Map<Long, List<Tag>> tags;
}
