# 1.概述

概述



# 2.DDL



参考17语法部分



# 3.增

参考17语法部分



# 4.删

参考17语法部分



# 5.改

参考17语法部分



# 7.查

参考17语法部分



# 8.视图

## 1.1概述

###  1.1什么是视图

视图是虚拟的表，与包含数据的表不一样，视图只包含使用动态检查数据的查询。

视图为虚拟表。

它们包含的不是数据而是根据需要检索数据的查询。视图提供了一种MySQL的SELECT语句层次的封装，可用来简化数据处理以及重新格式化基础数据或保护基础数据。





###  1.2为什么使用视图

a.重用SQL语句

b.简化复杂的SQL操作，在编写查询后，可以方便的重用他而不必知道他的基本查询细节

c.使用表的组成部分而不是整个表

d.保护数据，可以给用户表特定部分的访问权，而不是表全部的访问权

e.更改数据表示格式，视图可以返回与底层表的表示和格式不同的数据





###  1.3 性能问题

因为试图不包含数据，所以每次使用视图时，都必须处理查询执行时所需的任一个检索。

如果你用多个联结和过滤创建了复杂的视图或嵌套了视图，可能回发现性能下降的很厉害，因此，在部署使用了大量视图的应用前，应该进行测试。



###  1.4 视图的规则和限制

1.与表一样，视图必须唯一命名

2.对于可以创建的视图数目没有限制

3.为了创建视图，必须具有足够的访问权限。这些权限通常有数据库管理人员授予

4.视图可以嵌套，即可以利用从其他视图中检索数据的查询来构造一个视图

5.order By  可以用在视图中，但如果从该视图检索数据Select 中也含有 order by ,那么该视图中的order by 将被覆盖

6.视图不能索引，也不能有关联的触发器或默认值

7.视图可以和表一起使用。例如，编写一条连接表和视图的select语句。



##  1.2 操作

### 1.视图创建语句

​	CREATE VIEW  视图名称   AS   查询语句；

### 2.查看创建视图的语句

​	SHOW CREATE VIEW viewName;    

### 3.用DROP删除视图，其语法为 

​	DROP  VIEW  viewName;

### 4.更新视图时

可以先用DROP再用CREATE，也可以直接用CREATE OR REPLACE VIEW.

如果要更新的视图不存在，则第2条更新语句会创建一个试图；

如果要更新的试图存在，则第2条更新语句会替换原有试图；

```
CREATE OR REPLACE VIEW 
视图名称
AS
SELECT column_name(s) FROM table_name WHERE condition
```



注意：

​	不是所有的视图都可以更新，基本上可以说，如果MySQL不能正确地确定被更新的基数据，则不允许更新（包括插入和删除）。

这实际上意味着，如果视图定义中有以下操作，则不能进行视图的更新：

- 分组 （使用GROUP BY 和 having ）
- 联结
- 子查询
- 并
- 聚集函数 （Min()、Count()、Sum()等）
- DISTINCT
- 导出(计算)列



# 9.存储过程

## 9.1 概述

​	MySQL5 添加了对存储过程的支持，因此，本章内容适用于MYSQL5及以后的版本。

​	存储过程简单来说，就是为以后的使用而保存的一条或多条MySQL语句的集合。

​    可将其视为批文件，虽然它们的作用不仅限于批处理。 

​	

### 9.1.1 为什么要使用存储过程

​	1.通过把处理封装在容易使用的单元中，简化复杂的操作。 （简化）

​	2.由于不要求反复建立一系列处理步骤，这保证了数据的完整性。如果所有开发人员和应用程序都使用同一（试验和测试）存储过程，则所使用的代码都是相同的。  （安全）

​	3.简化对变动的管理。如果表名、列名或业务逻辑（或别的内容）有变化，只需要更改存储过程的代码。使用它的人员甚至不需要 知道这些变化。 （高效）



### 9.1.2 缺陷

​	1.存储过程的编写比基本SQL语句复杂，编写存储过程需要更高的技能，更丰富的经验。

​	2.可能不是每个人都有创建 存储过程 的安全访问权限。许多数据库管理员限制存储过程的创建权限，允许用户使用存储过程，但不与允许他们创建过程。



## 9.2 使用

### 9.2.1 执行存储过程 

MySQL执行存储过程的语句为 CALL，CALL接受存储过程的名字以及需要传递给它的任意参数。

```sql
CALL  存储过程名( );
```

存储过程可以显示结果，也可以不显示结果。具体看如何定义的存储过程。



注意：仅试验过 无参数的存储过程，暂未确定带参存储过程



### 9.2.2 创建存储过程 

