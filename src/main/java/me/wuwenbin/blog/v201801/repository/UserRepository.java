package me.wuwenbin.blog.v201801.repository;

import me.wuwenbin.blog.v201801.model.User;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by Wuwenbin on 2018/1/17 at 20:11
 */
@Repository
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public interface UserRepository extends IPageAndSortRepository<User, Long> {

    User findByOpenIdAndEnable(String openId, Boolean enable);

    User findByUsernameAndPasswordAndEnable(String username, String password, boolean enable);

    int updateEnableById(boolean enable, long id) throws Exception;
}
