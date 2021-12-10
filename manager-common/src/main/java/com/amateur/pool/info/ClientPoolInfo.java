package com.amateur.pool.info;

import lombok.Data;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yeyu
 * @date 2021/12/9 17:59
 */
@Data
public class ClientPoolInfo {

    private List<PoolInfo> poolInfoList = new ArrayList<>();

    private transient Map<String,ThreadPoolExecutor> poolExecutorMap = new HashMap<>();

    @Data
    public static class PoolInfo {
        private String beanName;
        private int corePoolSize;
        private int maximumPoolSize;
        private long keepAliveTime;
    }

}
