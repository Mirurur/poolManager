package com.amateur.handler.executorservice;

import com.amateur.info.PoolInfo;

import java.util.concurrent.Executor;

/**
 * @author sun
 */
public interface Worker {

    void handler(Executor executor, PoolInfo poolInfo);
}
