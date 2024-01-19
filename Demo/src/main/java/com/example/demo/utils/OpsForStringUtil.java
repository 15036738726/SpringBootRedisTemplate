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
     * 判断是否有对应业务进入临界区 控制
     * setIfAbsent 方法将键 key 的值设置为 value，并且仅在键不存在时才设置成功。
     * 如果键已存在，则不进行任何操作，并返回 false；如果设置成功，则返回 true
     * @param key
     * @return
     */
    public boolean trySetNx(String key){
        return redisTemplate.opsForValue().setIfAbsent(key, 1);
    }

    /**
     * 删除key
     * @param key
     */
    public void delSetNx(String key){
        redisTemplate.delete(key);
    }

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
