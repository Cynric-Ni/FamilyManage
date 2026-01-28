package com.cynric.familymanagement.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.cynric.familymanagement.enums.UserRoleEnum;
import com.cynric.familymanagement.enums.UserStatusEnum;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("users")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private UUID id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("email")
    private String email;

    /**
     * 用户角色
     */
    @TableField("role")
    private UserRoleEnum role;

    /**
     * 用户状态
     */
    @TableField("status")
    private  UserStatusEnum status;

    @TableId("phone")
    private String phone;

    @TableField("gender")
    private String gender;

    /**
     * 家庭角色
     */
    @TableField("family_role")
    private String familyRole;

    /**
     * 头像链接
     */
    @TableField("avatar_url")
    private String avatarUrl;
    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creatAt;

    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateAt;

    /**
     * 创建人ID
     */
    @TableField("created_by")
    private UUID createdBy;

    /**
     * 更新人ID
     */
    @TableField("updated_by")
    private UUID updatedBy;

    @TableField("deleted_at")
    @TableLogic
    @JsonIgnore
    private LocalDateTime deletedAt;

    //========== 虚拟字段（不映射到数据库）==========
    /**
     * 显示名称
     */

    @TableField(exist = false)
    @JsonProperty("displayName")
    private String displayName;

    /**
     * 创建人用户名（用于显示）
     */
    @TableField(exist = false)
    private String createdByUsername;

    /**
     * 更新人用户名（用于显示）
     */
    @TableField(exist = false)
    private String updatedByUsername;

    // ========== 业务方法 ==========
    /**
     * 获取显示名称
     */
    public String getDisplayName() {
        if (familyRole != null && !familyRole.trim().isEmpty()) {
            return familyRole;
        }
        return username;
    }

    /**
     * 判断是否已删除
     */
    public boolean isDeleted() {
        return deletedAt != null;
    }

    /**
     * 判断是否启用
     */
    public boolean isActive() {
        return UserStatusEnum.ACTIVE.equals(status);
    }

    /**
     * 判断是否为管理员
     */
    public boolean isAdmin() {
        return UserRoleEnum.ADMIN.equals(role);
    }

    /**
     * 判断是否为家庭成员
     */
    public boolean isMember() {
        return UserRoleEnum.MEMBER.equals(role);
    }

    /**
     * 判断是否为游客
     */
    public boolean isGuest(){
        return UserRoleEnum.GUEST.equals((role));
    }

}
