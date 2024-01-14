package com.example.demo.utils;

import java.util.List;

/**
 * 回调函数
 */
@FunctionalInterface
public interface FindAllCallBack<T> {
    List<T> findAll();
}