```
DELIMITER //

CREATE PROCEDURE 存储过称名()
BEGIN
	SELECT avg(prod_price) as priceaverage from products;
	...[其他SQL语句]
END //


DELIMITER ;

```

BEGIN和END语句用来限定存储过称体。

DELIMITER: 用来改变Mysql的结束标志，不加会报错




### 9.2.3 删除存储过程

```sql
drop  procedure if exists 存储过程名;
```



### 9.2.4 带参存储过程

Mysql支持 IN（传递参数给存储过程）， OUT（从存储过程传出）、INOUT ( 对存储过程传入和传出 ) 这三种类型参数



```sql
-- 如果指定存储过程存在就删除
drop PROCEDURE if EXISTS param;

-- 修改sql结束标志
DELIMITER //

-- 创建带参 存储过程  
-- in 传入参数
-- out 传出参数
-- into 传入传出
CREATE PROCEDURE param( IN param bigint , out pl DECIMAL(8,2), out ph DECIMAL(8,2), out pa DECIMAL(8,2) ,out total bigint)
BEGIN
	SELECT Min(one_id) INTO pl FROM one;
	SELECT Max(one_id) INTO ph FROM one;
	SELECT avg(one_id) INTO pa FROM one;
	select sum(one_id)  from one where one_id < param  into total; 
END//

-- 改回结束标志
DELIMITER ;

-- 调用存储过程
CALL param (10,@pl,@ph,@pa,@to);

-- 调用后 可以一直查询 存储过程 传出的结果  直到下一次调用存储过程之前 值都不变
SELECT @pl;

select @ph;

SELECT @pa;

select @to;
```



### 9.2.5 建立智能存储过程

可以在存储过程中定义局部变量  

```sql
create  procedure 存储过程名(参数) comment '备注内容'
BEGIN
	-- 注释 DECLARE 变量名 变量类型   没有默认值
	DECLARE oneVar decimal(8,2);
	-- 注释 DECLARE 变量名 变量类型   有默认值
	DECLARE twoVar decimal(8,2) DEFAULT 6 ;
	
	-- IF THEN END 分支
	IF booleanVar THEN
		-- booleanVar 值为true时 执行
		SELECT * from tablename ;
	END IF;

END;
```



***IF语句 :还支持 ELSEIF  和 ELSE 子句 （前者还使用 THEN子句，后者不使用）***



### 9.2.6 检查存储过程

查询创建存储过程的语句

```sql

show CREATE procedure 存储过程名;
```

查询存储过程 创建详情 

```sql
show procedure status [ like  '存储过程名'  ]
```

[] 中括号的内容 若不存在就是查询全部存储过程的信息









# 10.游标

Mysql游标 仅支持在 存储过程中使用，具体内容参考 《MySQL必知必会》 第24章 使用游标



# 11.触发器

## 11.1 概述

触发器是MySQL响应一下任意语句而自动执行的一条MySQL语句（或位于 BEGIN和END语句之间的一组语句）:

[个人理解：触发器执行的可能是一条语句或是一个存储过程]

- DELETE
- INSERT
- UPDATE

其他sql语句不支持触发器[只有改变表内容的语句才支持触发器]





## 11.2 使用



### 11.2.1 创建触发器

 创建触发器时，需要给出4条信息

- 唯一的触发器名
- 触发器关联的表
- 触发器应该响应的活动（DELETE、INSERT、UPDATE）
- 触发器何时执行 （处理之前 或 处理之后）



注意：在 MySQL 5 中 ，仅要求 触发器名 在每个表中唯一，但在每个数据库中可以不唯一

​          但不建议这样做， 建议定义的触发器名在每个数据库中唯一

```sql
-- 学习时的mysql版本不允许触发器有任何返回值
-- 所以这里定义一个变量 存储 '触发器的返回值'
-- cfname  为触发器名
-- after insert on one 表示 在one表执行插入语句后，执行触发器
-- for EACH row 表示每次插入 都执行触发器
CREATE  TRIGGER cfname AFTER INSERT on one  for EACH ROW SELECT 'one added' into @tag;

-- 未执行插入语句时，触发器未触发  值为 null 
SELECT @tag;

-- 执行 one表的 插入语句  ，触发触发器
insert into one (name) VALUE ("触发器");

-- 执行插入语句后，触发器触发  值为 'one added' 
SELECT @tag;

```

注意:触发器仅支持表  视图不支持（临时表也不支持）

因此每个表最多支持 6 个触发器 （INSERT、UPDATE、DELETE的 BEFORE (之前) 和 AFTER （之后））



