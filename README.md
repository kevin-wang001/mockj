# mockj

#### 项目介绍
mockjs的java实现。同时内置随机 random 一个对象的功能


#### 使用说明
**配置规则:**
>   属性名|生成规则:属性值  

* 1 属性值： string  
'name|min-max'：'value'  通过重复 'value' 生成一个字符串，重复次数大于等于 min，小于等于 max。  
'name|count': 'value' 通过重复 'value' 生成一个字符串，重复次数等于 count  

* 2 属性值：number  
'name|+1': 100 属性值自动加 1，初始值为 100  
'name|-1': 100 属性值自动减 1，初始值为 100  
'name|1-100': 100 生成一个大于等于 1、小于等于 100 的整数，属性值 100 只用来确定类型  
'name|1-100.1-10': 100 生成一个浮点数，整数部分大于等于 1、小于等于 100，小数部分保留 1 到 10 位  

* 3 属性值：boolean  
'name|1': value  生成一个固定的布尔值，值为 value  
'name|@random': value   随机生成一个布尔值  

* 4 属性值：object  
'name|min-max': {} 从属性值 {} 中随机选取 min 到 max 个属性  
'name|count': {} 从属性值 {} 中随机选取 count 个属性。  

* 5 属性值：array
'name|1': [{}, {} ...] 从属性值 [{}, {} ...] 中随机选取 1 个元素，作为最终值  
'name|min-max': [{}, {} ...] 通过重复属性值 [{}, {} ...] 生成一个新数组，重复次数大于等于 min，小于等于 max  
'name|count': [{}, {} ...] 通过重复属性值 [{}, {} ...] 生成一个新数组，重复次数为 count  


#### TODO
1. baseValue为正则的支持  
2. RelectionRandom