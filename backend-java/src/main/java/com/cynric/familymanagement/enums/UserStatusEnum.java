package com.cynric.familymanagement.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum UserStatusEnum {
    ACTIVE(0, "启用", "账户正常使用"),
    DISABLED(1, "禁用", "账户被禁用");

    @EnumValue
    private final Integer code;
    private final String name;
    private final String description;

    UserStatusEnum(Integer code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public static UserStatusEnum getByCode(Integer code) {
        for (UserStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return ACTIVE; // 默认返回启用状态
    }
}
