package com.example.demo;

import com.example.demo.utils.OpsForListUtil;
import com.example.demo.utils.SupportCachePageUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    SupportCachePageUtil supportCachePageUtil;

    @Autowired
    OpsForListUtil opsForListUtil;

    @Autowired
    RedisTemplate redisTemplate;


    @Test
    void contextLoads() {

        opsForListUtil.headPush("PROJECT_INFO_BUSINESS_KEY_LIST","1111");
        opsForListUtil.headPush("PROJECT_INFO_BUSINESS_KEY_LIST","2222");


        Long projectInfoBusinessKeyList = redisTemplate.opsForList().size("PROJECT_INFO_BUSINESS_KEY_LIST");
        System.out.println(projectInfoBusinessKeyList);


        Object o = redisTemplate.opsForList().leftPop("PROJECT_INFO_BUSINESS_KEY_LIST");
        System.out.println(o);
        List range = redisTemplate.opsForList().range("PROJECT_INFO_BUSINESS_KEY_LIST", 0, 1);
        System.out.println(range);


    }

}
