package com.amateur.worker;

import com.amateur.constant.WorkerGroupConstant;
import com.amateur.info.PoolParam;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yeyu
 * @date 2021/12/16 11:39
 */
@Component
public class DefaultSetterWorker implements Worker {

    @Override
    public void doSetter(Map<String, Executor> beans, PoolParam poolParam) {
        Executor executor = beans.get(poolParam.getBeanName());
        ThreadPoolExecutor threadPoolExecutor = null;
        if (executor instanceof ThreadPoolExecutor) {
            threadPoolExecutor = (ThreadPoolExecutor) executor;
        } else if (executor instanceof ThreadPoolTaskExecutor) {
            threadPoolExecutor = ((ThreadPoolTaskExecutor) executor).getThreadPoolExecutor();
        }
        if (ObjectUtils.isEmpty(threadPoolExecutor)) {
            return;
        }
        threadPoolExecutor.setCorePoolSize(poolParam.getCorePoolSize());
        threadPoolExecutor.setMaximumPoolSize(poolParam.getMaxPoolSize());
        threadPoolExecutor.setKeepAliveTime(poolParam.getKeepAliveTime(), TimeUnit.SECONDS);
    }

    @Override
    public String tag() {
        return WorkerGroupConstant.SETTER;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
