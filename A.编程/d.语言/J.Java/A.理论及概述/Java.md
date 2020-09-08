# Java基础

# 1.lambda表达式

## 1.1 概述 

lamba表达式是一个可传递的代码块，可以在以后执行一次或多次。

在没有lamba表达式时，通常是将一些代码块封装在类中，然后以这类的对象做为参数，传递到其他对象中，供其调用。例如Arrays.sort(待比较值，比较器)；               //这里的比较器就是  Comparator接口对象（compare()）





## 1.2语法



### 1.2.1 格式一

(参数...)  -> {  ...   };



案例一：

	//String strArray = []

Arrays.sort(strArray,(first,second)->first.length-second.length );



案例二：

Timer time = new  Time(1000, event-> System.out.println("The time is "+new  Date));

time.start();



注意：如果一个lambda表达式只在某些分支返回一个值，而在另外一些分支不返回值，这是不合法的。

		例如 (int x) -> { if(x>= 0 ) return 1; }





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

	System.out::println      x->System.out.println(x)
	
	Math::pow    				(x,y)->Math.pow(x,y)



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

	如果有多个重载方法，编译器就会尝试从上下文中找出你指的哪一个方法。
	
	例如：
	
		Math.max 方法有两个版本，一个用于整数，另一个用于double值。选择哪一个版本取决于 Math::max 转
	
		换为哪个函数式接口的方法参数。 
	
	类似于lambda表达式，方法引用不能独立存在，总是会转换为函数式接口的实例。



疑惑：意识是，所有lambda表达式和方法引用，最后都会转换成函数式接口吗？

待测试：编写lambda表达式，和方法引用  然后反编辑看结果  

结果：class文件中没有任何变化



简单的说，可以认为是  方法调用的简化形式，参数自行推断，



## 1.5 构造器引用

构造器引用和方法引用类似，只不过方法名为new。



例如：

	Person::new    //具体调用Person的那个构造器，取决于上下文环境。


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

	String 本身就是不会改变的变量  所以可以直接引用。
	
	可以改变的变量必须定义为 final类型



2.lambda表达式不能有同名的局部变量。

	所以lambda表达式的参数名，和lambda内定义的变量  不能和外部变量重名。



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

	p、q 为 int,long,double   ;
	
	P、Q 为 Int、Long、Double;



注意：若果设计自己的函数式接口，记得加上@FunctionalInterface注解（不是必须的）。  



# 2.JavaSE8的流库

## 2.1 从迭代到流的操作

在处理集合时，我们通常会迭代遍历它的元素，并在每个元素上执行某项操作。

流的版本比循环版本要更易于阅读，因为我们不必扫描整个代码去查找过滤和计数操作，方法名就可以直接告诉我们其代码意欲何为。



流与集合的差异：

- 流不存储其元素。这些元素可能存储在底层的集合中，或者按需生成。
- 流的操作不会修改其数据源。例如：filter方法不会从新的流中移除元素，而是会生成一个新的流，其中不包含被过滤掉的元素。
- 流的操作是尽可能惰性执行的。这意味着直至需要结果时，操作才会执行。



操作流时的典型流程:

- 创建一个流
- 指定将初始流转换为其他流的中间操作，可能包含多个步骤。
- 应用终止操作，从而产生结果。这个操作会强制执行之前的惰性操作。从此这个流再也不能用了。







## 2.2流的创建

可以使用多种不同的API创建流。

具体内容参考 2.? API



## 2.3 filter、map和flatMap方法

### 2.3.1 filter

流的转换会产生一个新的流，它的元素派生自另一个流中的元素。我们已经看到了filter转换会产生一个流，它的元素与某种条件相匹配。

例如：

	List\<String\> wordList = ...;
	
	Stream\<String\> longWords = wordList.stream().filter(w -> w.length() > 12);

**filter的引元是Predicate\<T\>,即从T到boolean的函数。**



### 2.3.2 map

通常，我们想要按照某种方式来转换流中的值，此时，可以使用map方法并传递执行该转换的函数。

