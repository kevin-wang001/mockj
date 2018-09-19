# mockj

#### 项目介绍
生成mock数据的利器，mockjs的java实现。同时内置随机 random 一个对象的功能


#### 使用说明
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

#### 例子
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

* **使用随机模板1：**
```java
Foo mock = Mock.mock(TemplateHelper.randomTemplate(Foo.class), Foo.class);
```

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

#### TODO
1. baseValue为正则的支持  
2. RelectionRandom