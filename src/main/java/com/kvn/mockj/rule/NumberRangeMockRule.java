package com.kvn.mockj.rule;

import com.kvn.mockj.MockField;

import java.util.Random;

/**
 * 按区间范围规则生成 mock 数据。形如 "number|1-10":"1"
 * Created by wangzhiyuan on 2018/9/14
 */
public class NumberRangeMockRule extends AbstactMockRule {

    public NumberRangeMockRule(MockField mockField) {
        super(mockField);
    }

    /**
     * 是否适用本规则。需要优先按照 baseValue 的类型来匹配，再按 ruleStr 来匹配
     * @param mockField 需要 mock 的属性
     * @return
     */
    public static boolean match(MockField mockField) {
        boolean typeMatch = int.class == mockField.getBaseValueType() || Number.class.isAssignableFrom(mockField.getBaseValueType());
        return typeMatch && AbstactMockRule.isRangePattern(mockField.getRuleStr());
    }




    @Override
    public Object doGenerate() {
        String baseValue = this.mockField.getBaseValue();
        String[] strs = this.mockField.getRuleStr().split("-");
        if (strs.length == 1) {
            // 生成固定的数字 rule * baseValue
            return Integer.valueOf(strs[0]) * Integer.valueOf(baseValue);
        }
        int min = Integer.valueOf(strs[0]);
        int max = Integer.valueOf(strs[1]);
        // 生成区间内的数字
        return new Random().nextInt(max - min) + min;
    }

}
