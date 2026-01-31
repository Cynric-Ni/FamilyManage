# 开发日志 (Development Log)

记录每次开发会话的详细进度和学习笔记。

---

## 2026-02-01 - 用户认证模块开发（第一天）

### 📅 开发时间
- 开始时间：今天
- 结束时间：进行中
- 总耗时：约 2-3 小时

### 🎯 本次目标
实现用户注册功能的后端基础架构

### ✅ 已完成的工作

#### 1. User 实体类优化
**文件**: `backend-java/src/main/java/com/cynric/familymanagement/entity/User.java`

**修复的问题**：
- ❌ 第 64 行：`@TableId("phone")` 错误地标记为主键
  - ✅ 修正为：`@TableField("phone")`
- ❌ 密码字段缺少安全保护
  - ✅ 添加：`@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)`
  - ✅ 添加：`@NotBlank(message = "密码不能为空")`
- ❌ 字段命名拼写错误
  - ✅ `creatAt` → `createdAt`
  - ✅ `updateAt` → `updatedAt`
- ❌ 不合理的 `@Null` 注解
  - ✅ 移除 role 和 status 字段上的 `@Null` 注解
- ❌ familyRole 验证注解缺少 message
  - ✅ 添加：`@NotBlank(message = "家庭角色不能为空")`

**学习要点**：
- `@JsonProperty(access = WRITE_ONLY)` 确保密码只能写入，不会在 JSON 响应中暴露
- BCrypt 密码哈希固定 60 字符，数据库字段应设置为 VARCHAR(60) 或更大

#### 2. DTO 类创建
**目录**: `backend-java/src/main/java/com/cynric/familymanagement/dto/`

**创建的文件**：

1. **RegisterRequest.java** (`dto/request/`)
   - 用户名验证：3-50 字符，只允许字母、数字、下划线
   - 密码验证：6-20 字符，至少包含一个字母和一个数字
   - 确认密码字段
   - 邮箱格式验证
   - 手机号格式验证（中国大陆格式：`^1[3-9]\d{9}$`）
   - 家庭角色字段（可选）

2. **LoginRequest.java** (`dto/request/`)
   - 用户名字段（必填）
   - 密码字段（必填）
   - 记住我字段（可选，默认 false）

3. **UserResponse.java** (`dto/response/`)
   - 包含所有用户信息字段
   - **不包含密码字段**（安全考虑）
   - 包含虚拟字段：displayName, createdByUsername, updatedByUsername

**学习要点**：
- DTO 的作用：分离前端接口与数据库结构，提供数据验证和安全保护
- Request DTO 用于接收前端数据，Response DTO 用于返回数据给前端
- 使用 `@Pattern` 注解进行正则表达式验证

#### 3. Mapper 层创建
**文件**: `backend-java/src/main/java/com/cynric/familymanagement/mapper/UserMapper.java`

**实现内容**：
- 继承 `BaseMapper<User>`
- 自动获得基础 CRUD 方法
- 添加详细的使用说明和示例注释

**学习要点**：
- MyBatis-Plus 的 BaseMapper 提供了丰富的方法，无需编写 SQL
- 常用方法：insert, selectById, selectOne, selectList, updateById, deleteById, selectCount
- QueryWrapper 用于构建查询条件
- 逻辑删除（@TableLogic）会自动过滤已删除的记录

#### 4. Service 层实现
**文件**:
- `backend-java/src/main/java/com/cynric/familymanagement/service/UserService.java`
- `backend-java/src/main/java/com/cynric/familymanagement/service/impl/UserServiceImpl.java`

**实现的功能**：

**用户注册流程**：
1. ✅ 验证两次密码是否一致
2. ✅ 检查用户名是否已存在
3. ✅ 检查邮箱是否已存在（如果提供）
4. ✅ 检查手机号是否已存在（如果提供）
5. ✅ 使用 BCrypt 加密密码（强度 10）
6. ✅ 设置默认角色（MEMBER）和状态（ACTIVE）
7. ✅ 保存到数据库
8. ✅ 转换为 UserResponse 返回

**关键代码**：
```java
// BCrypt 密码加密
BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
String encryptedPassword = passwordEncoder.encode(request.getPassword());

// 唯一性检查
QueryWrapper<User> wrapper = new QueryWrapper<>();
wrapper.eq("username", request.getUsername());
Long count = userMapper.selectCount(wrapper);

// 事务管理
@Transactional(rollbackFor = Exception.class)
```

