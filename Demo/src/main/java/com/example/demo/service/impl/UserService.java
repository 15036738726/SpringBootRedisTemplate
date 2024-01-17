package com.example.demo.service.impl;

import com.example.demo.model.MyUser;
import com.example.demo.service.IUserService;
import com.example.demo.utils.DefineBusinessCacheKey;
import com.example.demo.utils.FindAllCallBack;
import com.example.demo.utils.SupportCachePageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户数据查询service
 */
@Service
public class UserService implements IUserService {
    @Autowired
    private UserService userService;
    /**
     * 列表缓存服务支持
     */
    @Autowired
    private SupportCachePageUtil supportCachePageUtil;
    @Override
    public List<MyUser> queryAll() {
        // 这里不走数据库了，假设从数据库中查出如下数据
        List<MyUser> list = new ArrayList<>();
        for(int i = 0;i<101;i++) {
            MyUser user = new MyUser().builder().name("测试用户"+(i+1)).age(i+1).build();
            list.add(user);
        }
        return list;
    }

    @Override
    public List<MyUser> queryPage(Integer currentPage, Integer pageSize) {
        List list = supportCachePageUtil.queryPage(DefineBusinessCacheKey.USER_INFO_BUSINESS_KEY,currentPage, pageSize, new FindAllCallBack() {
            @Override
            public List findAll() {
                return userService.queryAll();
            }
        });
        return list;
    }
}
