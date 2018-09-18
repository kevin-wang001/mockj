package com.kvn.mockj;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by wangzhiyuan on 2018/9/18
 */
public class TemplateHelper {
    private JSONObject template;

    /**
     * 根据 mockClass 生成随机的 mock 模板。注意：只会生成 简单数据类型 和 String 类型的 mock 模板。
     * @param mockClass
     * @param exceptField
     * @return
     */
    public static String randomTemplate(Class mockClass, String... exceptField) {
        return random(mockClass, exceptField).template.toJSONString();
    }

    public static TemplateHelper random(Class mockClass, String... exceptField) {
        TemplateHelper helper = new TemplateHelper();
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
                case "java.lang.Integer":
                case "int":
                case "java.lang.Long":
                case "long":
                case "java.lang.Float":
                case "float":
                case "java.lang.Double":
                case "double":
                    template.put(fieldName + "|0-" + (Short.MAX_VALUE - 10), "1");
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

        helper.template = template;
        return helper;
    }

    private static boolean isExcepted(Field field, String[] exceptField) {
        if (exceptField == null) {
            return false;
        }

        return Arrays.asList(exceptField).contains(field.getName());
    }

    /**
     * 往 template 中添加模板
     * @param filedName 字段名称
     * @param baseValue 对应 MockField 中的 baseValue。值类型为：String、JSONObject、JSONArray等
     * @return
     */
    public TemplateHelper put(String filedName, Object baseValue){
        this.template.put(filedName, baseValue);
        return this;
    }

    public String toTemplate(){
        return this.template.toJSONString();
    }
}
