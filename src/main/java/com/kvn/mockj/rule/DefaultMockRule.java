package com.kvn.mockj.rule;

import com.alibaba.fastjson.JSON;
import com.kvn.mockj.MockField;

/**
 * 默认的 MockRule
 * Created by wangzhiyuan on 2018/9/14
 */
public class DefaultMockRule extends AbstactMockRule {

    public DefaultMockRule(MockField mockField) {
        super(mockField);
    }

    /**
     * 是否适用本规则
     * @param mockField 需要 mock 的属性
     * @return
     */
    public static boolean match(MockField mockField) {
        return true;
    }


    @Override
    public Object doGenerate() {
        return this.mockField.getBaseValue();
    }

}
