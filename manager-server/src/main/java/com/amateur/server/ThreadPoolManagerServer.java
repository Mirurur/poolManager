package com.amateur.server;

import com.amateur.handler.PoolServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetSocketAddress;

/**
 * @author yeyu
 * @date 2021/11/22 16:02
 */
@Component
@Slf4j
public class ThreadPoolManagerServer implements Runnable, DisposableBean {

    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;

    @Resource
    private PoolServerHandler poolServerHandler;

    @Override
    public void run() {
        bossGroup = new NioEventLoopGroup(1);
        workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline()
                                    .addLast(new StringEncoder())
                                    .addLast(new StringDecoder())
                                    .addLast(poolServerHandler);
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(8888)).sync().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        log.info("pool server start success,bind:{}",8888);
                    }
                }
            });
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    @Override
    public void destroy() throws Exception {
        assert bossGroup != null;
        bossGroup.shutdownGracefully();
        assert workGroup != null;
        workGroup.shutdownGracefully();
        log.info("pool manager server shut down...");
    }
}
