package com.amateur.scanner;

import com.amateur.config.Properties;
import com.amateur.context.PoolContext;
import com.amateur.util.SpringUtil;
import com.amateur.worker.Worker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yeyu
 * @date 2021/12/14 17:38
 */
@Component
@Slf4j
public class WorkerScanner extends AbstractScanner {

    @Autowired
    public WorkerScanner(Properties properties, PoolContext poolContext) {
        super(properties, poolContext);
    }

    @Override
    public void scan() {
        log.info("start to scan worker");
        Map<String, Worker> beans = SpringUtil.getBeansWithClass(Worker.class);
        beans.values().forEach(poolContext::addWorkerLast);
    }
}
