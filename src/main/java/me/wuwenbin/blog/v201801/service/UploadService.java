package me.wuwenbin.blog.v201801.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * created by Wuwenbin on 2018/1/25 at 13:58
 */
public interface UploadService {

    Map<String, Object> uploadQrCode(MultipartFile qrCodeImg, String type);

    Map<String, Object> uploadAvatar(MultipartFile avatar, String username);
}
