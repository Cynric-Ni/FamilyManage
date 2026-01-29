package com.cynric.familymanagement.service.impl;

import com.cynric.familymanagement.entity.User;
import com.cynric.familymanagement.mapper.UserMapper;
import com.cynric.familymanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        User u = userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username, String password) {

    }
}
