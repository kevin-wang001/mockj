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
    @Test
    public void mockByRandom() {
        TemplateHelper helper = TemplateHelper.random(Foo.class);
        helper.put("courses|2", new JSONArray(Lists.newArrayList("语文", "数学", "英语")));
        helper.put("courses2|1-2", new JSONArray(Lists.newArrayList("语文", "数学", "英语")));
        helper.put("map|2-4", JSONObject.parseObject("{\"110000\": \"北京市\",\"120000\": \"天津市\",\"130000\": \"河北省\",\"140000\": \"山西省\"}"));
        // ==> CollectionRangeMockRule
        helper.put("foo|1", JSONArray.parseArray("[{\"name\":\"zhangsan\",\"age\":12},{\"name\":\"lisi\",\"age\":10},{\"name\":\"wangwu\",\"age\":8}]"));
        // ==> DefaultMockRule
        helper.put("foo1", JSONArray.parseObject("{\"name\":\"wangwu\",\"age\":8}"));
        System.out.println(helper);

        for (int i = 0; i < 5; i++) {
//            Foo mock = Mock.mock(helper.toTemplate(), Foo.class);
//            System.out.println(JSON.toJSONString(mock));
            System.out.println(Mock.mock(helper.toTemplate()));
        }

    }

    @Test
    public void testTemplatePut() {
        TemplateHelper helper = TemplateHelper.random(Foo.class);
        helper.put("name|1-3", "www");
        // age的值为 [1,3,5]中的任意一个
        helper.put("age|1", JSON.parseArray("[1, 3, 5]"));
        System.out.println(helper.toTemplate());
        System.out.println(Mock.mock(helper.toTemplate()));
    }

    @Test
    public void complex(){
        String template = "{\n" +
                "  \"array|1-10\": [\n" +
                "    {\n" +
                "      \"name|1-5\": [\n" +
                "\t\t  {\n" +
                "\t\t\t\"array|+1\": [\n" +
                "\t\t\t\t\"AAA\",\n" +
                "\t\t\t\t\"BBB\",\n" +
                "\t\t\t\t\"CCC\",\n" +
                "        \"DDD\",\n" +
                "        \"EEE\",\n" +
                "        \"FFF\",\n" +
                "        \"GGG\"\n" +
                "\t\t\t  ]\n" +
                "\t\t  }\n" +
                "\t  ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        System.out.println(Mock.mock(template));
    }

    public static void main(String[] args) {
        String template = "{\n" +
                "\"success\": true,\n" +
                "\"code\": 200,\n" +
                "\"data\": {\n" +
                "\"colors|1\": [\n" +
                "[\"#000000\", \"#FF0000\", \"#FFFF00\"],\n" +
                "[\"#00FFFF\", \"#FF0000\", \"#C0C0C0\"],\n" +
                "[\"#000000\", \"#0000FF\", \"#C0C0C0\"]\n" +
                "],\n" +
                "\"xAxis|1\": [\n" +
                "[\"一月\", \"二月\", \"三月\", \"四月\", \"五月\", \"六月\"],\n" +
                "[\"二月\", \"三月\", \"四月\", \"五月\", \"六月\", \"七月\"],\n" +
                "[\"三月\", \"四月\", \"五月\", \"六月\", \"七月\", \"八月\"]\n" +
                "],\n" +
                "\"legend\": [\"2017\", \"2018\", \"2019\"],\n" +
                "\"data\": [{\n" +
                "\"name\": \"2017\",\n" +
                "\"data\": [\"@integer(0, 100)\", \"@integer(0, 100)\", \"@integer(0, 100)\", \"@integer(0, 100)\", \"@integer(0, 100)\", \"@integer(0, 100)\"]\n" +
                "},\n" +
                "{\n" +
                "\"name\": \"2018\",\n" +
                "\"data\": [\"@integer(0, 100)\", \"@integer(0, 100)\", \"@integer(0, 100)\", \"@integer(0, 100)\", \"@integer(0, 100)\", \"@integer(0, 100)\"]\n" +
                "},\n" +
                "{\n" +
                "\"name\": \"2019\",\n" +
                "\"data\": [\"@integer(0, 100)\", \"@integer(0, 100)\", \"@integer(0, 100)\", \"@integer(0, 100)\", \"@integer(0, 100)\", \"@integer(0, 100)\"]\n" +
                "}\n" +
                "]\n" +
                "}\n" +
                "}";
        System.out.println(Mock.mock(template));

    }
}