package com.example.demo.service.impl;

import com.example.demo.model.MyProject;
import com.example.demo.service.IProjectService;
import com.example.demo.utils.FindAllCallBack;
import com.example.demo.utils.SupportCachePageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目数据查询service
 */
@Service
public class ProjectService implements IProjectService {
    // 自己注入自己，方便分页查询时，回调函数中调用当前类的指定方法
    @Autowired
    private ProjectService projectService;

    /**
     * 列表缓存服务支持
     */
    @Autowired
    private SupportCachePageUtil supportCachePageUtil;

    @Override
    public List<MyProject> queryAll() {
        // 这里不走数据库了，假设从数据库中查出如下数据
        List<MyProject> list = new ArrayList<>();
        for(int i = 0;i<101;i++) {
            MyProject bean = new MyProject().builder().projectName("测试项目" + (i + 1)).projectState(0).updateTime(LocalDateTime.now().toString()).build();
            list.add(bean);
        }
        return list;
    }

    /**
     * 前端传入参数进行分页查询
     * @return
     */
    @Override
    public List<MyProject> queryPage(Integer currentPage,Integer pageSize){
        List<MyProject> list = supportCachePageUtil.queryPage(SupportCachePageUtil.PROJECT_INFO_BUSINESS_KEY,currentPage,pageSize,new FindAllCallBack(){
            // 当缓存中没有时，需要指定执行目标业务的"查询所有数据"的方法,查询完成之后,设置到缓存 执行回调函数的调用，去查询数据库
            @Override
            public List<MyProject> findAll() {
                return projectService.queryAll();
            }
        });
        return  list;
    }
}
