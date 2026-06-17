#dormitory-management-system
 🏢 高校宿舍管理系统

基于 Spring Boot + Vue 2 的轻量级高校宿舍管理平台，包含用户管理、楼宇管理、宿舍管理、学生管理、缺勤记录五大核心模块。

🛠 技术栈

| 前端 | 后端 |
|------|------|
| Vue 2 | Spring Boot 2.7 |
| Element UI | MyBatis-Plus 3.5 |
| Axios | JWT |
| Vue Router | MySQL 8.0 |

🚀 快速开始
 1. 克隆项目
```bash
git clone https://github.com/你的用户名/dormitory-management-system.git
```

2. 后端启动
- 修改 `application.properties` 中的数据库密码
- 运行 `DmsApplication.java`

3. 前端启动
```bash
cd dormitory-frontend
npm install
npm run serve
```
4. 访问
- 前端：`http://localhost:8080`
- 默认账号：`admin / admin`
📂 模块说明

| 模块 | 功能 |
|------|------|
| 用户管理 | 登录、注册、增删改查、条件分页 |
| 楼宇管理 | 楼宇增删改查、条件分页 |
| 宿舍管理 | 宿舍增删改查、条件分页 |
| 学生管理 | 学生增删改查、条件分页 |
| 缺勤记录 | 缺勤增删改查、条件分页 |



MIT © 2026 
