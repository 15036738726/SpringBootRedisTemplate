package com.example.demo.test;

import com.example.demo.model.MyProject;
import com.example.demo.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    public IProjectService projectService;

    @Autowired
    public RedisTemplate redisTemplate;


    @RequestMapping("/query")
    @ResponseBody
    public String queryUser(){
        return "哈喽";
    }

    // http://localhost:9092/test/queryPage?currentPage=2&pageSize=10
    @RequestMapping("/queryPage")
    @ResponseBody
    public List<MyProject> queryPage(Integer currentPage, Integer pageSize){
        List<MyProject> list = projectService.queryPage(currentPage,pageSize);
        return list;
    }

    // http://localhost:9092/test/add
    /**
     * leftPush leftPushAll RightPushAll测试
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public String queryPage(){
        MyProject myProject1 = new MyProject().builder().projectName("项目1").build();
        MyProject myProject2 = new MyProject().builder().projectName("项目2").build();
        MyProject myProject3 = new MyProject().builder().projectName("项目3").build();

        List<MyProject> list = new ArrayList<>();
        list.add(myProject1);
        list.add(myProject2);
        list.add(myProject3);

        ListOperations ope = redisTemplate.opsForList();
        //redisTemplate.delete("leftPushAll");
        ope.leftPushAll("leftPushAll",list);
        List leftPushAllRange = ope.range("leftPushAll", 0, 2);
        System.out.println(leftPushAllRange.size()+","+leftPushAllRange);

        ope.rightPushAll("rightPushAll",list);
        List rightPushAllRange = ope.range("rightPushAll", 0, 2);
        System.out.println(rightPushAllRange.size()+","+rightPushAllRange);

        //redisTemplate.delete("leftPush");
        ope.leftPush("leftPush",list);
        List leftPushRange = ope.range("leftPush", 0, 3);
        System.out.println(leftPushRange.size()+","+leftPushRange);
        /**
         * 第一次执行打印
         * 3,[{projectName=项目3, projectState=null, updateTime=null}, {projectName=项目2, projectState=null, updateTime=null}, {projectName=项目1, projectState=null, updateTime=null}]
         * 3,[{projectName=项目1, projectState=null, updateTime=null}, {projectName=项目2, projectState=null, updateTime=null}, {projectName=项目3, projectState=null, updateTime=null}]
         * 1,[[{projectName=项目1, projectState=null, updateTime=null}, {projectName=项目2, projectState=null, updateTime=null}, {projectName=项目3, projectState=null, updateTime=null}]]
         * 第二次执行打印
         * 3,[{projectName=项目3, projectState=null, updateTime=null}, {projectName=项目2, projectState=null, updateTime=null}, {projectName=项目1, projectState=null, updateTime=null}]
         * 3,[{projectName=项目1, projectState=null, updateTime=null}, {projectName=项目2, projectState=null, updateTime=null}, {projectName=项目3, projectState=null, updateTime=null}]
         * 2,[[{projectName=项目1, projectState=null, updateTime=null}, {projectName=项目2, projectState=null, updateTime=null}, {projectName=项目3, projectState=null, updateTime=null}], [{projectName=项目1, projectState=null, updateTime=null}, {projectName=项目2, projectState=null, updateTime=null}, {projectName=项目3, projectState=null, updateTime=null}]]
         */

        /**
         * 结论:leftPush rightPush 都将插入内容作为一个整理进行插入,比如插入list{ele1,ele2,ele3} 则执行一次插入,缓存中这个key对应的就是一个内容,为list{ele1,ele2,ele3} 长度为1
         * 而对比pushAll key对应三个元素,可以看作为 循环插入的元素,然后做push动作  内容为 key对应 {ele1},{ele2},{ele3} 长度为3
         */
        return "设置成功";
    }


}
