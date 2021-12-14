package com.amateur.scanner;

import java.util.Set;

/**
 * @author yeyu
 * @date 2021/12/14 17:41
 */
public abstract class AbstractScanner implements Scanner {
    @Override
    public void scan() {

        Set<Class<?>> classes = doScan();

        filterClasses(classes);

        createBean(classes);

    }

    public abstract Set<Class<?>> doScan();

    private void filterClasses(Set<Class<?>> classes) {
        classes.removeIf(next -> !check(next));
    }

    public abstract boolean check(Class<?> clazz);


    private void createBean(Set<Class<?>> classes) {

    }
}
