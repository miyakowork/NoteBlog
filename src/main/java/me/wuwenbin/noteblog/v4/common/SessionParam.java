package me.wuwenbin.noteblog.v4.common;

/**
 * created by Wuwenbin on 2018/1/23 at 12:43
 */
public interface SessionParam {

    /**
     * 已登录的用户
     */
    String LOGIN_USER = "session_login_user_key";

    /**
     * sessionIdCookie的名称
     */
    String SESSION_ID_COOKIE = "sid";

    /**
     * name
     */
    String REMEMBER_COOKIE_NAME = "DA4DE6E44EEEC32C30C278A452C355B9";

    /**
     * cookie分隔符
     */
    String COOKIE_SPLIT = "__!!__";

    /**
     * 存放用户信息的cookie的密钥，用来加密解密用
     */
    String COOKIE_SECRET_KEY = "877A0812AA66E18B";


    /**
     * 前后台登录地址
     */
    String LOGIN_URL = "/login";

    /**
     * 后台首页地址
     */
    String MANAGEMENT_INDEX = "/management/index";
}
