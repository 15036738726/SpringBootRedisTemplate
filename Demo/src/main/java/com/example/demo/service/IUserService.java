package com.example.demo.service;

import com.example.demo.model.MyUser;

import java.util.List;

public interface IUserService {
    List<MyUser> queryAll();

    List<MyUser> queryPage(Integer currentPage, Integer pageSize);
}
