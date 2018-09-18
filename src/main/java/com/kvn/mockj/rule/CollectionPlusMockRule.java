package com.kvn.mockj.rule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.kvn.mockj.MockDataContext;
import com.kvn.mockj.MockField;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * 为集合（数组）类型的模板生成 mock 数据。按顺序往后取值，数值 n 为步长
 * <pre>
 * 形如："array|+1": [
 *     "AMD",
 *     "CMD",
 *     "UMD"
 *   ]
 * </pre>
 * Created by wangzhiyuan on 2018/9/14
 */
public class CollectionPlusMockRule extends AbstactMockRule {
    private static final Pattern pattern = Pattern.compile("\\+\\d{1,}");

    public CollectionPlusMockRule(MockField mockField) {
        super(mockField);
    }

    /**
     * 是否适用本规则
     * @param mockField 需要 mock 的属性
     * @return
     */
    public static boolean match(MockField mockField) {
        return Collection.class == mockField.getBaseValueType() && mockField.getRuleStr() != null && pattern.matcher(mockField.getRuleStr()).matches();
    }

    @Override
    public Object doGenerate() {
        JSONArray jsonArray = JSON.parseArray(mockField.getBaseValue());
        // 步长
        Integer step = Integer.valueOf(mockField.getRuleStr());
        int nextInCache = MockDataContext.getHistory().get(mockField) == null ? step : (int) MockDataContext.getHistory().get(mockField);
        int next = nextInCache + step;

        // next 已经到最大值，next 从头开始
        if (next > jsonArray.size()) {
            next = step;
        }

        MockDataContext.getHistory().put(mockField, next);
        JSONArray rtn = new JSONArray();
        rtn.add(jsonArray.get(nextInCache));
        return rtn;
    }

}
