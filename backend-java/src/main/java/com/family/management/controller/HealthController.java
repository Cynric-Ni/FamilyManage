package com.family.management.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 健康检查控制器
 * 用于测试应用是否正常运行
 *
 * @RestController 注解说明：
 * 1. 这是 @Controller 和 @ResponseBody 的组合
 * 2. 表示这个类的所有方法返回的都是数据（JSON），不是页面
 */
@RestController
@RequestMapping("/api")  // 所有接口都以 /api 开头
public class HealthController {

    /**
     * 健康检查接口
     * 访问地址：http://localhost:8080/api/health
     *
     * @return 返回系统状态信息
     */
    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "UP");
        result.put("message", "家庭管理系统后端服务运行正常");
        result.put("timestamp", LocalDateTime.now());
        result.put("version", "0.0.1-SNAPSHOT");
        return result;
    }

    /**
     * 欢迎接口
     * 访问地址：http://localhost:8080/api/welcome
     */
    @GetMapping("/welcome")
    public String welcome() {
        return "欢迎使用家庭管理系统！这是你的第一个 Spring Boot API 🎉";
    }
}
