package com.kvn.mockj.v2.handler;

import com.kvn.mockj.v2.Options;

import java.util.Map;

/**
 * Created by wangzhiyuan on 2019/9/26
 */
public interface TypeHandler {
    boolean apply(Class clazz);

    Object handle(Options options);
}
