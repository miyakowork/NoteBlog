package me.wuwenbin.noteblog.v4.repository;

import me.wuwenbin.modules.repository.annotation.field.SQL;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IBaseCrudRepository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.modules.repository.provider.find.annotation.Primitive;
import me.wuwenbin.noteblog.v4.model.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by Wuwenbin on 2018/1/17 at 20:11
 */
@Repository
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public interface UserRepository extends IPageAndSortRepository<User, Long>, IBaseCrudRepository<User, Long> {

    User findByOpenIdAndEnable(String openId, Boolean enable);

    User findByUsernameAndPasswordAndEnable(String username, String password, boolean enable);

    int updateEnableById(boolean enable, long id) throws Exception;

    @SQL("UPDATE t_user SET nickname = ? WHERE default_role_id = 1 AND username = ?")
    int updateAdminNickname(String nickname, String username);

    @SQL("UPDATE t_user SET password = ? WHERE default_role_id = 1 AND username = ?")
    int updateAdminPass(String pass, String username);

    @SQL("UPDATE t_user SET avatar = ? WHERE default_role_id = 1 AND username = ?")
    int updateAdminAvatar(String avatar, String username);

    @Primitive
    String findNicknameById(long id);
}
