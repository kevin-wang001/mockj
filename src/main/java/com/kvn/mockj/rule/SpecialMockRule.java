package com.kvn.mockj.rule;

import com.kvn.mockj.MockField;

/**
 * 特殊的 MockRule。baseValue 以 @ 开头的都认定为使用 SpecialMockRule
 * Created by wangzhiyuan on 2018/9/14
 */
public class SpecialMockRule extends AbstactMockRule {

    public SpecialMockRule(MockField mockField) {
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
    public String doGenerate() {
        return this.mockField.getBaseValue();
    }

}
