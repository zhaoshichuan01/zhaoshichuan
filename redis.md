# Redis 零基础入门到 SpringBoot 集成实战文档
## 一、Redis 核心概念
### 1. 定义与核心特性
Redis 是一款**高性能的内存型键值数据库**，主打极速读写（读写速度达 10w+/s），核心特性如下：
- **内存存储**：数据优先存内存，这是高性能的核心原因；
- **持久化机制**：支持 RDB/AOF 两种方式，可将内存数据刷到磁盘，重启不丢数据；
- **多数据结构**：支持 String、Hash、List、Set、ZSet 等多种数据结构，满足不同业务场景；
- **单线程 + 多路复用**：避免线程切换开销，保证高性能同时保证线程安全；
- **跨平台**：支持 Windows/macOS/Linux，可部署集群、主从复制架构。

### 2. 常用数据结构与场景
| 数据结构 | 核心用途 | 类比 Java 数据结构 |
|----------|----------|-------------------|
| String | 验证码、计数器、简单缓存 | String/Integer |
| Hash | 存储用户信息等多字段数据 | HashMap |
| List | 消息队列、最新数据列表 | LinkedList |
| Set | 去重、抽奖、好友关系 | HashSet |
| ZSet | 排行榜、按分值排序场景 | TreeSet（带分值） |

### 3. 典型应用场景
- **缓存**：存储 MySQL 热点数据（如商品详情），减少数据库压力；
- **分布式锁**：秒杀、抢票等多服务竞争场景，保证资源互斥访问；
- **计数器**：文章阅读量、点赞数（`incr` 命令原子递增）；
- **限流器**：接口限流（如 1 分钟最多访问 10 次）；
- **排行榜**：游戏战力榜、商品销量榜。

## 二、macOS 环境 Redis 安装与基础操作
### 1. 安装 Redis（brew 一键安装）
```bash
# 安装最新稳定版
brew install redis

# 验证安装成功（输出版本号即成功）
redis-server --version
```

### 2. 启动/停止/重启 Redis 服务
```bash
# 启动服务 + 开机自启
brew services start redis

# 停止服务
brew services stop redis

# 重启服务
brew services restart redis

# 查看服务状态
brew services list | grep redis
```

### 3. 连接 Redis 客户端并测试
```bash
# 本地连接（默认端口 6379，无密码）
redis-cli

# 基础命令测试
SET name zsc  # 存 String 类型数据
GET name      # 取数据，输出 "zsc"
EXIT          # 退出客户端
```

### 4. 持久化文件路径配置（关键）
#### 4.1 定位数据目录
通过配置文件查看 Redis 持久化文件存储路径：
```bash
grep -E "^dir" /usr/local/etc/redis.conf
```
**默认输出**：`dir /usr/local/var/db/redis/`，该目录用于存放 `dump.rdb`（RDB 快照文件）和 `appendonly.aof`（AOF 日志文件）。

#### 4.2 创建数据目录（解决“文件不存在”问题）
若目录不存在，手动创建并赋权：
```bash
# 创建目录
sudo mkdir -p /usr/local/var/db/redis/

# 赋权给当前用户（避免 Redis 写入权限不足）
sudo chown -R $(whoami) /usr/local/var/db/redis/
```

#### 4.3 RDB 持久化触发方式
RDB 是 Redis 默认的持久化方式，将内存数据以快照形式写入 `dump.rdb` 文件，触发方式分两种：
- **自动触发**：满足配置文件中默认规则
  ```
  save 900 1    # 900秒内至少1次写操作
  save 300 10   # 300秒内至少10次写操作
  save 60 10000 # 60秒内至少10000次写操作
  ```
- **手动触发**
  ```redis
  BGSAVE  # 异步触发，不阻塞 Redis 服务（推荐）
  SAVE    # 同步触发，阻塞 Redis 服务（不推荐）
  ```

## 三、SpringBoot 集成 Redis 实战
### 1. 添加依赖（pom.xml）
```xml
<!-- SpringBoot 整合 Redis 起步依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

<!-- 连接池依赖（提升性能，推荐添加） -->
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-pool2</artifactId>
</dependency>
```

### 2. 配置 Redis 连接（application.yml）
```yaml
spring:
  redis:
    # 本地地址
    host: localhost
    # 默认端口
    port: 6379
    # 密码（本地无密码，留空）
    password:
    # 数据库索引（Redis 默认 16 个库，从 0 开始）
    database: 0
    # 连接池配置
    lettuce:
      pool:
        max-active: 8   # 最大连接数
        max-idle: 8     # 最大空闲连接
        min-idle: 0     # 最小空闲连接
        max-wait: -1ms  # 连接等待时间（-1 表示无限制）
```

### 3. 编写 Redis 工具类（封装常用操作）
```java
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 存 String 类型数据（带过期时间）
     */
    public void set(String key, String value, long timeout, TimeUnit unit) {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set(key, value, timeout, unit);
    }

    /**
     * 存 String 类型数据（永久有效）
     */
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取 String 类型数据
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 删除指定 key
     */
    public Boolean delete(String key) {
        return stringRedisTemplate.delete(key);
    }

    /**
     * 自增（计数器场景）
     */
    public Long incr(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * Hash 类型数据存储
     */
    public void hSet(String key, String hashKey, String value) {
        stringRedisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * Hash 类型数据获取
     */
    public String hGet(String key, String hashKey) {
        return (String) stringRedisTemplate.opsForHash().get(key, hashKey);
    }
}
```

