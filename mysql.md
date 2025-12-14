MySQL 零基础入门到 SpringBoot 集成实战文档

一、MySQL 核心概念

1. 定义与核心特性

MySQL 是一款开源的关系型数据库管理系统（RDBMS），基于「客户端-服务器」架构，核心特性如下：

- 支持关系模型：数据以表为单位存储，表与表之间可通过外键建立关联；

- ACID 特性：保证事务的原子性、一致性、隔离性、持久性；

- 跨平台兼容：支持 Windows/macOS/Linux 等主流操作系统；

- 开源免费：社区版完全免费，适合个人开发和中小企业使用；

- 支持 SQL 标准：通过结构化查询语言（SQL）实现数据的增删改查。

2. 核心术语

- 数据库（Database/Schema）：存储多个表的容器（如 zsc 数据库）；

- 表（Table）：存储具体数据的二维结构，由行（记录）和列（字段）组成（如 student 表）；

- 字段（Column）：表的列，定义数据类型（如 id、name、age）；

- 主键（Primary Key）：唯一标识表中每条记录的字段（如 id 字段，通常设为自增）；

- SQL 命令：操作数据库的指令（如 CREATE DATABASE 建库、SELECT 查询数据）。

3. 典型应用场景

适用于需要结构化存储、事务支持的场景，如：用户信息管理、订单系统、学生成绩管理、商品库存管理等。

二、macOS 环境 MySQL 安装与基础配置

1. 安装 MySQL（brew 一键安装）

使用 brew 安装可自动配置环境变量，简化操作：

# 安装最新稳定版 MySQL
brew install mysql

# 验证安装成功（输出版本号即生效）
mysql --version

2. 启动/停止/重启 MySQL 服务

# 启动服务 + 开机自启
brew services start mysql

# 停止服务
brew services stop mysql

# 重启服务
brew services restart mysql

# 查看服务状态（显示 started 即为运行中）
brew services list | grep mysql

3. 本地连接 MySQL 客户端（无密码默认配置）

macOS brew 安装的 MySQL 默认无密码，直接连接：

# 以 root 用户连接本地 MySQL
mysql -u root

# 连接成功后，提示符变为 mysql>
# 退出客户端
exit

4. 基础 SQL 命令（客户端操作）

# 1. 查看所有数据库
show databases;

# 2. 创建数据库（如 zsc，指定字符集为 utf8mb4 支持中文）
CREATE DATABASE IF NOT EXISTS zsc
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

# 3. 切换到目标数据库（操作表前必须执行）
USE zsc;

# 4. 创建表（如 student 学生表）
CREATE TABLE IF NOT EXISTS student (
id INT PRIMARY KEY AUTO_INCREMENT COMMENT '学生学号（自增主键）',
name VARCHAR(50) NOT NULL COMMENT '学生姓名',
gender ENUM('男','女','未知') DEFAULT '未知' COMMENT '性别',
age TINYINT UNSIGNED COMMENT '年龄（非负整数）',
class VARCHAR(20) COMMENT '班级（如：高三1班）',
enroll_date DATE COMMENT '入学时间'
) COMMENT '学生信息表';

# 5. 插入数据
INSERT INTO student (name, gender, age, class, enroll_date)
VALUES ('张三', '男', 18, '高三1班', '2023-09-01');

# 6. 查询数据
SELECT * FROM student;

# 7. 修改数据
UPDATE student SET age = 19 WHERE name = '张三';

# 8. 删除数据
DELETE FROM student WHERE id = 1;

三、MySQL Workbench 可视化工具使用

1. 工具介绍

MySQL Workbench 是官方推出的可视化数据库管理工具，支持数据库连接、表设计、SQL 编辑、数据管理等功能，无需记忆复杂 SQL 命令即可操作。

2. 下载与安装

从 MySQL 官网下载对应 macOS 版本：https://dev.mysql.com/downloads/workbench/，双击安装包按引导完成安装。

3. 连接本地 MySQL 数据库

1. 打开 MySQL Workbench，点击「MySQL Connections」旁的「+」号创建新连接；

2. 填写连接信息：
   Connection Name：自定义名称（如「Local MySQL」）；

3. Connection Method：默认「Standard (TCP/IP)」；

4. Hostname：本地连接填「localhost」或「127.0.0.1」；

5. Port：默认 3306（未修改过则保持默认）；

6. Username：填「root」；

