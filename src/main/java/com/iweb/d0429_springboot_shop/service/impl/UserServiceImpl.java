package com.iweb.d0429_springboot_shop.service.impl;

import com.iweb.d0429_springboot_shop.entity.User;
import com.iweb.d0429_springboot_shop.mapper.UserMapper;
import com.iweb.d0429_springboot_shop.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Yang
 * @date 2023/4/29 15:37
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean login(User user) {
        if (user.getName()==null||user.getPassword()==null){
            return false;
        }
        User confirmUser = userMapper.getUser(user);
        return confirmUser != null;
    }

    @Override
    public boolean register(User user) {
        if (user.getName()==null||user.getPassword()==null){
            return false;
        }
        if (null!=userMapper.getByUsername(user.getName())){
            return false;
        }
        userMapper.add(user);
        return userMapper.getUser(user)!=null;
    }

    @Override
    public User get(User user) {
        return userMapper.getUser(user);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }
}
