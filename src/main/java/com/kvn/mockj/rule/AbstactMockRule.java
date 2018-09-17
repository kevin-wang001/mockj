package com.kvn.mockj.rule;

import com.kvn.mockj.MockField;

import java.util.regex.Pattern;

/**
 * Created by wangzhiyuan on 2018/9/13
 */
public abstract class AbstactMockRule implements MockRule {
    protected MockField mockField;

    public AbstactMockRule(MockField mockField) {
        this.mockField = mockField;
    }

    private static final Pattern pattern = Pattern.compile("\\d{1,}(-\\d{1,})?");

    /**
     * 判断 content 是不是一个区间。形如： 1-10、2等
     * @param content
     * @return
     */
    public static boolean isRangePattern(String content) {
        boolean matches = pattern.matcher(content).matches();
        String[] strs = content.split("-");
        if (strs.length == 2) {
            int min = Integer.valueOf(strs[0]);
            int max = Integer.valueOf(strs[1]);
            if (min > max) {
                throw new IllegalArgumentException("区间[" + content + "]设置错误");
            }
        }
        return matches;
    }
}
