package com.kvn.mockj.rule;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by wangzhiyuan on 2018/9/14
 */
public class DefaultMockRule extends AbstactMockRule implements MockRule {

    public DefaultMockRule(String ruleStr) {
        super(ruleStr);
    }

    /**
     * 是否适用本规则
     * @param ruleStr 规则串
     * @return
     */
    public static boolean match(String ruleStr) {
        return true;
    }


    @Override
    public String generateMockData(String baseValue) {
        return baseValue;
    }

}
