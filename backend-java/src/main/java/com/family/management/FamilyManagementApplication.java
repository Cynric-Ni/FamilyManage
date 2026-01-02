package com.family.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 家庭管理系统 - 应用主类
 *
 * @SpringBootApplication 注解说明：
 * 这是一个组合注解，包含了：
 * 1. @Configuration - 标记这是一个配置类
 * 2. @EnableAutoConfiguration - 启用自动配置
 * 3. @ComponentScan - 自动扫描组件
 */
@SpringBootApplication
public class FamilyManagementApplication {

    /**
     * 应用程序入口
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(FamilyManagementApplication.class, args);
    }
}
