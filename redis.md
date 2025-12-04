### Redis相关知识点全面整理

---

#### 1. Redis是什么

- Redis 是一个**高性能的开源内存数据库**，支持键值对存储、多种数据结构（String、Hash、List、Set、ZSet）。
- 主要用于**缓存、消息队列、排行榜、分布式锁、会话管理等场景**。

---

#### 2. Redis的安装与启动

- **Linux/Mac**：可用包管理器（apt/yum/brew）或源码安装。
    - 启动命令：`redis-server /path/to/redis.conf`
    - 服务管理：`systemctl start redis` / `brew services start redis`
- **Windows**：官方不直接支持，可用微软维护的 Redis for Windows 或 WSL。
- **配置文件**：一般为 `redis.conf`，常见路径 `/etc/redis/redis.conf` 或 `/usr/local/etc/redis.conf`。

---

#### 3. Redis密码配置

- 默认无密码，生产环境建议设置密码。
- 在 `redis.conf` 文件中添加或修改：
  ```
  requirepass yourpassword
  ```
- Redis 6.0 及以上支持 ACL（账号+密码）：
  ```
  user myuser on >mypassword ~* +@all
  ```
- 修改配置后需重启 Redis 服务。

---

#### 4. Redis数据存储位置

- 默认所有数据都存储在**Redis服务端的内存（RAM）**中，速度极快。
- 可选持久化机制（RDB快照、AOF日志）定期/实时保存到磁盘，防止断电或重启丢失。
- 缓存数据与Spring Boot项目进程无关，只要Redis服务在运行，数据就不会丢失。

---

#### 5. Spring Boot集成Redis

##### 5.1 添加依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

##### 5.2 配置连接信息

`application.properties` 或 `application.yml`：

```properties
spring.redis.host=localhost
spring.redis.port=6379
spring.redis.password=你的密码   # 如有密码
spring.redis.database=0
spring.redis.username=你的账号   # Redis 6.0及以上支持
```

##### 5.3 使用RedisTemplate操作

- `StringRedisTemplate`：操作String类型键值对
- `RedisTemplate<String, Object>`：可操作对象类型（需序列化配置）

**示例：**

```java
@Autowired
private StringRedisTemplate stringRedisTemplate;

stringRedisTemplate.opsForValue().set("key", "value");
String value = stringRedisTemplate.opsForValue().get("key");
```

##### 5.4 操作常用数据结构

- String：`opsForValue()`
- Hash：`opsForHash()`
- List：`opsForList()`
- Set：`opsForSet()`
- ZSet：`opsForZSet()`

##### 5.5 设置过期时间

```java
stringRedisTemplate.opsForValue().set("code", "123456", 5, TimeUnit.MINUTES);
```

##### 5.6 推荐序列化配置（存储对象建议用JSON）

```java
@Bean
public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(factory);
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
    template.setHashKeySerializer(new StringRedisSerializer());
    template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
    template.afterPropertiesSet();
    return template;
}
```

---

#### 6. Redis与Spring Boot项目的关系

- Redis服务和Spring Boot项目是**独立进程**，Spring Boot不会自动启动或关闭Redis服务。
- Spring Boot项目关闭后，Redis服务和数据不受影响。
- Redis服务关闭后，缓存数据会丢失（除非开启持久化），Spring Boot访问Redis会报错。

---

#### 7. Redis缓存与JVM本地缓存区别

| 缓存类型      | 存储位置             | 服务重启是否丢失 | 适用场景             |
|---------------|----------------------|------------------|----------------------|
| JVM本地缓存   | 当前Java进程的内存   | 会丢失           | 单机服务、临时缓存   |
| Redis缓存     | Redis服务器的内存    | 不会丢失         | 分布式/多服务场景    |

---

#### 8. 常见业务场景

- 登录验证码缓存
- 热点数据缓存
- 分布式锁
- 排行榜/计数器
- 消息队列（发布/订阅）

---

#### 9. 常见问题与排查

- 连接失败：检查Redis服务是否启动、配置是否正确
- 密码错误：确认`requirepass`配置和连接密码一致
- 数据序列化：推荐用JSON序列化对象，便于调试和兼容
- 性能优化：合理设置过期时间、使用连接池参数

---

#### 10. 推荐学习路径

1. 了解Redis基本原理和数据结构
2. 本地搭建Redis，Spring Boot集成依赖和连接
3. 用RedisTemplate实现基本读写和数据结构操作
4. 理解Redis与Spring Boot项目的独立性
5. 实现实际业务场景的缓存、分布式锁等功能
6. 深入学习持久化、集群高可用等进阶知识

---