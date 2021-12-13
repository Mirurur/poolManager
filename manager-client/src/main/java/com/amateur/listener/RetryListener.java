package com.amateur.listener;

import com.amateur.config.ConnectConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author yeyu
 * @date 2021/12/13 9:38
 */
@Slf4j
public class RetryListener implements ChannelFutureListener {

    private final ConnectConfig connectConfig;

    private int currentAddress = 0;

    private int retryTimes = 1;

    public RetryListener(ConnectConfig connectConfig) {
        this.connectConfig = connectConfig;
    }


    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if (retryTimes >= connectConfig.getMaxRetryTimes()) {
            log.warn("try to reconnect {} times,but failed", retryTimes);
            throw new RuntimeException("connected server filed");
        }
        Channel channel = channelFuture.channel();
        InetSocketAddress socketAddress = connectConfig.getSocketAddressList().get(currentAddress);
        if (!channelFuture.isSuccess()) {
            channel.eventLoop().schedule(() -> {
                log.info("try to reconnect server {} times", retryTimes);
                channel.connect(socketAddress).addListener(RetryListener.this);
                retryTimes++;
                currentAddress = (++currentAddress) % connectConfig.getSocketAddressList().size();
            }, 3, TimeUnit.SECONDS);
        }
        if (channelFuture.isSuccess()) {
            if (channelFuture.isSuccess()) {
                log.info("client start success,connected ip:{},port:{}", socketAddress.getHostName(), socketAddress.getPort());
            }
        }
    }
}
