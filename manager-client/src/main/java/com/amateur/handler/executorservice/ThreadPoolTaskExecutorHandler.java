package com.amateur.handler.executorservice;

import com.amateur.constant.ExecutorServiceHandlerConstant;
import com.amateur.pool.info.PoolInfo;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

/**
 * @author sun
 */
@Component(ExecutorServiceHandlerConstant.THREAD_POOL_TASK_EXECUTOR_HANDLER)
public class ThreadPoolTaskExecutorHandler implements ExecutorServiceHandler{

    private final String beanName = ExecutorServiceHandlerConstant.THREAD_POOL_TASK_EXECUTOR_HANDLER;

    private ExecutorServiceHandler delegate;

    public ThreadPoolTaskExecutorHandler() {
    }

    @Override
    public void setDelegate(ExecutorServiceHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public void handler(ExecutorService executor, PoolInfo poolInfo) {
        if (executor instanceof ThreadPoolTaskExecutor) {
            ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) executor;
            poolInfo.setKeepAliveTime(taskExecutor.getKeepAliveSeconds());
            poolInfo.setCorePoolSize(taskExecutor.getCorePoolSize());
            poolInfo.setMaximumPoolSize(taskExecutor.getMaxPoolSize());
        } else if (delegate != null){
            delegate.handler(executor, poolInfo);
        }
    }
}
