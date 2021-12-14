package com.amateur.info;

import lombok.Data;

import java.util.List;

/**
 * 客户端信息
 *
 * @author sun
 */
@Data
public class ClientInfo {

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 客户端ip地址
     */
    private String ipAddr;

    /**
     * 客户端地址
     */
    private String port;

    /**
     * 线程池信息
     */
    private List<PoolInfo> poolList;
}
