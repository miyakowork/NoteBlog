package me.wuwenbin.noteblog.v4.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * created by Wuwenbin on 2018/1/25 at 13:58
 */
public interface UploadService {

    Map<String, Object> upload(MultipartFile qrCodeImg, String type);

    Map<String, Object> uploadAvatar(MultipartFile avatar, String username);
}
