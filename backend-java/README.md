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

- **Java**: 25
- **Spring Boot**: 4.0.1
- **Spring Framework**: 7.x
- **构建工具**: Maven 4.0.0-rc-5

### Spring Boot 4.0.1 新特性

- ✅ 基于 Spring Framework 7 构建
- ✅ 完全模块化的代码库（更小、更专注的 jar 包）
- ✅ 原生支持 Java 25（最低 Java 17）
- ✅ JSpecify 空安全注解支持
- ✅ API 版本控制支持
- ✅ HTTP Service Clients 支持

### Maven 4 新特性

- ✅ 更快的构建速度和改进的并行构建
- ✅ Build POM 和 Consumer POM 分离
- ✅ 支持 POM Model Version 4.1.0（可选）
- ✅ 自动 parent 版本管理
- ✅ 新的 BOM packaging type
- ✅ 更好的依赖解析机制

## 快速开始

### 环境要求

- **JDK 25** 或更高版本
- **Maven 4.0.0-rc-5** 或更高版本（推荐使用项目自带的 mvnw）

### 运行项目

#### 方式一：使用 Maven Wrapper（推荐）

Maven Wrapper 会自动下载并使用正确版本的 Maven，无需手动安装。

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

#### 方式二：使用本地 Maven

如果你已经安装了 Maven 4.0.0-rc-5：

```bash
mvn spring-boot:run
```

#### 方式三：使用 IDE

1. 用 IntelliJ IDEA 或 Eclipse 打开项目
2. 找到 `FamilyManagementApplication.java`
3. 右键点击 -> Run

### 测试接口

启动成功后访问：

- 健康检查：http://localhost:8080/api/health
- 欢迎页面：http://localhost:8080/api/welcome

## 开发进度

- [x] 项目基础结构
- [x] Maven 4 配置
- [x] Spring Boot 4.0.1 升级
- [x] 健康检查接口
- [ ] 数据库集成（PostgreSQL + pgvector）
- [ ] 用户认证（JWT）
- [ ] 物品管理 API
- [ ] 知识库管理 API
- [ ] AI 服务集成

## 学习资源

### 官方文档

- [Spring Boot 4.0 Release Notes](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Release-Notes)
- [Spring Boot 官方文档](https://spring.io/projects/spring-boot)
- [Spring Framework 7 文档](https://docs.spring.io/spring-framework/reference/)
- [Maven 4 新特性](https://maven.apache.org/whatsnewinmaven4.html)
- [Maven 4 迁移指南](https://maven.apache.org/guides/mini/guide-migration-to-mvn4.html)

### 技术博客

- [Spring Boot 4.0.0 available now](https://spring.io/blog/2025/11/20/spring-boot-4-0-0-available-now/)
- [Spring Boot 4.0.1 available now](https://spring.io/blog/2025/12/18/spring-boot-4-0-1-available-now/)

## 常见问题

### Q: 为什么使用 Java 25 而不是 LTS 版本？

A: Java 25 是最新版本，可以体验最新特性。Spring Boot 4 原生支持 Java 25，同时保持 Java 17 兼容性。如果需要长期支持，可以降级到 Java 21 LTS。

### Q: Maven 4 稳定吗？

A: Maven 4.0.0-rc-5 是 Release Candidate 版本，即将正式发布。对于学习项目来说非常合适，可以提前体验新特性。生产环境建议等待 GA 版本。

### Q: 如何升级到 POM Model Version 4.1.0？

A: 在 pom.xml 中将 `<modelVersion>4.0.0</modelVersion>` 改为 `<modelVersion>4.1.0</modelVersion>`，即可使用 Maven 4 的新特性（如自动 parent 版本、subprojects 等）。

## 故障排查

### 编译错误

如果遇到编译错误，请检查：

1. Java 版本是否为 25+：`java -version`
2. Maven 版本是否为 4.0.0-rc-5+：`mvn -version` 或 `.\mvnw.cmd -version`
3. 清理并重新构建：`mvn clean install`

### 依赖下载失败

如果依赖下载失败，可以尝试：

1. 使用国内镜像（阿里云）：在 `~/.m2/settings.xml` 中配置镜像
2. 清理本地仓库：删除 `~/.m2/repository` 中的相关目录
3. 重新下载：`mvn dependency:resolve`