例如：

	我们可以像下面这样将所有单词都转换为小写：
	
	Stream\<String\>  lowercaseWords = words.stream().map(String::toLowerCase);



### 2.3.3 flatMap

假设我们在一个字符串流上映射letters方法：

Stream\<Stream\<String\>\> result = word.stream().map( w -> letters(w));

```
public static Stream<String> letters(String s){
	
	List<String> result = new ArrayList<>();
	
	for(int i =0; i < s.length();i++) {
		result.add(s.substring(i,i+1));
	}
	
	return result.stream();
	
}
```

那么上述操作就会得到包含流的流，就像 [...["y","o","u","r"],["b","o'',"a","t"],...]。为了将其摊平为字母流

[..."y","o","u","r","b","o'',"a","t",...] ，可以使用flatMap方法而不是map方法。

Stream\<String\>  flatResult = words.stream().flatMap(w->letters(w));



具体操作参考 API 操作流。



## 2.4 抽取子流和连接流

### 2.4.1抽取子流

调用stream.limit(n)会返回一个新的流，它的n个元素之后结束（如果原来的流更短，那么就会在流结束时结束）。这个方法对于裁剪无限流的尺寸会显得特别有用。

例如：

	Stream\<Double\>  randoms  =  Stream.generate(Math::random).limit(100);



调用stream.skip(n)正好相反：它会丢弃前n个元素。这个方法在将文本分隔为单词时会显得很方便，因为按照skip方法的工作方式，第一个元素是没什么用的空字符串。我们可以通过调用skip来跳过它：

例如：

	Stream\<String\>  words =  Stream.of(contents.split("\\\PL+")).skip(1);



### 2.4.2连接流

我们可以用Stream类的静态的concat方法将两个流连接起来：

例如:

	Stream\<String\> combined = Stream.concat( letters("Hello") , letters("World"));

当然，第一个流不应该是无限的，否则第二个流永远都不会得到处理的机会。	



## 2.5其他流的转换

### 2.5.1去重

distinct方法会返回一个流，它的元素是从原有流中产生的，即原来的元素按照同样的顺序剔除重复元素后产生的。这个流显示能够记住它已经看到过的元素。

例如：

	Stream\<String\>  uniqueWords  =  Stream.of("merrily","merrily","gently").distinct();



### 2.5.2排序

对于排序，有多种sorted方法的变体可用。其中一种用于操作Comparable元素的流，而另一种可以接受一个Comparator。

例如：

	Stream\<String\>  longestFirst  =  words.stream().sorted(Comparator.comparing(String::length).reversed());



### 2.5.3 peek

peek方法会产生另一个流，它的元素与原来流中的元素相同，但是在每次获取一个元素时，都会调用一个函数。

Object[]  powers  =  

		Steram.iterate(1.0, p -> p * 2).peek( e -> System.out.println("Fetching"+e)).limit(20).toArray()

当实际访问一个元素时，就会打印出来一条消息。通过这种方式，你可以验证 iterate 返回的无限流是被惰性处理的。

	对于调式，你可以让peek调用一个你设置了断点的方法。



## 2.6简单约简

从流数据中获得答案，本节所讨论的方法被称为约简。约简是一种终结操作（terminal operation）,它们会将流约简为可以在程序中使用的非流值。



### 2.6.1 返回数量

count统计数量

max最大值

min最小值

例如：

	Optional\<String\>   largest = words.max(String::compareToIgnoreCase);
	
	System.out.println("largest"+largest.orElse(""));



### 2.6.2匹配

findFirst 返回的是非空集合中的第一个值。通常与filter组合使用。

例如：

	Optional\<String\>  startsWithQ  = words.filter( s -> s.startsWith("Q")).findFirst();



findAny 匹配任意的匹配。

例如：

	Optional\<String\>  startsWithQ  = words.parallel().filter( s -> s.startsWith("Q")).findAny ();



anyMatch 判断是否存在匹配。

例如：

	boolean  aWordStartsWithQ = words.parallel().anyMatch ( s -> s.startsWith("Q"));



allMatch 判断是否全部匹配。

例如：

	boolean  aWordStartsWithQ = words.parallel().allMatch( s -> s.startsWith("Q"));



noneMatch 判断是否全不部匹配。

例如：

	boolean  aWordStartsWithQ = words.parallel().noneMatch ( s -> s.startsWith("Q"));





## 2.7Optional类型

Optional\<T\> 对象是一种包装器对象，要么包装了类型T的对象。要么没有包装任何对象。

对于第一种情况，我们称这种值为存在的。

Optional\<T\> 类型被当作一种更安全的方式，用来替代类型T的引用，这种引用要么引用某个对象，要么为null。

但是，它只有在正确使用的情况下才会更安全。



### 2.7.1使用Optional值

//orElse 使用某种默认值

String  result  =  optionalString.orElse("");



//orElseGet 计算默认值

String  result = optionalString.orElseGet(()->Locale.getDefault().getDisplayName()); 



//orElseThrow 或者可以在没有任何值时抛出异常

String  result  = optionalString.orElseThrow(IllegalStateException::new);



//ifPresent  方法会接收一个函数，如果该可选值存在，那么它会被传递给该函数。否则，不会发生任何事情。

optionalValue.ifPresent(results::add)

注意：调用 ifPresent 不会有任何返回值，如果想处理函数结果，应该使用map。



//map  方法会接收一个函数，如果该可选值存在，那么它会被传递给该函数。否则，不会发生任何事情。且具有返回值

Optional\<Boolean\>   added = optionalValue.map(results::add)

注意：在optionalValue存在的情況下包装在Optional中的true或者false，以及在optionalValue不存在的情况下的空Optional。





### 2.7.2 不适合使用Optional值的方式

没有正确地使用Optional的值，那么相比较以往的得到“某物或null”的方法，你并没有得到任何好处。



### 2.7.3创建Optional

前文中都是描述，使用其他类的方法创建Optional对象，Optional类自身也提供了许多方法用于创建Optional对象。

- Optional.of(result)
- Optional.empty()
- Optional.ofNullable(result)   
    - ofNullable在result不为空时调用of,为空时调用 empty





### 2.7.4用flatMap来构建Optional值的函数

假设你有一个可以产生Optional\<T>  对象的方法f,并且目标类型T具有一个可以产生Optional\<U>对象的方法g。

如果他们是普通的方法，那么你可以通过 s.f().g()来将它们组合起来。但是这种组合没法工作，因为 s.f() 的类型为Optional\<T> ,而不是T.

因此，需要调用：

​	Optional\<U> result  =  s.f().flatMap(T::g);



P<15> 具体内容看此页 《Java核心技术 卷II》



## 2.8 收集结果-collection

当处理完流之后，通常会 **遍历查看元素** 或 **将结果收集到数据结构**中

- 调用iteractor方法
- 调用forEach方法
    - stream.forEach(System.out::println) 
    - 在并行流上，forEach会任意顺序遍历元素
- 调用forEachOrdered方法
    - 在并行流上，会按照流中的顺序遍历元素，但会丧失并行流的优势。
- 调用toArray
    - Object[] result = stream.toArray()
    - String[] result = stream.toArray(String[]::new)
    - 向toArray中传递指定类型的数组构造器，就可以获得指定类型的数组，否则返回 Object类型数组
- 调用Collect
    - 它会接收一个Collector接口的实例，Collectors类提供了大量用于生成Collector的工厂方法。
    - List\<String> list = stream.collect(Collectors.toList());
    - Set\<String> list = stream.collect(Collectors.toSet());
        - 转化为list或set
    - TreeSet\<String> tree = stream.collect(Collectors.toCollection(TreeSet::new))
        - 转换成指定类型
    - String result  = stream.collect(Collectors.joining());
    - String result  = stream.collect(Collectors.joining(","));
        - 拼接流中所有字符串（可以指定分隔符）
    - IntSummaryStatistics summary = stream.collect(Collectors.summarizingInt(String::length));
        - double  ave = summary.getAverage();
        - double  max= summary.getMax();



## 2.9 收集到映射表-Map

假设我们有一个Stream\<Person\>，并且想要将其元素收集到一个映射表中，这样后续就可以通过他们的ID来查找人员了，

Collectors.toMap() 方法，可以产生映射表。

- Map\<Integer,Person>	idToName = people.collect(Collectors.toMap(Person::getId,Person::getName));
    - 此处调用 toMap方法，将Person的id转换为键，Person的name转化为值
- Map\<Integer,Person>	idToPerson = people.collect(Collectors.toMap(Person::getId,Function.identity()));
    - 第二个函数可以使用Function.identity()  这个样 值保存的内容就是Person的自身
- Stream\<Locale>  locales  = Stream.of(Locale.getAvailableLocales());
- Map\<String,String> languageNames = locales.collect(Collectors.toMap(Locale::getDisplayLanguage, l ->  l.getDisplayLanguage(l) , (existingValue,newValue) -> existingValue ));
    - 多个元素可能会存在相同的键，那么就会存在冲突，收集器将会抛出一个IllegalStateException对象。可以通过提供第3个函数引元来覆盖这中行为，该函数会针对给定的已有值和新值来解决冲突并确定键对应的值。
- Map\<Integer,Person> idToPerson = people.collect(Collectors.toMap(Person::getId,Function.identity(),(existingValue,newValue)->{throw new IllegalStateException();},TreeMap::new));
    - 如果想要获得指定类型的Map，需要将指定map的构造器做为参数传入。





## 2.10 群组和分区

可以使用分组函数，对流中元素进行分组。

- Map\<String,List\<Locale>> map  = locales.collect(Collectors.groupingBy(Locale::getCountry));

    - 根据指定内容进行分组，此处根据 getCountry值做键，将相同内容存到list

- Map\<Boolean,List\<Locale>>  englishAndOtherLocales  = locales.collect(

    ​    Collectors.partitiongBy( l -> l.getLanguage().equals("en"))

    );

    - 当分类函数是断言函数时(即返回Boolean值的函数)，可以使用 partitioningBy 比较高效。

    

    

























## 2.? API

### 2.?.1 创建流

**java.util.stream.Stream 8**

- static  \<T\>  Stream\<T\>  of(T...  values)                         产生一个元素为给定值的流。
- static  \<T\>  Stream\<T\>  empty()                                   产生一个不包含任何元素的流。
- static  \<T\>  Stream\<T\>  generate(Supplier\<T\>  s)  产生一个无限流，它的值是通过反复调用函数s而构建的。
- static  \<T\>  Stream\<T\>  iterate(T seed, UnaryOperate\<T\>  f) 产生一个无限流，它的元素包含种子、在种子上调用f产生的值、在前一个元素上调用f产生的值，等等。



**java.util.Arrays 1.2**

- static \<T\>  Stream\<T\>  stream(T[]  array,int  startInclusive,int  endExclusive) 
    - 产生一个流，它的元素是由数组中指定范围内的元素构成的。



**java.util.regex.Pattern 1.4**

- Stream\<String\> splitAsStream (CharSequence  input)
    - 产生一个流，它的元素是输入中由该模式界定的部分。



**java.nio.file.Files 7**

- static  Stream\<String\>  lines(Path path) 
- static  Stream\<String\>  lines(Path path, Charset cs)
    - 产生一个流，它的元素是指定文件中的行，该文件的字符集为UTF-8，或者为指定的字符集。



**java.util.function.Supplier\<T\>**

- T  get()    提供一个值。



**java.util.Collection\<E\>  1.2**

- default  Stream\<E\>  stream()    产生当前集合中所有元素的顺序流。
- default  Stream\<E\>  parallelStream()  产生当前集合中所有元素的并行流。





### 2.?.2 操作流

**java.util.stram.Stream<T>  8**

- Stream\<T\>  filter (Predicate<? super T> p)   

    -  产生一个流，其中包含当前流中满足p的所有元素。

- long  count()      

    - 产生当前流中元素的数量。这是一个终止操作。

- \<R\>   Stream\<R\>  map(Function\<?  super  T, ?  extends R\> mapper)

    - 产生一个流，它包含将mapper应用于当前流中所有元素所产生的结果

- \<R\>   Stream\<R\>  flatMap(Function<?  super  T, ?  extends Stream\<? extends R\>>  mapper)

    - 产生一个流，它是通过将mapper应用于当前流中所有元素所产生的结果连接到一起而获得的。（注意，这里的每个结果都是一个流）。

- Stream\<T\>   limit( long maxSize);

    - 产生一个流，其中包含了当前流中最初的maxSize的元素。

- Stream\<T\>    skip(long  n)

    - 产生一个流，它的元素是当前流中除了前n个元素之外的所有元素。

- static\<T\>  Stream\<T\>  concat(Stream\<? extends  T\>  a, Stream\<?  extends  T\>  b)

    - 产生一个流，它的元素是a的元素后面跟着b的元素。

- Stream\<T\>   distinct()

    - 产生一个流，包含当前流中所有不同的元素。

- Stream\<T\> sorted()

- Stream\<T\> sorted(Comparator\<?  super  T\>  comparator)

    - 产生一个流，它的元素是当前流中的所有元素按照顺序排序。第一个方法要求元素是实现了Comparable的类的实例

- Stream\<T\> peek (Consumer\<?  super T \> action)

    - 产生一个流，它与当前流中的元素相同，在获取其中每个元素时，会将其传递给action。

    

    

### 2.?.3 简约

**java.util.stream.Stream 8**

- Optional\<T\>   max(Comparator\<?  super T \> comparator)
- Optional\<T\>   min(Comparator\<?  super T \> comparator)
    - 分别产生这个流的最大元素和最小元素，使用由给定比较器定义的排序规则，如果这个流为空，会产生一个空的Optional对象。这些操作都是终结操作。
- Optional\<T\>   findFirst()
- Optional\<T\>   findAny()
    - 分别产生这个流的第一个和任意一个元素，如果这个流为空，会产生一个空的Optional对象。这些操作都是终结操作。
- boolean  anyMatch( Predicate\< ?  super T\>  predicate)
- boolean  allMatch( Predicate\< ?  super T\>  predicate)
- boolean  noneMatch( Predicate\< ?  super T\>  predicate)
    - 分别在这个流中任意元素、所有元素和没有任何元素匹配给定断言时返回true。这些操作都是终结操作。



### 2.?.4 Optional 

#### **java.until.Optional 8**

- T  get()
    - 产生这个Optional的值，或者在该Optional为空时，抛出一个NoSuchElementException对象

- T  orElse(T  other)
    - 产生这个Optional的值，或者在该Optional为空时，产生other。
- T  orElseGet(Supplier\<?  extends  T\>  other)
    - 产生这个Optional的值，或者在该Optional为空时，产生调用other的结果。
- \<X  extends  Throwable\>  T  orElseThrow(Supplier\<?  extends X> exceptionSupplier)
    - 产生这个Optional的值，或者在该Optional为空时，抛出调用exceptionSupplier的结果。
- void ifPresent(Consumber\<?  supper  T\>  consumer)
    - 如果该Optional不为空，那么就将它的值传给consumer
- \<U\>  Optional\<U\>  map(Function\<? super T,? extends U\>  mapper)
    - 产生将该Optional的值传递给mapper后的结果，只要这个Optional不为空且结果不为null，否则产生一个空的Optional
- boolean  isPresent()
    - 如果该Optional不为空，则返回true。





#### **java.util.Optional 8** (创建)

- static\<T\>  Optional\<T\>   of(T value)
- static\<T\>  Optional\<T\>    ofNullable(T value)
    - 产生一个具有给定值的Optional。如果value为null,那么第一方法会抛出一个NullPointerException对象，而第二个方法会产生一个空Optional。
- static\<T\>  Optional\<T\>    empty()
    - 产生一个空的Optional。



#### **java.util.Optional 8** 

- \<U\>  Optional\<U\>   flatMap(Function\<? super T,Optional\<U\>\>  mapper )
    - 产生将mapper应用于当前的Optional值所产生的结果，或者在当前Optional为空时，返回一个空的Optional







### 2.?.5 收集结果-Collection

**java.util.stream.BaseStream 8**

- Iterator\<T>	iteator()
    - 产生一个用获取当前流中各个元素的迭代器。这是一种终结操作。



**java.util.stream.Collectores 8**

- static  \<T>  Collector\<T,?,List\<T>>	toList()

- static  \<T>  Collector\<T,?,Set\<T>>	 toSet()

    - 产生一个将元素收集到列表或集中的收集器。

- static  \<T,C  extends  Collection\<T>>  Collector\<T,?,C>	 toCollection(Supplier\<C>  collectionFactory)

    - 产生一个将元素收集到任意集合中的收集器，可以传递一个诸如 TreeSet::new的构造器引用。

- static  Collector\<CharSequence , ? , String>   joining()

- static  Collector\<CharSequence , ? , String>   joining(CharSequence  delimiter)

- static  Collector\<CharSequence , ? , String>   joining(CharSequence  delimiter,CharSequence prefix,CharSequence suffix)

    - 产生一个字符串的收集器。分隔符会置于字符串之间，而第一个字符串之前可以有前缀，最后一个字符串之后可以有后缀。如果没有指定那么它们都为空。	

- static \<T>  Collector\<T, ? , IntSummaryStatistics>   summarizingInt(ToIntFunction\<? super T>  mapper)

- static \<T>  Collector\<T, ? , LongSummaryStatistics>   summarizingLong(ToLongFunction\<? super T>  mapper)

- static \<T>  Collector\<T, ? , DoubleSummaryStatistics>   summarizingDouble(ToDoubleFunction\<? super T>  mapper)

    - 产生能够生产 （Int|Long|Double） SummaryStatistics对象的收集器，通过它可以获得将mapper应用于每个元素后所产生的结果的个数、总和、平均值、最大值和最小值。

    

    **IntSummaryStatistics**

    **LongSummaryStatistics**

    **DoubleSummaryStatistics**

    - long  getCount()
        - 产生汇总后的元素的个数
    - (int|long|double)   getSum()
    - double  getAverage()
        - 产生汇总后的元素的总和或平均值，或者在没有任何元素时返回0.
    - (int|long|double)  getMax()
    - (int|long|double)  getMin()
        - 产生汇总后打的元素的最大值和最小值，或者在没有任何元素时，产生（Integer|Long|Double).(MAX|MIN)_VALUE。





### 2.?.6 收集结果-Map



**java.util.stream.Collecttor 8**

- static\<T,K,U> Collector\<T,?,Map\<K,U>> toMap(参数略)
- static<T,K,U> Collector\<T,?,ConcurrentMap\<K,U>> toConcurrentMap(参数略)
    - 产生一个收集器，它会产生一个映射表或者并发映射表。
    - 具体内容参考原文 P<23> 《Java核心技术 卷II》



### 2.?.7 分组

**java.util.stream.Collecttor 8**

- static\<T,K> Collector\<T,?Map<K,List\<T>>> groupingBy(Function\<? super T,? extends K> classifier)
- static\<T,K> Collector\<T,?Map<K,List\<T>>> groupingByConcurrent(Function\<? super T,? extends K> classifier)
    - 产生一个收集器，它会产生一个映射表或并发映射表，其键是将classifier应用于所有收集到的元素上所产生的结果，值是由具有相同键的元素构成的一个个列表。
- static\<T,K> Collector\<T,?Map<Boolean,List\<T>>>  partitioningBy(Predicate\<? super T,? extends K> predicate)
    - 产生一个收集器，它会产生一个映射表，其键是true/false，而值是由满足/不满足断言的元素构成的列表。



# 3.类加载器



# 4.并发

# 