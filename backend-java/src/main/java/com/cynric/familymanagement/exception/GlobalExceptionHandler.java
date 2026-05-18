package com.cynric.familymanagement.exception;

import com.cynric.familymanagement.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * 统一处理所有 Controller 抛出的异常，返回统一的 Result 格式
 *
 * @RestControllerAdvice = @ControllerAdvice + @ResponseBody
 * 作用：拦截所有 @RestController 抛出的异常
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理业务异常
     * 当 Service 层抛出 IllegalArgumentException 时触发
     *
     * 例如：用户名已存在、密码不一致等业务逻辑错误
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("业务异常: {}", e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * 处理参数校验失败异常（@Valid 触发）
     * 当 @Valid 校验失败时，Spring 会抛出 MethodArgumentNotValidException
     *
     * 例如：用户名长度不符合要求、邮箱格式错误等
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 获取所有校验失败的字段和错误信息
        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)  // 获取 @NotBlank、@Email 等注解中的 message
                .collect(Collectors.joining("; "));  // 用分号连接多个错误信息

        log.warn("参数校验失败: {}", errorMessage);
        return Result.error("参数校验失败: " + errorMessage);
    }

    /**
     * 处理参数绑定异常
     * 当请求参数无法绑定到方法参数时触发
     *
     * 例如：前端传了字符串，但后端期望的是数字
     */
    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(BindException e) {
        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        log.warn("参数绑定失败: {}", errorMessage);
        return Result.error("参数错误: " + errorMessage);
    }

    /**
     * 处理所有未被捕获的异常
     * 这是最后的兜底处理，防止系统错误信息泄露给前端
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        // 记录完整的异常堆栈信息，方便排查问题
        log.error("系统异常", e);

        // 返回给前端的信息不包含具体错误细节，防止信息泄露
        return Result.systemError("系统错误，请稍后重试");
    }
}
