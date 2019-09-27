package com.kvn.mockj.handler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhiyuan on 2019/9/26
 */
public class TypeHandlerFactory {
    public static Map<Class, TypeHandler> handlerMap = new HashMap<>();

    static {
        TypeHandler[] typeHandlers = new TypeHandler[]{
                new ObjectHandler(),
                new ArrayHandler(),
                new StringHandler(),
                new NumberHandler(),
                new BooleanHandler()
        };

        for (TypeHandler typeHandler : typeHandlers) {
            for (Class clazz : typeHandler.support()) {
                handlerMap.put(clazz, typeHandler);
            }
        }

    }


    public static TypeHandler getTypeHandler(Class clazz) {
        return handlerMap.get(clazz);
    }

}
