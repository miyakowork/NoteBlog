package me.wuwenbin.noteblog.v4.service;

import me.wuwenbin.noteblog.v4.model.Article;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * created by Wuwenbin on 2018/1/16 at 16:16
 */
public interface ArticleService {

    boolean postArticle(Article article, String tagNames) throws Exception;

    boolean updateArticle(Article article, String tagNames) throws Exception;

    Map<String, Object> upload(MultipartFile file);

    Map<String, Object> upload(String type, String base64Data);

    boolean updateTopById(long articleId, boolean top) throws Exception;

}