7. Password：默认无密码，留空。

8. 点击「Test Connection」测试连接，提示「Successfully made the MySQL connection」即为成功；

9. 点击「OK」保存连接，双击连接名称即可进入操作界面。

4. 界面核心区域解析

- 顶部导航栏：显示当前连接名称（如「Local MySQL」），右侧为窗口控制按钮（最小化、最大化、关闭）；

- 左侧面板：
  Administration：管理区，包含服务状态查看、数据导入/导出、启动/停止服务等；

- Schemas：数据库列表，展开可查看当前连接下的所有数据库及表（核心操作入口）。

中间主区域（Query 1）：SQL 编辑器，可编写、执行 SQL 语句；

底部面板（Action Output）：显示 SQL 执行结果、错误提示等信息。

5. 可视化管理表数据（无需写 SQL）

适合少量数据的增删改查，操作类似 Excel：

1. 左侧 Schemas 面板展开目标数据库（如 zsc），再展开「Tables」；

2. 右键目标表（如 student），选择对应操作：
   Select Rows - Limit 1000：查看表中所有数据（最多显示 1000 条）；

3. Edit Table Data：进入编辑模式，可直接新增、修改、删除数据。

4. 操作说明：
   新增数据：点击表格最后一行空白处，输入数据后点击空白处自动保存；

5. 修改数据：双击要修改的单元格，改完回车即可；

6. 删除数据：选中行首序号，右键选择「Delete Row(s)」确认删除；

7. 筛选数据：在列名下方的筛选框输入关键词，自动过滤数据。

6. 常见问题：版本兼容警告

连接时可能提示：Incompatible/nonstandard server version or connection protocol detected (9.5.0)

原因：MySQL Workbench 官方仅适配 5.6/5.7/8.0 版本，而安装的是 MySQL 9.5.0（最新版）。

解决方案：

- 忽略警告：点击「OK」继续，核心功能（查询、增删改查、表管理）不受影响；

- 降级 MySQL 到 8.0（推荐，全功能兼容）：
  # 停止当前 MySQL 服务
  brew services stop mysql

# 卸载 9.5 版本
brew uninstall mysql

# 清理残留
sudo rm -rf /usr/local/var/mysql

# 安装 8.0 稳定版
brew install mysql@8.0

# 启动并关联路径
brew services start mysql@8.0
brew link --force mysql@8.0

- 替代工具：使用 DBeaver（免费、跨平台、原生中文、适配所有 MySQL 版本）。

7. 中文界面设置（非原生，需汉化包）

MySQL Workbench 无官方中文包，可通过替换汉化文件实现：

1. 下载对应版本汉化包（如 main_menu.xml）；

2. 定位安装目录：macOS 路径为 /Applications/MySQLWorkbench.app/Contents/Resources/data/；

3. 备份并替换 main_menu.xml 文件；

4. 重启 MySQL Workbench 即可看到中文界面（部分元素可能仍为英文）。

四、SpringBoot 集成 MySQL 实战

1. 前置条件

已创建 SpringBoot 项目，MySQL 服务正常运行，且已创建目标数据库（如 zsc）。

2. 添加依赖（pom.xml）

<!-- SpringBoot 整合 MySQL 核心依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- MySQL 驱动（SpringBoot 2.x+ 需指定版本，适配 8.0+ MySQL） -->
<dependency>
    <groupId>com.mysql</groupId>
   <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>

3. 配置 MySQL 连接（application.yml）

spring:
# 数据库配置
datasource:
url: jdbc:mysql://localhost:3306/zsc?useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
username: root  # 数据库用户名
password:  # 本地无密码，留空
driver-class-name: com.mysql.cj.jdbc.Driver
# JPA 配置（简化 SQL 操作，自动映射实体类与表）
jpa:
hibernate:
ddl-auto: update  # 自动根据实体类更新表结构（创建/修改表）
show-sql: true  # 控制台打印执行的 SQL 语句
properties:
hibernate:
format_sql: true  # 格式化 SQL 语句，便于查看

4. 编写实体类（与数据库表映射）

以 Student类为例，对应 student 表：

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data  // Lombok 注解，自动生成 getter/setter/toString 等方法
@Entity  // 标识为 JPA 实体类，对应数据库表
@Table(name = "student")  // 指定对应表名（若类名与表名一致可省略）
public class Student {

