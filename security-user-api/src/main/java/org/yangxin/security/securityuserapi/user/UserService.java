package org.yangxin.security.securityuserapi.user;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yangxin
 * 2021/4/14 下午8:44
 */
public interface UserService {

    /**
     * 创建用户
     *
     * @param userInfo 要创建的用户信息
     * @return 要创建的用户信息
     */
    UserInfo create(UserInfo userInfo);

    /**
     * 更新用户信息
     *
     * @param userInfo 要更新用户信息
     * @return 要更新用户信息
     */
    UserInfo update(UserInfo userInfo);

    /**
     * 删除用户
     *
     * @param id 要删除的用户Id
     */
    void delete(Long id);

    /**
     * 查询用户
     *
     * @param id 要查询的用户Id
     * @return 查询得到的用户信息
     */
    UserInfo get(Long id);

    /**
     * 通过名字，查询用户信息
     *
     * @param name 姓名
     * @return 相同姓名的用户信息
     */
    List<UserInfo> query(String name);

    /**
     * 用户登录
     *
     * @param userInfo 要登录的用户信息
     * @return 登录后的用户信息
     */
    UserInfo login(UserInfo userInfo);
}
