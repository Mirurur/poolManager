package com.amateur.controller;

import com.amateur.handler.PoolServerHandler;
import com.amateur.pool.PoolParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yeyu
 * @date 2021/12/10 9:55
 */
@RestController
@RequestMapping("op")
public class OpController {

    @Resource
    private PoolServerHandler handler;

    @PostMapping("modify")
    public Boolean modify(@RequestBody PoolParam param) {
        return handler.modifyPoolInfo(param);
    }
}
