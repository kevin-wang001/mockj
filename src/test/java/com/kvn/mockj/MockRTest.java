package com.kvn.mockj;

import com.alibaba.fastjson.JSON;
import com.kvn.mockj.Foo;
import com.kvn.mockj.reflection.MockR;
import com.kvn.mockj.reflection.TypeReference;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wangzhiyuan on 2018/9/21
 */
public class MockRTest {

    @Test
    public void random() {
        TypeReference<Foo> typeReference = new TypeReference<Foo>(){};
        Foo foo = MockR.random(typeReference);
        System.out.println(foo);
        System.out.println(JSON.toJSONString(foo));
    }
}