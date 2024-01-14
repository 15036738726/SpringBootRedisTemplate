package com.example.demo.init;

import com.example.demo.model.MyProject;
import com.example.demo.service.IProjectService;
import com.example.demo.utils.OpsForListUtil;
import com.example.demo.utils.OpsForStringUtil;
import com.example.demo.utils.SupportCachePageUtil;
import org.apache.ibatis.annotations.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 服务器启动时,假定我们把项目数据初始化到redis中
 */
@Component
public class Init implements ApplicationRunner {

    @Autowired
    private IProjectService projectService;

    @Autowired
    private OpsForListUtil opsForListUtil;
    @Autowired
    private OpsForStringUtil opsForStringUtil;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<MyProject> projectList = projectService.queryAll();
        // 设置到缓存
        projectList.stream().forEach(e -> {
            Long aLong = opsForListUtil.tailPush(SupportCachePageUtil.PROJECT_INFO_BUSINESS_KEY + "_LIST", e);
        });
        opsForStringUtil.setValue(SupportCachePageUtil.PROJECT_INFO_BUSINESS_KEY + "_STRING", new Long(projectList.size()));
        System.out.println("数据初始化成功");
    }

}
