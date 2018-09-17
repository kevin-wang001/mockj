package com.kvn.mockj.rule;

import com.kvn.mockj.MockField;

/**
 * 特殊的 MockRule，为 ruleStr 为 @random 的规则生成数据
 * Created by wangzhiyuan on 2018/9/14
 */
public class RandomMockRule extends AbstactMockRule {

    public RandomMockRule(MockField mockField) {
        super(mockField);
    }

    /**
     * 是否适用本规则
     * @param mockField 需要 mock 的属性
     * @return
     */
    public static boolean match(MockField mockField) {
        return "@random".equals(mockField.getRuleStr()) && isSupportRandom(mockField.getBaseValueType());
    }

    /**
     * 只支持简单类型 和 String。不支持 Object、Collection等
     * @return
     */
    private static boolean isSupportRandom(Class type) {
        if (type.isPrimitive()) {
            return true;
        }

        try {
            if (((Class) type.getField("TYPE").get(null)).isPrimitive()) {
                return true;
            }
        } catch (Exception e) {
        }

        if (String.class == type) {
            return true;
        }
        return false;
    }


    @Override
    public Object doGenerate() {
        int numberLength = 100000; // 数字保留5位

        if (String.class == mockField.getBaseValueType()) {
            return STRING_RANDOM.randomAlphabetic(4);
        }

        switch (mockField.getBaseValueType().getName()) {
            case "java.lang.Boolean":
            case "boolean":
                return Boolean.valueOf(RANDOM.nextBoolean()).toString();
            case "java.lang.Character":
            case "char":
                return Character.valueOf((char) (RANDOM.nextInt() % 128)).toString();
            case "java.lang.Byte":
            case "byte":
                return Byte.valueOf((byte) Math.abs((RANDOM.nextInt() % 128))).toString();
            case "java.lang.Short":
            case "short":
                return Short.valueOf((short) Math.abs((RANDOM.nextInt() % 32767))).toString();
            case "java.lang.Integer":
            case "int":
                return Integer.valueOf(Math.abs(RANDOM.nextInt() % numberLength)).toString();
            case "java.lang.Long":
            case "long":
                return Long.valueOf(Math.abs(RANDOM.nextLong() % numberLength)).toString();
            case "java.lang.Float":
            case "float":
                return Float.valueOf(Math.abs(RANDOM.nextFloat())).toString();
            case "java.lang.Double":
            case "double":
                return Double.valueOf(Math.abs(RANDOM.nextDouble())).toString();
        }

        throw new RuntimeException(mockField.toString() + "暂时不支持 @random");
    }

}
