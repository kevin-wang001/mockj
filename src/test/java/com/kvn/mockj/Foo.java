package com.kvn.mockj;

import lombok.Data;

import java.util.Date;

/**
 * Created by wangzhiyuan on 2018/9/12
 */
@Data
public class Foo {
    private String name;

    // 数字类型
    private int age;
    private int next;
    private Double point;

    // boolean 类型
    private boolean boy;

    // date 类型
    private Date startDate;
    private Date endDate;


    public static String useTemplate(){
        return "{" +
                "\"name|1-10\": \"★\"," +
                "\"age|1-100\": 100," +
                "\"next|+1\": 100," +
                "\"point|1-100.1-2\": 100," +
                "\"boy|@random\": true," +
                "\"startDate\": \"$date\"," +
                "\"endDate\": \"$date(yyyy-MM-dd)\"" +
                "}";
    }
}
