---
home: true
heroImage: /logo.png
actionText: Quick Start →
actionLink: /guide/
features:
- title: Spring Family
  details: The Back-End using Spring Boot， Spring Security， Spring Security OAuth2， Spring Data JPA。
- title: Vue Family
  details: The Front-End using Vue， vue-element-admin， ElementUI， axios。
- title: Back-End and Front-End Separation
  details: Provide front-end Vue, back-end Spring Boot completely separated from the background management system base template.
- title: Code Generator
  details: Only need to write entity class, then online generate the code of front-end and back-end. add, delete, change, select, import and export and so on can be used directly.
- title: Complete Basic Functions
  details: Built-in user, role, menu, department, post, notification, monitoring, code generation and other basic functions.
- title: still updating
  details: Lzpeng Boot's still being updated，plan to implement workflow and report modules.
footer: MIT Licensed | Copyright (c) 2020 lzpeng723 All Rights Reserved lzpeng723
---

### Background Management System is as easy as counting one, two, three.

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