### 4. 编写测试接口（验证功能）
```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
public class RedisTestController {

    @Resource
    private RedisUtil redisUtil;

    /**
     * 存数据接口：http://localhost:8080/redis/set/zhangsan
     */
    @GetMapping("/redis/set/{name}")
    public String setRedis(@PathVariable String name) {
        redisUtil.set("username", name, 5, TimeUnit.MINUTES);
        return "存数据成功！key=username，value=" + name;
    }

    /**
     * 取数据接口：http://localhost:8080/redis/get
     */
    @GetMapping("/redis/get")
    public String getRedis() {
        String username = redisUtil.get("username");
        return "取数据结果：username=" + (username == null ? "不存在" : username);
    }

    /**
     * 计数器接口：http://localhost:8080/redis/incr
     */
    @GetMapping("/redis/incr")
    public String incr() {
        Long count = redisUtil.incr("count", 1);
        return "当前计数器值：" + count;
    }

    /**
     * Hash 类型存数据接口：http://localhost:8080/redis/hset
     */
    @GetMapping("/redis/hset")
    public String hSet() {
        redisUtil.hSet("user:1", "name", "张三");
        redisUtil.hSet("user:1", "age", "18");
        return "Hash 类型数据存储成功！";
    }

    /**
     * Hash 类型取数据接口：http://localhost:8080/redis/hget
     */
    @GetMapping("/redis/hget")
    public String hGet() {
        String name = redisUtil.hGet("user:1", "name");
        String age = redisUtil.hGet("user:1", "age");
        return "用户信息：name=" + name + "，age=" + age;
    }
}
```

### 5. 启动项目测试
1. 确保 Redis 服务已启动（`brew services start redis`）；
2. 启动 SpringBoot 项目；
3. 通过浏览器/Postman 访问接口，验证数据存、取功能。

## 四、Redis 持久化验证实验（内存 vs 磁盘数据）
### 实验目标
验证 **“set 值后关闭 Redis（不持久化）→ 重启后数据丢失”** 的场景。

### 实验步骤
#### 1. 禁用 Redis 自动持久化
```bash
# 进入 Redis 客户端
redis-cli

# 清空所有 RDB 自动快照规则（临时生效，重启 Redis 恢复）
CONFIG SET save ""

# 生成旧的 dump.rdb（覆盖原有文件，不含后续新值）
SAVE

# 退出客户端
EXIT
```

#### 2. 通过 Java 接口存新值
访问接口：`http://localhost:8080/redis/set/test_no_persist`，存入 `key=username，value=test_no_persist`。

#### 3. 验证内存中有值
```bash
redis-cli GET username
```
输出 `test_no_persist`，说明值已存入 Redis 内存。

#### 4. 强制杀死 Redis 进程（不触发持久化）
```bash
# 查找 Redis 进程 ID（PID）
ps aux | grep redis-server | grep -v grep

# 强制杀死进程（-9 表示强制终止，无刷盘机会）
kill -9 [你的 Redis PID]
```

#### 5. 重启 Redis 并验证数据丢失
```bash
# 重启 Redis 服务
brew services start redis

# 验证数据
redis-cli GET username
```
输出 `(nil)`，同时访问 Java 接口 `http://localhost:8080/redis/get`，返回 `username=不存在`，验证成功。

#### 6. 恢复 Redis 持久化配置（实验后必做）
```bash
redis-cli
# 恢复默认 RDB 快照规则
CONFIG SET save "900 1 300 10 60 10000"
EXIT
```

## 五、关键注意事项
1. **持久化机制选择**
    - RDB 优势：文件小、恢复快；劣势：可能丢失最后一次快照后的数- 据；
    - AOF 优势：数据安全性高（每秒刷盘）；劣势：文件大、恢复慢；
    - 生产环境推荐 **RDB + AOF 混合持久化**。
2. **SpringBoot 序列化问题**
    - 推荐使用 `StringRedisTemplate`，避免 `RedisTemplate` 默认的 JDK 序列化导致的数据乱码；
3. **关闭 Redis 的两种方式对比**
    - 优雅关闭（`brew services stop redis`）：Redis 会自动执行 `SAVE`，刷盘后再停止，数据不丢失；
    - 强制关闭（`kill -9 PID`）：Redis 无刷盘机会，仅内存中的数据会丢失。
4. **内存管理**
    - Redis 数据存内存，需合理设置过期时间（`set` 时指定 `timeout`），避免内存溢出。

## 六、进阶学习方向
1. 掌握 List、Set、ZSet 数据结构的 SpringBoot 操作；
2. 学习 Redis 缓存策略（缓存穿透、缓存击穿、缓存雪崩解决方案）；
3. 实现 Redis 分布式锁（基于 `SETNX` 命令）；
4. 了解 Redis 主从复制、哨兵模式、集群部署（高可用架构）。