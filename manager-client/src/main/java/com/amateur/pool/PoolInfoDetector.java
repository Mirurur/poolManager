package com.amateur.pool;

import com.amateur.annotation.PoolControl;
import com.amateur.pool.info.ClientPoolInfo;
import com.amateur.pool.info.PoolInfo;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yeyu
 * @date 2021/12/9 17:44
 */
@Component
public class PoolInfoDetector implements ApplicationContextAware {

    private final ClientPoolInfo clientPoolInfo = new ClientPoolInfo();

    public ClientPoolInfo getClientPoolInfo() {
        return clientPoolInfo;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(PoolControl.class);
        List<PoolInfo> list = new ArrayList<>();
        beansWithAnnotation.forEach((k, v) -> {
            if (ExecutorService.class.isAssignableFrom(v.getClass())) {
                list.add(getPoolInfo((ExecutorService) v));
            }
        });
        clientPoolInfo.setList(list);

    }

    public PoolInfo getPoolInfo(ExecutorService executor) {
        PoolInfo poolInfo = new PoolInfo();
        if (executor instanceof ThreadPoolExecutor) {
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
            poolInfo.setCorePoolSize(threadPoolExecutor.getCorePoolSize());
            poolInfo.setMaximumPoolSize(threadPoolExecutor.getMaximumPoolSize());
            poolInfo.setKeepAliveTime(threadPoolExecutor.getKeepAliveTime(TimeUnit.SECONDS));
        } else {
            ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) executor;
            poolInfo.setKeepAliveTime(taskExecutor.getKeepAliveSeconds());
            poolInfo.setCorePoolSize(taskExecutor.getCorePoolSize());
            poolInfo.setMaximumPoolSize(taskExecutor.getMaxPoolSize());
        }
        return poolInfo;
    }
}
