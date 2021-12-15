package com.amateur.context;

import com.amateur.detector.Detector;
import com.amateur.info.ClientInfo;
import com.amateur.info.PoolInfo;
import com.amateur.worker.Worker;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author yeyu
 * @date 2021/12/14 18:00
 */
@Component
public class PoolContext {

    private final List<Detector> detectorList;

    private final Map<String, LinkedList<Worker>> workerMap;

    private final ClientInfo clientInfo;

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public Map<String, LinkedList<Worker>> getWorkerMap() {
        return workerMap;
    }

    public PoolContext() {
        this.detectorList = new ArrayList<>();
        this.workerMap = new HashMap<>();
        this.clientInfo = new ClientInfo();
        this.clientInfo.setPoolList(new ArrayList<>());
    }

    public void addWorkerLast(Worker worker) {
        LinkedList<Worker> workers = workerMap.get(worker.tag());
        if (CollectionUtils.isEmpty(workers)) {
            workers = new LinkedList<>();
            workerMap.put(worker.tag(),workers);
        }
        workers.addLast(worker);
    }

    public void addWorkerBefore(Worker worker) {
        LinkedList<Worker> workers = workerMap.get(worker.tag());
        if (CollectionUtils.isEmpty(workers)) {
            workers = new LinkedList<>();
            workerMap.put(worker.tag(),workers);
        }
        workers.addFirst(worker);
    }

    public void addPoolInfo(List<PoolInfo> poolInfoList) {
        this.clientInfo.getPoolList().addAll(poolInfoList);
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
