package com.kvn.mockj.v2.handler;

import com.alibaba.fastjson.JSONArray;
import com.kvn.mockj.v2.Context;
import com.kvn.mockj.v2.Handler;
import com.kvn.mockj.v2.Options;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by wangzhiyuan on 2019/9/26
 */
public class ArrayHandler implements TypeHandler {
    @Override
    public boolean apply(Class clazz) {
        return false;
    }


    @Override
    public Object handle(Options options) {
        // 'name|1': []
        // 'name|count': []
        // 'name|min-max': []
        JSONArray jaR = new JSONArray();

        // 'arr': [{ 'email': '@EMAIL' }, { 'email': '@EMAIL' }]
        if (options.getRule().getParameters() == null || options.getRule().getParameters().isEmpty()) {
            JSONArray templates = (JSONArray) options.getTemplate();
            for (int i = 0; i < templates.size(); i++) {
                Context context = new Context();
                jaR.add(Handler.gen(templates.get(i), i + "", context));
            }
        } else {
            // 'method|1': ['GET', 'POST', 'HEAD', 'DELETE']
            if(options.getRule().getMin() != null && options.getRule().getMin() == 1 && options.getRule().getMax() == null){
                JSONArray ja = (JSONArray) Handler.gen(options.getTemplate(), null, options.getContext());
                jaR.add(ja.get(RandomUtils.nextInt(0, ja.size() - 1)));
            } else {

                // 'data|+1': [{}, {}]
                if(StringUtils.isNotEmpty(options.getRule().getParameters().get(1))){
                    JSONArray ja = (JSONArray) Handler.gen(options.getTemplate(), null, options.getContext());
                    int index = options.getContext().getOrderIndex() >= ja.size() ? 0 : options.getContext().getOrderIndex();
                    jaR.add(ja.get(index));
                    options.getContext().setOrderIndex(index + 1);
                } else {
                    // 'data|1-10': [{}]
                    JSONArray ja0 = new JSONArray();
                    for (int i = 0; i < options.getRule().getCount(); i++) {
                        // 'data|1-10': [{}, {}]
                        JSONArray templates = (JSONArray) options.getTemplate();
                        JSONArray ja = new JSONArray();
                        for (int j = 0; j < templates.size(); j++) {
                            ja.add(Handler.gen(templates.get(j), ja.size() + "", options.getContext()));
                        }
                        ja0.add(ja);
                    }
                    jaR.addAll(ja0);

                }




            }

        }

        return jaR;
    }
}
