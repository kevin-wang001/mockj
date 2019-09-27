package com.kvn.mockj.handler;

import com.alibaba.fastjson.JSONObject;
import com.kvn.mockj.Handler;
import com.kvn.mockj.Options;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangzhiyuan on 2019/9/26
 */
public class ObjectHandler implements TypeHandler {

    @Override
    public Class[] support() {
        return new Class[]{JSONObject.class};
    }

    @Override
    public Object handle(Options options) {
        JSONObject jo = new JSONObject();

        // 'obj|min-max': {}
        if (options.getRule().getMin() != null) {
            JSONObject template = (JSONObject) options.getTemplate();
            List<String> keyList = template.keySet().parallelStream().collect(Collectors.toList());
            Collections.shuffle(keyList);

            int count = keyList.size() > options.getRule().getCount() ? options.getRule().getCount() : keyList.size();
            for (int i = 0; i < count; i++) {
                String key = keyList.get(i >= keyList.size() ? i - keyList.size() : i);
                String parsedKey = parseName(key);
                jo.put(parsedKey, Handler.gen(template.get(key), key, options.getContext()));
            }

            return jo;
        }


        // 'obj': {}
        JSONObject template = (JSONObject) options.getTemplate();
        Iterator<String> it = template.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String parsedKey = parseName(key);
            jo.put(parsedKey, Handler.gen(template.get(key), key, options.getContext()));
        }

        return jo;
    }


    private static String parseName(String name) {
        int index = name.indexOf("|");
        return index < 0 ? name : name.substring(0, index);
    }
}
