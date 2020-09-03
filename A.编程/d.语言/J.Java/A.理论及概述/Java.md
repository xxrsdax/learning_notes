# Java基础

# 1.lambda表达式

## 1.1 概述 

lamba表达式是一个可传递的代码块，可以在以后执行一次或多次。

在没有lamba表达式时，通常是将一些代码块封装在类中，然后以这类的对象做为参数，传递到其他对象中，供其调用。例如Arrays.sort(待比较值，比较器)；               //这里的比较器就是  Comparator接口对象（compare()）





## 1.2语法



### 1.2.1 格式一

(参数...)  -> {  ...   };



案例一：

​	//String strArray = []

Arrays.sort(strArray,(first,second)->first.length-second.length );



案例二：

Timer time = new  Time(1000, event-> System.out.println("The time is "+new  Date));

time.start();



注意：如果一个lambda表达式只在某些分支返回一个值，而在另外一些分支不返回值，这是不合法的。

​		例如 (int x) -> { if(x>= 0 ) return 1; }





## 1.3 函数式接口

对于只有一个抽象方法的接口，需要这种接口的对象时，就可以提供一个 lambda表达式。

这种接口称为函数式接口（ functional  interface ）。

```
注意：
	JavaSE 8中，接口可以声明非抽象方法。	
```



注意： 不能把lambda表达式赋给类型为Object的变量，Object不是一个函数式接口。



Java API 在java.util.function包中定义了很多非常通用的函数式接口。

```Java
其中一个接口BigFunction<T,U,R> ,描述了类型为T和U而且返回类型为R的函数。
例如： BiFunction<String,String,Integer> comp = (first,second)->first.length-second.length
    
java.util.function 包中有一个尤其有用的接口 Predicate
例如：
    public interface Predicate<T>{
    	boolean test(T);
    
    	//Additional  default and static methods
	}
ArrayList类有一个removeIf方法，它的参数就是一个Predicate。
    这个接口专门用来传递lambda表达式。
    例如：下面的语句将从一个数据组列表删除所有null值：
    	list.removeIf(e->e == null)

```





## 1.4 方法引用

可以通过某种语法调用已被实现的方法。

要用 :: 操作符分隔方法名与对象或类名。主要有3中情况：

- object::instanceMethod
- Class::staticMethod
- Class::instanceMethod



前2种情况中，方法引用等价于提供方法参数的lambda表达式。

例如：

​	System.out::println      x->System.out.println(x)

​	Math::pow    				(x,y)->Math.pow(x,y)



第3种情况，第一个参数会成为方法的目标，

String::compareToIgnoreCase      （x,y）-> x.compareToIgnoreCase(y)



可以使用

this::instanceMethod

super::instanceMethod





例如：

//在idea中写一下语句会报错

Timer t = new Timer( 1000, System.out::println);

//等价于一下  语句

Timer t = new Timer( 1000, event -> System.out.println(event));



表达式  System::println 是一个方法引用 （method reference） .



例如：

//对字符串排序，而不考虑字母的大小写。

//String[] strings = []    compareToIgnoreCase 是String内部实现的一个方法

Arrays.sort( strings,  String::compareToIgnoreCase);

//等价于以下 语句

Arrays.sort( strings, (x,y)-> x.compareToIgnoreCase(y));



注意：

​	如果有多个重载方法，编译器就会尝试从上下文中找出你指的哪一个方法。

​	例如：

​		Math.max 方法有两个版本，一个用于整数，另一个用于double值。选择哪一个版本取决于 Math::max 转

​		换为哪个函数式接口的方法参数。 

​	类似于lambda表达式，方法引用不能独立存在，总是会转换为函数式接口的实例。



疑惑：意识是，所有lambda表达式和方法引用，最后都会转换成函数式接口吗？

待测试：编写lambda表达式，和方法引用  然后反编辑看结果  

结果：class文件中没有任何变化



简单的说，可以认为是  方法调用的简化形式，参数自行推断，



## 1.5 构造器引用

