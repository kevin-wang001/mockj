package com.kvn.mockj.rule;

import com.kvn.mockj.MockField;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wangzhiyuan on 2018/9/13
 */
public class MockRuleFactory {

    private static final Set<Class<? extends AbstactMockRule>> RULE_LIST = new HashSet<>();

    /**
     * 注册规则。DefaultMockRule、SpecialMockRule 不注册到 RULE_LIST 中
     */
    static {
        RULE_LIST.add(BooleanMockRule.class);
        RULE_LIST.add(CollectionPlusMockRule.class);
        RULE_LIST.add(CollectionRangeMockRule.class);
        RULE_LIST.add(NumberDecimalMockRule.class);
        RULE_LIST.add(NumberRangeMockRule.class);
        RULE_LIST.add(NumberPlusMockRule.class);
        RULE_LIST.add(ObjectRangeMockRule.class);
        RULE_LIST.add(ObjectInArrayMockRule.class);
        RULE_LIST.add(RandomMockRule.class);
        RULE_LIST.add(StringMockRule.class);
    }

    public static MockRule find(MockField mockField) {
        // baseValue 以 $ 开头，则使用 SpecialMockRule
        if (mockField.getBaseValue().startsWith("$")) {
            return new SpecialMockRule(mockField);
        }

        for (Class<? extends AbstactMockRule> ruleClass : RULE_LIST) {
            try {
                Method matchMethod = ruleClass.getMethod("match", MockField.class);
                boolean matches = (boolean) matchMethod.invoke(null, mockField);
                if (matches) {
                    return ruleClass.getConstructor(MockField.class).newInstance(mockField);
                }
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(ruleClass.getSimpleName() + "中没有match(String)方法", e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return new DefaultMockRule(mockField);
    }

}
