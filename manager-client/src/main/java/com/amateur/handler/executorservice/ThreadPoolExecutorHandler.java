package com.amateur.handler.executorservice;

import com.amateur.constant.ExecutorServiceHandlerConstant;
import com.amateur.pool.info.PoolInfo;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author sun
 */
@Component(ExecutorServiceHandlerConstant.THREAD_POOL_EXECUTOR_HANDLER)
public class ThreadPoolExecutorHandler implements ExecutorServiceHandler{

    private final String beanName = ExecutorServiceHandlerConstant.THREAD_POOL_EXECUTOR_HANDLER;

    private ExecutorServiceHandler delegate;

    public ThreadPoolExecutorHandler() {
    }

    @Override
    public void setDelegate(ExecutorServiceHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public void handler(ExecutorService executor, PoolInfo poolInfo) {
        if (executor instanceof ThreadPoolExecutor) {
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
            poolInfo.setCorePoolSize(threadPoolExecutor.getCorePoolSize());
            poolInfo.setMaximumPoolSize(threadPoolExecutor.getMaximumPoolSize());
            poolInfo.setKeepAliveTime(threadPoolExecutor.getKeepAliveTime(TimeUnit.SECONDS));
        } else if (delegate != null){
            delegate.handler(executor, poolInfo);
        }
    }
}
