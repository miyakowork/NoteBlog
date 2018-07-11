package me.wuwenbin.noteblog.v4.service;

import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.noteblog.v4.model.frontend.vo.SearchPageVo;

/**
 * created by Wuwenbin on 2018/2/11 at 15:16
 */
public interface SearchService {

    Page<SearchPageVo> findPagination(Page<SearchPageVo> pageVoPage, String sp);
}
