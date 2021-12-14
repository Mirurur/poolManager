package com.amateur.factory;

import com.amateur.handler.executorservice.Worker;

import java.util.*;

/**
 * @author sun
 */
public class ExecutorServiceHandlerFactory {

    //todo: 防御性拷贝？
    private final static Map<String, Worker> handlerMap;

    //todo: 通过SPI加载子类 or 将子类添加到ioc中，在启动后获取ioc容器中父类型为ExecutorServiceHandler的子类
    static {
        HashMap<String, Worker> map = new HashMap<>();
//        map.put(ExecutorServiceHandlerConstant.THREAD_POOL_EXECUTOR_HANDLER, new ThreadPoolExecutorHandler());
//        map.put(ExecutorServiceHandlerConstant.THREAD_POOL_TASK_EXECUTOR_HANDLER, new ThreadPoolTaskExecutorHandler());
        handlerMap = Collections.unmodifiableMap(map);
    }

    public static Worker getHandler(String name) {
        return handlerMap.get(name);
    }

    public static List<Worker> getHandlerList() {
        return new ArrayList<>(handlerMap.values());
    }

    public static Map<String, Worker> getHandlerMap() {
        return handlerMap;
    }
}
