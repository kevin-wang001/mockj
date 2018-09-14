package com.kvn.mockj.rule;

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
        RULE_LIST.add(RangeMockRule.class);
    }

    public static MockRule find(String ruleStr) {
        for (Class<? extends AbstactMockRule> ruleClass : RULE_LIST) {
            try {
                Method matchMethod = ruleClass.getMethod("match", String.class);
                boolean matches = (boolean) matchMethod.invoke(null, ruleStr);
                if (matches) {
                    return (MockRule) ruleClass.getConstructor(String.class).newInstance(ruleStr);
                }
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(ruleClass.getSimpleName() + "中没有match(String)方法", e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return new DefaultMockRule(ruleStr);
    }

}
