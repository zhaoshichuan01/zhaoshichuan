package com.example.zhaoshichuan.controller;

import com.example.zhaoshichuan.util.RedisUtil;
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
     * 测试存数据：http://localhost:8080/redis/set/zhangsan
     */
    @GetMapping("/redis/set/{name}")
    public String setRedis(@PathVariable String name) {
        // 存 key=username，value=传入的name，过期时间 5 分钟
        redisUtil.set("username", name, 5, TimeUnit.MINUTES);
        return "Redis 存数据成功！key=username，value=" + name;
    }

    /**
     * 测试取数据：http://localhost:8080/redis/get
     */
    @GetMapping("/redis/get")
    public String getRedis() {
        String username = redisUtil.get("username");
        return "Redis 取数据：username=" + (username == null ? "不存在" : username);
    }

    /**
     * 测试删数据：http://localhost:8080/redis/delete
     */
    @GetMapping("/redis/delete")
    public String deleteRedis() {
        Boolean result = redisUtil.delete("username");
        return "Redis 删除数据：" + (result ? "成功" : "失败（key不存在）");
    }

    /**
     * 测试计数器：http://localhost:8080/redis/incr
     */
    @GetMapping("/redis/incr")
    public String incr() {
        // 每次访问，count 自增 1
        Long count = redisUtil.incr("count", 1);
        return "当前计数器值：" + count;
    }

    @GetMapping("/redis/hset")
    public String hSet() {
        // 存用户信息：key=user:1（用户ID），子key=name/age/class
        redisUtil.hSet("user:1", "name", "张三");
        redisUtil.hSet("user:1", "age", "18");
        redisUtil.hSet("user:1", "class", "高三1班");
        return "Hash 存用户信息成功！";
    }

    @GetMapping("/redis/hget")
    public String hGet() {
        String name = redisUtil.hGet("user:1", "name");
        String age = redisUtil.hGet("user:1", "age");
        return "用户信息：name=" + name + "，age=" + age;
    }
}
