package com.amateur.scanner;

/**
 * @author yeyu
 * @date 2021/12/14 17:36
 *
 * <p>Scanner扫描器 获取Spring容器中的Detector和Worker并将其加入到PoolContext中</>
 */
public interface Scanner {
    void scan();
}
