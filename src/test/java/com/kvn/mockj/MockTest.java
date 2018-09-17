package com.kvn.mockj;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Created by wangzhiyuan on 2018/9/17
 */
public class MockTest {

    @org.junit.Test
    public void mockByRandom() {
        JSONObject template = Mock.randomTemplate(Foo.class);
        template.put("courses|2", new JSONArray(Lists.newArrayList("语文","数学","英语")));
        template.put("courses2|1-2", new JSONArray(Lists.newArrayList("语文","数学","英语")));
        template.put("map|2-4", JSONObject.parseObject("{\"110000\": \"北京市\",\"120000\": \"天津市\",\"130000\": \"河北省\",\"140000\": \"山西省\"}"));
        System.out.println(template);

        for (int i = 0; i < 5; i++) {
            Foo mock = Mock.mock(template.toJSONString(), Foo.class);
            System.out.println(JSON.toJSONString(mock));
        }

    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Foo foo = Mock.mock(Foo.useTemplate(), Foo.class);
            System.out.println(JSON.toJSONString(foo));
        }
    }
}