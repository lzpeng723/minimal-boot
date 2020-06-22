# 快速上手

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