package com.amateur.scanner;

import com.amateur.config.Properties;
import com.amateur.context.PoolContext;

/**
 * @author yeyu
 * @date 2021/12/14 17:41
 */
public abstract class AbstractScanner implements Scanner {

    final Properties properties;

    final PoolContext poolContext;

    public AbstractScanner(Properties properties, PoolContext poolContext) {
        this.properties = properties;
        this.poolContext = poolContext;
    }

    @Override
    public abstract void scan();


}
