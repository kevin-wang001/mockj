package com.kvn.mockj.rule;

import com.kvn.mockj.MockField;

import java.util.Random;

/**
 * 按区间范围规则生成 mock 数据。形如 "string|1-10":"★"
 * Created by wangzhiyuan on 2018/9/14
 */
public class StringMockRule extends AbstactMockRule {

    public StringMockRule(MockField mockField) {
        super(mockField);
    }

    /**
     * 是否适用本规则。需要优先按照 baseValue 的类型来匹配，再按 ruleStr 来匹配
     * @param mockField 需要 mock 的属性
     * @return
     */
    public static boolean match(MockField mockField) {
        boolean typeMatch = String.class == mockField.getBaseValueType();
        return typeMatch && mockField.getRuleStr() != null && isRangePattern(mockField.getRuleStr());
    }




    @Override
    public Object doGenerate() {
        String baseValue = (String) this.mockField.getBaseValue();
        String[] strs = this.mockField.getRuleStr().split("-");
        if (strs.length == 1) {
            return generate(Integer.valueOf(strs[0]), baseValue);
        }
        int min = Integer.valueOf(strs[0]);
        int max = Integer.valueOf(strs[1]);
        return generate(new Random().nextInt(max - min + 1) + min, baseValue);
    }

    /**
     * 生成固定数量的字符。count 个 baseValue
     * @param count
     * @param baseValue
     * @return
     */
    private String generate(Integer count, String baseValue) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < count; i++) {
            sb.append(baseValue);
        }
        return sb.toString();
    }

}
