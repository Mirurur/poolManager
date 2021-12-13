package com.amateur.handler;

import com.alibaba.fastjson.JSON;
import com.amateur.config.ConnectConfig;
import com.amateur.listener.RetryListener;
import com.amateur.pool.AbstractInfoDetector;
import com.amateur.pool.info.ClientPoolInfo;
import com.amateur.pool.info.PoolParam;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author yeyu
 * @date 2021/12/9 16:33
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class PoolClientHandler extends SimpleChannelInboundHandler<String> {

    @Resource
    private AbstractInfoDetector infoDetector;

    @Resource
    private ConnectConfig connectConfig;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            ctx.writeAndFlush(JSON.toJSONString(infoDetector.getClientPoolInfo()));
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("receive from server:" + msg);
        PoolParam poolParam = JSON.parseObject(msg, PoolParam.class);
        infoDetector.getClientPoolInfo().getPoolExecutorMap().forEach((k, v) -> {
            if (k.equals(poolParam.getBeanName())) {
                v.setCorePoolSize(poolParam.getCorePoolSize());
                v.setMaximumPoolSize(poolParam.getMaxPoolSize());
                v.setKeepAliveTime(poolParam.getKeepAliveTime(), TimeUnit.SECONDS);
            }
        });
        ClientPoolInfo.PoolInfo poolInfo = infoDetector.getClientPoolInfo().getPoolInfoList().stream()
                .filter(it -> it.getBeanName().equals(poolParam.getBeanName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("not found beanName:" + poolParam.getBeanName()));
        poolInfo.setCorePoolSize(poolParam.getCorePoolSize());
        poolInfo.setMaximumPoolSize(poolParam.getMaxPoolSize());
        poolInfo.setKeepAliveTime(poolParam.getKeepAliveTime());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        infoDetector.saveInfo();
        ctx.writeAndFlush(JSON.toJSONString(infoDetector.getClientPoolInfo()));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.warn("disconnect from server,try to reconnect server");
        ctx.channel().connect(connectConfig.getDefaultConnectAddress()).addListener(new RetryListener(connectConfig));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
