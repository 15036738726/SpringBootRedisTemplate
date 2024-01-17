package com.example.demo.init;

import com.example.demo.utils.DefineBusinessCacheKey;
import com.example.demo.utils.OpsForStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 服务器启动时,我们可以把数据进行一下预加载设置到缓存中,这里就不搞了
 */
@Component
public class Init implements ApplicationRunner {

    @Autowired
    private OpsForStringUtil opsForStringUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 这里我们方便测试,每次启动的时候都把对应的key 清空一下
        opsForStringUtil.deleteByPrex(DefineBusinessCacheKey.PROJECT_INFO_BUSINESS_KEY);
        opsForStringUtil.deleteByPrex(DefineBusinessCacheKey.USER_INFO_BUSINESS_KEY);
    }

}