如果 BEFOR 触发器失败，则MYSQL将不执行请求的操作，如果BEFOR触发器或语句本身失败，则MYSQL将不执行 AFTER 触发器（如果存在 AFTER触发器）



### 11.2.2 INSERT 触发器

​	

INSERT 触发器在INSERT语句执行之前或之后执行。



- 在INSERT触发器代码内，可引用一个名为NEW的虚拟表，访问被插入的行；
- 在BEFORE INSERT 触发器中，NEW 中的值也可以被更新（允许更改被插入的值）；
- 对于 AUTO_INCREMENT列，NEW 在INSERT 执行之前包含0，在INSERT执行之后包含新的自动生成值。



```mysql
CREATE TRIGGER  neworder AFTER INSERT ON orders for EACH ROW SELECT NEW.order_num;
```



注意：通常 BEFORE 用于数据验证和净化。





### 11.2.3 DELETE 触发器

DELETE 触发器在 DELETE 语句执行之前或之后执行。



- 在DELETE触发器代码内，你可以引用一个名为OLD的虚拟表，访问被删除的行；
- OLD中的值全都是只读的，不能更新。



```mysql
CREATE TRIGGER deleteorder  BEFOR DELETE ON orders 
FOR EACH ROW 
BEGIN 
	INSERT INTO archive_orders (order_num,order_date,cust_id)
	VALUES(OLD.order_num,OLD.order_date,OLD.cust_id);
END;
```



注意：

​	**多语句触发器**，正如所见，触发器deleteorder使用BEGIN和END语句标记触发器体。这在此例子中并不是必需的，不过也没有害处。使用BEGIN END块的好处是触发器能容纳多条SQL语句（在 BEGIN	END块中一条挨着一条）。





### 11.2.4 UPDATE 触发器

UPDAET 触发器在UPDAE语句执行之前或之后执行。

- 在UPDATE触发器代码中，你可以引用一个名为OLD的虚拟表访问以前（UPDATE语句前的值），引用一个名为NEW的虚拟表访问新更新的值；
- 在BEFORE UPDATE触发器中，NEW中的值可能也被更新（允许更改将要用于UPDATE语句中的值）；
- OLD中的值全都是只读的，不能更新。



```mysql
CREATE TRIGGER updatevendor BEFORE UPDATE ON vendors FOR EACH ROW 
SET NEW.vend_state = Upper(NEW.vend_state);
```



### 注意： 其他内容参考 《Mysql必知必会》 P186 第25章



# 12.管理事务处理

## 12.1 概述

```
并非所有引擎都支持事务处理
   MyISAM 和 InnoDB是两种最常使用的引擎。
   前者不支持明确的事务处理管理，而后者支持。
```

事务处理（transaction processing）可以用来维护数据库的完整性，它保证成批的MySQL操作要么完全执行，要么完全不执行。

- 事务（transaction）：指一组SQL语句。
- 回退（rollback）:指撤销指定SQL语句的过程。
- 提交（commit）：指将未存储的SQL语句结果写入数据库表
- 保留点（savepoint）:指事务处理中设置的临时占位符（placeholder）,你可以对它发布回退（与回退整个事务处理不同）。



## 12.2 控制事务处理



### 12.2.1 开始事务

START TRANSACTION;



### 12.2.2 使用ROLLBACK

ROLLBACK;



```
事务处理用来管理 INSERT、UPDATE、DELETE语句，但不能回退 CREATE 和 DROP 操作，事务处理块中可以使用这两条语句，但如果你执行回退，它们不会撤销
```



### 12.2.3 使用COMMIT

COMMIT;





### 12.2.4 使用保留点

更复杂的事务处理可能需要部分提交或回退。

为了支持回退部分事务处理，必须能在事务处理块中合适的位置放置占位符，这样，如果需要回退，可以回退到某个占位符。

这些占位符称为保留点，为了创建占位符可以如下操作



//设置保留点

SAVEPOINT 保留点名称;

//回退到指定保留点

ROLLBACK TO 保留点名称;



```
保留点越多越好
	因为保留点越多，你就可以越灵活的进行回退
释放保留点
	事务结束后，就可以自动释放。也可以用 RELEASE SAVEPOINT 明确地释放保留点。
```





### 12.2.5 更改默认的提交行为

默认的MySQL行为是自动提交所有更改。

可以指定为不自动提交。

SET autocommit = 0;

```
autocommit 标志决定是否自动提交更改。
标志位连接专用：
	autocommit 标志是针对每个连接而不是服务器的。
```







# 13.全球化和本地化

## 13.1 字符集和校对顺序

