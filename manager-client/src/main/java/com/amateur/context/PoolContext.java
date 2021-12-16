package com.amateur.context;

import com.amateur.detector.Detector;
import com.amateur.info.ClientInfo;
import com.amateur.info.PoolInfo;
import com.amateur.worker.Worker;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author yeyu
 * @date 2021/12/14 18:00
 * 上下文信息，保存了Detector集合，Worker集合，与线程池信息
 */
@Component
public class PoolContext {

    private final List<Detector> detectorList;

    private final Map<String, List<Worker>> workerMap;

    private final ClientInfo clientInfo;

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public Map<String, List<Worker>> getWorkerMap() {
        return workerMap;
    }

    public PoolContext() {
        this.detectorList = new ArrayList<>();
        this.workerMap = new HashMap<>();
        this.clientInfo = new ClientInfo();
        this.clientInfo.setPoolList(new ArrayList<>());
    }

    public void addWorker(Worker worker) {
        if (StringUtils.isEmpty(worker.tag())) {
            throw new RuntimeException("add worker failed,worker tag must not be null");
        }
        List<Worker> workers = workerMap.get(worker.tag());
        if (CollectionUtils.isEmpty(workers)) {
            workers = new LinkedList<>();
            workerMap.put(worker.tag(),workers);
        }
        workers.add(worker);
    }


    public void addPoolInfo(List<PoolInfo> poolInfoList) {
        if (!CollectionUtils.isEmpty(poolInfoList)) {
            this.clientInfo.getPoolList().addAll(poolInfoList);
        }
    }

    public List<Detector> getDetectorList() {
        return detectorList;
    }

    public void addDetector(Detector detector) {
        this.detectorList.add(detector);
    }

    public boolean removeDetector(Detector detector) {
        return this.detectorList.remove(detector);
    }

}
