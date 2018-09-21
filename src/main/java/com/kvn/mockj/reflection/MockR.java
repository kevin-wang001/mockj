package com.kvn.mockj.reflection;

import org.apache.commons.lang3.RandomStringUtils;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * 通过反射，随机生成一个 mock 对象。<br/>
 * 支持所有的数据类型。特别注意的是：集合类型（List、Map、Set）的属性必需带泛型参数，否则不处理
 */
public class MockR {
    /************************************** 随机实例化 **************************************/
    private static final RandomStringUtils STRING_RANDOM = new RandomStringUtils();
    private static final Random RANDOM = new Random();

    public static <T> T random(TypeReference<T> typeReference) {
        Class targetClass = (Class) typeReference.getType();
        // 1. 原始类型是 Collection 类型
        if (Collection.class.isAssignableFrom(targetClass)) {
            if (typeReference.getArgTypes() == null) {
                throw new IllegalArgumentException("参数错误：Collection 类型必须带上泛型参数");
            }
            return (T) randomCollection((ParameterizedTypeImpl) typeReference.getType());
        }

        // 2. 数组类型
        if (targetClass.isArray()) {
            return (T) randomArray(targetClass);
        }

        // 3. Map类型
        if (Map.class.isAssignableFrom(targetClass)) {
            return (T) randomMap(typeReference.getArgTypes()[0], typeReference.getArgTypes()[1]);
        }

        // 4. 普通类型，不带泛型参数
        if (typeReference.getArgTypes() == null) {
            return (T) randomObject(targetClass, 2);
        }

        // 4. 普通类型，带泛型参数 FIXME
        return (T) randomObject(targetClass, 2);
    }

    /**
     * mock Map类型的对象，Map 中放1个元素
     * @return
     */
    private static Object randomMap(Type keyType, Type valueType) {
        Class<?> keyClass = (Class<?>) keyType;
        Class<?> valueClass = (Class<?>) valueType;

        if (!isSupportRandomMap(keyClass) || !isSupportRandomMap(valueClass)) {
            return null;
        }

        Object key = randomObject(keyClass, 2);
        Object value = randomObject(valueClass, 2);

        Map map = new HashMap();
        map.put(key, value);
        return map;
    }

    private static boolean isSupportRandomMap(Class clazz){
        if (Collection.class.isAssignableFrom(clazz)) {
            return false;
        }
        if (clazz.isArray()) {
            return false;
        }
        if (Map.class.isAssignableFrom(clazz)) {
            return false;
        }
        if (clazz.isInterface()) {
            return false;
        }
        if (Map.class.isAssignableFrom(clazz)) {
            return false;
        }
        return true;
    }


    private static Object randomArray(Class targetClass){
        Object array = Array.newInstance(targetClass.getComponentType(), 1);
        Array.set(array, 0, randomObject(targetClass.getComponentType(), 2));
        return array;
    }

