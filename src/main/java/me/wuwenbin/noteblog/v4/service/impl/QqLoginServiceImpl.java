package me.wuwenbin.noteblog.v4.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.utils.util.Maps;
import me.wuwenbin.noteblog.v4.common.SessionParam;
import me.wuwenbin.noteblog.v4.model.User;
import me.wuwenbin.noteblog.v4.repository.ParamRepository;
import me.wuwenbin.noteblog.v4.repository.UserRepository;
import me.wuwenbin.noteblog.v4.service.QqLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static me.wuwenbin.modules.utils.http.R.error;
import static me.wuwenbin.modules.utils.http.R.ok;
import static me.wuwenbin.noteblog.v4.common.ParamKey.APP_ID;
import static me.wuwenbin.noteblog.v4.common.ParamKey.APP_KEY;

/**
 * created by Wuwenbin on 2018/1/23 at 12:33
 */
@Slf4j
@Service
public class QqLoginServiceImpl implements QqLoginService {

    @Autowired
    private ParamRepository paramRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public R login(String callbackDomain, String code) {
        try {
            String appId = paramRepository.findValueByName(APP_ID);
            String appKey = paramRepository.findValueByName(APP_KEY);
            Map<String, Object> p1 = Maps.hashMap("grant_type", "authorization_code", "client_id", appId, "client_secret", appKey, "code", code, "redirect_uri", callbackDomain);
            String resp1 = HttpUtil.get("https://graph.qq.com/oauth2.0/token", p1);
            String accessToken = resp1.substring(13, resp1.length() - 66);
            String callback = HttpUtil.get("https://graph.qq.com/oauth2.0/me", Maps.hashMap("access_token", accessToken));
            String openId = callback.substring(45, callback.length() - 6);
            Map<String, Object> p2 = Maps.hashMap("access_token", accessToken, "oauth_consumer_key", appId, "openid", openId);
            JSONObject json2 = JSONUtil.parseObj(HttpUtil.get("https://graph.qq.com/user/get_user_info", p2));
            if (json2.getInt("ret") == 0) {
                User user = userRepository.findByOpenIdAndEnable(openId, true);
                if (user != null) {
                    return ok("授权成功！", "/").put(SessionParam.LOGIN_USER, user);
                } else {
                    User lockedUser = userRepository.findByOpenIdAndEnable(openId, false);
                    if (lockedUser != null) {
                        return error("QQ登录授权失败，原因：用户已被锁定！");
                    }
                    String nickname = json2.getStr("nickname");
                    String avatar = json2.getStr("figureurl_qq_2").replace("http://", "https://");
                    User registerUser = User.builder().nickname(nickname).avatar(avatar).openId(openId).build();
                    User afterRegisterUser = userRepository.save(registerUser);
                    if (afterRegisterUser != null) {
                        return ok("授权成功！", "/").put(SessionParam.LOGIN_USER, afterRegisterUser);
                    } else {
                        return error("QQ登录授权失败，原因：注册失败！");
                    }
                }
            } else {
                String errorMsg = json2.getStr("msg");
                log.error("QQ登录授权失败，原因：{}", errorMsg);
                return error("QQ登录授权失败，原因：{}", errorMsg);
            }
        } catch (StringIndexOutOfBoundsException e) {
            log.error("[accessToken] 返回值有误！");
            return error("请求重复或返回 [accessToken] 值有误！");
        } catch (Exception e) {
            log.error("QQ登录授权失败，原因：{}", e);
            return error("QQ登录授权失败，原因：{}", e.getMessage());
        }
    }
}
