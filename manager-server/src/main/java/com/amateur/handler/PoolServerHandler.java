package com.amateur.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author yeyu
 * @date 2021/12/9 16:42
 */
@Component
@Slf4j
@ChannelHandler.Sharable
public class PoolServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 存储客户端对应的channel组
     */
    private final static ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("receive from client:{}",msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        CHANNEL_GROUP.add(ctx.channel());
        log.info("client [{}] is active",ctx.channel().remoteAddress().toString().substring(1));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("client [{}] is inactive",ctx.channel().remoteAddress().toString().substring(1));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("client [{}] is inactive because of exception",ctx.channel().remoteAddress().toString().substring(1));
        ctx.close();
    }
}
