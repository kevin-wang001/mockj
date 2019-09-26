package com.kvn.mockj.v2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kvn.mockj.MockField;

import java.util.Map;

/**
 * Created by wangzhiyuan on 2018/9/12
 */
public class Mock {
    /**
     * @param template mock模板
     * @return 返回 mock 的 json 字符串
     */
    public static JSONObject mock(String template) {
        JSONObject json = JSON.parseObject(template);
        JSONObject result = new JSONObject();
        parseAndGenerate(json, template, result);
        return result;
    }

    /**
     * <pre>
     * v 可能出现的类型： string、 number、 boolean、 array、 JsonObject
     * 1. 如果是非 JsonObject 类型，就解析出一个 MockField
     * 2. 如果是 JsonObject 类型，就重新进行解析
     * </pre>
     */
    private static void parseAndGenerate(JSONObject json, String template, JSONObject result) {

        json.forEach((k, v) -> {
            if (v instanceof JSONObject) {
                JSONObject innerResult = new JSONObject();
                parseAndGenerate((JSONObject) v, template, innerResult);
                MockField mockField = MockField.parse(template, k, innerResult);
                result.put(mockField.getName(), mockField.generateMockData());
                return;
            }

            MockField mockField = MockField.parse(template, k, v);
            result.put(mockField.getName(), mockField.generateMockData());

        });
    }

    /**
     * @param template mock模板
     * @return 返回 mock 对象
     */
    public static <T> T mock(String template, Class<T> rtnClass) {
        JSONObject mock = mock(template);
//        return JSON.toJavaObject(mock, rtnClass);  char 类型的会报错
        return JSON.parseObject(mock.toJSONString(), rtnClass);
    }


    //================================================================================

    public static String mock2(String template){
        JSONObject jsonT = JSON.parseObject(template);
        Object rlt = Handler.gen(jsonT, null, new Context());
        return JSON.toJSONString(rlt);
    }



}
