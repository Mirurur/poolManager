package com.amateur.client.pool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yeyu
 * @date 2021/12/9 17:42
 */
@Configuration
public class CustomThreadPoolExecutorConfiguration {
    @Bean("threadPoolExecutor1")
    public ThreadPoolExecutor threadPoolExecutor1() {
        return new ThreadPoolExecutor(2, 5, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    @Bean("threadPoolExecutor2")
    public ThreadPoolExecutor threadPoolExecutor2() {
        return new ThreadPoolExecutor(2, 5, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    @Bean("threadPoolExecutor3")
    public ThreadPoolExecutor threadPoolExecutor3() {
        return new ThreadPoolExecutor(2, 5, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    @Bean("threadPoolExecutor4")
    public ThreadPoolTaskExecutor threadPoolExecutor4() {
        return new ThreadPoolTaskExecutor();
    }
}
