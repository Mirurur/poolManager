package com.amateur;

import com.amateur.server.ThreadPoolManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;

/**
 * @author yeyu
 * @date 2021/12/9 16:48
 */
@SpringBootApplication
public class PoolServerApplication implements ApplicationListener<ApplicationContextEvent> {

    @Resource
    private ThreadPoolManagerServer managerServer;

    public static void main(String[] args) {
        SpringApplication.run(PoolServerApplication.class,args);
    }

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            new Thread(managerServer).start();
        }
    }
}
