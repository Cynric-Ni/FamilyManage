# 家庭管理系统 (Family Management System)

## 项目概述

这是一个智能家庭管理系统，用于管理家庭物品、知识库，并通过 AI 助手提供智能分析和建议。系统支持多人协作，可随时随地访问。

### ⚠️ 重要：这是一个学习导向的项目

本项目的主要目的是**边实践边学习**，而不是快速完成功能。在开发过程中：

- ✅ **循序渐进**：一步一步指导，而不是一次性生成所有代码
- ✅ **详细解释**：每一步都要解释为什么这样做，帮助理解原理
- ✅ **互动式学习**：在每个阶段暂停，等待确认理解后再继续
- ✅ **鼓励提问**：遇到不理解的地方随时询问
- ❌ **避免一次完成**：不要高效地一次性完成所有工作
- ❌ **避免跳过步骤**：不要省略中间过程，即使看起来简单

### 核心功能
- **物品管理**：记录家庭物品的存放位置、数量、过期时间等信息
- **知识库管理**：存储个人笔记、文档、代码片段等知识内容
- **AI 家庭管家**：通过 AI 提供智能问答、数据分析、物品推荐等功能
- **多人协作**：支持家庭成员共同使用，权限分级管理
- **远程访问**：支持移动端和 Web 端随时访问

## 技术架构

### 整体架构：微服务混合架构

```
前端层 (Vue 3 + Nuxt 3)
        ↓
业务服务层 (Spring Boot 3)
        ↓
AI 服务层 (Python FastAPI)
        ↓
数据层 (PostgreSQL + Redis + 向量数据库)
```

### 技术栈详情

#### 后端 - 主服务 (Spring Boot)
- **框架**: Spring Boot 4.0.1
- **语言**: Java 25
- **Spring Framework**: 7.x
- **构建工具**: Maven 4.0.0-rc-5
- **核心依赖**:
  - Spring Web (RESTful API)
  - Spring Security (JWT 认证、权限管理)
  - Spring Data JPA (ORM)
  - Spring AI (AI 集成框架)
  - Spring Validation (参数校验)
- **数据库**:
  - PostgreSQL 15+ (主数据库)
  - pgvector (向量存储扩展)
  - Redis 7+ (缓存、会话)
- **文件存储**: MinIO / Aliyun OSS

**职责**:
- 用户认证与授权
- 物品 CRUD 操作
- 知识库元数据管理
- 业务逻辑处理
- 定时任务调度
- 调用 AI 服务

#### 后端 - AI 服务 (Python)
- **框架**: FastAPI 0.104+
- **语言**: Python 3.11+
- **核心依赖**:
  - LangChain (RAG 框架、Agent 框架)
  - OpenAI / Anthropic SDK (LLM 调用)
  - Qdrant Client / ChromaDB (向量数据库)
  - PaddleOCR (图像文字识别)
  - Pandas / NumPy (数据分析)
  - Transformers (模型加载)
  - Ollama (可选，本地模型)
- **向量数据库**: Qdrant / ChromaDB

**职责**:
- 文本向量化 (Embedding)
- RAG 知识库检索与问答
- OCR 图像识别
- 数据分析与可视化
- 智能推荐算法
- 本地模型运行

#### 前端
- **框架**: Vue 3.4+ (Composition API)
- **构建工具**: Vite 5+
- **全栈框架**: Nuxt 4 (SSR + PWA)
- **包管理器**: pnpm 9+ (快速、节省磁盘空间、严格依赖管理)
- **UI 组件库**: Element Plus / Ant Design Vue
- **状态管理**: Pinia
- **HTTP 客户端**: Axios / ofetch
- **移动端**: PWA (Progressive Web App)

#### 部署
- **容器化**: Docker + Docker Compose
- **反向代理**: Nginx / Traefik
- **内网穿透**: Tailscale / FRP
- **SSL**: Let's Encrypt

## 代码规范

### Java 后端规范

