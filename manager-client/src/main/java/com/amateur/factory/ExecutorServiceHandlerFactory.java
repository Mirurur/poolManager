package com.amateur.factory;

import com.amateur.constant.ExecutorServiceHandlerConstant;
import com.amateur.handler.executorservice.ExecutorServiceHandler;
import com.amateur.handler.executorservice.ThreadPoolExecutorHandler;
import com.amateur.handler.executorservice.ThreadPoolTaskExecutorHandler;

import java.util.*;

/**
 * @author sun
 */
public class ExecutorServiceHandlerFactory {

    //todo: 防御性拷贝？
    private final static Map<String, ExecutorServiceHandler> handlerMap;

    //todo: 通过SPI加载子类 or 将子类添加到ioc中，在启动后获取ioc容器中父类型为ExecutorServiceHandler的子类
    static {
        HashMap<String, ExecutorServiceHandler> map = new HashMap<>();
        map.put(ExecutorServiceHandlerConstant.THREADPOOL_EXECUTOR_HANDLER, new ThreadPoolExecutorHandler());
        map.put(ExecutorServiceHandlerConstant.THREADPOOL_TASK_EXECUTOR_HANDLER, new ThreadPoolTaskExecutorHandler());
        handlerMap = Collections.unmodifiableMap(map);
    }

    public static ExecutorServiceHandler getHandler(String name) {
        return handlerMap.get(name);
    }

    public static List<ExecutorServiceHandler> getHandlerList() {
        return new ArrayList<>(handlerMap.values());
    }

    public static Map<String, ExecutorServiceHandler> getHandlerMap() {
        return handlerMap;
    }
}
