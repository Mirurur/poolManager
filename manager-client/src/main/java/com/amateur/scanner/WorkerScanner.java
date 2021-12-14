package com.amateur.scanner;

import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author yeyu
 * @date 2021/12/14 17:38
 */
@Component
public class WorkerScanner extends AbstractScanner {

    @Override
    public Set<Class<?>> doScan() {
        return null;
    }


    @Override
    public boolean check(Class<?> clazz) {
        return false;
    }
}