#### 包结构
```
com.family.management
├── controller       # 控制器层（REST API）
├── service         # 服务层（业务逻辑）
├── repository      # 数据访问层（JPA Repository）
├── entity          # 实体类（数据库映射）
├── dto             # 数据传输对象
│   ├── request     # 请求 DTO
│   └── response    # 响应 DTO
├── config          # 配置类
├── security        # 安全相关（JWT、权限）
├── exception       # 自定义异常
├── util            # 工具类
├── ai              # AI 服务调用客户端
└── constant        # 常量定义
```

#### 命名约定
- **类名**: PascalCase，如 `ItemController`, `UserService`
- **方法名**: camelCase，如 `findItemById`, `createUser`
- **常量**: UPPER_SNAKE_CASE，如 `MAX_FILE_SIZE`
- **包名**: 全小写，如 `com.family.management.controller`

#### 编码规范
- 使用 Java 25 特性（Record、Switch 表达式、Pattern Matching 等）
- Controller 只负责参数校验和调用 Service
- Service 包含业务逻辑，事务管理在此层
- Repository 只做数据访问，不写业务逻辑
- 统一异常处理使用 `@ControllerAdvice`
- 统一响应格式：`Result<T>` 包装返回数据
- 必须编写单元测试（JUnit 5 + Mockito）

#### RESTful API 规范
```java
// ✅ 正确示例
@RestController
@RequestMapping("/api/items")
public class ItemController {

    @GetMapping("/{id}")           // 查询单个
    public Result<ItemDTO> getItem(@PathVariable Long id) {}

    @GetMapping                     // 查询列表
    public Result<PageResult<ItemDTO>> listItems(@RequestParam int page) {}

    @PostMapping                    // 创建
    public Result<ItemDTO> createItem(@Valid @RequestBody CreateItemRequest req) {}

    @PutMapping("/{id}")           // 更新
    public Result<ItemDTO> updateItem(@PathVariable Long id, @Valid @RequestBody UpdateItemRequest req) {}

    @DeleteMapping("/{id}")        // 删除
    public Result<Void> deleteItem(@PathVariable Long id) {}
}
```

#### AI 服务调用
```java
// Spring Boot 调用 Python AI 服务示例
@Service
public class AIServiceClient {

    @Value("${ai.service.url}")
    private String aiServiceUrl;

    private final RestTemplate restTemplate;

    public String chat(String question, List<String> context) {
        ChatRequest request = new ChatRequest(question, context);
        return restTemplate.postForObject(
            aiServiceUrl + "/ai/chat",
            request,
            ChatResponse.class
        ).getAnswer();
    }

    public List<Float> embedding(String text) {
        EmbeddingRequest request = new EmbeddingRequest(text);
        return restTemplate.postForObject(
            aiServiceUrl + "/ai/embedding",
            request,
            EmbeddingResponse.class
        ).getVector();
    }
}
```

### Python AI 服务规范

#### 目录结构
```
ai-service/
├── app/
│   ├── api/            # API 路由
│   │   ├── chat.py
│   │   ├── embedding.py
│   │   ├── ocr.py
│   │   └── analyze.py
│   ├── services/       # 业务逻辑
│   │   ├── llm_service.py
│   │   ├── vector_service.py
│   │   ├── ocr_service.py
│   │   └── rag_service.py
│   ├── models/         # 数据模型（Pydantic）
│   ├── config/         # 配置
│   ├── utils/          # 工具函数
│   └── main.py         # 入口文件
├── tests/              # 测试
├── requirements.txt    # 依赖
└── Dockerfile
```

#### 编码规范
- 遵循 PEP 8 规范
- 使用类型注解（Type Hints）
- 使用 Pydantic 进行数据校验
- 异步优先（async/await）
- 日志记录使用 loguru
- 环境变量管理使用 python-dotenv

#### API 示例
```python
# FastAPI 路由示例
from fastapi import APIRouter, HTTPException
from pydantic import BaseModel

router = APIRouter(prefix="/ai", tags=["AI"])

class ChatRequest(BaseModel):
    question: str
    context: list[str] = []

class ChatResponse(BaseModel):
    answer: str
    sources: list[str] = []

@router.post("/chat", response_model=ChatResponse)
async def chat(request: ChatRequest):
    """RAG 增强的对话接口"""
    try:
        # 调用 RAG 服务
        answer, sources = await rag_service.query(
            question=request.question,
            context=request.context
        )
        return ChatResponse(answer=answer, sources=sources)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
```

