package com.kvn.mockj.rule;

import com.alibaba.fastjson.JSONArray;
import com.kvn.mockj.MockField;

/**
 * 为集合（数组）类型的模板生成 mock 数据。
 * <pre>
 * 形如：'object|n1-n2': array
 * "foo|1-3":[
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
 * 特别的，如果 rule 的值为1的话（如："object|1: array"），就会返回 array 其中的一个元素，且类型与元素类型相同。否则，返回的都是数组类型
 * Created by wangzhiyuan on 2018/9/14
 */
public class CollectionRangeMockRule extends AbstactMockRule {

    public CollectionRangeMockRule(MockField mockField) {
        super(mockField);
    }

    /**
     * 是否适用本规则
     * @param mockField 需要 mock 的属性
     * @return
     */
    public static boolean match(MockField mockField) {
        return JSONArray.class == mockField.getBaseValueType() && mockField.getRuleStr() != null && isRangePattern(mockField.getRuleStr());
    }


    @Override
    public Object doGenerate() {
        JSONArray jsonArray = (JSONArray) mockField.getBaseValue();

        String[] strs = this.mockField.getRuleStr().split("-");
        if (strs.length == 1) {
            // 从 baseValue 中随机挑选 n 个值
            Integer count = Math.min(Integer.valueOf(strs[0]), jsonArray.size());
            return generate(count, jsonArray);
        }
        int min = Math.min(Integer.valueOf(strs[0]), jsonArray.size());
        int max = Math.min(Integer.valueOf(strs[1]), jsonArray.size());
        // 生成区间内的数字
        return generate(RANDOM.nextInt(max - min + 1) + min, jsonArray);
    }

    /**
     * 从 jsonArray 中随机挑选 count 个值
     * @param count
     * @param jsonArray
     * @return
     */
    private Object generate(Integer count, JSONArray jsonArray) {
        JSONArray rtn = new JSONArray();
        for (int i = 0; i < count; i++) {
            int index = RANDOM.nextInt(jsonArray.size());
            rtn.add(jsonArray.get(index));
        }
        return "1".equals(this.mockField.getRuleStr()) ? rtn.get(0) : rtn;
    }

}
