package com.amateur.controller;

import com.amateur.handler.PoolServerHandler;
import com.amateur.pool.PoolInfoContainer;
import com.amateur.pool.info.PoolParam;
import com.amateur.pool.info.ClientPoolInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author yeyu
 * @date 2021/12/10 9:55
 */
@RestController
@RequestMapping("op")
public class OpController {

    @Resource
    private PoolServerHandler handler;

    @Resource
    private PoolInfoContainer container;

    @PostMapping("modify")
    public Boolean modify(@RequestBody PoolParam param) {
        return handler.modifyPoolInfo(param);
    }

    @GetMapping("all")
    public Map<String, ClientPoolInfo> all() {
        return container.getMap();
    }
}
