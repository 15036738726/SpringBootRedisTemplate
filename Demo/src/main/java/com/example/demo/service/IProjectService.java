package com.example.demo.service;

import com.example.demo.model.MyProject;

import java.util.List;

public interface IProjectService {
    List<MyProject> queryAll();

    List<MyProject> queryPage(Integer currentPage, Integer pageSize);
}
