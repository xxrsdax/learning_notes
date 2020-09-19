# 1.Spring Framework的历史

- 诞生与2002年，成型于2003年，最早的作者为Rod Johnson
    - 《Expert One-on-One J2EE Design and Development》
    - 《Expert One-on-One J2EE Development  without  EJB》
- 目前已经发展到了Spring 5.x版本，支持JDK 8-11 及 Java EE 8





# 2.数据部分操作



## 2.1 JDBC必知必会

案例一：

​	1.创建Spring项目

​		选择 web  jdbc  h2  Lombok   actuator  

​	注意：项目启动后 可以访问  http://localhost:8080/actuator/beans    

​				可以查看到Spring内容器中所有的bean

​	2.配置所需要的bean

​		**数据源相关**

- DataSource (根据选择的连接池实现决定)

    

    **事务相关（可选）**

- PlatformTransactionManager( 实际对象  DataSourceTransactionManag )

- TransactionTemplate 



​		**操作相关（可选）**

- JdbcTemplate

​	

![image-20200910234942121](img/image-20200910234942121.png)

![image-20200910235143080](img/image-20200910235143080.png)



![image-20200910235418472](img/image-20200910235418472.png)



![image-20200910235513505](img/image-20200910235513505.png)



![image-20200910235656331](img/image-20200910235656331.png)



![image-20200910235709212](img/image-20200910235709212.png)



## 2.2连接池

### 2.2.1Hikaricp

![image-20200911074031821](img/image-20200911074031821.png)



![image-20200911074239828](img/image-20200911074239828.png)

![image-20200911074427820](img/image-20200911074427820.png)



### 2.2.2 Alibaba Druid

![image-20200911074614931](img/image-20200911074614931.png)





![image-20200911074711914](img/image-20200911074711914.png)



github:  alibaba.druid



![image-20200911074933221](img/image-20200911074933221.png)



![image-20200911075455126](img/image-20200911075455126.png)



![image-20200911075524936](img/image-20200911075524936.png)





![image-20200911075557436](img/image-20200911075557436.png)



![image-20200911075754633](img/image-20200911075754633.png)





## 2.3 Spring中的JDBC操作



![image-20200911080230803](img/image-20200911080230803.png)



![image-20200911080822545](img/image-20200911080822545.png)



![image-20200911080924938](img/image-20200911080924938.png)





![image-20200911081300083](img/image-20200911081300083.png)





## 2.4 Spring的事务抽象

### 2.4.1 一致的事务模型

![image-20200914073238091](img/image-20200914073238091.png)



### 2.4.2 事务抽象的核心接口

![image-20200914073309179](img/image-20200914073309179.png)



### 2.4.3 事务传播特性

![image-20200914073407051](img/image-20200914073407051.png)



3  会挂起原有事务

6 内部事务和外部事务 相对来说是独立的



### 2.4.4 事务隔离特性

![image-20200914073552106](img/image-20200914073552106.png)

代码中使用  -1 表示使用数据库的默认值



### 2.4.5 编程式事务

![image-20200914073758533](img/image-20200914073758533.png)

![image-20200914073845905](img/image-20200914073845905.png)





### 2.4.6 声明式事务

![image-20200914074129295](img/image-20200914074129295.png)

![image-20200914074227470](img/image-20200914074227470.png)

![image-20200914074550678](img/image-20200914074550678.png)

第一个方法插入成功

第二个方法被回滚

第三个方法插入成功 ，因为虽然调用了第二个方法，但第三个方法本身并没有标注事务，所以不会被代理



### 2.4.7 Spring 的JDBC异常抽象

![image-20200914075500911](img/image-20200914075500911.png)



**错误码**

![image-20200914075558850](img/image-20200914075558850.png)



**定制错误码**

![image-20200914080430687](img/image-20200914080430687.png)



## 2.?.答疑

### 2.?.1 Spring常用注解

![image-20200914081954800](img/image-20200914081954800.png)





![image-20200914082149681](img/image-20200914082149681.png)



@Autowired 按类型注入

@Qualifier 按名字注入

@Resource 按名字注入



### 2.?.2  actuator (健康监听)

![image-20200914205048992](img/image-20200914205048992.png)

*也可以替换成指定 名称



### 2.?.3多数据源

![image-20200914205510631](img/image-20200914205510631.png)



![image-20200914205658399](img/image-20200914205658399.png)

P13

### 2.?.4事务的本质

![image-20200914205902561](img/image-20200914205902561.png)



### 2.?.5 Alibab Druid 

#### 2.?.5.1 慢SQL日志

![image-20200914210431019](img/image-20200914210431019.png)



#### 2.?.5.2 druid 注意事项

![image-20200914210740188](img/image-20200914210740188.png)



责任链





## 2.5 Spring Data JPA

### 2.5.1 对象与关系的范式不匹配

![image-20200914211428402](img/image-20200914211428402.png)



### 2.5.2 Hibernate

![image-20200914211532611](img/image-20200914211532611.png)



![image-20200914211619210](img/image-20200914211619210.png)



### 2.5.3 JPA

![image-20200914211710176](img/image-20200914211710176.png)



可以理解为在ORM框架上做了一层抽象



### 2.5.4 Spring Data 	

![image-20200914211832152](img/image-20200914211832152.png)



又提高了一层抽象





### 2.5.5 JPA注解

![image-20200914212042228](img/image-20200914212042228.png)



![image-20200914212219975](img/image-20200914212219975.png)	





![image-20200914212229856](img/image-20200914212229856.png)



### 2.5.6 Lombok 注解

![image-20200914212337114](img/image-20200914212337114.png)





