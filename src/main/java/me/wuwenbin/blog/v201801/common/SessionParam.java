package me.wuwenbin.blog.v201801.common;

/**
 * created by Wuwenbin on 2018/1/23 at 12:43
 */
public interface SessionParam {

    /**
     * 已登录的用户
     */
    String LOGIN_USER = "session.login.user.key";


    /**
     * 前后台登录地址
     */
    String LOGIN_URL = "/login";

    /**
     * 后台首页地址
     */
    String MANAGEMENT_INDEX = "/management/index";
}