    @Id  // 标识为主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 自增策略（对应 MySQL 的 AUTO_INCREMENT）
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)  // 对应表中 name 字段，非空，长度 50
    private String name;

    @Column(name = "gender")  // 对应 gender 字段
    private String gender;

    @Column(name = "age")  // 对应 age 字段
    private Integer age;

    @Column(name = "class")  // 对应 class 字段
    private String className;  // 因 class 是 Java 关键字，用 className 映射

    @Column(name = "enroll_date")  // 对应 enroll_date 字段
    private Date enrollDate;
}

5. 编写 Repository 接口（数据访问层）

继承 JpaRepository，自动获得基础增删改查方法：

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// JpaRepository<实体类, 主键类型>
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
// 可自定义查询方法（JPA 自动解析方法名生成 SQL）
Student findByName(String name);  // 根据姓名查询学生
}

6. 编写 Service 层（业务逻辑层）

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // 新增/修改学生（save 方法：主键存在则修改，不存在则新增）
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    // 根据 ID 查询学生
    public Student getStudentById(Integer id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        return optionalStudent.orElse(null);  // 不存在则返回 null
    }

    // 查询所有学生
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // 根据 ID 删除学生
    public void deleteStudent(Integer id) {
        studentRepository.deleteById(id);
    }

    // 根据姓名查询学生
    public Student getStudentByName(String name) {
        return studentRepository.findByName(name);
    }
}

7. 编写 Controller 层（接口层，供前端调用）

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")  // 统一接口前缀
public class StudentController {

    @Autowired
    private StudentService studentService;

    // 新增学生：POST http://localhost:8080/student
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    // 根据 ID 查询学生：GET http://localhost:8080/student/1
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Integer id) {
        return studentService.getStudentById(id);
    }

    // 查询所有学生：GET http://localhost:8080/student
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // 修改学生：PUT http://localhost:8080/student
    @PutMapping
    public Student updateStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    // 删除学生：DELETE http://localhost:8080/student/1
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudent(id);
        return "学生删除成功";
    }

    // 根据姓名查询学生：GET http://localhost:8080/student/name/张三
    @GetMapping("/name/{name}")
    public Student getStudentByName(@PathVariable String name) {
        return studentService.getStudentByName(name);
    }
}

8. 启动项目测试

1. 确保 MySQL 服务已启动（brew services start mysql）；

2. 启动 SpringBoot 项目，JPA 会自动根据 Student 实体类创建/更新 student 表；

3. 通过 Postman/浏览器调用接口测试，例如：
   查询所有学生：访问 http://localhost:8080/student，返回学生列表；

4. 新增学生：用 Postman 发送 POST 请求，请求体为 JSON 格式：
   {
   "name": "李四",
   "gender": "女",
   "age": 17,
   "className": "高三2班",
   "enrollDate": "2023-09-01"
   }

五、关键注意事项

1. SQL 语句结束符：MySQL 客户端中，所有 SQL 语句必须以分号 ; 结尾，否则会显示 -> 等待补全；

2. 数据库连接配置：
   serverTimezone=Asia/Shanghai：指定时区为上海，避免时间错乱；

3. allowPublicKeyRetrieval=true：解决本地连接时的权限验证问题；

4. useSSL=false：开发环境关闭 SSL 验证，生产环境建议开启。

5. JPA ddl-auto 配置：
   update：开发环境使用，自动根据实体类更新表结构，不删除已有数据；

6. create：每次启动项目重建表（数据丢失），仅用于测试；

7. none：生产环境使用，关闭自动表结构更新。

8. 关键字避免：Java 关键字（如 class、interface）不能作为实体类属性名，需用别名映射（如 className 映射 class 字段）；

9. 数据类型匹配：实体类属性类型需与数据库表字段类型匹配（如 Date 对应 MySQL 的 DATE/DATETIME）。

六、进阶学习方向

1. SQL 进阶：学习复杂查询（联表查询、子查询、分组统计）、索引优化（EXPLAIN 分析执行计划）；

2. 事务管理：学习 Spring 事务注解 @Transactional，保证数据一致性；

3. 连接池配置：优化数据库连接池（如 HikariCP），提升并发性能；

4. MyBatis 替代 JPA：学习 MyBatis 框架，手动编写 SQL，更灵活适配复杂业务；

5. MySQL 高可用：了解主从复制、读写分离、分库分表，应对大数据量场景。
