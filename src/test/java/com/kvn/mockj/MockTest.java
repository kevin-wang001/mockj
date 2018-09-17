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
        for (int i = 0; i < 5; i++) {
            Foo foo = Mock.mock(Foo.useTemplate(), Foo.class);
            System.out.println(JSON.toJSONString(foo));
        }
    }
}