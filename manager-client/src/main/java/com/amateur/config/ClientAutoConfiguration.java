package com.amateur.config;

import com.amateur.client.ThreadPoolManagerClient;
import com.amateur.handler.PoolClientHandler;
import com.amateur.pool.DefaultPoolInfoDetector;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author yeyu
 * @date 2021/12/9 16:55
 */
@Configuration
@EnableConfigurationProperties(ConnectConfig.class)
public class ClientAutoConfiguration implements ApplicationListener<ContextRefreshedEvent> {

    @Bean
    public ConnectConfig connectConfig() {
        return new ConnectConfig();
    }

    @Bean
    public ThreadPoolManagerClient threadPoolManagerClient() {
        return new ThreadPoolManagerClient(connectConfig());
    }

    @Bean
    public PoolClientHandler poolClientHandler() {
        return new PoolClientHandler();
    }

    @Bean
    public DefaultPoolInfoDetector defaultPoolInfoDetector() {
        return new DefaultPoolInfoDetector();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        new Thread(threadPoolManagerClient()).start();
    }
}
