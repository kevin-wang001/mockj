package com.kvn.mockj.rule;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * 按区间范围规则生成 mock 数据
 * Created by wangzhiyuan on 2018/9/14
 */
public class RangeMockRule extends AbstactMockRule implements MockRule {
    private static final String regExp = "\\d{1,}(-\\d{1,})?";
    private static final Pattern pattern = Pattern.compile(regExp);

    public RangeMockRule(String ruleStr) {
        super(ruleStr);
    }

    /**
     * 是否适用本规则
     * @param ruleStr 规则串
     * @return
     */
    public static boolean match(String ruleStr) {
        boolean matches = pattern.matcher(ruleStr).matches();
        String[] strs = ruleStr.split("-");
        if (strs.length == 2) {
            int min = Integer.valueOf(strs[0]);
            int max = Integer.valueOf(strs[1]);
            if (min > max) {
                throw new IllegalArgumentException("规则[" + ruleStr + "]错误，区间设置错误");
            }
        }
        return matches;
    }


    @Override
    public String generateMockData(String baseValue) {
        String[] strs = this.ruleStr.split("-");
        if (strs.length == 1) {
            return generate(Integer.valueOf(strs[0]), baseValue);
        }
        int min = Integer.valueOf(strs[0]);
        int max = Integer.valueOf(strs[1]);
        return generate(new Random().nextInt(max - min) + min, baseValue);
    }

    private String generate(Integer count, String baseValue) {
        try {
            // baseValue 为数字，表示 mock 需要生成一串数字
            Integer.valueOf(baseValue);
            return count.toString();
        } catch (NumberFormatException e) {
            // baseValue 为字符
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < count; i++) {
                sb.append(baseValue);
            }
            return sb.toString();
        }
    }
}
