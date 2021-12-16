package com.amateur.detector;

import com.amateur.info.PoolInfo;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yeyu
 * @date 2021/12/9 17:36
 */
@Data
@Component
public class PoolInfoContainer {

    private Map<String, List<PoolInfo>> map = new ConcurrentHashMap<>(16);

    public void clear() {
        map.clear();
    }

    public void clearByAddress(String remoteAddress) {
        map.remove(remoteAddress);
    }
}
