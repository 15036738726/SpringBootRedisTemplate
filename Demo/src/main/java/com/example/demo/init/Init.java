package com.example.demo.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 服务器启动时,我们可以把数据进行一下预加载设置到缓存中,这里就不搞了
 */
@Component
public class Init implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }

}
