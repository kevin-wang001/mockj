package com.kvn.mockj;

import com.alibaba.fastjson.JSON;
import com.kvn.mockj.rule.MockRule;
import com.kvn.mockj.rule.MockRuleFactory;
import lombok.Data;

import java.util.Collection;

/**
 * Created by wangzhiyuan on 2018/9/13
 */
@Data
public class MockField {
    /**
     * mockField 所属的 template
     */
    private String mockTemplate;
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
    private Object baseValue;
    /**
     * 原始的基础值的类型
     */
    private Class baseValueType;

    public static MockField parse(String mockTemplate, String nameRule, Object value) {
        MockField mockField = new MockField();
        mockField.mockTemplate = mockTemplate;
        mockField.originName = nameRule;
        String[] arr = nameRule.split("\\|");
        if (arr.length > 2) {
            throw new IllegalArgumentException("mock模板配置错误：" + nameRule);
        }

        mockField.name = arr[0];
        if (arr.length == 2) {
            mockField.ruleStr = arr[1];
        }
        mockField.baseValue = value;
        // 可能出现的类型： string、 number、 boolean、 array、 JsonObject
        mockField.baseValueType = value.getClass();

        mockField.mockRule = MockRuleFactory.find(mockField);
        return mockField;
    }

    /**
     * 解析 baseValue 对应的 Class。解析的顺序与 MockRule 的应用顺序有关。<br/>
     * String.class 放到最后，属于默认类型。
     *
     * @param baseValue
     * @return
     */
    private static Class parseMockType(String baseValue) {
        // 1. int
        try {
            Integer.valueOf(baseValue);
            return int.class;
        } catch (NumberFormatException e) {
        }

        // 2. boolean
        if ("true".equals(baseValue) || "false".equals(baseValue)) {
            return boolean.class;
        }

        // 3. 集合类型
        try {
            JSON.parseArray(baseValue);
            return Collection.class;
        } catch (Exception e) {
        }

        // 4. Object
        try {
            JSON.parseObject(baseValue);
            return Object.class;
        } catch (Exception e) {
        }

        // 5. Sting
        return String.class;
    }

    public Object generateMockData() {
        return this.mockRule.generateMockData();
    }

    /**
     * 重写 hashCode 和 equals 的目的是在 MockDataContext 中需要使用 MockField 做为 Map 的 key
     */
    @Override
    public int hashCode() {
        return 31 * mockTemplate.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (!(other instanceof MockField)) {
            return false;
        }

        MockField mockField = (MockField) other;
        if (mockField.mockTemplate != null && this.mockTemplate != null && mockField.mockTemplate.equals(this.mockTemplate)
                && mockField.originName != null && this.originName != null && mockField.originName.equals(this.originName)
                && mockField.baseValue != null && this.baseValue != null && mockField.baseValue.equals(this.baseValue)) {
            return true;
        }

        return false;
    }
}
