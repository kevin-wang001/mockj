package com.kvn.mockj.v2.handler;

import com.alibaba.fastjson.JSONObject;
import com.kvn.mockj.v2.Options;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhiyuan on 2019/9/26
 */
public class StringHandler implements TypeHandler {
    @Override
    public boolean apply(Class clazz) {
        return false;
    }


    @Override
    public Object handle(Options options) {
        String sR = "";

        //  'foo': '★'
        if (options.getRule().getCount() == null) {
            return options.getTemplate();
        }

        // 'star|1-5': '★',
        for (int i = 0; i < options.getRule().getCount(); i++) {
            sR += options.getTemplate();
        }

        // 'email|1-10': '@EMAIL, ',
        // TODO 占位符的实现


        return sR;
    }
}
