package com.amateur.client.pool;

import com.amateur.annotation.PoolControl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    @PoolControl()
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(2, 5, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }
}
