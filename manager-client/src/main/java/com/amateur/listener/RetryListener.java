package com.amateur.listener;

import com.amateur.client.ThreadPoolManagerClient;
import com.amateur.config.Properties;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author yeyu
 * @date 2021/12/13 9:38
 */
@Slf4j
@Component(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RetryListener implements ChannelFutureListener {

    @Resource
    private Properties properties;

    @Resource
    private ThreadPoolManagerClient client;

    private int currentAddress = 0;

    private int retryTimes = 1;


    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if (retryTimes >= properties.getMaxRetryTimes()) {
            log.warn("try to reconnect {} times,but failed", retryTimes);
            throw new RuntimeException("connected server filed");
        }
        Channel channel = channelFuture.channel();
        InetSocketAddress socketAddress = properties.getSocketAddressList().get(currentAddress);
        if (!channelFuture.isSuccess()) {
            channel.eventLoop().schedule(() -> {
                log.info("try to reconnect server {} times", retryTimes);
                client.connect();
                retryTimes++;
                currentAddress = (++currentAddress) % properties.getSocketAddressList().size();
            }, 3, TimeUnit.SECONDS);
        }
        if (channelFuture.isSuccess()) {
            if (channelFuture.isSuccess()) {
                log.info("client start success,connected ip:{},port:{}", socketAddress.getHostName(), socketAddress.getPort());
            }
        }
    }
}
