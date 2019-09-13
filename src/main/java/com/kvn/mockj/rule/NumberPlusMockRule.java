package com.kvn.mockj.rule;

import com.kvn.mockj.MockDataContext;
import com.kvn.mockj.MockField;

import java.util.regex.Pattern;

/**
 * 在 baseValue 的基础上每次增加固定的值。形如："number|+1":201
 * Created by wangzhiyuan on 2018/9/14
 */
public class NumberPlusMockRule extends AbstactMockRule implements MockRule {
    private static final Pattern pattern = Pattern.compile("[+-]\\d{1,}");

    public NumberPlusMockRule(MockField mockField) {
        super(mockField);
    }

    /**
     * 是否适用本规则。需要优先按照 baseValue 的类型来匹配，再按 ruleStr 来匹配
     * @param mockField 需要 mock 的属性
     * @return
     */
    public static boolean match(MockField mockField) {
        boolean typeMatch = int.class == mockField.getBaseValueType() || Number.class.isAssignableFrom(mockField.getBaseValueType());
        return typeMatch && mockField.getRuleStr() != null && pattern.matcher(mockField.getRuleStr()).matches();
    }


    @Override
    public Object doGenerate() {
        Integer baseValue = (Integer) this.mockField.getBaseValue();
        // 步长
        Integer step = Integer.valueOf(this.mockField.getRuleStr());
        Integer lastValue = (Integer) MockDataContext.getHistory().get(mockField);
        int nextValue = lastValue == null ? baseValue : lastValue + step;
        MockDataContext.getHistory().put(this.mockField, nextValue);
        return nextValue;
    }

}
