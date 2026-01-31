package com.cynric.familymanagement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 用户注册请求 DTO
 * 用于接收前端传来的注册信息
 */
@Data
public class RegisterRequest {

    /**
     * 用户名
     * - 必填
     * - 长度 3-50 字符
     * - 只允许字母、数字、下划线
     */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度必须在 3-20 个字符之间")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "用户名只能包含字母、数字和下划线")
    private String username;

    /**
     * 密码
     * - 必填
     * - 长度 6-20 字符
     * - 至少包含一个字母和一个数字
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在 6-20 个字符之间")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]+$",
             message = "密码必须包含至少一个字母和一个数字")
    private String password;

    /**
     * 确认密码
     * - 必填
     * - 必须与密码一致（需要在 Service 层验证）
     */
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;

    /**
     * 邮箱
     * - 可选
     * - 如果填写必须符合邮箱格式
     */
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 手机号
     * - 可选
     * - 如果填写必须符合中国大陆手机号格式
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    /**
     * 家庭角色
     * - 可选
     * - 如：爸爸、妈妈、孩子等
     */
    @Size(max = 20, message = "家庭角色长度不能超过 20 个字符")
    private String familyRole;
}
