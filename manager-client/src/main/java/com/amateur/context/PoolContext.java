package com.amateur.context;

import com.amateur.detector.Detector;
import com.amateur.handler.executorservice.Worker;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yeyu
 * @date 2021/12/14 18:00
 */
@Data
@Component
public class PoolContext {

    private List<Detector> detectorList;

    private List<Worker> workerList;
}
