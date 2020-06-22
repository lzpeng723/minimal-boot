---
home: true
heroImage: /logo.png
actionText: 快速上手 →
actionLink: /zh/guide/
features:
- title: Spring 全家桶
  details: 后端采用 Spring Boot， Spring Security， Spring Security OAuth2， Spring Data JPA。
- title: Vue 全家桶
  details: 前端使用 Vue， vue-element-admin， ElementUI， axios。
- title: 前后端分离
  details: 提供前端Vue、后端Spring Boot完全分离的后台管理系统基础模板。
- title: 代码生成器
  details: 只需写好实体类即可在线生成前后端代码，增删改查导入导出等直接使用。
- title: 基础功能完善
  details: 内置用户、角色、菜单、部门、岗位、通知、监控、代码生成等基础功能。
- title: 持续更新
  details: Lzpeng Boot 还在持续更新，计划实现工作流、报表模块。
footer: MIT Licensed | Copyright (c) 2020 lzpeng723 All Rights Reserved lzpeng723
---

### 后台管理系统像数 1, 2, 3 一样容易

``` bash
# 克隆项目
git clone https://github.com/lzpeng723/lzpeng-boot.git

# 进入后端项目目录 安装依赖 启动项目
cd lzpeng-boot/lzpeng-boot-server && mvn compile && mvn springboot:run

# 进入前端项目目录 cnpm 安装依赖 启动项目
cd lzpeng-boot/lzpeng-boot-client && cnpm i -S && npm run dev
```

::: warning 注意
请确保你的 jdk 版本 >= 1.8

请确保你的 IDEA 安装 lombok 插件

请确保你已将 npm 的镜像设置为[淘宝镜像](https://registry.npm.taobao.org)并全局安装 cnpm ,然后使用 cnpm 安装依赖, 使用 npm 安装依赖 会出现 Can't found moudule "node-sass" 的错误
:::
