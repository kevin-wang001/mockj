package com.kvn.mockj.rule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kvn.mockj.MockField;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

/**
 * 对 Object 类型的数据做随机mock。
 * <pre>
 * 形如：'name|count': object
 * {
 *   "object|2": {
 *     "310000": "上海市",
 *     "320000": "江苏省",
 *     "330000": "浙江省",
 *     "340000": "安徽省"
 *   }
 * }
 *
 * 'name|min-max': object
 * {
 *   "object|2-4": {
 *     "110000": "北京市",
 *     "120000": "天津市",
 *     "130000": "河北省",
 *     "140000": "山西省"
 *   }
 * }
 * </pre>
 * Created by wangzhiyuan on 2018/9/14
 */
public class ObjectMockRule extends AbstactMockRule {

    public ObjectMockRule(MockField mockField) {
        super(mockField);
    }

    /**
     * 是否适用本规则。只要类型是 boolean，就按 BooleanMockRule 来处理
     * @param mockField 需要 mock 的属性
     * @return
     */
    public static boolean match(MockField mockField) {
        boolean typeMatch = Object.class == mockField.getBaseValueType();
        return typeMatch && AbstactMockRule.isRangePattern(mockField.getRuleStr());
    }


    @Override
    public String doGenerate() {
        String baseValue = this.mockField.getBaseValue();
        String[] strs = this.mockField.getRuleStr().split("-");
        if (strs.length == 1) {
            return generate(Integer.valueOf(strs[0]), JSON.parseObject(baseValue));
        }
        int min = Integer.valueOf(strs[0]);
        int max = Integer.valueOf(strs[1]);
        return generate(new Random().nextInt(max - min) + min, JSON.parseObject(baseValue));
    }

    /**
     * 从 base 中随机挑选出 count 个值返回
     * @param count
     * @param base
     * @return
     */
    private String generate(Integer count, JSONObject base) {
        JSONObject rlt = new JSONObject();
        Random random = new Random();
        ArrayList<String> keyList = new ArrayList<>(base.keySet());
        for (int i = 0; i < count; i++) {
            int index = random.nextInt(count > base.size() ? base.size() : count);
            String key = keyList.get(index);
            rlt.put(key, base.get(key));
        }
        return rlt.toJSONString();
    }

}
