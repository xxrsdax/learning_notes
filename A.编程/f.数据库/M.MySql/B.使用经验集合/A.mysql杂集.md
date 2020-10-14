# 1.内置函数篇

## 1.1 locate()方法

 1.1.普通用法：

```
   SELECT column from table where locate('keyword', condition)>0 
```

​     类似于 java 的 indexOf();不过 locate() 只要找到返回的结果都大于0(即使是查询的内容就是最开始部分)，没有查找到才返回0；

​	说明：locate(参数1 , 参数2)   参数1 一般为自己传的参数，参数2一般为指定的数据库列，其效率比 like 稍快。

​                但 locate()  不能使用索引，like可以使用前缀索引。



​    1.2. 指定其实位置：

​        **SELECT LOCATE('bar', 'foobarbar',5);**  

​        查询结果为 7

​        (从foobarbar的第五个位置开始查找)





# 2.语法篇

## 2.1 创建指定数据库并设置字符集

CREATE DATABASE IF NOT EXISTS yourdbname DEFAULT CHARSET utf8 COLLATE utf8_general_ci;