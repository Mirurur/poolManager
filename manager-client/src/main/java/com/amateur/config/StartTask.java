package com.amateur.config;

import com.amateur.client.ThreadPoolManagerClient;
import com.amateur.context.PoolContext;
import com.amateur.detector.Detector;
import com.amateur.scanner.Scanner;
import com.amateur.util.SpringUtil;

import java.util.Map;

/**
 * @author yeyu
 * @date 2021/12/15 11:26
 * 启动Spring容器时，同时启动该任务：1.启动客户端 2.收集线程池信息
 */
public class StartTask implements Runnable {


    private final PoolContext poolContext;

    private final ThreadPoolManagerClient client;

    public StartTask(ThreadPoolManagerClient client, PoolContext poolContext) {
        this.client = client;
        this.poolContext = poolContext;
    }

    @Override
    public void run() {
        // 启动client
        client.connect();
        // 加载Scanner
        Map<String, Scanner> beans = SpringUtil.getBeansWithClass(Scanner.class);
        // Scanner.scan()
        beans.values().forEach(Scanner::scan);
        // Detector.detect()
        poolContext.getDetectorList().forEach(Detector::detect);
    }
}
