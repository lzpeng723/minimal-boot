# Quick Start

``` bash
# clone the project
git clone https://github.com/lzpeng723/lzpeng-boot.git

# open the back-end project directory, install dependencies, start the project
cd lzpeng-boot/lzpeng-boot-server && mvn compile && mvn springboot:run

# open the front-end project directory, cnpm install dependencies, start the project
cd lzpeng-boot/lzpeng-boot-client && cnpm i -S && npm run dev
```

::: warning Warning
Make sure your JDK version >= 1.8

Make sure your IDEA has the lombok plugin installed

Make sure that you have set a mirror of npm to [taobao mirror](https://registry.npm.taobao.org) and cnpm is global installed
:::
