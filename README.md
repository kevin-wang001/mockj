# mockj

### 项目介绍
生成mock数据的利器，[mockjs](http://mockjs.com/examples.html) 的 java 实现。同时内置随机 random 一个对象的功能。



### 使用说明
数据模板中的每个属性由 3 部分构成：属性名、生成规则、属性值：
>   属性名|生成规则:属性值 

>   'name|rule': value  

// 属性名   name  
// 生成规则 rule  
// 属性值   value  

注意：  
属性名 和 生成规则 之间用竖线 | 分隔。  
生成规则 是可选的。 
 
生成规则 有 7 种格式：  
```text
'name|min-max': value
'name|count': value
'name|min-max.dmin-dmax': value
'name|min-max.dcount': value
'name|count.dmin-dmax': value
'name|count.dcount': value
'name|+step': value
```

生成规则 的 含义 需要依赖 属性值的类型 才能确定。  
属性值 中可以含有 @占位符。  
属性值 还指定了最终值的初始值和类型。  


### 生成规则和示例

#### 1 属性值是字符串 String
* 'name|min-max': string  

通过重复 string 生成一个字符串，重复次数大于等于 min，小于等于 max。

* 'name|count': string

通过重复 string 生成一个字符串，重复次数等于 count。

#### 2 属性值是数字 Number
* 'name|+1': number

属性值自动加 1，初始值为 number。

* 'name|min-max': number

生成一个大于等于 min、小于等于 max 的整数，属性值 number 只是用来确定类型。

* 'name|min-max.dmin-dmax': number

生成一个浮点数，整数部分大于等于 min、小于等于 max，小数部分保留 dmin 到 dmax 位。

Mock.mock("{
    \"number1|1-100.1-10\": 1,
    \"number2|123.1-10\": 1,
    \"number3|123.3\": 1,
    \"number4|123.10\": 1.123
}")
// =>
{
    "number1": 12.92,
    "number2": 123.51,
    "number3": 123.777,
    "number4": 123.1231091814
}

"name|+1": 100 属性值自动加 1，初始值为 100  
"name|-1": 100 属性值自动减 1，初始值为 100  
"name|1-100": 100 生成一个大于等于 1、小于等于 100 的整数，属性值 100 只用来确定类型  
"name|1-100.1-10": 100 生成一个浮点数，整数部分大于等于 1、小于等于 100，小数部分保留 1 到 10 位 


#### 3 属性值是布尔型 Boolean
* 'name|1': boolean

随机生成一个布尔值，值为 true 的概率是 1/2，值为 false 的概率同样是 1/2。


#### 4 属性值是对象 Object
* 'name|count': object

从属性值 object 中随机选取 count 个属性。

* 'name|min-max': object

从属性值 object 中随机选取 min 到 max 个属性。

#### 5 属性值是数组 Array
* 'name|1': array

从属性值 array 中随机选取 1 个元素，作为最终值。

* 'name|+1': array

从属性值 array 中顺序选取 1 个元素，作为最终值。

* 'name|min-max': array

通过重复属性值 array 生成一个新数组，重复次数大于等于 min，小于等于 max。

* 'name|count': array

通过重复属性值 array 生成一个新数组，重复次数为 count。



### 例子
[MockTest.java](/src/test/java/com/kvn/mockj/MockTest.java)  
* **手写模板：** 
```java
String template = "{" +
                  "\"name|1-10\": \"★\"," +
                  "\"flag\": \"@character\"," +
                  "\"age|1-100\": 100," +
                  "\"next|+1\": 100," +
                  "\"point|1-100.1-2\": 100," +
                  "\"boy|1\": true," +
                  "\"startDate\": \"@date\"," +
                  "\"endDate\": \"@date(yyyy-MM-dd)\"," +
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
        "flag":"@character",
        "boy|1":"true",
        "point|0-32757":"1",
        "endDate":"$date",
        "name":"xx",
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
    
**通过Api：Foo foo = MockR.random(new TypeReference\<Foo\>(){}); 能产生如下的数据**
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
    