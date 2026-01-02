# 家庭管理系统开发讨论记录

## 时间
2026-01-03

## 项目背景
作为一名程序员，随着年龄增长记忆力衰退，计划开发一套系统用于：
- 管理家里的物品
- 管理电脑里的知识库
- 接入 AI 充当家庭管家和助手
- 帮助维护和分析知识库
- 管理家中物品的存放位置
- 分析家庭数据
- 支持远程录入和查看数据

## 初始技术方案
原计划使用：Spring Boot 3 + Vue 3

## 使用场景确认
- ✅ 随时随地访问
- ✅ 多人协作
- ✅ AI 助手为核心

## 技术方案讨论

### 方案一：现代化全栈方案
**技术栈**：
- 后端: Node.js + NestJS
- 前端: Next.js 14+ (React) 或 Nuxt 3 (Vue)
- 数据库: PostgreSQL + Redis
- 向量数据库: Qdrant 或 Milvus
- AI: LangChain + OpenAI/Claude API
- 移动端: PWA 或 React Native

**优势**：
- TypeScript 全栈，代码复用性高
- Next.js/Nuxt 支持 SSR，SEO 友好
- 向量数据库支持语义搜索
- 部署简单，Docker 一键部署

### 方案二：保留原技术栈（优化版）
**技术栈**：
- 后端: Spring Boot 3 + Spring AI
- 前端: Vue 3 + Nuxt 3
- 数据库: PostgreSQL + pgvector
- AI: Spring AI 框架

**优势**：
- 熟悉 Java 生态
- Spring AI 已经很成熟
- pgvector 减少组件复杂度

### 方案三：Python 全栈
**技术栈**：
- 后端: FastAPI + Vue3
- AI: Python AI 生态

**优势**：
- Python 的 AI 生态最丰富
- 适合重度 AI 功能

## 最终选择：Spring Boot + Vue3 + Python AI 微服务（混合架构）

### 系统架构
```
┌─────────────────┐
│   Vue 3 前端     │
└────────┬────────┘
         │
┌────────▼────────────────────┐
│  Spring Boot 主服务          │
│  - 用户认证/权限             │
│  - 物品管理 CRUD            │
│  - 知识库存储               │
│  - 业务逻辑                 │
└────────┬────────────────────┘
         │ REST/gRPC 调用
┌────────▼────────────────┐
│  Python AI 服务         │
│  - 向量化处理           │
│  - RAG 知识库检索       │
│  - 图像识别 (OCR)       │
│  - 数据分析/可视化       │
│  - 本地模型运行         │
└─────────────────────────┘
```

### 后端技术栈

#### Spring Boot 主服务
- Spring Boot 3.4+
- Spring AI（集成 OpenAI/Ollama/Azure OpenAI）
- Spring Security（JWT 认证，多人权限管理）
- PostgreSQL + pgvector（主数据库 + 向量搜索）
- Redis（缓存 + 会话管理）
- MinIO/Aliyun OSS（文件存储，知识库附件）

#### Python AI 服务
- FastAPI（高性能异步框架）
- LangChain（RAG 和 Agent 框架）
- Qdrant/ChromaDB（向量数据库）
- PaddleOCR（中文 OCR，免费）
- Ollama（可选，本地运行开源模型）

**AI 服务提供的 API**：
- POST /ai/embedding         # 文本向量化
- POST /ai/chat             # 对话（RAG 增强）
- POST /ai/ocr              # 图片识别
- POST /ai/analyze          # 数据分析
- POST /ai/recommend        # 智能推荐

### 前端技术栈
- Vue 3 + Vite
- Nuxt 3（支持 SSR 和 PWA）
- Pinia（状态管理）
- Element Plus / Ant Design Vue（UI 组件库）
- Vite PWA（支持离线访问和手机安装）

### 部署方案
- Docker Compose（本地 NAS/服务器）
- Traefik/Nginx 反向代理 + SSL
- 内网穿透：Tailscale（推荐）或 FRP

### 服务间通信示例
```java
// Spring Boot 中调用 Python AI 服务
@Service
public class AIService {
    @Autowired
    private RestTemplate restTemplate;

    public String chat(String question, List<String> context) {
        return restTemplate.postForObject(
            "http://ai-service:8000/ai/chat",
            new ChatRequest(question, context),
            String.class
        );
    }
}
```

### Docker Compose 配置
```yaml
services:
  postgres:
    image: pgvector/pgvector:latest

  redis:
    image: redis:alpine

  backend:
    build: ./backend-java
    ports: ["8080:8080"]

  ai-service:
    build: ./ai-service-python
    ports: ["8000:8000"]
    # GPU 支持（可选）
    deploy:
      resources:
        reservations:
          devices:
            - driver: nvidia
              count: 1

  frontend:
    build: ./frontend-vue
    ports: ["3000:3000"]
```

## 核心功能模块

### 1. 物品管理
- 位置标签（房间、柜子、抽屉层级）
- 图片上传 + OCR 识别
- 过期提醒（食品、药品）
- AI 建议整理方案

### 2. 知识库
- 文档、笔记、代码片段
- 向量化存储 + 语义搜索
- AI 自动分类和打标签
- 知识图谱可视化

### 3. AI 家庭管家
- 自然语言查询："家里还有几瓶酱油？"
- 智能提醒："该买菜了，冰箱快空了"
- 数据分析："本月家庭开支分析"
- 语音交互（可选）

### 4. 多人协作
- 家庭成员账号 + 权限分级
- 共享清单（购物清单、待办事项）
- 操作日志

## 混合架构的优势

### 为什么选择 Spring Boot + Python 混合？

**Spring Boot 纯 Java 的局限**：
- AI 生态不如 Python 丰富
- 复杂 AI 功能（本地模型微调、图像识别）支持较弱
- LangChain、Hugging Face 等库需要等 Java 版本跟进

**混合架构的优点**：
- ✅ AI 能力最强：Python 的 AI 生态无可比拟
  - LangChain、LlamaIndex（RAG 框架）
  - Transformers、OpenCV（图像处理）
  - Pandas、NumPy（数据分析）
  - 本地模型支持（Ollama、Llama.cpp）
- ✅ 各司其职：Java 做业务，Python 做 AI
- ✅ 可扩展：AI 服务可独立升级、横向扩展
- ✅ 成本优化：AI 服务可以按需启动

**缺点**：
- 架构稍复杂（需要服务间通信）
- 部署两个服务（但 Docker Compose 可简化）

## 实际收益

1. **OCR 物品识别**：拍照自动识别物品名称
2. **智能问答**：语义搜索知识库，比关键词准确
3. **本地模型**：敏感数据不出家门，用 Ollama 运行本地 LLM
4. **数据洞察**：Python 的数据分析库生成可视化报告
5. **未来扩展**：语音助手、图像搜索等高级功能

## 下一步计划

可以开始的工作：
1. 生成完整的项目脚手架
2. 配置 Spring Boot 和 FastAPI 的服务间调用
3. 实现示例功能（如 RAG 知识库问答）
4. 创建 CLAUDE.md 项目文档
5. 设计数据库表结构
