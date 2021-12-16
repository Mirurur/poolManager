package com.amateur.scanner;

import com.amateur.config.Properties;
import com.amateur.context.PoolContext;
import com.amateur.detector.Detector;
import com.amateur.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yeyu
 * @date 2021/12/14 17:30
 */
@Component
@Slf4j
public class DetectorScanner extends AbstractScanner {

    @Autowired
    public DetectorScanner(Properties properties, PoolContext poolContext) {
        super(properties, poolContext);
    }

    @Override
    public void scan() {
        log.info("start to scan detector");
        Map<String, Detector> beans = SpringUtil.getBeansWithClass(Detector.class);
        beans.values().forEach(poolContext::addDetector);
    }


}
