package com.cynric.familymanagement.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum UserRoleEnum {
    ADMIN(0, "超级管理员", "拥有所有权限"),
    MEMBER(1, "家庭成员", "拥有基本读写权限"),
    GUEST(2, "游客", "只有只读权限");

    @EnumValue
    private final Integer code;

    private final String name;

    private final String description;

    UserRoleEnum(Integer code, String name, String description){
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public static UserRoleEnum getByCode(Integer code){
        for (UserRoleEnum role : values()){
            if (role.getCode().equals(code)){
                return role;
            }
        }
        return MEMBER;
    }

}
