package com.kvn.mockj.rule;

import com.kvn.mockj.MockField;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by wangzhiyuan on 2018/9/13
 */
public abstract class AbstactMockRule implements MockRule {
    protected static final Random RANDOM = new Random();
    protected static final RandomStringUtils STRING_RANDOM = new RandomStringUtils();

    protected MockField mockField;

    public AbstactMockRule(MockField mockField) {
        this.mockField = mockField;
    }

    @Override
    public final Object generateMockData() {
        return doGenerate();
    }




    /**
     * 生成 mock 数据
     * @return
     */
    protected abstract Object doGenerate();

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
