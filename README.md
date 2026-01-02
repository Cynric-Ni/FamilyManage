# 家庭管理系统 (Family Management System)

一个智能家庭管理系统，集成 AI 助手，用于管理家庭物品和知识库。

## 项目结构

```
FamilyManage/
├── backend-java/           # Spring Boot 后端服务
├── ai-service-python/      # Python AI 服务
├── frontend-vue/           # Vue 3 前端应用
├── docs/                   # 项目文档
├── CLAUDE.md              # Claude Code 项目指南
├── history.md             # 项目讨论记录
└── README.md              # 本文件
```

## 技术栈

- **后端**: Spring Boot 3 + Spring AI
- **AI 服务**: Python FastAPI + LangChain
- **前端**: Vue 3 + Nuxt 4 + pnpm
- **数据库**: PostgreSQL + pgvector + Redis
- **部署**: Docker + Docker Compose

## 快速开始

### 环境要求

- Java 17+
- Python 3.11+
- Node.js 20+
- pnpm 9+
- PostgreSQL 15+
- Redis 7+
- Docker (可选)

### 本地开发

详细的开发指南请参考 [CLAUDE.md](./CLAUDE.md)

## 学习项目

本项目是一个**学习导向**的项目，旨在通过实践学习微服务架构、AI 集成等技术。

详细信息请查看 [CLAUDE.md](./CLAUDE.md#⚠️-重要这是一个学习导向的项目)

## 文档

- [项目讨论记录](./history.md) - 技术选型和架构讨论
- [Claude Code 指南](./CLAUDE.md) - 开发规范和最佳实践

## 开发进度

- [x] 项目初始化
- [x] 技术方案确定
- [x] 项目文档编写
- [ ] 后端基础搭建
- [ ] 前端基础搭建
- [ ] AI 服务搭建
- [ ] 用户认证系统
- [ ] 物品管理功能
- [ ] 知识库功能
- [ ] AI 对话功能
