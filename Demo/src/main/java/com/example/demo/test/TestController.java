package com.example.demo.test;

import com.example.demo.model.MyProject;
import com.example.demo.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    public IProjectService projectService;


    @RequestMapping("/query")
    @ResponseBody
    public String queryUser(){
        return "哈喽";
    }

    // http://localhost:9092/test/queryPage?currentPage=2&pageSize=10
    @RequestMapping("/queryPage")
    @ResponseBody
    public List<MyProject> queryPage(Long currentPage, Long pageSize){
        List<MyProject> list = projectService.queryPage(currentPage,pageSize);
        return list;
    }


}
