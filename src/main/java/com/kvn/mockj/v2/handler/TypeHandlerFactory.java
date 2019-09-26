package com.kvn.mockj.v2.handler;

import java.util.Map;

/**
 * Created by wangzhiyuan on 2019/9/26
 */
public class TypeHandlerFactory {
    public static Map<Class, TypeHandler> handlerMap;


    public static TypeHandler getTypeHandler(Class clazz){
        return handlerMap.get(clazz);
    }

}
