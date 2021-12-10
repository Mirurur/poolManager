package com.amateur.pool.info;

import lombok.Data;

import java.util.List;

/**
 * @author yeyu
 * @date 2021/12/9 17:59
 */
@Data
public class ClientPoolInfo {

    private List<PoolInfo> list;

    @Data
    public static class PoolInfo {
        private String beanName;
        private int corePoolSize;
        private int maximumPoolSize;
        private long keepAliveTime;
    }
}
