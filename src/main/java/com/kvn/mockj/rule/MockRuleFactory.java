package com.kvn.mockj.rule;

import com.kvn.mockj.MockField;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzhiyuan on 2018/9/13
 */
public class MockRuleFactory {

    private static final List<Class<? extends AbstactMockRule>> RULE_LIST = new ArrayList<>();

    /**
     * 注册规则
     */
    static {
        RULE_LIST.add(DecimalNumberMockRule.class);
        RULE_LIST.add(PlusNumberMockRule.class);
        RULE_LIST.add(IntegerNumberMockRule.class);
    }

    public static MockRule find(MockField mockField) {
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
