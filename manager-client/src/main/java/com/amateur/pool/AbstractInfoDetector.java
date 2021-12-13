package com.amateur.pool;

import com.amateur.pool.info.ClientPoolInfo;

/**
 * @author yeyu
 * @date 2021/12/10 10:56
 */
public abstract class AbstractInfoDetector implements InfoDetector {

    private final ClientPoolInfo clientPoolInfo;

    public AbstractInfoDetector() {
        clientPoolInfo = new ClientPoolInfo();
    }

    public ClientPoolInfo getClientPoolInfo() {
        return clientPoolInfo;
    }

    @Override
    public void saveInfo() {
        doSaveInfo();
    }

    abstract void doSaveInfo();

}