MySQL需要适用不同的字符集（不同的字母和字符），适应不同的排序和检索数据的方法。

- 字符集 ： 为字母和符号的集合；
- 编码 ：为某个字符集成员的内部表示
- 校对：为规定字符如何比较的指令。



## 13.2 适用字符集和校对顺序

//查看所支持的 字符集完整列表

SHOW CHARACTER SET;



//查看所支持校对的完整列表

SHOW  COLLATION;



//为了确定所用的字符集和校对

SHOW  VARIABLES  LIKE 'character%';

SHOW  VARIABLES  LIKE 'collaction%';



//为了给表指定字符集合校对，可使用带子句的CREATE TABLE 

CREATE  TABLE  mytable(

​	column1  INT,

​	column2  VARCHAR(10)

) DEFAULT CHARACTER  SET hebrew  

 COLLATE  hebrew_general_ci;



这个例子中指定了 CHARACTER SET 和 COLLATE两者。一般，MySQL如下确定使什么样的字符集和校对。





# 14.安全管理

## 14.1 访问控制

用户不能对过多的数据具有过多的访问权。



## 14.2  管理用户

MySQL用户账号和信息存储在名为mysql的MySQL数据库中。一般不需要直接访问mysql数据库和表，但有时需要直接访问 ( 在需要获得所有用户账号列表时 )。



### 14.2.1 创建用户账号

//创建一个新的用户账号

CREATE USER 用户名  IDENTIFIED  BY '密码'



//重命名创键的账户

RENAME USER  旧用户名 To 新用户名



### 14.2.2 删除用户账号

//删除一个用户账号（以及相关的去权限）

DROP  USER 用户名;



### 14.2.3 设置访问权限

新创建的用户账号没有访问权限。只能登录MySQL。



//查看用户账户的权限

SHOW GRANTS FOR 用户名;



//设置权限

GRANT   SELECT   ON   库名.*  To 用户名;

```
允许用户在 库名.*(表示指定库的所有表)上使用指定权限（SELECT）。
GEANT要求设置权限时，至少给出一下信息。
1.要授予的权限
2.被授予访问权限的数据库或表
3.用户名	
```



//撤销权限

REVOKE  SELECT  ON 库名.*  FROM 用户名;

```
撤销指定用户在指定库或表中的指定权限	
```



GRANT 和 REVOKE可以在一下几个层次上控制访问权限：

- 整个服务器，使用  GRANT ALL 和 REVOKE ALL;
- 整个数据库 ，使用 ON  database.*;
- 特定的表，使用  ON  database.table;
- 特定的列；
- 特定的存储过程；



![image-20200831080056208](img/image-20200831080056208.png)

![image-20200831080205945](img/image-20200831080205945.png)

```
简化多次授权
	可通过列出各权限并用逗号分隔，将多条GRANT语句串在一起。
	GRANT SELECT,INSERT ON  库名.* To 用户名;	
```



### 14.2.4 更改口令 

//更改密码

SET PASSWORD  FOR 用户名= Password('新密码');

```
SET PASSWORD 更新用户密码；
Password()  加密函数，密码必须传递到该函数进行加密。
```



//在不指定名称时，表示更新当前登录用户的口令

SET  PASSWORD = Password('新密码')





# 15. 数据库维护

## 15.1 备份数据

像所有数据一样，MySQL的数据也必须经常备份。由于MySQL数据库是基于磁盘的文件，普通的备份系统和例程就能备份MySQL的数据。但是，由于这写文件总是处于打开和使用状态，普通的文件副本备份不一定总是有效。

- 使用命令行实用程序mysqldump转储所有数据内容到某个外部文件。在进行常规备份前这个实用程序应该正常运行，以便能正确地备份转储文件。
- 可用命令行使用程序mysqlhotcopy从一个数据库复制所有数据。
- 可以使用MySQL的BACKUP TABLE 或 SELECT INTO OUTFILE 转储所有数据到某个外部文件。这两条语句都接收将要创建的系统文件名，此系统文件必须不存在，否则会出错。数据可以用RESTORE  TABLE 来复原。

```
首先刷新未写数据
	为了保证所有数据被写到磁盘（包括索引数据），可能需要在进行备份前使用FLUSh TABLES 语句。
```



## 15.2  进行数据库维护

MySQL提供了一系列的语句，可以（应该）用来保证数据库正确和正常的运行。



1、检查 表键是否正确

ANALYZE  TABLE 表名;



2、对表进行检查

CHECK  TABLE  表名1,表名2;

