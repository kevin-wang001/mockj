package com.kvn.mockj.rule;

/**
 * Created by wangzhiyuan on 2018/9/13
 */
public interface MockRule {

    /**
     * 生成 mock 数据
     */
    Object generateMockData();
}
