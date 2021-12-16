package com.amateur.detector;

import com.amateur.constant.WorkerGroupConstant;
import com.amateur.context.PoolContext;
import com.amateur.util.SpringUtil;
import com.amateur.worker.Worker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * @author yeyu
 * @date 2021/12/15 10:19
 */
@Component
@Slf4j
public class DefaultDetector implements Detector {

    private final PoolContext poolContext;

    private final Map<String, List<Worker>> workerMap;

    @Autowired
    public DefaultDetector(PoolContext poolContext) {
        this.poolContext = poolContext;
        this.workerMap = poolContext.getWorkerMap();
    }

    @Override
    public void detect() {
        log.info("start to detect pool info");
        Map<String, Executor> beans = SpringUtil.getBeansWithClass(Executor.class);
        workerMap.get(WorkerGroupConstant.GETTER).forEach(worker -> poolContext.addPoolInfo(worker.doGetter(beans)));
    }


}
