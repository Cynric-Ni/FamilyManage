package com.cynric.familymanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户登录请求 DTO
 * 用于接收前端传来的登录信息
 */
@Data
public class LoginRequest {

    /**
     * 用户名
     * - 必填
     * - 可以是用户名、邮箱或手机号
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     * - 必填
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 记住我
     * - 可选
     * - 如果为 true，则延长 Token 有效期
     */
    private Boolean rememberMe = false;
}
