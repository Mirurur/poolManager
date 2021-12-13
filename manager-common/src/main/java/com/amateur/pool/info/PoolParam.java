package com.amateur.pool.info;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yeyu
 * @date 2021/12/10 9:57
 */
@Data
public class PoolParam implements Serializable {
    private String remoteAddress;
    private String beanName;
    private int corePoolSize;
    private int maxPoolSize;
    private long keepAliveTime;
}
