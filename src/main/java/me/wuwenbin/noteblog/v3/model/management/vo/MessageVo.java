package me.wuwenbin.noteblog.v3.model.management.vo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.repository.annotation.sql.SQLPkRefer;
import me.wuwenbin.noteblog.v3.model.Message;
import me.wuwenbin.noteblog.v3.model.User;

/**
 * created by Wuwenbin on 2018/1/25 at 14:25
 */
@Getter
@Setter
public class MessageVo extends Message {

    @SQLPkRefer(targetClass = User.class, targetColumn = "nickname", joinColumn = "user_id")
    private String nickname;

}
