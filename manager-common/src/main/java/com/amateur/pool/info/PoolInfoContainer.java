package com.amateur.pool.info;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yeyu
 * @date 2021/12/9 17:36
 */
@Data
public class PoolInfoContainer {

    private Map<String, ClientPoolInfo> map = new ConcurrentHashMap<>(16);
}
