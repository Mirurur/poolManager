package com.amateur.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author yeyu
 * @date 2021/12/13 9:26
 */
@ConfigurationProperties(prefix = "pool")
@Component
public class Properties {

    private List<String> addressList = Collections.singletonList("localhost:8888");

    private int maxRetryTimes = 3;

    public List<String> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<String> addressList) {
        this.addressList = addressList;
    }

    public int getMaxRetryTimes() {
        return maxRetryTimes;
    }

    public void setMaxRetryTimes(int maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    public InetSocketAddress getDefaultConnectAddress() {
        String[] ipAndPort = addressList.get(0).split(":");
        return new InetSocketAddress(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
    }

    public List<InetSocketAddress> getSocketAddressList() {
        List<InetSocketAddress> result = new ArrayList<>();
        addressList.forEach(address -> {
            String[] ipAndPort = address.split(":");
            result.add(new InetSocketAddress(ipAndPort[0], Integer.parseInt(ipAndPort[1])));
        });
        return result;
    }
}
