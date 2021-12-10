package com.amateur.pool;

import com.amateur.pool.info.ClientPoolInfo;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yeyu
 * @date 2021/12/9 17:36
 */
@Data
@Component
public class PoolInfoContainer {

    private Map<String, ClientPoolInfo> map = new ConcurrentHashMap<>(16);
}