**学习要点**：
- `@Service` 注解标记服务层组件
- `@Transactional` 注解开启事务，出错自动回滚
- BCrypt 每次加密结果都不同（自动加盐）
- 使用 QueryWrapper 构建查询条件
- Entity 到 DTO 的转换应该在 Service 层完成

### 🔍 重要知识点

#### 密码安全三层防护
1. **传输层**：HTTPS 加密传输（明文密码在加密通道中传输）
2. **存储层**：BCrypt 加密存储（强度 10，自动加盐，固定 60 字符）
3. **响应层**：`@JsonProperty(WRITE_ONLY)` 防止密码在 JSON 中暴露

#### 为什么不需要单独的盐字段？
BCrypt 哈希值结构：
```
$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy
│ │  │ └─ 盐值(22字符) ─────┘                                    │
│ │  └─ 强度(10)                                                 │
│ └─ 版本(2a)                                                    │
└─ 标识符($)                                                     │
                            哈希值(31字符) ─────────────────────┘
```
所有信息（算法、强度、盐值、哈希值）都在一个字符串中，不需要单独存储盐值。

#### MyBatis-Plus QueryWrapper 常用方法
```java
QueryWrapper<User> wrapper = new QueryWrapper<>();
wrapper.eq("username", "admin");           // WHERE username = 'admin'
wrapper.ne("status", "DISABLED");          // WHERE status != 'DISABLED'
wrapper.like("username", "test");          // WHERE username LIKE '%test%'
wrapper.between("created_at", start, end); // WHERE created_at BETWEEN start AND end
wrapper.orderByDesc("created_at");         // ORDER BY created_at DESC
```

### ❌ 遇到的问题

#### 问题 1：User 实体类字段命名错误
- **现象**：convertToUserResponse 方法中使用了 `user.getRoleEnum()`
- **原因**：User 实体类中字段名是 `role`，不是 `roleEnum`
- **解决**：修正为 `user.getRole()`

### 📝 待办事项

#### 下一步工作
- [ ] 在 UserController 中添加注册接口 `POST /api/users/register`
- [ ] 创建统一响应格式类 `Result<T>`
- [ ] 创建全局异常处理器 `@ControllerAdvice`
- [ ] 测试注册功能
- [ ] 实现登录功能
- [ ] 配置 MyBatis-Plus 自动填充（createdAt, updatedAt）
- [ ] 配置 JWT 认证

#### 技术债务
- [ ] 将 RuntimeException 替换为自定义异常类
- [ ] 添加单元测试
- [ ] 添加集成测试
- [ ] 优化异常信息的国际化

### 💡 学习心得

1. **DTO 的重要性**：
   - 分离前端接口与数据库结构
   - 提供数据验证
   - 保护敏感信息（如密码）
   - 灵活组合字段

2. **MyBatis-Plus 的优势**：
   - BaseMapper 提供丰富的 CRUD 方法
   - QueryWrapper 构建查询条件非常方便
   - 逻辑删除自动处理
   - 减少 SQL 编写，提高开发效率

3. **密码安全的重要性**：
   - 永远不要在传输时加盐（应该在存储时）
   - 使用 BCrypt 等现代加密算法
   - 密码字段永远不要返回给前端
   - HTTPS 是传输安全的基础

4. **事务管理**：
   - 使用 `@Transactional` 确保数据一致性
   - `rollbackFor = Exception.class` 确保所有异常都回滚
   - 事务应该在 Service 层，不是 Controller 层

### 📚 参考资料

- [MyBatis-Plus 官方文档](https://baomidou.com/)
- [Spring Boot 官方文档](https://spring.io/projects/spring-boot)
- [BCrypt 算法介绍](https://en.wikipedia.org/wiki/Bcrypt)
- [Jakarta Validation 注解](https://jakarta.ee/specifications/bean-validation/)

### 🎓 今日收获

- ✅ 理解了 DTO 的作用和设计原则
- ✅ 掌握了 MyBatis-Plus 的基本使用
- ✅ 理解了密码安全的三层防护
- ✅ 学会了使用 BCrypt 加密密码
- ✅ 理解了事务管理的重要性
- ✅ 掌握了 QueryWrapper 的使用方法

---

## 下次开发计划

### 目标
实现用户注册接口并测试

### 任务清单
1. 创建统一响应格式类 `Result<T>`
2. 在 UserController 中添加注册接口
3. 创建全局异常处理器
4. 使用 Postman 或 curl 测试注册功能
5. 实现登录功能

### 预计耗时
2-3 小时

---

**记录人**: Cynric
**最后更新**: 2026-02-01
