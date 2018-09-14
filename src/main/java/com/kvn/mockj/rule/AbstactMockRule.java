package com.kvn.mockj.rule;

/**
 * Created by wangzhiyuan on 2018/9/13
 */
public abstract class AbstactMockRule {
    protected String ruleStr;

    public AbstactMockRule(String ruleStr) {
        this.ruleStr = ruleStr;
    }

}
