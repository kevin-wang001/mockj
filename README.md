# mockj

### 项目介绍
生成mock数据的利器，mockjs的java实现。同时内置随机 random 一个对象的功能


### 使用说明
配置规则:  
>   属性名|生成规则:属性值  

* 1 属性值： string  
"name|min-max"："value"  通过重复 "value" 生成一个字符串，重复次数大于等于 min，小于等于 max。  
"name|count": "value" 通过重复 "value" 生成一个字符串，重复次数等于 count  

* 2 属性值：number  
"name|+1": 100 属性值自动加 1，初始值为 100  
"name|-1": 100 属性值自动减 1，初始值为 100  
"name|1-100": 100 生成一个大于等于 1、小于等于 100 的整数，属性值 100 只用来确定类型  
"name|1-100.1-10": 100 生成一个浮点数，整数部分大于等于 1、小于等于 100，小数部分保留 1 到 10 位  

* 3 属性值：boolean  
"name|1": value  生成一个固定的布尔值，值为 value  
"name|@random": value   随机生成一个布尔值  

* 4 属性值：object  
"name|min-max": {...} 从属性值 {...} 中随机选取 min 到 max 个属性  
"name|count": {...} 从属性值 {...} 中随机选取 count 个属性。  

* 5 属性值：array
"name|1": [{}, {} ...] 从属性值 [{}, {} ...] 中随机选取 1 个元素，作为最终值。且最终返回的数据类型与元素保持一致  
"name|min-max": [{}, {} ...] 从数组 [{}, {} ...] 中挑选 x 个值生成一个新数组返回，x 的值介于 min 与 max 之间  
"name|count": [{}, {} ...] 从数组 [{}, {} ...] 中挑选 count 个值生成一个新数组返回  

* 6 @random的支持  
支持对 string、number、boolean类型的使用 @random 规则，可以随机产生 mock 数据。  
"name|@random": "value"  

* 7 支持扩展函数来产生 mock 数据  
value 值以 $ 开头，则会路由到 $Function.java 中，调用 $Function.java 中指定的方法。可以自由进行扩展  
例如：  
>
    "flag": "$char"
    "startDate": "$date"

* 8 对随机模板的支持  
手写模板通常工作量比较大，如果没有特殊要求，可以通过 TemplateHelper.java 来辅助生成随机模板  



### 例子
[MockTest.java](/src/test/java/com/kvn/mockj/MockTest.java)  
* **手写模板：** 
```java
String template = "{" +
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
Foo foo = Mock.mock(template, Foo.class);
```
产生的mock数据：
>
    {
        "age":33,
        "boy":false,
        "courses":[
            "英语",
            "英语"
        ],
        "courses2":[
            "语文"
        ],
        "endDate":1537459200000,
        "flag":"s",
        "map":{
            "130000":"河北省",
            "140000":"山西省"
        },
        "name":"★★★★★★★",
        "next":100,
        "point":67.76,
        "startDate":1537524864573
    }

* **使用随机模板1：**
```java
Foo mock = Mock.mock(TemplateHelper.randomTemplate(Foo.class), Foo.class);
```
产生的随机 mock 模板：  
>
    {
        "age|0-32757":"1",
        "flag":"$char",
        "boy|@random":"true",
        "point|0-32757":"1",
        "endDate":"$date",
        "name|@random":"xx",
        "next|0-32757":"1",
        "startDate":"$date"
    }

产生的 mock 数据：  
>
    {
        "next":30277,
        "flag":"g",
        "endDate":"2018-09-21T10:15:56.998Z",
        "name":"RNvK",
        "boy":"true",
        "age":18243,
        "point":30984,
        "startDate":"2018-09-21T10:15:57.007Z"
    }

* **使用随机模板2：**  
随机模板只支持对 string、number、date等数据类型进行mock，所以其他的复杂对象类型的mock，需要手动添加mock模板。mockj提供了相应的api进行操作  
```java
TemplateHelper helper = TemplateHelper.random(Foo.class);
helper.put("courses|2", new JSONArray(Lists.newArrayList("语文","数学","英语")));
helper.put("courses2|1-2", new JSONArray(Lists.newArrayList("语文","数学","英语")));
helper.put("map|2-4", JSONObject.parseObject("{\"110000\": \"北京市\",\"120000\": \"天津市\",\"130000\": \"河北省\",\"140000\": \"山西省\"}"));
helper.put("foo|1", JSONArray.parseArray("[{\"name\":\"zhangsan\",\"age\":12},{\"name\":\"lisi\",\"age\":10},{\"name\":\"wangwu\",\"age\":8}]"));
helper.put("foo1", JSONArray.parseObject("{\"name\":\"wangwu\",\"age\":8}"));
Foo mock = Mock.mock(helper.toTemplate(), Foo.class);
```
产生的 mock 数据：  
>
    {
        "age":6120,
        "boy":false,
        "courses":[
            "数学",
            "语文"
        ],
        "courses2":[
            "数学",
            "语文"
        ],
        "endDate":1537525215493,
        "flag":"h",
        "foo":{
            "age":8,
            "boy":false,
            "flag":"",
            "name":"wangwu",
            "next":0
        },
        "foo1":{
            "age":8,
            "boy":false,
            "flag":"",
            "name":"wangwu",
            "next":0
        },
        "map":{
            "110000":"北京市",
            "120000":"天津市",
            "130000":"河北省",
            "140000":"山西省"
        },
        "name":"rdmo",
        "next":7825,
        "point":11937,
        "startDate":1537525215494
    }


### 提供反射接口，随机产生一个对象
这种方式支持的数据类型更加丰富。  
例如：  
>   
    Foo.class
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
    
**通过Api：Foo foo = MockR.random(typeReference); 能产生如下的数据**
>
    {
        "age":-61382,
        "boy":true,
        "courses":[
            "hSer"
        ],
        "courses2":[
            "eatH"
        ],
        "endDate":1537524456497,
        "flag":"￡",
        "foo":{
            "age":34475,
            "boy":false,
            "courses":[
                "BTvW"
            ],
            "courses2":[
                "mFWP"
            ],
            "endDate":1537524456497,
            "flag":"c",
            "map":{
                "YUJl":"IVvP"
            },
            "name":"JyAL",
            "next":-23634,
            "point":-96035,
            "startDate":1537524456497
        },
        "foo1":{
            "age":-98010,
            "boy":false,
            "courses":[
                "spTD"
            ],
            "courses2":[
                "WcQX"
            ],
            "endDate":1537524456498,
            "flag":"4",
            "map":{
                "ueYR":"ANbU"
            },
            "name":"XhFe",
            "next":75119,
            "point":21497,
            "startDate":1537524456498
        },
        "map":{
            "GzMk":"oJqQ"
        },
        "name":"tNzx",
        "next":-54482,
        "point":-20110,
        "startDate":1537524456497
    }
    

#### TODO
1. baseValue为正则的支持  