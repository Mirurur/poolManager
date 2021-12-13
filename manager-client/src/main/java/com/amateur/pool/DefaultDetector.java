package com.amateur.pool;

import com.amateur.handler.executorservice.ExecutorServiceContext;
import com.amateur.handler.executorservice.ExecutorServiceHandler;
import com.amateur.pool.info.PoolInfo;

import java.util.concurrent.ExecutorService;

/**
 * @author sun
 */
public class DefaultDetector extends BaseDetector {

    @Override
    public PoolInfo getPoolInfo(String beanName, ExecutorService executor) {
        PoolInfo poolInfo = new PoolInfo();
        poolInfo.setPoolBeanName(beanName);

        ExecutorServiceHandler fullyHandler = ExecutorServiceContext.getFullyHandler();
        if(fullyHandler != null) {
            fullyHandler.handler(executor, poolInfo);
        }
        return poolInfo;
    }
}
