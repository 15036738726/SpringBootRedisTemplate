package com.example.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 字符串类型缓存工具类
 */
@Component
public class OpsForStringUtil {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 删除key
     * @param key
     * @return
     */
    public Boolean delete(String key){
        return redisTemplate.delete(key);
    }

    /**
     * 判断是否有key
     * @param key
     * @return
     */
    public Boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置值
     * @param key
     * @param value
     */
    public void setValue(String key,Object value){
        redisTemplate.opsForValue().set(key,value);
    }

    /**
     * 返回值
     * @param key
     * @return
     */
    public Object getValue(String key){
        return redisTemplate.opsForValue().get(key);
    }
}
