package com.kvn.mockj;

import com.alibaba.fastjson.JSON;

import static org.junit.Assert.*;

/**
 * Created by wangzhiyuan on 2018/9/17
 */
public class MockTest {

    @org.junit.Test
    public void mock() {

    }

    public static void main(String[] args) {
        String template = "{" +
                "\"name|1-10\": \"★\"," +
                "\"age|1-100\": 100," +
                "\"point|1-100.1-2\": 100," +
                "\"boy|@random\": true" +
                "}";
        for (int i = 0; i < 5; i++) {
            Foo foo = Mock.mock(template, Foo.class);
            System.out.println(JSON.toJSONString(foo));
        }
    }
}