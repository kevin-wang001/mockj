package com.kvn.mockj.v2;

import lombok.Data;

import java.util.List;

/**
 * Created by wangzhiyuan on 2019/9/26
 */
@Data
public class Rule {
    // 1 name, 2 inc, 3 range, 4 decimal
    List parameters;
    // 1 min, 2 max
    boolean range;
    Integer min;
    Integer max;
    // min-max
    Integer count;
    // 是否有 decimal
    boolean decimal;
    Integer dmin;
    Integer dmax;
    // dmin-dimax
    Integer dcount;
}