构造器引用和方法引用类似，只不过方法名为new。



例如：

​	Person::new    //具体调用Person的那个构造器，取决于上下文环境。

​	

例如：

ArrayList\<String\>  names = ...;

Stream\<Person\> stream = names.stream().map(Person::new);

List\<Person\>    people = stream.collect(Collectors.toList());



//如果Person有多个构造器，会从上下文推断选择那个，这里会选择有一个String参数的构造器。

//因为它从上下文推导出这是在对一个字符串调用构造器。





## 1.6  变量作用域

你可能希望能够在lambda表达式中访问外围方法或类中的变量。

1.在lambda表达式中，只能引用值不会改变的变量。（lambda表达式中捕获的变量必须实际上是最终变量（effectively  final））。

例如：

​	String 本身就是不会改变的变量  所以可以直接引用。

​	可以改变的变量必须定义为 final类型



2.lambda表达式不能有同名的局部变量。

​	所以lambda表达式的参数名，和lambda内定义的变量  不能和外部变量重名。



3.在一个lambda表达式中使用this关键字时，是指创建这个lambda表达式的方法的this参数。





## 1.7处理lambda表达式

使用lambda表达式的重点是延迟执行（deferred execution）.

之所以希望以后再执行代码，这有很多原因。

- 在一次单独的线程中运行代码
- 多次运行代码
- 在算法的适当位置运行代码
- 发生某种情况时执行代码。
- 只在必要时才运行代码。



常用函数式接口

| 函数式接口          | 参数类型 | 返回类型 | 抽象方法名 | 描述                         | 其他方法                 |
| ------------------- | -------- | -------- | ---------- | ---------------------------- | ------------------------ |
| Runnable            | 无       | void     | run        | 作为无参数或返回值的动作运行 |                          |
| Supplier\<T\>       | 无       | T        | get        | 提供一个T类型的值            |                          |
| Consumer\<T\>       | T        | void     | accept     | 处理一个T类型的值            | andThen                  |
| BiConsumer<T,U>     | T,U      | void     | accept     | 处理T和U类型的值             | andThen                  |
| Function<T,R>       | T        | R        | apply      | 有一个T类型参数的函数        | compose,andThen,identity |
| BiFunction\<T,U,R\> | T,U      | R        | apply      | 有T和U类型参数的函数         | andThen                  |
| UnaryOperator\<T\>  | T        | T        | apply      | 类型T上的一元操作符          | compose,andThen,identity |
| BinaryOperator\<T\> | T,T      | T        | apply      | 类型T上的二元操作符          | andThen，maxBy,minBy     |
| Predicate\<T\>      | T        | boolean  | test       | 布尔值函数                   | and，or,negate,isEqual   |
| BiPredicate<T,U>    | T,U      | boolean  | test       | 有两个参数的布尔值函数       | and,or,negate            |



基本类型的函数式接口

| 函数式接口           | 参数类型 | 返回类型 | 抽象方法名   |
| -------------------- | -------- | -------- | ------------ |
| BooelanSupplier      | none     | boolean  | getAsBoolean |
| PSupplier            | none     | p        | getAsP       |
| PConsumer            | p        | void     | accept       |
| ObjPConsumer\<T\>    | T,p      | void     | accept       |
| PFunction\<T\>       | p        | T        | apply        |
| PToQFunction         | p        | q        | applyAsQ     |
| ToPFunction\<T\>     | T        | p        | applyAsP     |
| ToPBiFunction\<T,U\> | T,U      | p        | applyAsP     |
| PUnaryOperator       | p        | p        | applyAsP     |
| PBinaryOperator      | p,p      | p        | applyAsP     |
| PPredicate           | p        | boolean  | test         |

注意：

​	p、q 为 int,long,double   ;

​    P、Q 为 Int、Long、Double;



注意：若果设计自己的函数式接口，记得加上@FunctionalInterface注解（不是必须的）  







TODO  待续





# 2.JavaSE8的流库



# 3.类加载器



# 4.并发

# 