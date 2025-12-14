package com.example.zhaoshichuan.util;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类（入门版，封装 String 类型操作）
 */
@Component
public class RedisUtil {

    // 注入 StringRedisTemplate（SpringBoot 自动配置）
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 存 String 类型数据（带过期时间）
     * @param key 键
     * @param value 值
     * @param timeout 过期时间
     * @param unit 时间单位（秒/分钟等）
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
     * 存 Hash 类型数据
     * @param key 外层 key
     * @param hashKey Hash 的子 key
     * @param value 子 value
     */
    public void hSet(String key, String hashKey, String value) {
        stringRedisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 获取 Hash 类型数据
     */
    public String hGet(String key, String hashKey) {
        return (String) stringRedisTemplate.opsForHash().get(key, hashKey);
    }
}
