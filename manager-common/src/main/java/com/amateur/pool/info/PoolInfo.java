package com.amateur.pool.info;

import lombok.Data;

/**
 * @author yeyu
 * @date 2021/12/9 17:32
 */
@Data
public class PoolInfo {
    private String poolName;
    private int corePoolSize;
    private int maximumPoolSize;
    private long keepAliveTime;
    private String timeUnit = "s";
}
