package com.kvn.mockj.handler;

import com.kvn.mockj.Options;
import org.apache.commons.lang3.RandomUtils;

/**
 * Created by wangzhiyuan on 2019/9/26
 */
public class BooleanHandler implements TypeHandler {


    @Override
    public Class[] support() {
        return new Class[]{Boolean.class};
    }

    @Override
    public Object handle(Options options) {
        return RandomUtils.nextInt(0, 2) == 1;
    }

}
