package com.kvn.mockj.handler;

import com.kvn.mockj.Options;

/**
 * Created by wangzhiyuan on 2019/9/26
 */
public interface TypeHandler {
    Class[] support();

    Object handle(Options options);
}
