package me.wuwenbin.noteblog.v4.common.blog;

import cn.hutool.http.HttpUtil;
import jodd.json.JsonParser;
import me.wuwenbin.modules.utils.util.Maps;
import me.wuwenbin.noteblog.v4.model.User;
import me.wuwenbin.noteblog.v4.model.XParam;
import me.wuwenbin.noteblog.v4.model.frontend.bo.IpInfo;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * created by Wuwenbin on 2018/2/8 at 15:29
 */
public class BlogUtils {

    /**
     * 删除不必要的信息，避免暴露过多信息
     *
     * @param user
     * @return
     */
    public static Map<String, Object> toMap(User user) {
        if (user == null) {
            return null;
        }
        return Maps.hashMap("id", user.getId()
                , "nickname", user.getNickname()
                , "avatar", user.getAvatar()
                , "dri", user.getDefaultRoleId());
    }

    public static Map<String, Object> settingMap(List<XParam> xParams) {
        return xParams.stream()
                .filter(xParam -> !xParam.getName().equals("app_id") && !xParam.getName().equals("app_key"))
                .collect(Collectors.toMap(XParam::getName, XParam::getValue));
    }

    public static IpInfo getIpInfo(String ip) {
        String url = "http://ip.taobao.com/service/getIpInfo.php?ip=" + ip;
        String resp = HttpUtil.get(url);
        return JsonParser.create().parse(resp, IpInfo.class);
    }

    public static String getIpCnInfo(IpInfo ipInfo) {
        String temp = ipInfo.getData().getCountry() + ipInfo.getData().getRegion() + ipInfo.getData().getCity();
        if (!ipInfo.getData().getCounty().toLowerCase().contains("x")) {
            return temp + ipInfo.getData().getCounty();
        } else {
            return temp;
        }
    }

    public static Map<String, Object> toLowerKeyMap(Map<String, Object> originMap) {
        if (CollectionUtils.isEmpty(originMap)) {
            return null;
        }
        Map<String, Object> newMap = new TreeMap<>();
        originMap.forEach((k, v) -> newMap.put(k.toLowerCase(), v));
        return newMap;
    }

    public static List<Map<String, Object>> toLowerKeyListMap(List<Map<String, Object>> originListMap) {
        if (CollectionUtils.isEmpty(originListMap)) {
            return null;
        }
        List<Map<String, Object>> newListMap = new LinkedList<>();
        originListMap.forEach(m -> newListMap.add(toLowerKeyMap(m)));
        return newListMap;
    }
}
