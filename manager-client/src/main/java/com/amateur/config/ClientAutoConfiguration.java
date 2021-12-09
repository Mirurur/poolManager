package com.amateur.config;

import com.amateur.client.ThreadPoolManagerClient;
import com.amateur.handler.PoolClientHandler;
import com.amateur.pool.PoolInfoDetector;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author yeyu
 * @date 2021/12/9 16:55
 */
@Configuration
public class ClientAutoConfiguration implements ApplicationListener<ContextRefreshedEvent> {

    @Bean
    public ThreadPoolManagerClient threadPoolManagerClient() {
        return new ThreadPoolManagerClient();
    }

    @Bean
    public PoolClientHandler poolClientHandler() {
        return new PoolClientHandler();
    }

    @Bean
    public PoolInfoDetector poolInfoDetector() {
        return new PoolInfoDetector();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        new Thread(threadPoolManagerClient()).start();
    }
}
