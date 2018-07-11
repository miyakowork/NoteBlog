package me.wuwenbin.noteblog.v4.service.support;

import me.wuwenbin.modules.utils.util.Maps;

import java.util.Map;

/**
 * created by Wuwenbin on 2018/1/25 at 13:50
 */
public class LayUpload {

    public static Map<String, Object> ok(String message, String imgUrl) {
        return Maps.hashMap("code", 0, "msg", message, "data", Maps.hashMap("src", imgUrl));
    }

    public static Map<String, Object> err(String message) {
        return Maps.hashMap("code", 1, "msg", message);
    }
}
