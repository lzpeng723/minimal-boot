# 极简后台管理系统
本项目是一个后台管理系统的基础架子,为了方便快速开发而编写,
采用前后端分离进行开发,全部使用Rest风格API进行数据传递。
后端项目[在这里](https://www.github.com/lzpeng723/lzpeng-boot-server)
## 开发环境 
* node 12.14.0
* npm 6.13.4
* WebStorm
## 技术栈
* 后端Spring全家桶
* SpringBoot, Spring Security, Spring Security OAuth2, Spring Data JPA, Freemarker 等等
* 前端Vue全家桶
* Vue, vue-element-admin, ElementUI, axios 等等

## 已实现功能

```
- 登录 / 注销

- 系统管理
  - 用户管理(在这里给用户分配角色)
  - 角色管理(在这里给角色分配菜单权限)
  - 菜单管理(在这里配置菜单权限)
  - 部门管理
  - 岗位管理

- 系统监控
  - 定时任务(支持Java类任务, Rhino脚本任务, Nashorn脚本任务)
  - 数据监控
  - 服务监控(查看服务器的cpu,内存,jvm,硬盘等信息)
  - 日志管理
     - 请求日志(列出用户请求日志及所使用设备)
     - 方法日志(列出方法执行日志及)
     - 后台日志(查看并下载服务器日志)

- 系统工具
  - 组件查看(可以在不配置路由的情况下预览自己新建的Vue组件)
  - 图标查看
  - 表单构建(快速构建Vue组件)
  - 单据构建(待开发,前端配置自动生成所有代码)
  - 数据字典(查看项目中所用到的表以及字段详细信息)
  - 代码生成配置(生成代码所用的模板,在数据字典的详情界面进行代码生成操作)
  - 类加载信息(查看类或bean的加载信息)
  - 查询分析器(可执行sql,rhino,nashron,id查表名,实体查表名,显示表定义,显示所有表等)
  - 系统接口(查看系统所有API接口)

```

## 目前正在开发
* 工作流模块(使用bpmn.js )
* 完善部门,岗位与用户角色的关系

## 待实现
* 报表模块
* 代码生成不需要自己写实体类,可以由前端配置

## 快速开发
```bash
# 克隆项目
git clone https://github.com/lzpeng723/lzpeng-boot-client.git
# 进入项目目录
cd lzpeng-boot-client
# 安装依赖 建议先安装cnpm,正应安装更快
cnpm i -S
# 运行
npm run dev
```
* 访问[http://localhost:9527](http://localhost:9527)进入首页

## 参考文档
正在编写中...

## 视频教程
正在录制中...

## 致谢
* [若依管理系统](http://ruoyi.vip/)
* [vue-element-admin](https://panjiachen.gitee.io/vue-element-admin-site/zh/)
