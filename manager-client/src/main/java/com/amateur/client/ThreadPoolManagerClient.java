package com.amateur.client;

import com.amateur.config.Properties;
import com.amateur.handler.PoolClientHandler;
import com.amateur.listener.RetryListener;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author yeyu
 * @date 2021/11/22 14:55
 * Netty 客户端
 */
@Slf4j
@Component
public class ThreadPoolManagerClient implements DisposableBean {

    @Resource
    private PoolClientHandler poolClientHandler;

    @Resource
    private Properties properties;

    @Resource
    private RetryListener retryListener;

    private EventLoopGroup workGroup;

    private Bootstrap bootstrap;

    public ThreadPoolManagerClient() {
        init();
    }

    private void init() {
        workGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                // 使用JSON数据格式进行传输 所以用String编解码器
                                .addLast(new StringEncoder())
                                .addLast(new StringDecoder())
                                // 当没有发生写事件时，每隔3秒向服务端发送连接池信息
                                .addLast(new IdleStateHandler(0, 3, 0, TimeUnit.SECONDS))
                                .addLast(poolClientHandler);
                    }
                });
    }

    public void connect() {
        try {
            ChannelFuture channelFuture = bootstrap.connect(properties.getDefaultConnectAddress()).addListener(retryListener);
            channelFuture.channel().closeFuture().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        log.info("pool client channel has closed");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            workGroup.shutdownGracefully();
        }
    }

    @Override
    public void destroy() throws Exception {
        workGroup.shutdownGracefully();
        log.info("client shut down...");
    }
}
