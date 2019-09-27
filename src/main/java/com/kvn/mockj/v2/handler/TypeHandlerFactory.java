package com.kvn.mockj.v2.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhiyuan on 2019/9/26
 */
public class TypeHandlerFactory {
    public static Map<Class, TypeHandler> handlerMap = new HashMap<>();
    static {
        handlerMap.put(JSONObject.class, new ObjectHandler());
        handlerMap.put(JSONArray.class, new ArrayHandler());
        handlerMap.put(String.class, new StringHandler());
        handlerMap.put(Integer.class, new NumberHandler());
        handlerMap.put(Boolean.class, new BooleanHandler());

    }


    public static TypeHandler getTypeHandler(Class clazz){
        return handlerMap.get(clazz);
    }

}