### Vue 前端规范

#### 目录结构
```
frontend/
├── pages/              # Nuxt 3 页面（自动路由）
├── components/         # 组件
│   ├── common/        # 通用组件
│   ├── item/          # 物品相关组件
│   └── knowledge/     # 知识库相关组件
├── composables/        # 组合式函数
├── stores/             # Pinia 状态管理
├── api/                # API 调用
├── types/              # TypeScript 类型定义
├── assets/             # 静态资源
└── nuxt.config.ts      # Nuxt 配置
```

#### 编码规范
- 使用 Composition API（`<script setup>`）
- 组件名使用 PascalCase
- 使用 TypeScript 编写
- Props 必须定义类型
- 事件使用 `emit` 定义
- 使用 Composables 复用逻辑
- CSS 使用 scoped 样式

#### 组件示例
```vue
<script setup lang="ts">
interface Props {
  itemId: number
}

interface Emits {
  (e: 'update', id: number): void
  (e: 'delete', id: number): void
}

const props = defineProps<Props>()
const emit = defineEmits<Emits>()

// 使用 Composable
const { item, loading, error } = useItem(props.itemId)

const handleUpdate = () => {
  emit('update', props.itemId)
}
</script>

<template>
  <div class="item-card">
    <div v-if="loading">加载中...</div>
    <div v-else-if="error">{{ error }}</div>
    <div v-else>
      <h3>{{ item.name }}</h3>
      <button @click="handleUpdate">更新</button>
    </div>
  </div>
</template>

<style scoped>
.item-card {
  padding: 1rem;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
}
</style>
```

## 数据库设计原则

### PostgreSQL
- 使用 UUID 作为主键（安全性考虑）
- 时间字段使用 `timestamp with time zone`
- 软删除：添加 `deleted_at` 字段
- 审计字段：`created_at`, `updated_at`, `created_by`, `updated_by`
- 枚举类型优先使用数据库 ENUM 或整型 + 常量映射
- 合理使用索引（查询字段、外键）
- JSON 字段用于存储扩展属性

### 向量存储
- 知识库文本使用 pgvector 存储向量
- 向量维度：1536（OpenAI ada-002）或其他模型对应维度
- 使用 HNSW 索引加速检索

## 安全规范

### 认证与授权
- JWT Token 认证（Access Token + Refresh Token）
- Access Token 有效期：2 小时
- Refresh Token 有效期：7 天
- 密码使用 BCrypt 加密（强度 10）
- 敏感操作需要二次验证

### 权限设计
```
角色层级：
- ADMIN（管理员）：所有权限
- MEMBER（家庭成员）：基本读写权限
- GUEST（访客）：只读权限
```

### 数据安全
- 所有 API 输入必须校验
- SQL 使用参数化查询（JPA 自动处理）
- 文件上传限制类型和大小
- XSS 防护：前端输出转义
- CSRF 防护：使用 CSRF Token
- HTTPS 强制加密传输

## AI 功能设计

### RAG（检索增强生成）流程
```
用户提问
  ↓
向量化查询
  ↓
从向量数据库检索相关文档（Top K）
  ↓
构建 Prompt（问题 + 检索到的上下文）
  ↓
调用 LLM 生成答案
  ↓
返回答案 + 引用来源
```

### OCR 识别流程
```
用户上传图片
  ↓
调用 PaddleOCR 识别文字
  ↓
结构化提取（物品名称、数量、日期等）
  ↓
返回识别结果 + 可信度
```

### 智能推荐
- 基于物品使用频率推荐补货
- 基于季节和历史数据推荐整理方案
- 基于知识库内容推荐相关文档

## 开发指南

