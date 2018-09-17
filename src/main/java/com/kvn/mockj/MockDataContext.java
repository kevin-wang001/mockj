package com.kvn.mockj;

import java.util.HashMap;
import java.util.Map;

/**
 * mock 数据的上下文。用于保存当前线程中需要保存上一次 mock 值的数据。例如："number|+1": 610，需要基于上一次的 mock 值做累加
 * Created by wangzhiyuan on 2018/9/17
 */
public class MockDataContext {
    private static final ThreadLocal<MockDataContext> MOCK_CONTEXT = new ThreadLocal<MockDataContext>(){
        @Override
        protected MockDataContext initialValue() {
            return new MockDataContext();
        }
    };

    /**
     * 历史的 mock 值
     */
    private Map<MockField, Object> history = new HashMap<>();

    private MockDataContext() {
    }

    public static MockDataContext get() {
        return MOCK_CONTEXT.get();
    }

    public static Map<MockField, Object> getHistory() {
        return get().history;
    }
}