```
CHECK TABLE 用来针对许多问题对表进行检查，在MyISAM表上还对索引进行检查。
CHANGED  检查自最后一次检查以来改动过的表。
EXTENDED 执行最彻底的检查
FAST 只见检查未正确关闭的表
MEDIUM 检查所有被删除的链表并进行键检验
QUICK 只进行快速扫描。 
```



3、如果MyISAM表访问产生不正确和不一致的结果，可能需要用REPAIR TABLE来修复相应的表。这条语句不应该经常使用，如果需要经常使用，可能会有更大的问题要解决。



4、如果从一个表中删除大量数据，应该使用OPTIMIZE TABLE来收回所用的空间，从而优化表的性能。

 



## 15.3 诊断启动问题

服务器启动问题通常在对MySQL配置或服务器本身进行更改时出现。MySQL在这个问题发送时报告错误，但由于多数MySQL服务器是做为系统进程或服务自动启动的，这些消息可能看不到。



在排除系统启动问题，首先尽量手动启动服务器。

下面是几个重要的mysql命令行选项：

-  --help 显示帮组------------ 一个选项列表；
-  --safe-mode装载减去某些最佳配置的服务器；
-  --verbose 显示全文本消息（为获得更详细的帮组消息与 --help 联合使用）；
-  --version 显示版本信息然后退出。



## 15.4 查看日志文件

- 错误日志。包含启动和关闭问题以及任意关键错误的细节。名为 hostname.err,位于 data目录中。

    ​                  可以用  --log--error 命令行选项更改。

- 查询日志。记录所有MySQL活动， 名为 hostname.log,位于data目录中。此名字可以用 --log命令行选项更改。

- 二进制日志。它记录更新过数据的所有语句。名为 hostname-bin.位于 data目录内。

- 缓查询日志。记录执行缓慢的任何查询。名为 hostname-slow.log，位于data目录中。



使用日志时，可用 FLUSH  LOGS 语句来刷新和重新开始所有日志文件。







# 16.改善性能 

## 16.1 改善性能

- 一段时间后，你可能需要调整内存分配、缓冲区大小等。
- MySQL是一个多用户多线程的DBMS,如果这些任务中的某一个执行缓慢，则所有请求都会执行缓慢。
    - 如果你遇到显著的性能不良，可使用 SHOW   PROCESSLIST 显示所有活动进程（以及它们的线程ID和执行时间）。你可以用KILL命令终结某个特定的进程（使用这个命令需要作为管理员登录）
- 应该验证 联结、并、子查询等，找出最佳的方法。
- 使用EXPLAIN语句让MySQL解释它将如何执行一条SELECT语句。

![image-20200901224505968](img/image-20200901224505968.png)



简单的说 注意查询的内容多少  注意索引的创建和删除





# 17.语法

![image-20200901225129055](img/image-20200901225129055.png)



## 1.更新表

ALTER TABLE 表名 (

​	ADD  列名  数据类型  [NULL| NOT NULL]   [约束]，

​	CHANG  column  columns     数据类型  [NULL| NOT NULL]   [约束]，

​	DROP   column,

​	...

);



## 2.创建索引

CREATE INDEX  索引名

ON  表名 ( 列表[ASC|DESC] , ... );



## 3.创建存储过程

CREATE  PROCEDURE  存储过程名 ( [parameters] )

BEGIN 

...

END;



## 4.创建表

CREATE TABLE 表名(

​	列名  属性名   [null|not null]  [约束]，

​	...

);



## 5.创建用户

CREATE  USER 用户名 [@hostname]

[IDENTIFIED  BY  [PASSWORD]  'password'];





## 6.创建视图

CREATE   [OR  REPLACE] VIEW 视图名称

AS 

SELECT  ...;



## 7.删除

DROP   DATABASE|INDEX|PROCEDURE|TABLE|TRIGGER|USER|VIEW    名称；



## 8.INSERT

INSERT  INTO 表名 (字段名...)   VALUES (值...)



## 9.INSERT SELECT

INSERT  INTO 表名  [(字段名...)]

SELECT 字段名... FROM  表名,...

[WHERE  ...] 



## 10.SELECT

SELECT 字段名  ...

FROM 表名

[WHERE  ...]

[UNION ...]

[GROUP BY ...]

[HAVING ...]

[ORDER BY ...]



## 11.UPDATE

UPDATE 表名

SET 字段名=值  ...

[WHERE ...]



## 12.DELETE

DELETE FROM 表名

[WHERE  ...]





# 18.数据类型

![image-20200901231316099](img/image-20200901231316099.png)





![image-20200901231326908](img/image-20200901231326908.png)

![image-20200901231401474](img/image-20200901231401474.png)

