package com.cynric.familymanagement.controller;

import com.cynric.familymanagement.dto.request.RegisterRequest;
import com.cynric.familymanagement.dto.response.UserResponse;
import com.cynric.familymanagement.entity.Result;
import com.cynric.familymanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 * 处理用户相关的 HTTP 请求
 */
@RestController
@RequestMapping("/api/users")  // 修改为复数形式，符合 RESTful 规范
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册接口
     *
     * @param request 注册请求信息
     * @return 注册成功后的用户信息
     *
     * 注意：不需要 try-catch，异常会被 GlobalExceptionHandler 自动捕获处理
     */
    @PostMapping("/register")
    public Result<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        // 调用 Service 层处理业务逻辑
        UserResponse userResponse = userService.register(request);

        // 返回成功响应
        return Result.success("注册成功", userResponse);
    }
}
