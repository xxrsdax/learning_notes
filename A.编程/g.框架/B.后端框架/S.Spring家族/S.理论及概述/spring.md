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

P10







































