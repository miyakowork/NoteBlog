package me.wuwenbin.noteblog.v4.service.impl;

import me.wuwenbin.modules.jpa.factory.DaoFactory;
import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.utils.http.Url;
import me.wuwenbin.modules.utils.lang.LangUtils;
import me.wuwenbin.modules.utils.util.Maps;
import me.wuwenbin.modules.utils.web.Injection;
import me.wuwenbin.noteblog.v4.model.frontend.vo.SearchPageVo;
import me.wuwenbin.noteblog.v4.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * created by Wuwenbin on 2018/2/11 at 15:22
 */
@Service
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public class SearchServiceImpl implements SearchService {

    @Autowired
    private DaoFactory daoFactory;

    @Override
    public Page<SearchPageVo> findPagination(Page<SearchPageVo> pageVoPage, String sp) {
        pageVoPage.setPageSize(15);
        String sql = "SELECT * FROM (" +
                "   (SELECT id AS resId,title AS resName,text_content AS resTemp,concat('/article/',id) AS resUrl,'article' AS resType,post FROM t_article a) UNION " +
                "   (SELECT id AS resId,title AS resName,clear_content AS resTemp,concat('/note?t=',title) AS resUrl,'note' AS resType,post FROM t_note b) UNION " +
                "   (SELECT id AS resId,`name` AS resName,'' AS resTemp,url AS resUrl,'file' AS resType,post FROM t_file c)" +
                ") d WHERE 1=1";
        if (!StringUtils.isEmpty(sp)) {
            sql += " AND (resTemp LIKE :s OR resName LIKE :s)";
            Map<String, Object> paramMap = Maps.hashMap("s", LangUtils.string.placeholder("%{}%", Injection.stripSqlXSS(Url.urlDecode(sp))));
            sql += " ORDER BY post DESC";
            return daoFactory.dynamicDao.findPageListBeanByMap(sql, SearchPageVo.class, pageVoPage, paramMap);
        }
        sql += " ORDER BY post DESC";
        return daoFactory.dynamicDao.findPageListBeanByArray(sql, SearchPageVo.class, pageVoPage);
    }
}
