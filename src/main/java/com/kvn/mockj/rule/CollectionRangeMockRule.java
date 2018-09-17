package com.kvn.mockj.rule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.kvn.mockj.MockField;

import java.util.Collection;
import java.util.Random;

/**
 * 为集合（数组）类型的模板生成 mock 数据
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
        return Collection.class == mockField.getBaseValueType() && isRangePattern(mockField.getRuleStr());
    }


    @Override
    public Object doGenerate() {
        JSONArray jsonArray = JSON.parseArray(mockField.getBaseValue());

        String[] strs = this.mockField.getRuleStr().split("-");
        if (strs.length == 1) {
            // 从 baseValue 中随机挑选 n 个值
            Integer count = Math.min(Integer.valueOf(strs[0]), jsonArray.size());
            return generate(count, jsonArray);
        }
        int min = Math.min(Integer.valueOf(strs[0]), jsonArray.size());
        int max = Math.min(Integer.valueOf(strs[1]), jsonArray.size());
        // 生成区间内的数字
        return generate(new Random().nextInt(max - min) + min, jsonArray);
    }

    /**
     * 从 jsonArray 中随机挑选 count 个值
     * @param count
     * @param jsonArray
     * @return
     */
    private JSONArray generate(Integer count, JSONArray jsonArray) {
        JSONArray rtn = new JSONArray();
        for (int i = 0; i < count; i++) {
            int index = RANDOM.nextInt(jsonArray.size());
            rtn.add(jsonArray.get(index));
        }
        return rtn;
    }

}
