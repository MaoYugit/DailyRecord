# Time Shards (时间碎片) | 项目开发规格书

## 1. 项目概述 (Project Overview)
*   **项目名称：** Time Shards (时间碎片)
*   **核心概念：** Time Fracture Zone (时间破碎区) —— 知识不是线性的流水账，而是散落在时空中的结晶。
*   **核心功能：** 个人知识管理、Markdown 写作/渲染、碎片化展示、沉浸式阅读、双模主题（昼/夜）。

---

## 2. 视觉与交互规范 (UI/UX Design)

### 2.1 视觉风格 (Visual Identity)
*   **关键词：** 极简 (Minimalist)、深邃 (Deep)、故障艺术 (Glitch)、毛玻璃 (Glassmorphism)。
*   **字体规范：**
    *   **标题：** 无衬线字体 (Inter, Roboto, 或系统默认 sans-serif)，强调现代感。
    *   **代码/正文数字：** 等宽字体 (JetBrains Mono, Fira Code)，强调“终端/数据”感。
*   **配色方案 (CSS Variables 定义)：**

| 颜色语义        | ☀️ 白天模式 (Light) | 🌙 黑夜模式 (Dark / Fracture) | 说明             |
| :-------------- | :----------------- | :--------------------------- | :--------------- |
| **背景色**      | `#FFFFFF` (纯白)   | `#0A0A0A` (深邃黑)           | 页面底色         |
| **容器背景**    | `#F3F4F6` (灰白)   | `rgba(255, 255, 255, 0.05)`  | 毛玻璃卡片背景   |
| **主文字**      | `#374151` (深灰)   | `#E0E0E0` (灰白)             | 正文内容         |
| **次文字**      | `#9CA3AF` (浅灰)   | `#6B7280` (暗灰)             | 元数据、页脚     |
| **强调色-理智** | `#000000` (黑)     | `#06B6D4` (赛博青)           | 按钮、链接、代码 |
| **强调色-神秘** | `#7C3AED` (紫)     | `#8B5CF6` (微光紫)           | 悬停、高亮       |

### 2.2 核心交互
1.  **首页视图 (The Void)：**
    *   摒弃传统列表，使用 **瀑布流卡片 (Masonry Layout)** 或 **3D 粒子云 (Three.js)** 展示文章。
    *   卡片悬停效果：轻微上浮 + 边缘发光 + 边框出现“断裂/错位”的故障动画。
2.  **黑白切换 (Theme Switch)：**
    *   开关位于导航栏右侧。
    *   **切换动画：** 不仅仅是变色，要有“灯光闪烁”或“信号重连”的过渡效果。
    *   **夜间特权：** 只有在夜间模式下，卡片才启用毛玻璃特效和青色霓虹光晕。
3.  **导航文案：**
    *   保持中文常规命名以确保易用性：**首页、分类、归档、关于、搜索**。
    *   但在**Loading 界面**、**404 页面**或**空状态**时，使用“Time Shards”风格的术语（如：*“正在扫描时间碎片...”*）。

---

## 3. 前端技术规格 (Frontend Specs)

### 3.1 技术栈
*   **框架：** Vue 3 (Composition API + `<script setup>`)
*   **构建工具：** Vite
*   **路由：** Vue Router 4
*   **状态管理：** Pinia (管理 User 信息、Theme 状态、Global Loading)
*   **样式方案：** **原生 CSS (Scoped)** + CSS Variables (`:root`) 实现主题切换。
*   **核心库：**
    *   `markdown-it`: 解析 Markdown。
    *   `highlight.js` 或 `Prism.js`: 代码高亮 (需定制一套黑色主题)。
    *   `KaTeX`: 数学公式渲染。
    *   `Three.js` : 用于首页粒子特效。
    *   `Axios`: 前后端通信。

