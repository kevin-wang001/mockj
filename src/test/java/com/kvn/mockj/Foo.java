package com.kvn.mockj;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    // collection 类型
    private List<String> courses;


    public static String useTemplate(){
        return "{" +
                "\"name|1-10\": \"★\"," +
                "\"age|1-100\": 100," +
                "\"next|+1\": 100," +
                "\"point|1-100.1-2\": 100," +
                "\"boy|@random\": true," +
                "\"startDate\": \"$date\"," +
                "\"endDate\": \"$date(yyyy-MM-dd)\"," +
                "\"courses|2\": [\"语文\",\"数学\",\"英语\"]" +
                "}";
    }

    public static void main(String[] args) {
        Foo foo = new Foo();
        foo.setAge(12);
        ArrayList<String> courses = new ArrayList<>();
        courses.add("语文");
        courses.add("数学");
        foo.setCourses(courses);
        String json = JSON.toJSONString(foo);
        String mock = Mock.mock(useTemplate());
        System.out.println(json);
        Foo foo1 = JSON.parseObject(json, Foo.class);
        System.out.println(foo1);
    }
}
