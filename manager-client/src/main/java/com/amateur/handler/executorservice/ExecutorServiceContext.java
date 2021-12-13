package com.amateur.handler.executorservice;

import com.amateur.factory.ExecutorServiceHandlerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 管理ExecutorServiceHandler
 *
 * @author sun
 */
public class ExecutorServiceContext {

    /**
     * 组装所有责任链
     *
     * @return
     */
    public static ExecutorServiceHandler getFullyHandler() {
        List<ExecutorServiceHandler> handlerList = ExecutorServiceHandlerFactory.getHandlerList();
        ExecutorServiceHandler firstHandler = null;
        ExecutorServiceHandler preHandler = null;
        for (ExecutorServiceHandler handler : handlerList) {
            if(preHandler != null) {
                preHandler.setDelegate(handler);
            } else {
                firstHandler = handler;
            }
            preHandler = handler;
        }
        return firstHandler;
    }

    /**
     *
     *
     * @param excludeNameSet 排除的责任链
     * @return
     */
    public static ExecutorServiceHandler getHandlerExclude(Set<String> excludeNameSet) {
        Map<String, ExecutorServiceHandler> handlerMap = ExecutorServiceHandlerFactory.getHandlerMap();
        ExecutorServiceHandler firstHandler = null;
        ExecutorServiceHandler preHandler = null;
        for (Map.Entry<String, ExecutorServiceHandler> entry : handlerMap.entrySet()) {
            String key = entry.getKey();
            ExecutorServiceHandler handler = entry.getValue();
            if(!excludeNameSet.contains(key)) {
                if(preHandler != null) {
                    preHandler.setDelegate(handler);
                } else {
                    firstHandler = handler;
                }
                preHandler = handler;
            }
        }
        return firstHandler;
    }

    /**
     *
     * @param includeNameSet 包含的责任链
     * @return
     */
    public static ExecutorServiceHandler getHandlerInclude(Set<String> includeNameSet) {
        Map<String, ExecutorServiceHandler> handlerMap = ExecutorServiceHandlerFactory.getHandlerMap();
        ExecutorServiceHandler firstHandler = null;
        ExecutorServiceHandler preHandler = null;
        for (Map.Entry<String, ExecutorServiceHandler> entry : handlerMap.entrySet()) {
            String key = entry.getKey();
            ExecutorServiceHandler handler = entry.getValue();
            if(includeNameSet.contains(key)) {
                if(preHandler != null) {
                    preHandler.setDelegate(handler);
                } else {
                    firstHandler = handler;
                }
                preHandler = handler;
            }
        }
        return firstHandler;
    }
}