### 3.2 目录结构规划
```text
src/
├── api/             # Axios 封装与接口定义 (article.js, user.js)
├── assets/          # 静态资源 (fonts, images, base.css)
├── components/      # 公共组件
│   ├── layout/      # Navbar.vue, Footer.vue
│   ├── common/      # ShardCard.vue (文章卡片), GlitchText.vue (特效字)
│   └── markdown/    # MarkdownViewer.vue (封装渲染逻辑)
├── views/           # 页面级组件 (Home, ArticleDetail, Category, Archive)
├── stores/          # Pinia 状态 (themeStore, userStore)
├── utils/           # 工具类 (date-format, glitch-effect)
└── App.vue
```

---

## 4. 后端技术规格 (Backend Specs)

### 4.1 技术栈
*   **语言/版本：** Java 21
*   **框架：** Spring Boot 3.x
*   **ORM：** MyBatis
*   **数据库：** MySQL 8.0
*   **构建工具：** Maven
*   **接口文档：** Swagger(集成在 Spring Boot 中)

---

## 5. 数据库设计 (Database Schema)

```sql
-- 1. 创建数据库
CREATE DATABASE IF NOT EXISTS myblog_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE myblog_db;

-- ==========================================
-- 系统模块 (sys_)
-- ==========================================

-- 2. 用户表 (sys_user)
-- 改动：表名单数+前缀，移除Enum，时间字段规范化，移除物理外键
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(60) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码(BCrypt加密)',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `nickname` varchar(60) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
  `bio` varchar(255) DEFAULT NULL COMMENT '简介',
  `role` tinyint(4) NOT NULL DEFAULT '0' COMMENT '角色: 0-普通用户, 1-管理员',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 3. 系统配置表 (sys_config) - 原 options
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `config_key` varchar(100) NOT NULL COMMENT '配置键',
  `config_value` longtext COMMENT '配置值',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_config_key` (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

-- 4. 附件表 (sys_attachment)
DROP TABLE IF EXISTS `sys_attachment`;
CREATE TABLE `sys_attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '上传用户ID',
  `original_name` varchar(255) NOT NULL COMMENT '原文件名',
  `file_path` varchar(255) NOT NULL COMMENT '物理路径',
  `file_url` varchar(255) NOT NULL COMMENT '访问URL',
  `file_type` varchar(50) DEFAULT NULL COMMENT '文件类型',
  `file_size` bigint(20) DEFAULT '0' COMMENT '文件大小(字节)',
  `storage_location` tinyint(4) DEFAULT '0' COMMENT '存储位置: 0-本地, 1-阿里云, 2-七牛云',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='附件表';


-- ==========================================
-- 博客业务模块 (blog_)
-- ==========================================

-- 5. 分类表 (blog_category)
DROP TABLE IF EXISTS `blog_category`;
CREATE TABLE `blog_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL COMMENT '分类名称',
  `slug` varchar(60) NOT NULL COMMENT 'URL别名',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父分类ID',
  `sort` int(11) DEFAULT '0' COMMENT '排序(数字越小越前)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`),
  UNIQUE KEY `uk_slug` (`slug`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分类表';

-- 6. 标签表 (blog_tag)
DROP TABLE IF EXISTS `blog_tag`;
CREATE TABLE `blog_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL COMMENT '标签名称',
  `slug` varchar(60) NOT NULL COMMENT 'URL别名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`),
  UNIQUE KEY `uk_slug` (`slug`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签表';

-- 7. 文章主表 (blog_article)
DROP TABLE IF EXISTS `blog_article`;
CREATE TABLE `blog_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '作者ID',
  `category_id` bigint(20) DEFAULT NULL COMMENT '分类ID',
  `title` varchar(255) NOT NULL COMMENT '文章标题',
  `slug` varchar(255) NOT NULL COMMENT '文章别名(URL)',
  `summary` varchar(500) DEFAULT NULL COMMENT '文章摘要',
  `content` longtext NOT NULL COMMENT '文章内容(Markdown)',
  `content_html` longtext COMMENT '文章内容(HTML缓存)',
  `cover_image` varchar(255) DEFAULT NULL COMMENT '封面图片',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态: 0-草稿, 1-已发布, 2-下架',
  `is_top` tinyint(1) DEFAULT '0' COMMENT '是否置顶: 0-否, 1-是',
  `view_count` int(11) DEFAULT '0' COMMENT '浏览量',
  `comment_count` int(11) DEFAULT '0' COMMENT '评论数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_slug` (`slug`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章表';

-- 8. 文章-标签关联表 (blog_article_tag)
DROP TABLE IF EXISTS `blog_article_tag`;
CREATE TABLE `blog_article_tag` (
  `article_id` bigint(20) NOT NULL COMMENT '文章ID',
  `tag_id` bigint(20) NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`article_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章标签关联表';

-- 9. 文章扩展属性表 (blog_article_meta)
-- 用于存储不常用的字段，避免主表过大
DROP TABLE IF EXISTS `blog_article_meta`;
CREATE TABLE `blog_article_meta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `article_id` bigint(20) NOT NULL COMMENT '文章ID',
  `meta_key` varchar(50) NOT NULL COMMENT '属性名',
  `meta_value` longtext COMMENT '属性值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_article_key` (`article_id`, `meta_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章扩展属性表';

-- 10. 评论表 (blog_comment)
DROP TABLE IF EXISTS `blog_comment`;
CREATE TABLE `blog_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `article_id` bigint(20) NOT NULL COMMENT '文章ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '评论人ID(空代表游客)',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父评论ID',
  `nickname` varchar(60) NOT NULL COMMENT '评论人昵称',
  `email` varchar(100) DEFAULT NULL COMMENT '评论人邮箱',
  `website` varchar(200) DEFAULT NULL COMMENT '评论人网站',
  `content` varchar(1000) NOT NULL COMMENT '评论内容',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态: 0-待审核, 1-通过, 2-垃圾评论',
  `is_admin` tinyint(1) DEFAULT '0' COMMENT '是否博主回复',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  KEY `idx_article_id` (`article_id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表';
```







# Role
你是一位全栈开发专家，擅长 java后端与 Vue 3 前端的对接。

# Context
我正在开发一个名为 "Time Shards" (时间碎片) 的个人记录/博客系统。
目前前端已经完成：
- 使用 Vue 3 + Vite + Pinia 开发。
- 首页已经实现了瀑布流布局，展示文章列表。
- 安装了 Axios 但尚未配置。
- 目前数据是硬编码在 `Home.vue` 中的。

# Goal
我需要你帮我搭建一个可连接的后端服务，并告诉我如何在前端对接它。

# Requirements

1. **后端技术栈**:
   
   *   **语言/版本：** Java 21
   *   **框架：** Spring Boot 3.x
   *   **ORM：** MyBatis
   *   **数据库：** MySQL 8.0
   *   **构建工具：** Maven
   *   **接口文档：** Swagger(集成在 Spring Boot 中)
   
2. **数据结构 (Data Model)**:
   
   ```sql
   -- 1. 创建数据库
   CREATE DATABASE IF NOT EXISTS myblog_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   USE myblog_db;
   
   -- ==========================================
   -- 系统模块 (sys_)
   -- ==========================================
   
   -- 2. 用户表 (sys_user)
   -- 改动：表名单数+前缀，移除Enum，时间字段规范化，移除物理外键
   DROP TABLE IF EXISTS `sys_user`;
   CREATE TABLE `sys_user` (
     `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
     `username` varchar(60) NOT NULL COMMENT '用户名',
     `password` varchar(100) NOT NULL COMMENT '密码(BCrypt加密)',
     `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
     `nickname` varchar(60) DEFAULT NULL COMMENT '昵称',
     `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
     `bio` varchar(255) DEFAULT NULL COMMENT '简介',
     `role` tinyint(4) NOT NULL DEFAULT '0' COMMENT '角色: 0-普通用户, 1-管理员',
     `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除: 0-未删除, 1-已删除',
     PRIMARY KEY (`id`),
     UNIQUE KEY `uk_username` (`username`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
   
   -- 3. 系统配置表 (sys_config) - 原 options
   DROP TABLE IF EXISTS `sys_config`;
   CREATE TABLE `sys_config` (
     `id` bigint(20) NOT NULL AUTO_INCREMENT,
     `config_key` varchar(100) NOT NULL COMMENT '配置键',
     `config_value` longtext COMMENT '配置值',
     `description` varchar(255) DEFAULT NULL COMMENT '描述',
     PRIMARY KEY (`id`),
     UNIQUE KEY `uk_config_key` (`config_key`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';
   
   -- 4. 附件表 (sys_attachment)
   DROP TABLE IF EXISTS `sys_attachment`;
   CREATE TABLE `sys_attachment` (
     `id` bigint(20) NOT NULL AUTO_INCREMENT,
     `user_id` bigint(20) NOT NULL COMMENT '上传用户ID',
     `original_name` varchar(255) NOT NULL COMMENT '原文件名',
     `file_path` varchar(255) NOT NULL COMMENT '物理路径',
     `file_url` varchar(255) NOT NULL COMMENT '访问URL',
     `file_type` varchar(50) DEFAULT NULL COMMENT '文件类型',
     `file_size` bigint(20) DEFAULT '0' COMMENT '文件大小(字节)',
     `storage_location` tinyint(4) DEFAULT '0' COMMENT '存储位置: 0-本地, 1-阿里云, 2-七牛云',
     `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     PRIMARY KEY (`id`),
     KEY `idx_user_id` (`user_id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='附件表';
   
   
   -- ==========================================
   -- 博客业务模块 (blog_)
   -- ==========================================
   
   -- 5. 分类表 (blog_category)
   DROP TABLE IF EXISTS `blog_category`;
   CREATE TABLE `blog_category` (
     `id` bigint(20) NOT NULL AUTO_INCREMENT,
     `name` varchar(60) NOT NULL COMMENT '分类名称',
     `slug` varchar(60) NOT NULL COMMENT 'URL别名',
     `description` varchar(200) DEFAULT NULL COMMENT '描述',
     `parent_id` bigint(20) DEFAULT '0' COMMENT '父分类ID',
     `sort` int(11) DEFAULT '0' COMMENT '排序(数字越小越前)',
     `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
     PRIMARY KEY (`id`),
     UNIQUE KEY `uk_name` (`name`),
     UNIQUE KEY `uk_slug` (`slug`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分类表';
   
   -- 6. 标签表 (blog_tag)
   DROP TABLE IF EXISTS `blog_tag`;
   CREATE TABLE `blog_tag` (
     `id` bigint(20) NOT NULL AUTO_INCREMENT,
     `name` varchar(60) NOT NULL COMMENT '标签名称',
     `slug` varchar(60) NOT NULL COMMENT 'URL别名',
     `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
     PRIMARY KEY (`id`),
     UNIQUE KEY `uk_name` (`name`),
     UNIQUE KEY `uk_slug` (`slug`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签表';
   
   -- 7. 文章主表 (blog_article)
   DROP TABLE IF EXISTS `blog_article`;
   CREATE TABLE `blog_article` (
     `id` bigint(20) NOT NULL AUTO_INCREMENT,
     `user_id` bigint(20) NOT NULL COMMENT '作者ID',
     `category_id` bigint(20) DEFAULT NULL COMMENT '分类ID',
     `title` varchar(255) NOT NULL COMMENT '文章标题',
     `slug` varchar(255) NOT NULL COMMENT '文章别名(URL)',
     `summary` varchar(500) DEFAULT NULL COMMENT '文章摘要',
     `content` longtext NOT NULL COMMENT '文章内容(Markdown)',
     `content_html` longtext COMMENT '文章内容(HTML缓存)',
     `cover_image` varchar(255) DEFAULT NULL COMMENT '封面图片',
     `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态: 0-草稿, 1-已发布, 2-下架',
     `is_top` tinyint(1) DEFAULT '0' COMMENT '是否置顶: 0-否, 1-是',
     `view_count` int(11) DEFAULT '0' COMMENT '浏览量',
     `comment_count` int(11) DEFAULT '0' COMMENT '评论数',
     `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
     PRIMARY KEY (`id`),
     UNIQUE KEY `uk_slug` (`slug`),
     KEY `idx_category_id` (`category_id`),
     KEY `idx_user_id` (`user_id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章表';
   
   -- 8. 文章-标签关联表 (blog_article_tag)
   DROP TABLE IF EXISTS `blog_article_tag`;
   CREATE TABLE `blog_article_tag` (
     `article_id` bigint(20) NOT NULL COMMENT '文章ID',
     `tag_id` bigint(20) NOT NULL COMMENT '标签ID',
     PRIMARY KEY (`article_id`,`tag_id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章标签关联表';
   
   -- 9. 文章扩展属性表 (blog_article_meta)
   -- 用于存储不常用的字段，避免主表过大
   DROP TABLE IF EXISTS `blog_article_meta`;
   CREATE TABLE `blog_article_meta` (
     `id` bigint(20) NOT NULL AUTO_INCREMENT,
     `article_id` bigint(20) NOT NULL COMMENT '文章ID',
     `meta_key` varchar(50) NOT NULL COMMENT '属性名',
     `meta_value` longtext COMMENT '属性值',
     PRIMARY KEY (`id`),
     UNIQUE KEY `uk_article_key` (`article_id`, `meta_key`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章扩展属性表';
   
   -- 10. 评论表 (blog_comment)
   DROP TABLE IF EXISTS `blog_comment`;
   CREATE TABLE `blog_comment` (
     `id` bigint(20) NOT NULL AUTO_INCREMENT,
     `article_id` bigint(20) NOT NULL COMMENT '文章ID',
     `user_id` bigint(20) DEFAULT NULL COMMENT '评论人ID(空代表游客)',
     `parent_id` bigint(20) DEFAULT '0' COMMENT '父评论ID',
     `nickname` varchar(60) NOT NULL COMMENT '评论人昵称',
     `email` varchar(100) DEFAULT NULL COMMENT '评论人邮箱',
     `website` varchar(200) DEFAULT NULL COMMENT '评论人网站',
     `content` varchar(1000) NOT NULL COMMENT '评论内容',
     `status` tinyint(4) DEFAULT '0' COMMENT '状态: 0-待审核, 1-通过, 2-垃圾评论',
     `is_admin` tinyint(1) DEFAULT '0' COMMENT '是否博主回复',
     `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
     `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
     PRIMARY KEY (`id`),
     KEY `idx_article_id` (`article_id`),
     KEY `idx_parent_id` (`parent_id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表';
   ```
   
   
   
3. **API 接口需求 (RESTful)**:
   请实现以下接口，并提供详细的 API 文档或说明：
   
   - `GET /api/articles`: 获取文章列表 (支持分页 `page`, `limit` 和分类过滤 `category`)。
   - `GET /api/articles/:id`: 获取单篇文章详情。
   - `POST /api/articles`: 创建新文章。
   - `GET /api/categories`: 获取所有分类列表。
   - `GET /api/search`: 搜索文章 (通过标题或内容)。
   
4. **前端对接指南**:
   - 请提供 `src/api/index.js` (或类似文件) 的代码，封装 Axios 请求以调用上述接口。
   - 告诉我如何修改 `src/views/Home.vue`，将硬编码的 `articles` 数组替换为从 API 获取的数据。

# Deliverables
1. 完整的后端项目代码结构和核心文件。
2. 数据库初始化脚本或说明。
3. 前端 Axios 封装代码。
4. 前端组件修改代码示例。
