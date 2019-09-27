package com.kvn.mockj;

import com.kvn.mockj.Context;
import com.kvn.mockj.Mock;
import org.junit.Test;

/**
 * Created by wangzhiyuan on 2019/9/26
 */
public class Mock2Test {

    @Test
    public void stringTest() {

        System.out.println(Mock.mock("{\n" +
                "  \"string|1-10\": \"★\"\n" +
                "}"));

        System.out.println(Mock.mock("{\n" +
                "                \"string|3\": \"★★★\"\n" +
                "}"));

    }

    @Test
    public void numberTest() {
        System.out.println(Mock.mock("{\n" +
                "                \"number|+1\": 202\n" +
                "}"));

        System.out.println(Mock.mock("{\n" +
                "                \"number|1-100\": 100\n" +
                "}"));

        System.out.println(Mock.mock("{\n" +
                "                \"number|1-100.1-10\": 1\n" +
                "}"));

        System.out.println(Mock.mock("{\n" +
                "                \"number|123.1-10\": 1\n" +
                "}"));
        System.out.println(Mock.mock("{\n" +
                "  \"number|123.3\": 1\n" +
                "}"));

        System.out.println(Mock.mock("{\n" +
                "  \"number|123.10\": 1.123\n" +
                "}"));
    }

    @Test
    public void booleanTest() {
        System.out.println(Mock.mock("{\n" +
                "                \"boolean|1\": true\n" +
                "}"));
    }

    @Test
    public void objectTest(){
        System.out.println(Mock.mock("{\n" +
                "                \"object|2\": {\n" +
                "                    \"310000\": \"上海市\",\n" +
                "                    \"320000\": \"江苏省\",\n" +
                "                    \"330000\": \"浙江省\",\n" +
                "                    \"340000\": \"安徽省\"\n" +
                "        }\n" +
                "}"));

        System.out.println(Mock.mock("{\n" +
                "  \"object|2-4\": {\n" +
                "    \"110000\": \"北京市\",\n" +
                "    \"120000\": \"天津市\",\n" +
                "    \"130000\": \"河北省\",\n" +
                "    \"140000\": \"山西省\"\n" +
                "  }\n" +
                "}"));
    }

    @Test
    public void arrayTest(){
        System.out.println(Mock.mock("{\n" +
                "  \"array|1\": [\n" +
                "    \"AMD\",\n" +
                "    \"CMD\",\n" +
                "    \"UMD\"\n" +
                "  ]\n" +

                "}"));

        Context context = new Context();
        for (int i = 0; i < 5; i++) {
            System.out.println(Mock.mock("{\n" +
                    "  \"array|+1\": [\n" +
                    "    \"AMD\",\n" +
                    "    \"CMD\",\n" +
                    "    \"UMD\"\n" +
                    "  ]\n" +
                    "}", context));
        }

        System.out.println(Mock.mock("{\n" +
                "  \"array|1-10\": [\n" +
                "    {\n" +
                "      \"name|+1\": [\n" +
                "        \"Hello\",\n" +
                "        \"Mock.js\",\n" +
                "        \"!\"\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}"));

        System.out.println(Mock.mock("{\n" +
                "  \"array|1-10\": [\n" +
                "    \"Mock.js\"\n" +
                "  ]\n" +
                "}"));

        System.out.println(Mock.mock("{\n" +
                "  \"array|1-10\": [\n" +
                "    \"Hello\",\n" +
                "    \"Mock.js\",\n" +
                "    \"!\"\n" +
                "  ]\n" +
                "}"));

    }

    @Test
    public void placeHolderTest(){
        System.out.println(Mock.mock("{\n" +
                "    \"success\":true,\n" +
                "    \"code\":200,\n" +
                "    \"data\":{\n" +
                "        \"colors|+1\":[\n" +
                "            [\n" +
                "                \"#000000\",\n" +
                "                \"#FF0000\",\n" +
                "                \"#FFFF00\"\n" +
                "            ],\n" +
                "            [\n" +
                "                \"#00FFFF\",\n" +
                "                \"#FF0000\",\n" +
                "                \"#C0C0C0\"\n" +
                "            ],\n" +
                "            [\n" +
                "                \"#000000\",\n" +
                "                \"#0000FF\",\n" +
                "                \"#C0C0C0\"\n" +
                "            ]\n" +
                "        ],\n" +
                "        \"xAxis|+1\":[\n" +
                "            [\n" +
                "                \"一月\",\n" +
                "                \"二月\",\n" +
                "                \"三月\",\n" +
                "                \"四月\",\n" +
                "                \"五月\",\n" +
                "                \"六月\"\n" +
                "            ],\n" +
                "            [\n" +
                "                \"二月\",\n" +
                "                \"三月\",\n" +
                "                \"四月\",\n" +
                "                \"五月\",\n" +
                "                \"六月\",\n" +
                "                \"七月\"\n" +
                "            ],\n" +
                "            [\n" +
                "                \"三月\",\n" +
                "                \"四月\",\n" +
                "                \"五月\",\n" +
                "                \"六月\",\n" +
                "                \"七月\",\n" +
                "                \"八月\"\n" +
                "            ]\n" +
                "        ],\n" +
                "        \"legend\":[\n" +
                "            \"2017\",\n" +
                "            \"2018\",\n" +
                "            \"2019\"\n" +
                "        ],\n" +
                "        \"data\":[\n" +
                "            {\n" +
                "                \"name\":\"2017\",\n" +
                "                \"data\":[\n" +
                "                    \"@integer(0, 100)\",\n" +
                "                    \"@integer(0, 100)\",\n" +
                "                    \"@integer(0, 100)\",\n" +
                "                    \"@integer(0, 100)\",\n" +
                "                    \"@integer(0, 100)\",\n" +
                "                    \"@integer(0, 100)\"\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\":\"2018\",\n" +
                "                \"data\":[\n" +
                "                    \"@integer(0, 100)\",\n" +
                "                    \"@integer(0, 100)\",\n" +
                "                    \"@integer(0, 100)\",\n" +
                "                    \"@integer(0, 100)\",\n" +
                "                    \"@integer(0, 100)\",\n" +
                "                    \"@integer(0, 100)\"\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\":\"2019\",\n" +
                "                \"data\":[\n" +
                "                    \"@integer(0, 100)\",\n" +
                "                    \"@integer(0, 100)\",\n" +
                "                    \"@integer(0, 100)\",\n" +
                "                    \"@integer(0, 100)\",\n" +
                "                    \"@integer(0, 100)\",\n" +
                "                    \"@integer(0, 100)\"\n" +
                "                ]\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}"));
    }

}