### 2.5.7 操作数据库

#### 2.5.7.1 注解

![image-20200914214330087](img/image-20200914214330087.png)

@EnableJpaRepositories 写在Config类上



![image-20200914214629781](img/image-20200914214629781.png)



#### 2.5.7.2 根据方法名定义查询

![image-20200914214442991](img/image-20200914214442991.png)



#### 2.5.7.3 保存数据

![image-20200914214718037](img/image-20200914214718037.png)

![image-20200914214803224](img/image-20200914214803224.png)





#### 2.5.7.4 查找数据

![image-20200914215113073](img/image-20200914215113073.png)



![image-20200914215704393](img/image-20200914215704393.png)



### 2.5.8 Repository接口转换成bean

#### 2.5.8.1 Repository Bean 是如何创建的

![image-20200915073936846](img/image-20200915073936846.png)





#### 2.5.8.2 接口中的方法是如何被解释的

![image-20200915074410173](img/image-20200915074410173.png)





## 2.6 Mybatis

### 2.6.1 认识MyBatis

![image-20200915075630300](img/image-20200915075630300.png)



SQL 整体比较简单 可以使用JPA

SQL 比较复杂 可以使用Mybatis



### 2.6.2 Mybatis配置

![image-20200915075826995](img/image-20200915075826995.png)

### 2.6.3 Mapp的扫描

![image-20200915075923501](img/image-20200915075923501.png)



**案例**

![image-20200915080027275](img/image-20200915080027275.png)



### 2.6.4 看官方文档





### 2.7.5 MyBatis生成器

#### 2.7.5.1 MyBatis生成器

![image-20200915080819128](img/image-20200915080819128.png)



#### 2.7.5.2  运行MyBatis Generator的方式

![image-20200915080925100](img/image-20200915080925100.png)



#### 2.7.5.2 配置MyBatis Generator

配置都是在XML中的

![image-20200915080951209](img/image-20200915080951209.png)

xml

注解

xml 注解 混合配置



#### 2.7.5.3 生成时可以使用的插件

![image-20200915081218078](img/image-20200915081218078.png)



#### 2.7.5.4 使用生成的对象

![image-20200915081304848](img/image-20200915081304848.png)



**案例**

![image-20200915081401794](img/image-20200915081401794.png)





### 2.6.6 MyBatis PageHelper

#### 2.6.6.1 认识 MyBatis PageHelper

![image-20200915082319082](img/image-20200915082319082.png)



#### 2.6.6.2 查看官网文档





P22









## 2.7内容小结

![image-20200916074218499](img/image-20200916074218499.png)









# 3.docker 辅助开发

## 3.1 什么是容器

![image-20200916075156435](img/image-20200916075156435.png)



## 3.2 docker流程

![image-20200916075239850](img/image-20200916075239850.png)



## 3.3 docker的作用

![image-20200916075323875](img/image-20200916075323875.png)





## 3.4 Docker常用命令

![image-20200916075450516](img/image-20200916075450516.png)

## 3.5 docker run的常用选项

![image-20200916075512580](img/image-20200916075512580.png)



## 3.6 镜像下载地址

![image-20200916075538882](img/image-20200916075538882.png)



## 3.7通过Docker启动MongoDB

![image-20200916075809296](img/image-20200916075809296.png)

## 3.8 进入容器

![image-20200916082608041](img/image-20200916082608041.png)



P25



# 4.Spring 操作 MongoDB

## 4.1 Spring对MongoDB的支持

![image-20200917080310259](img/image-20200917080310259.png)

​	

## 4.2 Spring Data  MongoDB 的基本用法

![image-20200917080505523](img/image-20200917080505523.png)



## 4.3 初始化 MongoDB的库及权限

![image-20200917080612909](img/image-20200917080612909.png)

## 4.4 Spring Data MongoDB的MongoTemplate

通过 MongoDB template 操作数据库



## 4.5 Spring Data MongoDB的Repository

![image-20200917081934857](img/image-20200917081934857.png)

通过 MongoDB 的Repository 实现去 操作数据库





# 5.Spring 操作 Redis 

## 5.1 Spring对Redis的支持

![image-20200918081625173](img/image-20200918081625173.png)



## 5.2 Jedis客户端的简单使用

![image-20200918081801045](img/image-20200918081801045.png)

？？？

## 5.3 Jedis客户端的简单使用

![image-20200918081959558](img/image-20200918081959558.png)



## 5.4 通过Docker启动Redis

![image-20200918082049031](img/image-20200918082049031.png)

P26



# ?.线上咖啡馆



## ?.1项目目标

![image-20200914212606798](img/image-20200914212606798.png)



## ?.2 项目中的对象实体

![image-20200914212734069](img/image-20200914212734069.png)



## ?.3 引入依赖

joda-money、usertype 处理金额

![image-20200914212844182](img/image-20200914212844182.png)



## ?.4 实体

### **?.4.1 Coffee**

![image-20200914213013765](img/image-20200914213013765.png)



![image-20200914213529998](img/image-20200914213529998.png)

可以把上面的PersistentMoneyAmount 替换成 PersistentMoneyMinorAmount

有了BaseEntity 可以替换成一下内容

![image-20200914213838658](img/image-20200914213838658.png)





### **?.4.2 CoffeeDrder**

![image-20200914213105335](img/image-20200914213105335.png)



有了BaseEntity 可以替换成以下形式

![image-20200914213939658](img/image-20200914213939658.png)



### **?.4.3 BaseEntity**

![image-20200914213720797](img/image-20200914213720797.png)





## ?.5配置文件

![image-20200914213258924](img/image-20200914213258924.png)

