package com.kvn.mockj.rule;

import com.kvn.mockj.MockField;

/**
 * 对 boolean 类型的数据做随机mock。形如："boolean|1":true<br/>
 * Created by wangzhiyuan on 2018/9/14
 */
public class BooleanMockRule extends AbstactMockRule {

    public BooleanMockRule(MockField mockField) {
        super(mockField);
    }

    /**
     * 是否适用本规则。只要类型是 boolean，就按 BooleanMockRule 来处理
     * @param mockField 需要 mock 的属性
     * @return
     */
    public static boolean match(MockField mockField) {
        boolean typeMatch = boolean.class == mockField.getBaseValueType() || Boolean.class == mockField.getBaseValueType();
        return typeMatch;
    }


    @Override
    public String doGenerate() {
        if ("@random".equals(mockField.getRuleStr().toLowerCase())) {
            return Boolean.valueOf(RANDOM.nextBoolean()).toString();
        }
        return this.mockField.getBaseValue();
    }

}
