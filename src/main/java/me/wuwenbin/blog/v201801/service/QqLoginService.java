package me.wuwenbin.blog.v201801.service;

import me.wuwenbin.modules.utils.http.R;

/**
 * created by Wuwenbin on 2018/1/23 at 12:30
 */
public interface QqLoginService {

    R login(String callbackDomain,String code);
}
