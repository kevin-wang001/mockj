package com.kvn.mockj.v2.handler;

import com.alibaba.fastjson.JSONObject;
import com.kvn.mockj.v2.Options;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhiyuan on 2019/9/26
 */
public class NumberHandler implements TypeHandler {
    @Override
    public boolean apply(Class clazz) {
        return false;
    }


    @Override
    public Object handle(Options options) {
        return 2;
    }
}
