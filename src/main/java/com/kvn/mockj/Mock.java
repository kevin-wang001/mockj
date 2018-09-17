package com.kvn.mockj;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by wangzhiyuan on 2018/9/12
 */
public class Mock {
    /**
     *
     * @param template mock模板
     * @return 返回 mock 的 json 字符串
     */
    public static String mock(String template) {
        JSONObject result = new JSONObject();
        JSONObject json = JSON.parseObject(template);
        json.forEach((k, v) -> {
            MockField mockField = MockField.parse(template, k, v.toString());
            result.put(mockField.getName(), mockField.generateMockData());
        });
        return result.toJSONString();
    }

    /**
     *
     * @param template mock模板
     * @return 返回 mock 对象
     */
    public static <T> T mock(String template, Class<T> rtnClass) {
        return JSON.parseObject(mock(template), rtnClass);
    }

    /**
     * 根据 mockClass 生成 random 类型的 mock模板。注意：只会生成 简单数据类型 和 String 类型的 mock 模板。
     * @param mockClass
     * @return
     */
    public static JSONObject randomTemplate(Class mockClass, String... exceptField) {
        JSONObject template = new JSONObject();
        Field[] declaredFields = mockClass.getDeclaredFields();
        for (Field field : declaredFields) {
            if (isExcepted(field, exceptField)) {
                continue;
            }

            String fieldName = field.getName();
            String fieldClassName = field.getType().getName();

            switch (fieldClassName) {
                case "java.lang.Boolean":
                case "boolean":
                    template.put(fieldName + "|@random", "true");
                    continue;
                case "java.lang.Character":
                case "char":
                    template.put(fieldName, "$char");
                    continue;
                case "java.lang.Byte":
                case "byte":
                    template.put(fieldName + "|0-" + (Byte.MAX_VALUE - 10) , "1");
                    continue;
                case "java.lang.Short":
                case "short":
                    template.put(fieldName + "|0-" + (Short.MAX_VALUE - 10), "1");
                    continue;
                case "java.lang.Integer":
                case "int":
                case "java.lang.Long":
                case "long":
                case "java.lang.Float":
                case "float":
                case "java.lang.Double":
                case "double":
                    template.put(fieldName + "|0-" + (Integer.MAX_VALUE - 10), "1");
                    continue;
            }

            if (String.class == field.getType()) {
                template.put(fieldName + "|@random", "xx");
                continue;
            }

            if (BigDecimal.class == field.getType()) {
                template.put(fieldName + "|@random", "1");
                continue;
            }

            if (Date.class == field.getType()) {
                template.put(fieldName, "$date");
                continue;
            }

        }

        return template;
    }

    private static boolean isExcepted(Field field, String[] exceptField) {
        if (exceptField == null) {
            return false;
        }

        return Arrays.asList(exceptField).contains(field.getName());
    }

}
