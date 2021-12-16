package com.amateur.worker;

import com.amateur.info.PoolInfo;
import com.amateur.info.PoolParam;
import org.springframework.core.Ordered;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * @author sun
 * Worker 分为两种 SetterWorker 和 GetterWorker,前者负责处理服务端发送的消息，修改线程池信息，后者收集当前客户端的线程池信息
 */
public interface Worker extends Ordered {

    default List<PoolInfo> doGetter(Map<String, Executor> beans) {
        return null;
    }

    default void doSetter(Map<String,Executor> beans, PoolParam poolParam) {

    }

    String tag();
}
