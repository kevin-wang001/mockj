package com.kvn.mockj.rule;

import com.kvn.mockj.MockField;

import java.math.BigDecimal;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * 对含有小数的数字生成 mock。形如："number|1-100.1-10": 1<br/>
 * 整数部分1-100表示的是整数位的范围是1-100的数字，小数部分1-10，表示的是小数位的位数是1-10位小数
 * Created by wangzhiyuan on 2018/9/14
 */
public class DecimalNumberMockRule extends AbstactMockRule {

    private static final Pattern pattern = Pattern.compile("\\d{1,}(-\\d{1,})\\.\\d{1,}(-\\d{1,})");

    public DecimalNumberMockRule(MockField mockField) {
        super(mockField);
    }

    /**
     * 是否适用本规则。需要优先按照 baseValue 的类型来匹配，再按 ruleStr 来匹配
     * @param mockField 需要 mock 的属性
     * @return
     */
    public static boolean match(MockField mockField) {
        boolean typeMatch = int.class == mockField.getBaseValueType() || Number.class.isAssignableFrom(mockField.getBaseValueType());
        return typeMatch && pattern.matcher(mockField.getRuleStr()).matches();
    }


    @Override
    public String doGenerate() {
        String[] splits = this.mockField.getRuleStr().split("\\.");
        String[] integerRange = splits[0].split("-");
        // 整数位
        int integerPlace = generateNumByRange(integerRange);
        // 小数位
        int decimalPlace = 0;
        if (splits.length == 2) {
            String[] decimalFigures = splits[1].split("-");
            decimalPlace = generateNumByFigure(decimalFigures);
        }
        return new BigDecimal(integerPlace + "." + decimalPlace).toString();
    }

    /**
     * 按位数生成随机数
     *
     * @return
     */
    private int generateNumByFigure(String[] figures) {
        int base = (int) Math.pow(10, Integer.valueOf(figures[0]));
        int max = 0;
        if (figures.length == 1) {
            max = (int) Math.pow(10, Integer.valueOf(figures[0]));
        } else {
            max = (int) Math.pow(10, Integer.valueOf(figures[1]));
        }

        return (int) (Math.random() * (max - base)) + base;
    }

    /**
     * 按范围生成固定范围内的数字
     *
     * @param range
     * @return
     */
    private int generateNumByRange(String[] range) {
        if (range.length == 1) {
            return Integer.valueOf(range[0]);
        }

        int min = Integer.valueOf(range[0]);
        int max = Integer.valueOf(range[1]);
        return new Random().nextInt(max - min) + min;
    }

}
