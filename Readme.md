## 📌 项目名称
教学管理系统: 基于前后端分离开发了一个教学管理系统，实现了学员，班级，员工，部门的CRUD功能，基于echarts，anxios实现了报表统计，基于jwt令牌，通过存储token实现登录功能。
后端使用SpringBoot+MyBatis实现RESTful API，前端基于Vue3+Element-Plus构建交互界面，MySQL数据库支持高效查询和事务管理。



## ✨ 功能特性
用户认证：登录、登出、权限管理  
- CRUD 功能：  
  - 部门管理（增删改查）  
  - 学员管理（增删改查）  
  - 员工管理（增删改查）
  - 班级管理（增删改查） 
- 数据统计：  
  - 员工职位，性别报表统计（图表展示）  
  - 学员学历，班级统计
- 其他功能： 
   jwt令牌登录认证，日志本地保存。

## 🛠 技术栈  
前端：Vue3,element-plus,Axios,ECharts（如果用了图表库）  
后端：Spring Boot,Mybatis,MySQL,Maven 
其他工具：Git, JWT,  

## 🚀 快速开始  
 环境要求  
JDK 21（根据你的后端技术选填）  
MySQL 
npm（前端依赖管理）
 
## 安装与运行

1. 克隆仓库  
   bash
   git clone https://github.com/your-repo/teaching-management-system.git
   cd backend
   
2. 安装依赖  
   bash
   npm install  # 或 pip install -r requirements.txt / mvn install
   
3. 配置数据库  
   - 修改 `application.yml` 或 `.env` 文件，填写数据库信息  
4. 启动服务  
   bash
   npm start  # 或 python app.py / ./mvnw spring-boot:run
   

## 前端
1. 进入前端目录  
   bash
   cd frontend
   
2. 安装依赖  
   bash
   npm install
   
3. 启动开发服务器  
   bash
   npm run dev
  
4. 访问 `http://localhost:5173`  


## 📊 数据库设计
 
- emp 表：存储员工信息  
- dept 表：存储部门信息  
- clazz 表：存储班级信息
- emp_expr 表：存储员工工作经历信息
- student 表：存储学员信息表
- log 表：存储员工增加日志

## 截图展示  
![image](https://github.com/user-attachments/assets/b04dcfce-13de-4d4d-90d6-54868975010c)
![image](https://github.com/user-attachments/assets/34f08c2f-5bcb-4239-9571-5b8aa36efdcb)
![image](https://github.com/user-attachments/assets/b634206a-7aa7-45a8-9874-17bd27ed1279)

---
## 🤝 贡献指南
欢迎提交 PR 或 Issue！  
1. Fork 本仓库  
2. 创建分支 (`git checkout -b feature/xxx`)  
3. 提交更改 (`git commit -m 'feat: add xxx'`)  
4. 推送分支 (`git push origin feature/xxx`)  
5. 提交 Pull Request  

-

## 📜 许可证  
MIT © [luotovo]  
---
