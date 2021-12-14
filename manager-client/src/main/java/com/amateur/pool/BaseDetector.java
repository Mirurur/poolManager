package com.amateur.pool;

import com.amateur.annotation.PoolControl;
import com.amateur.pool.info.ClientInfo;
import com.amateur.pool.info.PoolInfo;
import com.amateur.util.SpringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @author sun
 */
public abstract class BaseDetector implements Detector {

    private final ClientInfo clientInfo;

    public BaseDetector() {
        this.clientInfo = new ClientInfo();
        this.clientInfo.setPoolList(new ArrayList<>());
    }

    private ClientInfo getClientInfo() {
        return clientInfo;
    }

    @Override
    public final ClientInfo saveClientInfo() {
        // 通过Spring容器获取到所有需要被管理的线程池
        Map<String, Object> threadPoolMap = SpringUtil.getBeansWithAnnotation(PoolControl.class);
        ClientInfo clientInfo = getClientInfo();
        List<PoolInfo> poolList = getClientInfo().getPoolList();

        // 获取所有线程池的信息
        threadPoolMap.forEach((k, v) -> {
            if (ExecutorService.class.isAssignableFrom(v.getClass())) {
                poolList.add(getPoolInfo(k, (ExecutorService) v));
            }
        });

        return clientInfo;
    }

    public abstract PoolInfo getPoolInfo(String beanName, ExecutorService executor);
}
