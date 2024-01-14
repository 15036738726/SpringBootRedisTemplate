package com.example.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * redis相关操作工具类
 */
@Component
public class OpsForListUtil {

    /**
     * RedisTemplate常用方法（超详细）
     * https://blog.csdn.net/zzvar/article/details/118388897
     */

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * leftPush rightPush
     * leftPop  rightPop
     * range(key,startIndex从0开始,endIndex)  -1返回所有
     */

    /**
     * 返回所有
     * @param key
     * @return
     */
    public List getAll(String key){
        return redisTemplate.opsForList().range(key,0,-1);
    }

    /**
     * 然后指定范围内的数据
     * @param key
     * @param startIndex
     * @param endIndex
     * @return
     */
    public List get(String key,Long startIndex,Long endIndex){
        return redisTemplate.opsForList().range(key,startIndex,endIndex);
    }

    /**
     * 从尾部弹出num个元素
     * @param key
     * @param num
     * @return
     */
    public List tailPop(String key,Long num){
        return redisTemplate.opsForList().rightPop(key, num);
    }

    /**
     * 从头部弹出num个元素
     * @param key
     * @param num
     * @return
     */
    public List headPop(String key,Long num){
        return redisTemplate.opsForList().leftPop(key, num);
    }

    /**
     * 从头部加入
     * @param key
     * @param data
     * @return
     */
    public Long headPush(String key,Object data){
        return redisTemplate.opsForList().leftPush(key, data);
    }

    public Long headPushAll(String key,Object data){
        return redisTemplate.opsForList().leftPushAll(key, data);
    }

    /**
     * 从尾部加入
     * @param key
     * @param data
     * @return
     */
    public Long tailPush(String key,Object data){
        return redisTemplate.opsForList().rightPush(key,data);
    }

    /**
     * 获取集合指定位置的值  index(K key, long index)
     * @param key
     * @param index
     * @return
     */
    public Object getOnebyIndex(String key,Long index){
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取集合长度
     * @param key
     * @return
     */
    public Long getSize(String key){
        return redisTemplate.opsForList().size(key);
    }


}
