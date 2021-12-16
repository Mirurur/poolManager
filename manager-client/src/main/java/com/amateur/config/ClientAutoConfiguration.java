package com.amateur.config;

import com.amateur.client.ThreadPoolManagerClient;
import com.amateur.context.PoolContext;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;

/**
 * @author yeyu
 * @date 2021/12/9 16:55
 * 加入到SpringBoot自启动配置中
 */
@Configuration
@EnableConfigurationProperties(Properties.class)
@ComponentScan(basePackages = {"com.amateur"})
public class ClientAutoConfiguration implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private ThreadPoolManagerClient client;

    @Resource
    private PoolContext poolContext;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        new Thread(new StartTask(client,poolContext)).start();
    }

}
