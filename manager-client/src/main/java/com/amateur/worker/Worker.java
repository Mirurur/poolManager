package com.amateur.worker;

import com.amateur.info.PoolInfo;
import com.amateur.info.PoolParam;
import org.springframework.core.Ordered;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * @author sun
 */
public interface Worker extends Ordered {

    default List<PoolInfo> doGetter(Map<String, Executor> beans) {
        return null;
    }

    default void doSetter(Map<String,Executor> beans, PoolParam poolParam) {

    }

    String tag();
}
