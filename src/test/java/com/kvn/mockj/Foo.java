package com.kvn.mockj;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by wangzhiyuan on 2018/9/12
 */
@Data
public class Foo {
    // String 类型
    private String name;

    // char 类型
    private char flag;

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
    private Set<String> courses2;

    // map 类型
    private Map<String, String> map;

    // Object 类型
    private Foo foo;

    private Foo foo1;


    public static String useTemplate(){
        return "{" +
                "\"name|1-10\": \"★\"," +
                "\"flag\": \"$char\"," +
                "\"age|1-100\": 100," +
                "\"next|+1\": 100," +
                "\"point|1-100.1-2\": 100," +
                "\"boy|@random\": true," +
                "\"startDate\": \"$date\"," +
                "\"endDate\": \"$date(yyyy-MM-dd)\"," +
                "\"courses|2\": [\"语文\",\"数学\",\"英语\"]," +
                "\"courses2|1-2\": [\"语文\",\"数学\",\"英语\"]," +
                "\"map|2-4\": {\"110000\": \"北京市\",\"120000\": \"天津市\",\"130000\": \"河北省\",\"140000\": \"山西省\"}" +
                "}";
    }

    public static void main(String[] args) {
        JSONObject jsonObject = JSON.parseObject("{\n" +
                "  \"array|1-10\": [\n" +
                "    {\n" +
                "      \"name|1-5\": [\n" +
                "\t\t  {\n" +
                "\t\t\t\"array|+1\": [\n" +
                "\t\t\t\t\"AMD\",\n" +
                "\t\t\t\t\"CMD\",\n" +
                "\t\t\t\t\"UMD\"\n" +
                "\t\t\t  ]\n" +
                "\t\t  }\n" +
                "\t  ]\n" +
                "    }\n" +
                "  ]\n" +
                "}");
        System.out.println(jsonObject.size());
    }

}
