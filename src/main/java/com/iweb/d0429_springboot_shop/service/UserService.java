package com.iweb.d0429_springboot_shop.service;

import com.iweb.d0429_springboot_shop.entity.User;

/**
 * @author Yang
 * @date 2023/4/29 15:37
 */
public interface UserService {
    /** 登录业务方法
     * @param user 用户对象
     * @return 是否登录成功
     */
    boolean login(User user);

    /** 注册业务方法
     * @param user 用户对象
     * @return 是否注册成功
     */
    boolean register(User user);

    /** 查询用户的业务方法
     * @param user 用户对象
     * @return 用户对象
     */
    User get(User user);

    void update(User user);
}
