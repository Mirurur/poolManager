package com.amateur.constant;

/**
 * @author yeyu
 * @date 2021/12/15 14:49
 */
public interface WorkerGroupConstant {
    /**
     * 标记此worker是从客户端拉取线程池信息
     */
    String GETTER = "getter";

    /**
     * 标记此worker用于处理服务端信息
     */
    String SETTER = "setter";
}
