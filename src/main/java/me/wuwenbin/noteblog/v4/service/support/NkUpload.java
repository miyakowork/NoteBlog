package me.wuwenbin.noteblog.v4.service.support;

import me.wuwenbin.modules.utils.util.Maps;

import java.util.Map;

/**
 * created by Wuwenbin on 2018/1/17 at 11:58
 */
public final class NkUpload {

    public static Map<String, Object> ok(String url) {
        return Maps.hashMap("code", "000", "message", "上传成功", "item", Maps.hashMap("url", url));
    }

    public static Map<String, Object> err(String message) {
        return Maps.hashMap("error", "001", "message", message);
    }

}
