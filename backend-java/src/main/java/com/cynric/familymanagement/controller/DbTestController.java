package com.cynric.familymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据库测试控制器
 * 用于测试数据库连接和查询功能
 */
@RestController
@RequestMapping("/api/db-test")
public class DbTestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 测试数据库连接
     * 访问地址：http://localhost:8080/api/db-test/test
     *
     * @return 连接状态信息
     */
    @GetMapping("/test")
    public String testConnection() {
        try {
            Integer result = jdbcTemplate.queryForObject(
                    "SELECT 1", Integer.class);
            return "数据库连接成功: " + result;
        } catch (Exception e) {
            return "数据库连接失败: " + e.getMessage();
        }
    }

    /**
     * 列出所有数据库表
     * 访问地址：http://localhost:8080/api/db-test/tables
     *
     * @return 表名列表
     */
    @GetMapping("/tables")
    public List<String> listTables() {
        return jdbcTemplate.queryForList(
                "SELECT tablename FROM pg_tables WHERE schemaname = 'public'",
                String.class);
    }
}
