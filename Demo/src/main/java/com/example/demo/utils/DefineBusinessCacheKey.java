package com.example.demo.utils;

/**
 * 所有需要支持列表缓存服务的  都在此处定义缓存Key
 */
public interface DefineBusinessCacheKey {
    // 项目查询业务 对应的缓存key
    String PROJECT_INFO_BUSINESS_KEY = "PROJECT_INFO_BUSINESS_KEY";
    // 用户查询业务 对应的缓存key
    String USER_INFO_BUSINESS_KEY = "USER_INFO_BUSINESS_KEY";
}
