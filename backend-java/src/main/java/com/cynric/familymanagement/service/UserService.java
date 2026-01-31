package com.cynric.familymanagement.service;

import com.cynric.familymanagement.dto.request.RegisterRequest;
import com.cynric.familymanagement.dto.response.UserResponse;

/**
 * 用户服务接口
 * 定义用户相关的业务逻辑方法
 */
public interface UserService {

    /**
     * 用户注册
     *
     * @param request 注册请求信息
     * @return 注册成功后的用户信息
     * @throws RuntimeException 如果用户名、邮箱或手机号已存在，或密码不一致
     */
    UserResponse register(RegisterRequest request);
}
