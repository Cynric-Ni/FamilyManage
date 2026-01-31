package com.cynric.familymanagement.dto.response;

import com.cynric.familymanagement.enums.UserRoleEnum;
import com.cynric.familymanagement.enums.UserStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 用户响应 DTO
 * 用于返回给前端的用户信息（不包含密码等敏感信息）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    /**
     * 用户 ID
     */
    private UUID id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户角色
     */
    private UserRoleEnum role;

    /**
     * 用户状态
     */
    private UserStatusEnum status;

    /**
     * 性别
     */
    private String gender;

    /**
     * 家庭角色
     */
    private String familyRole;

    /**
     * 头像 URL
     */
    private String avatarUrl;

    /**
     * 显示名称（优先显示家庭角色，否则显示用户名）
     */
    private String displayName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    /**
     * 创建人用户名
     */
    private String createdByUsername;

    /**
     * 更新人用户名
     */
    private String updatedByUsername;
}