### 环境要求
- **Java**: JDK 25+
- **Python**: Python 3.11+
- **Node.js**: Node 20+
- **数据库**: PostgreSQL 15+, Redis 7+
- **Docker**: Docker 24+, Docker Compose 2.20+
- **Maven**: Maven 4.0.0-rc-5+

### 本地开发

#### 启动后端（Spring Boot）
```bash
cd backend-java
./mvnw spring-boot:run
# 或
./gradlew bootRun
```

#### 启动 AI 服务（Python）
```bash
cd ai-service-python
python -m venv venv
source venv/bin/activate  # Windows: venv\Scripts\activate
pip install -r requirements.txt
uvicorn app.main:app --reload --port 8000
```

#### 启动前端（Vue）
```bash
cd frontend-vue
pnpm install
pnpm dev
```

### Docker 部署
```bash
# 构建并启动所有服务
docker-compose up -d

# 查看日志
docker-compose logs -f

# 停止服务
docker-compose down
```

### 环境变量配置

#### Spring Boot (application.yml)
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/family_management
    username: ${DB_USER}
    password: ${DB_PASSWORD}
  redis:
    host: ${REDIS_HOST:localhost}
    port: ${REDIS_PORT:6379}

ai:
  service:
    url: ${AI_SERVICE_URL:http://localhost:8000}

jwt:
  secret: ${JWT_SECRET}
  access-token-validity: 7200  # 2小时
  refresh-token-validity: 604800  # 7天
```

#### Python AI 服务 (.env)
```
OPENAI_API_KEY=your-api-key
ANTHROPIC_API_KEY=your-api-key
VECTOR_DB_URL=http://localhost:6333
REDIS_URL=redis://localhost:6379
LOG_LEVEL=INFO
```

### 测试
```bash
# Java 单元测试
./mvnw test

# Python 测试
pytest

# 前端测试
pnpm test
```

## pnpm 包管理规范

### 为什么使用 pnpm

1. **性能优秀**：比 npm/yarn 快 2-3 倍
2. **节省磁盘空间**：使用硬链接和符号链接，避免重复安装
3. **严格依赖管理**：防止幽灵依赖（Phantom Dependencies）
4. **Monorepo 支持**：原生支持 workspace，适合多包管理

### pnpm 常用命令

```bash
# 安装依赖
pnpm install              # 安装所有依赖
pnpm add <package>        # 添加依赖到 dependencies
pnpm add -D <package>     # 添加依赖到 devDependencies
pnpm add -g <package>     # 全局安装

# 删除依赖
pnpm remove <package>     # 移除依赖

# 更新依赖
pnpm update               # 更新所有依赖
pnpm update <package>     # 更新指定依赖

# 运行脚本
pnpm dev                  # 开发模式
pnpm build                # 构建
pnpm test                 # 测试
pnpm lint                 # 代码检查

# 清理缓存
pnpm store prune          # 清理未使用的包
```

### 项目配置

#### .npmrc（推荐配置）
```ini
# 使用 pnpm 严格模式
shamefully-hoist=false
strict-peer-dependencies=false

# 国内镜像加速（可选）
registry=https://registry.npmmirror.com

# 锁定包管理器
engine-strict=true
```

#### package.json
```json
{
  "name": "family-management-frontend",
  "version": "1.0.0",
  "private": true,
  "type": "module",
  "packageManager": "pnpm@9.0.0",
  "engines": {
    "node": ">=20.0.0",
    "pnpm": ">=9.0.0"
  },
  "scripts": {
    "dev": "nuxt dev",
    "build": "nuxt build",
    "generate": "nuxt generate",
    "preview": "nuxt preview",
    "postinstall": "nuxt prepare",
    "lint": "eslint .",
    "lint:fix": "eslint . --fix",
    "type-check": "vue-tsc --noEmit"
  }
}
```

### 依赖管理最佳实践

#### 版本锁定
- ✅ 使用 `pnpm-lock.yaml` 锁定版本（提交到 Git）
- ✅ 定期更新依赖（每月检查一次）
- ✅ 使用 `^` 前缀允许补丁版本更新
- ❌ 不要使用 `*` 或 `latest`

#### 依赖分类
```json
{
  "dependencies": {
    // 生产环境需要的包
    "vue": "^3.4.0",
    "nuxt": "^4.0.0"
  },
  "devDependencies": {
    // 开发环境需要的包
    "typescript": "^5.3.0",
    "eslint": "^8.56.0"
  }
}
```

#### 幽灵依赖处理
pnpm 默认使用非扁平化的 node_modules 结构，自动防止幽灵依赖。如果某个包未在 package.json 中声明，将无法 import。

```javascript
// ❌ 错误：未声明的依赖
import _ from 'lodash'  // 即使其他包依赖了 lodash，这里也会报错

// ✅ 正确：显式声明依赖
// 先运行: pnpm add lodash
import _ from 'lodash'
```

### Workspace 支持（可选）

如果未来需要 Monorepo 管理多个前端项目：

#### pnpm-workspace.yaml
```yaml
packages:
  - 'frontend-web'
  - 'frontend-admin'
  - 'packages/*'
```

#### 跨包依赖
```json
{
  "dependencies": {
    "@family/shared": "workspace:*"
  }
}
```

## 项目约束

### 性能要求
- API 响应时间 < 500ms（不含 AI 调用）
- AI 对话响应时间 < 5s
- 支持 100+ 并发用户
- 知识库向量检索 < 1s

### 数据限制
- 单个文件上传 < 50MB
- 知识库单文档 < 10MB
- 物品图片 < 5MB
- 向量数据库存储上限：100万条

### 兼容性
- 浏览器：Chrome 90+, Firefox 88+, Safari 14+, Edge 90+
- 移动端：iOS 14+, Android 10+
- 响应式设计，支持屏幕宽度 >= 375px

## 最佳实践

### 不要做
- ❌ 不要在 Controller 层写业务逻辑
- ❌ 不要直接返回 Entity，使用 DTO 转换
- ❌ 不要在循环中调用数据库（N+1 问题）
- ❌ 不要硬编码配置，使用配置文件或环境变量
- ❌ 不要在前端存储敏感信息（密码、密钥等）
- ❌ 不要跳过输入校验
- ❌ 不要在代码中打印敏感日志（密码、Token）

### 应该做
- ✅ 使用 DTO 进行数据传输
- ✅ 使用分页查询大数据集
- ✅ 合理使用缓存（Redis）
- ✅ 日志记录关键操作
- ✅ 异常处理要具体（不要吞掉异常）
- ✅ 定期备份数据库
- ✅ 使用事务保证数据一致性
- ✅ API 版本控制（如 `/api/v1/items`）

## Git 工作流

### 分支策略
- `main`: 生产环境分支
- `develop`: 开发分支
- `feature/*`: 功能分支
- `hotfix/*`: 紧急修复分支

### Commit 规范
```
<type>(<scope>): <subject>

type: feat, fix, docs, style, refactor, test, chore
scope: 模块名（item, knowledge, ai, auth 等）
subject: 简短描述

示例:
feat(item): 添加物品过期提醒功能
fix(auth): 修复 JWT Token 过期时间计算错误
docs(readme): 更新部署文档
```

## 监控与运维

### 日志
- Spring Boot: 使用 Logback，日志级别 INFO
- Python: 使用 loguru，结构化日志
- 日志文件按天滚动，保留 30 天

### 健康检查
- Spring Boot Actuator: `/actuator/health`
- FastAPI: `/health`
- 前端: `/api/health`

### 监控指标
- 系统负载（CPU、内存、磁盘）
- API 响应时间
- 数据库连接池状态
- Redis 命中率
- AI 服务调用次数和耗时

## 参考资源

- [Spring Boot 官方文档](https://spring.io/projects/spring-boot)
- [Spring AI 文档](https://docs.spring.io/spring-ai/reference/)
- [FastAPI 文档](https://fastapi.tiangolo.com/)
- [LangChain 文档](https://python.langchain.com/)
- [Vue 3 文档](https://vuejs.org/)
- [Nuxt 3 文档](https://nuxt.com/)
- [PostgreSQL pgvector](https://github.com/pgvector/pgvector)
