package com.amateur.worker;

import com.amateur.constant.WorkerGroupConstant;
import com.amateur.info.PoolInfo;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yeyu
 * @date 2021/12/15 11:43
 */
@Component
public class DefaultWorker implements Worker {

    @Override
    public List<PoolInfo> handler(Map<String, Executor> beans) {
        List<PoolInfo> list = new ArrayList<>();
        beans.forEach((k,v)->{
            if (v instanceof ThreadPoolExecutor) {
                list.add(getPoolInfo(k,(ThreadPoolExecutor)v));
            } else if (v instanceof ThreadPoolTaskExecutor) {
                ThreadPoolExecutor threadPoolExecutor = ((ThreadPoolTaskExecutor) v).getThreadPoolExecutor();
                list.add(getPoolInfo(k,threadPoolExecutor));
            }
        });
        return list;
    }

    private PoolInfo getPoolInfo(String beanName,ThreadPoolExecutor threadPoolExecutor) {
        PoolInfo poolInfo = new PoolInfo();
        poolInfo.setPoolBeanName(beanName);
        poolInfo.setCorePoolSize(threadPoolExecutor.getCorePoolSize());
        poolInfo.setKeepAliveTime(threadPoolExecutor.getKeepAliveTime(TimeUnit.SECONDS));
        poolInfo.setMaximumPoolSize(threadPoolExecutor.getMaximumPoolSize());
        return poolInfo;
    }

    @Override
    public String tag() {
        return WorkerGroupConstant.GETTER;
    }
}
