package com.amateur.pool.info;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 线程池信息
 *
 * @author sun
 */
@Data
public class PoolInfo {
    /**
     * 全限定类名
     */
    private String fullClassName;

    /**
     * 线程池Bean名称
     */
    private String poolBeanName;

    private int corePoolSize;

    private int maximumPoolSize;

    private String workQueueFullyClassName;

    private long keepAliveTime;

    private String threadFactoryFullyClassName;

    private String handlerFullyClassName;
}
