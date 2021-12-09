package com.amateur.handler;

import com.alibaba.fastjson.JSON;
import com.amateur.pool.PoolInfoDetector;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yeyu
 * @date 2021/12/9 16:33
 */
@Component
@ChannelHandler.Sharable
public class PoolClientHandler extends SimpleChannelInboundHandler<String> {

    @Resource
    private PoolInfoDetector poolInfoDetector;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            ctx.writeAndFlush(JSON.toJSONString(poolInfoDetector.getClientPoolInfo()));
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("receive from server:"+msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(JSON.toJSONString(poolInfoDetector.getClientPoolInfo()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
