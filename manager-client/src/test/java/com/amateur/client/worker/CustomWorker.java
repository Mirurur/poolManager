package com.amateur.client.worker;

import com.amateur.constant.WorkerGroupConstant;
import com.amateur.info.PoolInfo;
import com.amateur.worker.Worker;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * @author yeyu
 * @date 2021/12/16 14:20
 */
@Component
public class CustomWorker implements Worker {
    @Override
    public String tag() {
        return WorkerGroupConstant.GETTER;
    }

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public List<PoolInfo> doGetter(Map<String, Executor> beans) {
        System.out.println("custom worker do get info");
        return null;
    }
}
