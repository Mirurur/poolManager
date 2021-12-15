package com.amateur.worker;

import com.amateur.info.PoolInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * @author sun
 */
public interface Worker {

    List<PoolInfo> handler(Map<String, Executor> beans);

    String tag();
}
