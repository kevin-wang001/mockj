package com.kvn.mockj.rule;

import com.kvn.mockj.$Function;
import com.kvn.mockj.MockField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 特殊的 MockRule。baseValue 以 $ 开头的都认定为使用 SpecialMockRule
 * Created by wangzhiyuan on 2018/9/14
 */
public class SpecialMockRule extends AbstactMockRule {
    private static final Pattern pattern = Pattern.compile("(\\$\\w+)(\\(.*\\))?");

    public SpecialMockRule(MockField mockField) {
        super(mockField);
    }

    /**
     * 是否适用本规则
     * @param mockField 需要 mock 的属性
     * @return
     */
    public static boolean match(MockField mockField) {
        return true;
    }


    @Override
    public String doGenerate() {
        String methodName = null;
        String paramStr = null;

        Matcher matcher = pattern.matcher(mockField.getBaseValue());
        boolean found = matcher.find();
        if (!found) {
            throw new RuntimeException(mockField.getBaseValue() + "错误，匹配不到函数名");
        }
        int groupCount = matcher.groupCount();
        if (groupCount == 1) {
            methodName = matcher.group(1);
        } else if (groupCount == 2) {
            methodName = matcher.group(1);
            String group2 = matcher.group(2);
            paramStr = group2 == null ? null : group2.substring(1, group2.length() - 1);
        }
        try {
            return (String) $Function.class.getMethod(methodName, String.class).invoke(null, paramStr);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("$Function 中不存在方法" + methodName, e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
