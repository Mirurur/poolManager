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
public abstract class BaseDetector implements Detector{

    @Override
    public final ClientInfo getClientInfo() {
        Map<String, Object> threadPoolMap = SpringUtil.getBeansWithAnnotation(PoolControl.class);
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setClientName("123");
        List<PoolInfo> poolList = new ArrayList<>();

        threadPoolMap.forEach((k, v) -> {
            if (ExecutorService.class.isAssignableFrom(v.getClass())) {
                poolList.add(getPoolInfo(k, (ExecutorService) v));
            }
        });
        clientInfo.setPoolList(poolList);
        return clientInfo;
    }

    public abstract PoolInfo getPoolInfo(String beanName, ExecutorService executor);
}
