package com.amateur.handler.executorservice;

import com.amateur.pool.info.PoolInfo;

import java.util.concurrent.ExecutorService;

/**
 * @author sun
 */
public interface ExecutorServiceHandler {

    void handler(ExecutorService executor, PoolInfo poolInfo);

    void setDelegate(ExecutorServiceHandler delegate);
}
