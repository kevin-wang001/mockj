package com.kvn.mockj;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.junit.Test;

/**
 * Created by wangzhiyuan on 2018/9/17
 */
public class MockTest {

    /**
     * 测试 randomTemplate
     */
    @org.junit.Test
    public void mockByRandom() {
        TemplateHelper helper = TemplateHelper.random(Foo.class);
        helper.put("courses|2", new JSONArray(Lists.newArrayList("语文", "数学", "英语")));
        helper.put("courses2|1-2", new JSONArray(Lists.newArrayList("语文", "数学", "英语")));
        helper.put("map|2-4", JSONObject.parseObject("{\"110000\": \"北京市\",\"120000\": \"天津市\",\"130000\": \"河北省\",\"140000\": \"山西省\"}"));
        // ==> OneInArrayMockRule
        helper.put("foo|@one", JSONArray.parseArray("[{\"name\":\"zhangsan\",\"age\":12},{\"name\":\"lisi\",\"age\":10},{\"name\":\"wangwu\",\"age\":8}]"));
        // ==> DefaultMockRule
        helper.put("foo1", JSONArray.parseObject("{\"name\":\"wangwu\",\"age\":8}"));
        System.out.println(helper);

        for (int i = 0; i < 5; i++) {
            Foo mock = Mock.mock(helper.toTemplate(), Foo.class);
            System.out.println(JSON.toJSONString(mock));
        }

    }

    @Test
    public void testTemplatePut() {
        TemplateHelper helper = TemplateHelper.random(Foo.class);
        helper.put("name|1-3", "www");
        // age的值为 [1,3,5]中的任意一个
        helper.put("age|@one", "[1, 3, 5]");
        System.out.println(helper.toTemplate());
        for (int i = 0; i < 5; i++) {
            System.out.println(Mock.mock(helper.toTemplate()));
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Foo foo = Mock.mock(Foo.useTemplate(), Foo.class);
            System.out.println(JSON.toJSONString(foo));
        }
    }
}