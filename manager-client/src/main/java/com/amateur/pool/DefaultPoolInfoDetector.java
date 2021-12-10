package com.amateur.pool;

import com.amateur.annotation.PoolControl;
import com.amateur.pool.info.ClientPoolInfo;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yeyu
 * @date 2021/12/9 17:44
 */
@Component
public class DefaultPoolInfoDetector extends AbstractInfoDetector implements ApplicationContextAware {

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    void doSaveInfo() {
        Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(PoolControl.class);
        List<ClientPoolInfo.PoolInfo> poolInfoList = new ArrayList<>();
        Map<String,ThreadPoolExecutor> executorMap = new HashMap<>();
        beansWithAnnotation.forEach((k, v) -> {
            ClientPoolInfo.PoolInfo poolInfo;
            ThreadPoolExecutor poolExecutor = null;
            if (v instanceof ThreadPoolExecutor) {
                poolExecutor = (ThreadPoolExecutor) v;
            } else if (v instanceof ThreadPoolTaskExecutor) {
                ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) v;
                poolExecutor = taskExecutor.getThreadPoolExecutor();
            }
            if (ObjectUtils.isEmpty(poolExecutor)) {
                return;
            }
            poolInfo = getPoolInfo(k, poolExecutor);
            poolInfoList.add(poolInfo);
            executorMap.put(k,poolExecutor);
        });
        clientPoolInfo.getPoolInfoList().addAll(poolInfoList);
        clientPoolInfo.getPoolExecutorMap().putAll(executorMap);
    }

    private ClientPoolInfo.PoolInfo getPoolInfo(String beanName, ThreadPoolExecutor executor) {
        ClientPoolInfo.PoolInfo poolInfo = new ClientPoolInfo.PoolInfo();
        poolInfo.setBeanName(beanName);
        poolInfo.setCorePoolSize(executor.getCorePoolSize());
        poolInfo.setMaximumPoolSize(executor.getMaximumPoolSize());
        poolInfo.setKeepAliveTime(executor.getKeepAliveTime(TimeUnit.SECONDS));
        return poolInfo;
    }
}
