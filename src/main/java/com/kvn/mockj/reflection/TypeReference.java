package com.kvn.mockj.reflection;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * <pre>
 * 类型引用。
 * 例如：
 *  new TypeReference<List<String>>() {} 对应的 type 为 List<String>，rawType 为 List，argTypes 为 String
 * </pre>
 *
 * Created by wangzhiyuan on 2018/9/20
 */
public class TypeReference<T> {
    // T 带泛型的类型
    protected final Type type;
    // T 的原始类型
    protected Type rawType;
    // T 的泛型类型
    protected Type[] argTypes;

    protected TypeReference() {
        // 只有通过取 GenericSuperclass 才能拿到泛型类型，所以将构造函数的访问级别设置成 protected
        Type superClass = getClass().getGenericSuperclass();
        type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        if (type instanceof ParameterizedTypeImpl) {
            rawType = ((ParameterizedTypeImpl) type).getRawType();
            argTypes = ((ParameterizedTypeImpl) type).getActualTypeArguments();
        }
    }

    public Type getType() {
        return type;
    }

    public Type getRawType() {
        return rawType;
    }

    public Type[] getArgTypes() {
        return argTypes;
    }

    public final static Type LIST_STRING = new TypeReference<List<String>>() {}.getType();

    public static void main(String[] args) {
        TypeReference<List> typeReference = new TypeReference<List>() {
        };
        Type type = typeReference.getType();
        System.out.println(typeReference);
    }
}