    /**
     * mock Collection类型的对象，集合中放1个元素
     * @param targetType
     * @return
     */
    private static Object randomCollection(ParameterizedTypeImpl targetType){
        Type rawType = targetType.getRawType();
        Type argType = targetType.getActualTypeArguments()[0];
        Class targetClass = (Class) rawType;
        Collection instance = null;
        if (targetClass.isInterface()) {
            if (List.class == targetClass) {
                instance = new ArrayList<>();
            } else if (Set.class == targetClass) {
                instance = new HashSet<>();
            }
        } else {
            try {
                instance = (Collection) targetClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (instance == null) {
            return null;
        }

        Object obj = randomObject((Class) argType, 2);
        instance.add(obj);
        return instance;
    }

    /**
     * @param targetClass
     * @param cascadeLayers 级连产生 mock 数据的层数
     * @return
     */
    private static Object randomObject(Class targetClass, int cascadeLayers) {
        // 基础类型 或者 基础类型的包装类型
        if (isPrimitiveOrWrapClass(targetClass)) {
            return randomInstancePrimitiveClass(targetClass);
        }
        // 字符串
        if (targetClass == String.class) {
            return STRING_RANDOM.randomAlphabetic(4);
        }
        // BigDecimal
        if (targetClass == BigDecimal.class) {
            return new BigDecimal(RANDOM.nextFloat());
        }
        // 日期
        if (targetClass == Date.class) {
            return new Date();
        }
        if (targetClass == Timestamp.class) {
            return new Timestamp(System.currentTimeMillis());
        }

        // 枚举
        if (targetClass.isEnum()) {
            return targetClass.getEnumConstants()[0];
        }

        // 接口类型，不能被实例化
        if (targetClass.isInterface()) {
            return null;
        }
        Object instance = null;
        try {
            instance = targetClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // 本次 random 不支持级连产生复杂对象
        if (cascadeLayers <= 0) {
            return null;
        }

        // 其他:一般的复杂对象
        Method[] methods = targetClass.getMethods();
        for (Method m : methods) {
            /**
             * 通过setter方法来设置对象属性的值
             * */
            if (!m.getName().startsWith("set") || m.getParameterTypes().length != 1) {
                continue;
            }
            Class<?> fieldClass = m.getParameterTypes()[0];

            Object fieldValue = null;
            // Collection 类型单独处理：由于 targetClass 是丢失了泛型信息的，只能通过相应的 getter 去获取泛型信息
            if (Collection.class.isAssignableFrom(fieldClass)) {
                ParameterizedTypeImpl type = findGenericType(targetClass, m);
                fieldValue = type == null ? null : randomCollection(type);
            } else if (fieldClass.isArray()) { // Array 类型单独处理
                fieldValue = randomArray(fieldClass);
            } else if (Map.class.isAssignableFrom(fieldClass)) {
                ParameterizedTypeImpl pt = findGenericType(targetClass, m);
                fieldValue = pt == null ? null : randomMap(pt.getActualTypeArguments()[0], pt.getActualTypeArguments()[1]);
            } else {
                fieldValue = randomObject(fieldClass, cascadeLayers - 1);
            }

            try {
                m.invoke(instance, fieldValue);
            } catch (Exception e) {
                // 调用 set 方法失败，继续下一个属性
                e.printStackTrace();
            }
        }
        return instance;
    }

    private static ParameterizedTypeImpl findGenericType(Class targetClass, Method setter) {
        Class<?> fieldClass = setter.getParameterTypes()[0];
        String getMethodName = fieldClass == boolean.class ? "is" + setter.getName().substring(3) :"g" + setter.getName().substring(1);
        Method getMethod = null;
        try {
            getMethod = targetClass.getDeclaredMethod(getMethodName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            System.out.println("属性[" + targetClass.getSimpleName() + "#" + fieldClass.getName() + "]不存在 getter 方法");
            return null;
        }
        ParameterizedType pt = (ParameterizedType) getMethod.getGenericReturnType();
        return pt instanceof ParameterizedTypeImpl ? (ParameterizedTypeImpl) pt : null;
    }

    /**
     * 初始化 基础类型 或者 基础类型的包装类型
     *
     * @param clazz
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private static <T> T randomInstancePrimitiveClass(Class<T> clazz) {
        int numberLength = 100000; // 数字保留5位

        String className = clazz.getName();
        switch (className) {
            case "java.lang.Boolean":
            case "boolean":
                return (T) Boolean.valueOf(RANDOM.nextBoolean());
            case "java.lang.Character":
            case "char":
                return (T) Character.valueOf((char) (RANDOM.nextInt() % 128));
            case "java.lang.Byte":
            case "byte":
                return (T) Byte.valueOf((byte) (RANDOM.nextInt() % 128));
            case "java.lang.Short":
            case "short":
                return (T) Short.valueOf((short) (RANDOM.nextInt() % 32767));
            case "java.lang.Integer":
            case "int":
                return (T) Integer.valueOf(RANDOM.nextInt() % numberLength);
            case "java.lang.Long":
            case "long":
                return (T) Long.valueOf(RANDOM.nextInt() % numberLength);
            case "java.lang.Float":
            case "float":
                return (T) Float.valueOf(RANDOM.nextInt() % numberLength);
            case "java.lang.Double":
            case "double":
                return (T) Double.valueOf(RANDOM.nextInt() % numberLength);
            default:
                throw new RuntimeException(className + "不支持，bug");
        }

    }

    /**
     * 判断clz是否是基本类型 或者 基本类型的包装类型
     *
     * @param clz
     * @return
     */
    private static boolean isPrimitiveOrWrapClass(Class clz) {
        if (clz.isPrimitive()) {
            return true;
        }

        return isWrapClass(clz);
    }

    private static boolean isWrapClass(Class clz) {
        try {
            return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

}
