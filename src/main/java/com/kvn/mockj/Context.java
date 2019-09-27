package com.kvn.mockj;

import lombok.Data;

/**
 * Created by wangzhiyuan on 2019/9/26
 */
@Data
public class Context {

    // 'data|+1': [{}, {}]
    private int orderIndex;

    // "number|+1": 202
    // 此规则的初始自增值（即：202）
    private Integer incInitValue;

}
