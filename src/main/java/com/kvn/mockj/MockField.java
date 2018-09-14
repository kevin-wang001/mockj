package com.kvn.mockj;

import com.kvn.mockj.rule.MockRule;
import com.kvn.mockj.rule.MockRuleFactory;
import lombok.Data;

/**
 * Created by wangzhiyuan on 2018/9/13
 */
@Data
public class MockField {
    /**
     * 字段名
     */
    private String name;
    /**
     * 原字段名，形如：字段名|规则串
     */
    private String originName;
    /**
     * 规则串
     */
    private String ruleStr;
    /**
     * mock规则
     */
    private MockRule mockRule;
    /**
     * 原始的基础值
     */
    private String baseValue;

    public static MockField parse(String nameRule, String value){
        MockField mockField = new MockField();
        mockField.originName = nameRule;
        String[] arr = nameRule.split("\\|");
        if (arr.length > 2) {
            throw new IllegalArgumentException("mock模板配置错误：" + nameRule);
        }

        mockField.name = arr[0];
        if (arr.length == 2) {
            mockField.ruleStr = arr[1];
            mockField.mockRule = MockRuleFactory.find(mockField.ruleStr);
        }
        mockField.baseValue = value;
        return mockField;
    }

    public String generateMockData(){
        return this.mockRule.generateMockData(baseValue);
    }

}
