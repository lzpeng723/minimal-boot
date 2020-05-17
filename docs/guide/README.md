# Introduction
The minimalist background management system is a basic template of a background management system, and all other front-end and back-end codes can be automatically generated after the entity class is defined. Separate front-end and back-end development, all use Rest style API for data transmission. Its original intention was to develop quickly, focusing on business code writing.

After writing the entity class and starting the project, can automatically create a table, generate the code related to the entity class on the front-end page, and then place it in the front-end and back-end files after generation. Restart the project. Writing of real business code.

## Development Tools and Environment
### back-end
* jdk 1.8
* maven 3.6.1
* mysql 5.5+
* IDEA 2019.3.3 (install lombok plugin)
* SpringBoot， Spring Security， Spring Security OAuth2， Spring Data JPA ...
### front-end
* node v12.14.0
* bpm 6.13.4
* WebStorm 2018.2.8
* Vue， vue-element-admin， ElementUI， axios ...

## Function Overview

```
- login / logout

- System Management
  - User Management(Assign roles to users here)
  - Role Management(Assign menus and permissions to roles here)
  - Menu Management(Configure menus and permissions here)
  - Department Management
  - Position Management

- System Monitoring
  - Scheduled Tasks(Support Java tasks, Rhino script tasks, Nashorn script tasks)
  - Data Monitoring
  - Server Monitoring(View server cpu, memory, jvm, hard disk and other information)
  - Log Management
     - Request Log(List user request logs and devices used)
     - Method Log(List method execution logs)
     - Server Log(View and download server logs)

- System Tools
  - Component View(Can preview your new Vue component without configuring routing)
  - Icon View
  - Form Construction(Quickly generate Vue components code)
  - Bill Construction(To be developed, the front-end configuration automatically generates entity classes and all codes)
  - Data Dictionary(View details of the tables and columns used in the project)
  - Code Generation Configuration(The template used to generate the code, and the code generation operation is performed on the detailed interface of the data dictionary)
  - Class Loading Information(View class or bean loading information)
  - Query analyzer(Can execute sql, jpql, rhino, nashron, id table lookup name, entity table lookup name, display table definition, display all tables, etc.)
  - System API(View all API interfaces of the system)

```

## Todo

Lzpeng Boot is still under development, here are some features that are not currently supported but are already planned:

- Workflow
- Report

You are welcome to contribute to the development of Lzpeng Boot!

## Why Not...?

### Mybatis

JPA is indeed capable of doing what Mybatis can do in theory, but Mybatis must write mapper files, and JPA is officially provided by Spring. With QueryDSL, complex queries can also be achieved, eliminating the trouble of writing mapper files.

