package com.cynric.familymanagement.service;

import com.cynric.familymanagement.entity.User;

public interface UserService {
    //用户名查询用户
    User findByUserName(String username);

    //注册
    void register(String username, String password);
}
