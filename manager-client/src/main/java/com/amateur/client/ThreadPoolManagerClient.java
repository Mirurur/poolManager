package com.amateur.client;

import com.amateur.handler.PoolClientHandler;
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
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author yeyu
 * @date 2021/11/22 14:55
 */
@Slf4j
public class ThreadPoolManagerClient implements Runnable, DisposableBean {

    private EventLoopGroup workGroup;

    @Resource
    private PoolClientHandler poolClientHandler;

    @Value("${pool.server.ip:127.0.0.1}")
    private String ip;

    @Value("${pool.server.port:8888}")
    private Integer port;

    @Override
    public void run() {
        workGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
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
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(ip, port)).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (!channelFuture.isSuccess()) {
                        EventLoop eventLoop = channelFuture.channel().eventLoop();
                        // 连接不上 重新连接
                        eventLoop.schedule(() -> {
                            log.info("try to reconnect server...");
                            bootstrap.connect(new InetSocketAddress(ip, port)).addListener(this);
                        }, 3, TimeUnit.SECONDS);
                    }
                    if (channelFuture.isSuccess()) {
                        log.info("client start success,connected ip:{},port:{}", ip, port);
                    }
                }
            });
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            workGroup.shutdownGracefully();
        }
    }

    @Override
    public void destroy() throws Exception {
        assert workGroup != null;
        workGroup.shutdownGracefully();
        log.info("client shut down...");
    }
}
