package com.kvn.mockj.rule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.kvn.mockj.MockField;

import java.util.Collection;

/**
 * 对 baseValue为 Collection 类型的数据做 mock，mock 的值从 array 中挑选一个。
 * <pre>
 * 形如：'object|@one': array
 * "foo|@one":[
 *         {
 *             "name":"zhangsan",
 *             "age":12
 *         },
 *         {
 *             "name":"lisi",
 *             "age":10
 *         },
 *         {
 *             "name":"wangwu",
 *             "age":8
 *         }
 *     ]
 * </pre>
 * Created by wangzhiyuan on 2018/9/14
 */
public class OneInArrayMockRule extends AbstactMockRule {

    public OneInArrayMockRule(MockField mockField) {
        super(mockField);
    }

    /**
     * 是否适用本规则。
     * @param mockField 需要 mock 的属性
     * @return
     */
    public static boolean match(MockField mockField) {
        boolean typeMatch = Collection.class == mockField.getBaseValueType();
        return typeMatch && mockField.getRuleStr() != null && "@one".equals(mockField.getRuleStr().toLowerCase());
    }


    @Override
    public Object doGenerate() {
        JSONArray jsonArray = JSON.parseArray(this.mockField.getBaseValue());
        int index = RANDOM.nextInt(jsonArray.size());
        return jsonArray.get(index);
    }

}
