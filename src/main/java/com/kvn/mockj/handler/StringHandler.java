package com.kvn.mockj.handler;

import com.kvn.mockj.Options;

/**
 * Created by wangzhiyuan on 2019/9/26
 */
public class StringHandler implements TypeHandler {

    @Override
    public Class[] support() {
        return new Class[]{String.class};
    }

    @Override
    public Object handle(Options options) {
        String sR = "";

        //  'foo': '★'
        if (options.getRule().getCount() == null) {
            sR += options.getTemplate();
        } else {
            // 'star|1-5': '★',
            for (int i = 0; i < options.getRule().getCount(); i++) {
                sR += options.getTemplate();
            }
        }

        // 'email|1-10': '@EMAIL ,'
        options.setTemplate(sR);
        return PlaceholderHandler.doGenerate(options);
    }
}
