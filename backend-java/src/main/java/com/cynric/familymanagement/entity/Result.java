package com.cynric.familymanagement.entity;

import lombok.Getter;

/**
 * 统一响应结果封装类
 * 用于包装所有 API 的返回数据
 *
 * @param <T> 响应数据的类型
 */
@Getter
public class Result<T> {

    /**
     * 响应状态码
     * 0: 成功
     * 1: 业务错误
     * 500: 系统错误
     */
    private final Integer code;

    /**
     * 响应消息
     */
    private final String msg;

    /**
     * 响应数据
     */
    private final T data;

    /**
     * 私有构造函数，只能通过静态工厂方法创建对象
     */
    private Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // ==================== 成功响应 ====================

    /**
     * 成功响应（带数据）
     *
     * @param data 响应数据
     * @param <E>  数据类型
     * @return Result 对象
     */
    public static <E> Result<E> success(E data) {
        return new Result<>(0, "操作成功", data);
    }

    /**
     * 成功响应（不带数据）
     *
     * @return Result 对象
     */
    public static Result<Void> success() {
        return new Result<>(0, "操作成功", null);
    }

    /**
     * 成功响应（自定义消息）
     *
     * @param msg  自定义消息
     * @param data 响应数据
     * @param <E>  数据类型
     * @return Result 对象
     */
    public static <E> Result<E> success(String msg, E data) {
        return new Result<>(0, msg, data);
    }

    // ==================== 错误响应 ====================

    /**
     * 业务错误响应（默认错误码 1）
     *
     * @param msg 错误消息
     * @return Result 对象
     */
    public static Result<Void> error(String msg) {
        return new Result<>(1, msg, null);
    }

    /**
     * 错误响应（自定义错误码）
     *
     * @param code 错误码
     * @param msg  错误消息
     * @return Result 对象
     */
    public static Result<Void> error(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }

    /**
     * 系统错误响应（错误码 500）
     *
     * @param msg 错误消息
     * @return Result 对象
     */
    public static Result<Void> systemError(String msg) {
        return new Result<>(500, msg, null);
    }
}
