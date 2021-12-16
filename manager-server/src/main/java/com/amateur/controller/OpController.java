package com.amateur.controller;

import com.amateur.detector.PoolInfoContainer;
import com.amateur.handler.PoolServerHandler;
import com.amateur.info.PoolInfo;
import com.amateur.info.PoolParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
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
    public Map<String, List<PoolInfo>> all() {
        return container.getMap();
    }
}
