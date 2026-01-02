# Family Management Backend

家庭管理系统 - Spring Boot 后端服务

## 项目结构

```
backend-java/
├── src/
│   ├── main/
│   │   ├── java/com/family/management/
│   │   │   ├── controller/      # 控制器层（REST API）
│   │   │   ├── service/         # 服务层（业务逻辑）
│   │   │   ├── repository/      # 数据访问层
│   │   │   ├── entity/          # 实体类（数据库表）
│   │   │   ├── dto/             # 数据传输对象
│   │   │   │   ├── request/     # 请求 DTO
│   │   │   │   └── response/    # 响应 DTO
│   │   │   ├── config/          # 配置类
│   │   │   ├── security/        # 安全相关
│   │   │   ├── exception/       # 自定义异常
│   │   │   ├── util/            # 工具类
│   │   │   ├── constant/        # 常量定义
│   │   │   ├── ai/              # AI 服务客户端
│   │   │   └── FamilyManagementApplication.java  # 启动类
│   │   └── resources/
│   │       └── application.yml  # 配置文件
│   └── test/                    # 测试代码
├── pom.xml                      # Maven 配置
└── README.md                    # 本文件
```

## 技术栈

- **Java**: 17
- **Spring Boot**: 3.4.1
- **构建工具**: Maven

## 快速开始

### 环境要求

- JDK 17 或更高版本
- Maven 3.6+ (可选，可以使用项目自带的 mvnw)

### 运行项目

#### 方式一：使用 Maven 命令（推荐）

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

#### 方式二：使用 IDE

1. 用 IntelliJ IDEA 或 Eclipse 打开项目
2. 找到 `FamilyManagementApplication.java`
3. 右键点击 -> Run

### 测试接口

启动成功后访问：

- 健康检查：http://localhost:8080/api/health
- 欢迎页面：http://localhost:8080/api/welcome

## 开发进度

- [x] 项目基础结构
- [x] Maven 配置
- [x] 健康检查接口
- [ ] 数据库集成
- [ ] 用户认证
- [ ] 物品管理 API
- [ ] AI 服务集成

## 学习资源

- [Spring Boot 官方文档](https://spring.io/projects/spring-boot)
- [Maven 入门教程](https://maven.apache.org/guides/getting-started/)
