package com.amateur.pool;

import com.amateur.annotation.PoolControl;
import com.amateur.pool.info.ClientPoolInfo;
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
        List<ClientPoolInfo.PoolInfo> list = new ArrayList<>();
        beansWithAnnotation.forEach((k, v) -> {
            if (ExecutorService.class.isAssignableFrom(v.getClass())) {
                list.add(getPoolInfo((ExecutorService) v,k));
            }
        });
        clientPoolInfo.setList(list);

    }

    /**
     * Spring Boot中有两种创建线程池的方式
     * <li>通过JDK原生ThreadPoolExecutor创建</li>
     * <li>通过SpringBoot提供的ThreadPoolTaskExecutor</li>
     * @param executor 线程池
     * @param beanName Spring容器中Bean名称
     * @return 线程池信息
     * TODO 待修改
     */
    public ClientPoolInfo.PoolInfo getPoolInfo(ExecutorService executor,String beanName) {
        ClientPoolInfo.PoolInfo poolInfo = new ClientPoolInfo.PoolInfo();
        poolInfo.setBeanName(beanName);
        if (executor instanceof ThreadPoolExecutor) {
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
            poolInfo.setCorePoolSize(threadPoolExecutor.getCorePoolSize());
            poolInfo.setMaximumPoolSize(threadPoolExecutor.getMaximumPoolSize());
            poolInfo.setKeepAliveTime(threadPoolExecutor.getKeepAliveTime(TimeUnit.SECONDS));
        } else if (executor instanceof ThreadPoolTaskExecutor) {
            ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) executor;
            poolInfo.setKeepAliveTime(taskExecutor.getKeepAliveSeconds());
            poolInfo.setCorePoolSize(taskExecutor.getCorePoolSize());
            poolInfo.setMaximumPoolSize(taskExecutor.getMaxPoolSize());
        }
        return poolInfo;
    }
}
