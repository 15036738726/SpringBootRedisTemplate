package com.example.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Set;

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
     * 模糊删除 传入将要删除的前缀key
     * @param prex
     * @return
     */
    public Long deleteByPrex(String prex) {
        Set<String> keys = redisTemplate.keys(prex+"*");
        if (!CollectionUtils.isEmpty(keys)) {
            Long delete = redisTemplate.delete(keys);
            return delete;
        }
        return 0L;
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
