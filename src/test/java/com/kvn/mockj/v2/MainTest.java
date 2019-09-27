package com.kvn.mockj.v2;

import com.kvn.mockj.Foo;

/**
 * Created by wangzhiyuan on 2019/9/26
 */
public class MainTest {
    public static void main(String[] args) {
        String s = Mock.mock2(Foo.useTemplate());
        System.out.println(s);
    }
}
