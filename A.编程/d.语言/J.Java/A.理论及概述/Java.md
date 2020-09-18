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

	Object[]  powers  =  	Steram.iterate(1.0, p -> p * 2).peek( e -> System.out.println("Fetching"+e)).limit(20).toArray()

当实际访问一个元素时，就会打印出来一条消息。通过这种方式，你可以验证 iterate 返回的无限流是被惰性处理的。

	对于调式，你可以让peek调用一个你设置了断点的方法。



## 2.6简单约简

从流数据中获得答案，本节所讨论的方法被称为约简。约简是一种终结操作（terminal operation）,它们会将流约简为可以在程序中使用的非流值。



### 2.6.1 返回数量

count统计数量

max最大值

min最小值

例如：

```java
Optional<String>   largest = words.max(String::compareToIgnoreCase);
System.out.println("largest"+largest.orElse(""));
```



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

    

    



## 2.11 下游收集器

具体内容参考 P<25> 《java核心技术 卷II》



## 2.12约简操作

具体内容参考 P<28> 《java核心技术 卷II》



## 2.13基本类型流

上文中基本都是对象流或者将基本类型包装成包装类，但将每个基本数据类型包装到包装器对象中是很低效的。

double、float、long、int、short、char、byte、boolean

流库中具有专门的类型IntStream、LongStream和DoubleStream,用来存储基本类型值，而无需使用包装器。

int、short、char、byte、boolean       **IntStream**

long															**LongStream**

double														**DoubleStream**



**创建基本类型流**

- 创建IntStream
    - IntStream.of()
    - Arrays.stream()
    - IntStream  stream = IntStream.of(1,1,2,3,5);
- 使用静态的generate和iterate方法
- 此外IntStream和LongStream有静态方法range和rangeClosed,
    - IntStream  zTnn = IntStream.range(0,100);
    - IntStream zTH = IntStream.rangeClosed(0,100);
- CharSequence 接口拥有codePoints和chars方法，可以生成由字符的Unicode码或由UTF-16编码机制的码元构成的IntStream。
    - String  sentence  =  "\\uD835\\uDD46 is the set of octonions.";
    - Intstream  codes = sentence.codePoints();
- 如果你有一个对象流时，可以用mapToInt、mapToLong和mapToDouble将其转换为基本类型流。
    - Stream\<String> words = ...;
    - Intstream lengths = words.mapToInt(String::length);
    - 为了将基本类型流转换成对象流，需要使用boxed方法
    - Stream\<Integer> integers  = IntStream.range(0,100).boxed();



**基本类型流与对象流的差异**

通常，基本类型流上的方法与对象流上的方法类似。

- toArray方法会返回基本类型数组
- 产生可选结果的方法会返回一个OptionalInt、OptionalLong或OptionalDouble.
    - 这些类与Optional类类似，但是具有getInt、getAsLong和getAsDouble方法，而不是get方法。
- 具有返回总和、平均值、最大值和最小值的sum、average、max和min方法。
    - 对象流没有定义这些方法。
- summaryStatistics方法会产生一个类型为IntSummaryStatistics、LongSummaryStatistics或DoubleSummaryStatistics的对象，他们可以同时报告流的总和、平均值、最大值和最小值。



## 2.14并行流

流使得并行处理块操作变得很容易。这个过程几乎是自动的，但是需要遵守一些规则。

首先，必须有一个并行流。可以用Collection.parallelStream()方法从任何集合中获取一个并行流：



- Stream\<String>  parallelWords = words.parallelStream();
    - 从任何集合中获取一个并行流
- Stream\<String>  parallelWords = Stream.of(wordArray).parallel();
    - 将任意的顺序流转换为并行流



**只要在终结方法执行时，流处于并行模式，那么所有的中间流操作都将被并行化。**



当流操作并行运行时，其目标是要让其返回结果与顺序执行时返回的结果相同。

重要的是，这些操作可以以任意顺序执行。



**例一：错误案例**

```
int[]  shortWords = new int[12];
words.parallelStream().forEach(
	s ->  {
		if(s.length() < 12){
			shortWords[s.length()]++;
		}
	}
)
```

上述代码在并行流中运行是会出错的。

传递给forEach的函数会在多个并发线程中运行，每个都会更新共享的数组。

如果多次运行这个程序，你很可能就会发现每次运行都会产生不同的计数值，而且每个都是错的。





**例二：修正版**

```java
Map<Integer,Long> shortWordCounts = words.parallelStream()
		.filter(s -> s.length() < 10)
		.collect(groupingBy(String::length,counting()));
```

注意：传递给并行流操作的函数不应该被堵塞。并行流使用fork-join池来操作流的各个部分。

​			如果多个流操作被阻塞，那么池可能就无法做任何事情了。



**排序并不排斥高效的并行处理**。

​	例如：当计算stream.map(fun)时，流可以被划分为n的部分，它们会被并行地处理。

​				然后，结果将会按照顺序重新组装起来。



当放弃排序需求时，有些操作可以被更有效地并行化。

通过在流上调用unordered方法，就可以明确表示我们对排序不感兴趣。



为了让并行流正常工作，需要满足大量的条件：

- 数据应该在内存中。
    - 必须等到数据到达是非常低效的。
- 流应该可以被高效地分成若干个子部分。
    - 由数组或平衡二叉树支撑的流都可以工作得很多，但是 Stream.iterate返回的结果不行。
- 流操作的工作量应该具有较大的规模。
    - 如果总工作负载并不是很大，那么搭建并行计算时所付出的代价就没有什么意义
- 流操作不应该被阻塞。



**换句话说不要将所有的流都转换成并行流。**

**只有在对已经位于内存中的数据执行大量计算操作时，才应该使用并行流。**

 

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





### 2.?.8基本类型流



**java.util.stream.IntStream 8**

- static  IntStream range(int statInclusive, int endExclusive)
- static  IntStream rangeClosed(int statInclusive, int endExclusive)
    - 产生一个由给定范围内的整数构成的IntStream。
- static  IntStream of(int... values)
    - 产生一个由给定元素构成的IntStream。
- int[]  toArray()
    - 产生一个由当前流中的元素构成的数组
- int sum()
- OptionalDouble  average()
- OptionalInt  max()
- OptionalInt  min()
- IntSummaryStatstics  summaryStatistics()
    - 产生当前流中元素的总和、平均值、最大值、最小值，或者从中可以获得这些结果所有四种值的对象。
- Stream\<Integer>  boxed()



**java.util.stream.LongStream 8**

- static  LongStream range(long  statInclusive, long  endExclusive)
- static  LongStream rangeClosed(long  statInclusive,long endExclusive)
    - 产生一个由给定范围内的整数构成的LongStream。
- static    LongStream of(long... values)
    - 用给定元素产生一个LongStream
- long[] toArray()
    - 用当前流中的元素产生一个数组。
- long  sum()
- OptionalDouble  average()
- OptionalLong       max()
- OptionalLong       min()
- LongSummaryStatistics  summaryStatistics()
    - 产生当前流中元素的总和、平均值、最大值、最小值，或者从中可以获得这些结果的所有四种值的对象。
- Stream\<Long>  boxed()
    - 产生用于当前流中的元素的包装器对象流。



**java.util.stream.DoubleStream 8**

- static  DoubleStream  of(double ...  values)
    - 用给定元素产生一个DoubleStream
- double[]  toArray()
    - 用当前流中的元素产生一个数组
- double   sum()
- OptionalDouble  average()
- OptionalDouble  max()
- OptionalDouble  min()
- DoubleSummaryStatistics  summaryStatistics()
    - 产生当前流中元素的总和、平均值、最大值、和最小值，或者从中可以获得这些结果的所有四种值的对象。
- Stream\<Double>  boxed()



**java.lang.CharSequence 1.0**

- IntStream  codePoints()
    - 产生由当前字符串的所有Unicode码点构成的流。



**java.util.Random 1.0**

- IntStream  ints()
- IntStream  ints(int randomNumberOrigin ,int randomNumberBound)
- IntStream  ints(long  streamSize)
- IntStream  ints(long streamSize,int randomNumberOrigin,int randomNumberBound)
- LongStream longs()
- LongStream longs(long  randomNumberOrigin,long ranomNumberBound)
- LongStream longs(long streamSize)
- LongStream longs(long streamSize,long randomNumberOrigin,long  randomNumberBound)
- DoubleStream doubles()
- DoubleStream doubles(double randomNumberOrigin,double randomNumberBound)
- DoubleStream doubles(long  streamSize)
- DoubleStream doubles(long streamSize,double randomNumberOrigin,double  randomNumberBound)
    - 产生随机数流。如果提供了streamSize，这个流就是具有给定数量元素的有限流。当提供了边界时，其元素将位于randomNumberOrigin(包含)和 randomNumberBound(不包含)的区间内。



**java.util.Optional ( Integer | Long | Double)**

- static  Optional( Integer | Long | Double)  of  ( (int | long | double) value)
    - 用所提供的基本类型值产生一个可选对象
-  (int | long | double)  getAs ( Integer | Long | Double)()
    - 产生当前可选对象的值，或者在其为空时抛出一个NoSuchElementException异常
-  (int | long | double)  orElse( (int | long | double)  other)
-  (int | long | double)  orElseGet( (int | long | double) Supplier other)
    - 产生当前可选对象的值，或者在这个对象为空时产生可替代的值
- void  ifPresent (  (int | long | double)  Consumer consumer)
    - 如果当前可选对象不为空，则将其值传递给Consumer



**java.util. (Int | long | double) SummaryStatistics**

-   long  getCount()
-   (int | long | double)   getSum()
-  double  getAverage()
-  (int | long | double)  getMax()
-  (int | long | double)  getMin()
    - 产生收集到的元素的个数、总和、平均值、最大值和最小值





### 2.?.9并发流

**java.util.stream.BaseStream\<T,S extends BaseStream\<T,S>**

- S  parallel()
    - 产生一个与当前流中元素相同的并行流
- S  unordered()
    - 产生一个与当前流中元素相同的无序流。



**java.util.Collection\<E> 1.2**

- Stream\<E>  parallelStream() 8
    - 用于当前集合中的元素产一个并行流







#  3.类加载器



**Java语言提供了以下三种确保安全的机制:**

- 语言设计特性（对数组的边界进行检查，无不受检查的类型转换，无指针算法等）。
- 访问控制机制，用于控制代码能够执行的操作（比如文件访问，网络访问等）
- 代码签名，利用该特性，代码的作者就能够用标准的加密算法来认证 Java 代码。
    - 这样，该代码的使用者就能够准确地知道谁创建了该代码，以及代码被标识后是否被修改过。



首先，我们来讨论**类加载器**，它可以在将类加载到虚拟机中的时候检查类的完整性。

我们将展示这种机制是如何探测类文件中的损坏的。



为了获得最大的安全性，无论是加载类的默认机制，还是自定义的类加载器，都需要与负责控制代码运行的**安全管理器类**协同工作。



## 3.1 类加载器

Java编译器会为虚拟机转换源指令。

虚拟机代码存储在以 .class 为扩展名的类文件中。

每个类文件都包含某个类或者接口的定义和实现代码。

这个类文件必须由一个程序进行解释，该程序能够将虚拟机的指令集翻译成目标机器的机器语言。 





### 3.1.1 类加载过程

虚拟机只加载程序执行时所需要的类文件。

虚拟机执行步骤：

- 虚拟机有一个用于加载类文件的机制。
    - 例如：从磁盘中读取文件或者请求Web上的文件；
    - 它使用该机制加载MyProgram类文件中的内容。
- 如果MyProgram类拥有类型为另一个类的域，或是拥有超类，那么这些类文件也会被加载。
    - 加载某个类所依赖的所有类的过程称为类的**解析**。
- 接着，虚拟机执行MyProgram中的main方法。
    - 它是静态的，无需创建类的实例
- 如果main方法或者main调用的方法要用到更多的类，那么接下来就会架子这些类。
    - 然而，类加载机制并非只使用单个的类加载器。



每个Java程序至少拥有三个类加载器：

- 引导类加载器
- 扩展类加载器
- 系统类加载器（有时也称为应用类加载器）



（注意:类加载时，优先由上级加载器进行加载）



**引导类加载器**负责加载系统类（通常从 JAR 文件 rt.jar 中进行加载）。

它是虚拟机不可分割的一部分，而且通常是用 C语言来实现的。

引导类加载器没有对应的ClassLoader对象。



例如：

​	String.class.getClassLoader();    //由于String 由 引导类记载器负责加载，所以这里的返回值为 null。



**扩展类加载器**用于从 jre/lib/ext 目录加载“标准的扩展”。

- 可以将 JAR 文件放入该目录，这样即使没有任何类路径，扩展类加载器也可以找到其中的各个类。
- （有些人推荐使用该机制来避免“可恶的类路径”，不过请看看下面提到的警告事项）



**系统类加载器**用于加载应用类。

- 它在由 CLASSPATH 环境变量或者 -classpath 命令行选项设置的类路径中的目录里或者是 JAR/ZIP 文件里查找这些类。



### 3.1.2 类加载器的层次结构

​	





















# 4.并发

# 







# 5.Collection



Collection 是单列集合的顶级接口



## 5.0 Collection 接口的方法

```java
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package java.util;

import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see     Set
 * @see     List
 * @see     Map
 * @see     SortedSet
 * @see     SortedMap
 * @see     HashSet
 * @see     TreeSet
 * @see     ArrayList
 * @see     LinkedList
 * @see     Vector
 * @see     Collections
 * @see     Arrays
 * @see     AbstractCollection
 * @since 1.2
 */

public interface Collection<E> extends Iterable<E> {
    // Query Operations

    int size();

    boolean isEmpty();

    boolean contains(Object o);

    Iterator<E> iterator();

    Object[] toArray();

    <T> T[] toArray(T[] a);

    // Modification Operations

    boolean add(E e);

    boolean remove(Object o);

    // Bulk Operations

    boolean containsAll(Collection<?> c);

    boolean addAll(Collection<? extends E> c);

    boolean removeAll(Collection<?> c);

    /**
	 * 遍历集合  如果满足一定条件  则从集合中移除元素
     * @since 1.8
     */
    default boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        boolean removed = false;
        final Iterator<E> each = iterator();
        while (each.hasNext()) {
            if (filter.test(each.next())) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    }

    /**
	 * 保留所有
     */
    boolean retainAll(Collection<?> c);

    /**
	 * 清除
     */
    void clear();
 

    /**
     * 判断是否相等
     */
    boolean equals(Object o);

    /**
     * hashCode
     */
    int hashCode();

    /**
     * 分割
     * @since 1.8
     */
    @Override
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, 0);
    }

    /**
     * 获取流
     * @since 1.8
     */
    default Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
	 * 获取并行流
     * collection
     * @since 1.8
     */
    default Stream<E> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }
}

```



## 5.1 List



### 5.1.0 List接口的方法

```java
/*
 * Copyright (c) 1997, 2014, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package java.util;

import java.util.function.UnaryOperator;

/**
 *
 * @param <E> the type of elements in this list
 *
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see Collection
 * @see Set
 * @see ArrayList
 * @see LinkedList
 * @see Vector
 * @see Arrays#asList(Object[])
 * @see Collections#nCopies(int, Object)
 * @see Collections#EMPTY_LIST
 * @see AbstractList
 * @see AbstractSequentialList
 * @since 1.2
 */

public interface List<E> extends Collection<E> {
    // Query Operations

    int size();

    boolean isEmpty();

    boolean contains(Object o);

    Iterator<E> iterator();

    Object[] toArray();

    <T> T[] toArray(T[] a);


    // Modification Operations

    boolean add(E e);

    boolean remove(Object o);


    // Bulk Modification Operations

    boolean containsAll(Collection<?> c);

    boolean addAll(Collection<? extends E> c);

    boolean addAll(int index, Collection<? extends E> c);

    boolean removeAll(Collection<?> c);

    boolean retainAll(Collection<?> c);

    /**
     * 替换全部
     */
    default void replaceAll(UnaryOperator<E> operator) {
        Objects.requireNonNull(operator);
        final ListIterator<E> li = this.listIterator();
        while (li.hasNext()) {
            li.set(operator.apply(li.next()));
        }
    }

    /**
	 * 排序  Comparator  compare
     * @since 1.8
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    default void sort(Comparator<? super E> c) {
        Object[] a = this.toArray();
        Arrays.sort(a, (Comparator) c);
        ListIterator<E> i = this.listIterator();
        for (Object e : a) {
            i.next();
            i.set((E) e);
        }
    }

    /**
     */
    void clear();


    // Comparison and hashing

    boolean equals(Object o);

    int hashCode();


    // Positional Access Operations

    E get(int index);

    E set(int index, E element);

    void add(int index, E element);

    E remove(int index);


    // Search Operations

    int indexOf(Object o);

    int lastIndexOf(Object o);


    // List Iterators

    ListIterator<E> listIterator();

    ListIterator<E> listIterator(int index);

    // View

    List<E> subList(int fromIndex, int toIndex);

    /**
     * @return a {@code Spliterator} over the elements in this list
     * @since 1.8
     */
    @Override
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, Spliterator.ORDERED);
    }
}

```



### 5.1.2 ArrayList

#### 5.1.2.0 ArrayList类代码

```java
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package java.util;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see     Collection
 * @see     List
 * @see     LinkedList
 * @see     Vector
 * @since   1.2
 */
/**
 * RandomAccess  随机存储   就是一个接口什么方法都没有
 
 * Cloneable     克隆  一个接口什么方法都没有 
 					但如果想要调用 Object中clone方法   
 					这该类必须实现Cloneable 否则抛出 CloneNotSupportedException
 
 * Serializable  序列计接口 一个接口什么方法都没有
 					但涉及序列化和反序列化是 必须实现该接口 否则抛出异常
 					
 * 
 */
public class ArrayList<E> extends AbstractList<E>
  implements List<E>, RandomAccess, Cloneable, java.io.Serializable {
    
    private static final long serialVersionUID = 8683452581122892189L;

    /**
     * 默认初始容量。
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 用空实例的共享空数组实例。
     	当创建ArrayList对象时，传递长度参数，且所传长度为0时，直接赋值给 elementData。
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     *共享的空数组实例，用于默认大小的空实例。
     *	当创建ArrayList对象时，没有传递长度参数，直接赋值 给elementData
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * 	ArrayList 存储数据的地方
     *
     */
    transient Object[] elementData; // non-private to simplify nested class access

    private int size;

    /**
     *如果没有指定 集合长度 或 指定的集合长度为0 直接将elementData 赋值一个 {}
     */
    public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        }
    }

    /**
     * 如果没有指定 集合长度 直接将elementData 赋值一个 {}
     */
    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    /**
  	 *将指定集合数据作为参数添加到 
     */
    public ArrayList(Collection<? extends E> c) {
        elementData = c.toArray();
        if ((size = elementData.length) != 0) {
            // c.toArray might (incorrectly) not return Object[] (see 6260652)
            if (elementData.getClass() != Object[].class)
                elementData = Arrays.copyOf(elementData, size, Object[].class);
        } else {
            // replace with empty array.
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }

    /**
     * Trims the capacity of this <tt>ArrayList</tt> instance to be the
     * list's current size.  An application can use this operation to minimize
     * the storage of an <tt>ArrayList</tt> instance.
     */
    public void trimToSize() {
        modCount++;
        if (size < elementData.length) {
            elementData = (size == 0)
              ? EMPTY_ELEMENTDATA
              : Arrays.copyOf(elementData, size);
        }
    }

    /**
     * Increases the capacity of this <tt>ArrayList</tt> instance, if
     * necessary, to ensure that it can hold at least the number of elements
     * specified by the minimum capacity argument.
     *
     * @param   minCapacity   the desired minimum capacity
     */
    public void ensureCapacity(int minCapacity) {
        int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
            // any size if not default element table
            ? 0
            // larger than default for default empty table. It's already
            // supposed to be at default size.
            : DEFAULT_CAPACITY;

        if (minCapacity > minExpand) {
            ensureExplicitCapacity(minCapacity);
        }
    }

    private void ensureCapacityInternal(int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }

        ensureExplicitCapacity(minCapacity);
    }

    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;

        // overflow-conscious code
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }

    /**
     * The maximum size of array to allocate.
     * Some VMs reserve some header words in an array.
     * Attempts to allocate larger arrays may result in
     * OutOfMemoryError: Requested array size exceeds VM limit
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * Increases the capacity to ensure that it can hold at least the
     * number of elements specified by the minimum capacity argument.
     *
     * @param minCapacity the desired minimum capacity
     */
    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    public int size() {
        return size;
    }

    /**
     * Returns <tt>true</tt> if this list contains no elements.
     *
     * @return <tt>true</tt> if this list contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns <tt>true</tt> if this list contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this list contains
     * at least one element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     *
     * @param o element whose presence in this list is to be tested
     * @return <tt>true</tt> if this list contains the specified element
     */
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the lowest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
     * or -1 if there is no such index.
     */
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (elementData[i]==null)
                    return i;
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    /**
     * Returns the index of the last occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the highest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
     * or -1 if there is no such index.
     */
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size-1; i >= 0; i--)
                if (elementData[i]==null)
                    return i;
        } else {
            for (int i = size-1; i >= 0; i--)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    /**
     * Returns a shallow copy of this <tt>ArrayList</tt> instance.  (The
     * elements themselves are not copied.)
     *
     * @return a clone of this <tt>ArrayList</tt> instance
     */
    public Object clone() {
        try {
            ArrayList<?> v = (ArrayList<?>) super.clone();
            v.elementData = Arrays.copyOf(elementData, size);
            v.modCount = 0;
            return v;
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
    }

    /**
     * Returns an array containing all of the elements in this list
     * in proper sequence (from first to last element).
     *
     * <p>The returned array will be "safe" in that no references to it are
     * maintained by this list.  (In other words, this method must allocate
     * a new array).  The caller is thus free to modify the returned array.
     *
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * @return an array containing all of the elements in this list in
     *         proper sequence
     */
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence (from first to last element); the runtime type of the returned
     * array is that of the specified array.  If the list fits in the
     * specified array, it is returned therein.  Otherwise, a new array is
     * allocated with the runtime type of the specified array and the size of
     * this list.
     *
     * <p>If the list fits in the specified array with room to spare
     * (i.e., the array has more elements than the list), the element in
     * the array immediately following the end of the collection is set to
     * <tt>null</tt>.  (This is useful in determining the length of the
     * list <i>only</i> if the caller knows that the list does not contain
     * any null elements.)
     *
     * @param a the array into which the elements of the list are to
     *          be stored, if it is big enough; otherwise, a new array of the
     *          same runtime type is allocated for this purpose.
     * @return an array containing the elements of the list
     * @throws ArrayStoreException if the runtime type of the specified array
     *         is not a supertype of the runtime type of every element in
     *         this list
     * @throws NullPointerException if the specified array is null
     */
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            // Make a new array of a's runtime type, but my contents:
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    // Positional Access Operations

    @SuppressWarnings("unchecked")
    E elementData(int index) {
        return (E) elementData[index];
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param  index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E get(int index) {
        rangeCheck(index);

        return elementData(index);
    }

    /**
     * Replaces the element at the specified position in this list with
     * the specified element.
     *
     * @param index index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E set(int index, E element) {
        rangeCheck(index);

        E oldValue = elementData(index);
        elementData[index] = element;
        return oldValue;
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param e element to be appended to this list
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     */
    public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        elementData[size++] = e;
        return true;
    }

    /**
     * Inserts the specified element at the specified position in this
     * list. Shifts the element currently at that position (if any) and
     * any subsequent elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public void add(int index, E element) {
        rangeCheckForAdd(index);

        ensureCapacityInternal(size + 1);  // Increments modCount!!
        System.arraycopy(elementData, index, elementData, index + 1,
                         size - index);
        elementData[index] = element;
        size++;
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E remove(int index) {
        rangeCheck(index);

        modCount++;
        E oldValue = elementData(index);

        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
        elementData[--size] = null; // clear to let GC do its work

        return oldValue;
    }

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present.  If the list does not contain the element, it is
     * unchanged.  More formally, removes the element with the lowest index
     * <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>
     * (if such an element exists).  Returns <tt>true</tt> if this list
     * contained the specified element (or equivalently, if this list
     * changed as a result of the call).
     *
     * @param o element to be removed from this list, if present
     * @return <tt>true</tt> if this list contained the specified element
     */
    public boolean remove(Object o) {
        if (o == null) {
            for (int index = 0; index < size; index++)
                if (elementData[index] == null) {
                    fastRemove(index);
                    return true;
                }
        } else {
            for (int index = 0; index < size; index++)
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }

    /*
     * Private remove method that skips bounds checking and does not
     * return the value removed.
     */
    private void fastRemove(int index) {
        modCount++;
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
        elementData[--size] = null; // clear to let GC do its work
    }

    /**
     * Removes all of the elements from this list.  The list will
     * be empty after this call returns.
     */
    public void clear() {
        modCount++;

        // clear to let GC do its work
        for (int i = 0; i < size; i++)
            elementData[i] = null;

        size = 0;
    }

    /**
     * Appends all of the elements in the specified collection to the end of
     * this list, in the order that they are returned by the
     * specified collection's Iterator.  The behavior of this operation is
     * undefined if the specified collection is modified while the operation
     * is in progress.  (This implies that the behavior of this call is
     * undefined if the specified collection is this list, and this
     * list is nonempty.)
     *
     * @param c collection containing elements to be added to this list
     * @return <tt>true</tt> if this list changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    public boolean addAll(Collection<? extends E> c) {
        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);  // Increments modCount
        System.arraycopy(a, 0, elementData, size, numNew);
        size += numNew;
        return numNew != 0;
    }

    /**
     * Inserts all of the elements in the specified collection into this
     * list, starting at the specified position.  Shifts the element
     * currently at that position (if any) and any subsequent elements to
     * the right (increases their indices).  The new elements will appear
     * in the list in the order that they are returned by the
     * specified collection's iterator.
     *
     * @param index index at which to insert the first element from the
     *              specified collection
     * @param c collection containing elements to be added to this list
     * @return <tt>true</tt> if this list changed as a result of the call
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @throws NullPointerException if the specified collection is null
     */
    public boolean addAll(int index, Collection<? extends E> c) {
        rangeCheckForAdd(index);

        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);  // Increments modCount

        int numMoved = size - index;
        if (numMoved > 0)
            System.arraycopy(elementData, index, elementData, index + numNew,
                             numMoved);

        System.arraycopy(a, 0, elementData, index, numNew);
        size += numNew;
        return numNew != 0;
    }

    /**
     * Removes from this list all of the elements whose index is between
     * {@code fromIndex}, inclusive, and {@code toIndex}, exclusive.
     * Shifts any succeeding elements to the left (reduces their index).
     * This call shortens the list by {@code (toIndex - fromIndex)} elements.
     * (If {@code toIndex==fromIndex}, this operation has no effect.)
     *
     * @throws IndexOutOfBoundsException if {@code fromIndex} or
     *         {@code toIndex} is out of range
     *         ({@code fromIndex < 0 ||
     *          fromIndex >= size() ||
     *          toIndex > size() ||
     *          toIndex < fromIndex})
     */
    protected void removeRange(int fromIndex, int toIndex) {
        modCount++;
        int numMoved = size - toIndex;
        System.arraycopy(elementData, toIndex, elementData, fromIndex,
                         numMoved);

        // clear to let GC do its work
        int newSize = size - (toIndex-fromIndex);
        for (int i = newSize; i < size; i++) {
            elementData[i] = null;
        }
        size = newSize;
    }

    /**
     * Checks if the given index is in range.  If not, throws an appropriate
     * runtime exception.  This method does *not* check if the index is
     * negative: It is always used immediately prior to an array access,
     * which throws an ArrayIndexOutOfBoundsException if index is negative.
     */
    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    /**
     * A version of rangeCheck used by add and addAll.
     */
    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    /**
     * Constructs an IndexOutOfBoundsException detail message.
     * Of the many possible refactorings of the error handling code,
     * this "outlining" performs best with both server and client VMs.
     */
    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

    /**
     * Removes from this list all of its elements that are contained in the
     * specified collection.
     *
     * @param c collection containing elements to be removed from this list
     * @return {@code true} if this list changed as a result of the call
     * @throws ClassCastException if the class of an element of this list
     *         is incompatible with the specified collection
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if this list contains a null element and the
     *         specified collection does not permit null elements
     * (<a href="Collection.html#optional-restrictions">optional</a>),
     *         or if the specified collection is null
     * @see Collection#contains(Object)
     */
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        return batchRemove(c, false);
    }

    /**
     * Retains only the elements in this list that are contained in the
     * specified collection.  In other words, removes from this list all
     * of its elements that are not contained in the specified collection.
     *
     * @param c collection containing elements to be retained in this list
     * @return {@code true} if this list changed as a result of the call
     * @throws ClassCastException if the class of an element of this list
     *         is incompatible with the specified collection
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if this list contains a null element and the
     *         specified collection does not permit null elements
     * (<a href="Collection.html#optional-restrictions">optional</a>),
     *         or if the specified collection is null
     * @see Collection#contains(Object)
     */
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        return batchRemove(c, true);
    }

    private boolean batchRemove(Collection<?> c, boolean complement) {
        final Object[] elementData = this.elementData;
        int r = 0, w = 0;
        boolean modified = false;
        try {
            for (; r < size; r++)
                if (c.contains(elementData[r]) == complement)
                    elementData[w++] = elementData[r];
        } finally {
            // Preserve behavioral compatibility with AbstractCollection,
            // even if c.contains() throws.
            if (r != size) {
                System.arraycopy(elementData, r,
                                 elementData, w,
                                 size - r);
                w += size - r;
            }
            if (w != size) {
                // clear to let GC do its work
                for (int i = w; i < size; i++)
                    elementData[i] = null;
                modCount += size - w;
                size = w;
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Save the state of the <tt>ArrayList</tt> instance to a stream (that
     * is, serialize it).
     *
     * @serialData The length of the array backing the <tt>ArrayList</tt>
     *             instance is emitted (int), followed by all of its elements
     *             (each an <tt>Object</tt>) in the proper order.
     */
    private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException{
        // Write out element count, and any hidden stuff
        int expectedModCount = modCount;
        s.defaultWriteObject();

        // Write out size as capacity for behavioural compatibility with clone()
        s.writeInt(size);

        // Write out all elements in the proper order.
        for (int i=0; i<size; i++) {
            s.writeObject(elementData[i]);
        }

        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }

    /**
     * Reconstitute the <tt>ArrayList</tt> instance from a stream (that is,
     * deserialize it).
     */
    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
        elementData = EMPTY_ELEMENTDATA;

        // Read in size, and any hidden stuff
        s.defaultReadObject();

        // Read in capacity
        s.readInt(); // ignored

        if (size > 0) {
            // be like clone(), allocate array based upon size not capacity
            ensureCapacityInternal(size);

            Object[] a = elementData;
            // Read in all elements in the proper order.
            for (int i=0; i<size; i++) {
                a[i] = s.readObject();
            }
        }
    }

    /**
     * Returns a list iterator over the elements in this list (in proper
     * sequence), starting at the specified position in the list.
     * The specified index indicates the first element that would be
     * returned by an initial call to {@link ListIterator#next next}.
     * An initial call to {@link ListIterator#previous previous} would
     * return the element with the specified index minus one.
     *
     * <p>The returned list iterator is <a href="#fail-fast"><i>fail-fast</i></a>.
     *
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: "+index);
        return new ListItr(index);
    }

    /**
     * Returns a list iterator over the elements in this list (in proper
     * sequence).
     *
     * <p>The returned list iterator is <a href="#fail-fast"><i>fail-fast</i></a>.
     *
     * @see #listIterator(int)
     */
    public ListIterator<E> listIterator() {
        return new ListItr(0);
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * <p>The returned iterator is <a href="#fail-fast"><i>fail-fast</i></a>.
     *
     * @return an iterator over the elements in this list in proper sequence
     */
    public Iterator<E> iterator() {
        return new Itr();
    }

    /**
     * An optimized version of AbstractList.Itr
     */
    private class Itr implements Iterator<E> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = modCount;

        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            checkForComodification();
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = ArrayList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                ArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void forEachRemaining(Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            final int size = ArrayList.this.size;
            int i = cursor;
            if (i >= size) {
                return;
            }
            final Object[] elementData = ArrayList.this.elementData;
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            while (i != size && modCount == expectedModCount) {
                consumer.accept((E) elementData[i++]);
            }
            // update once at end of iteration to reduce heap write traffic
            cursor = i;
            lastRet = i - 1;
            checkForComodification();
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    /**
     * An optimized version of AbstractList.ListItr
     */
    private class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        @SuppressWarnings("unchecked")
        public E previous() {
            checkForComodification();
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] elementData = ArrayList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i;
            return (E) elementData[lastRet = i];
        }

        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                ArrayList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(E e) {
            checkForComodification();

            try {
                int i = cursor;
                ArrayList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    /**
     * Returns a view of the portion of this list between the specified
     * {@code fromIndex}, inclusive, and {@code toIndex}, exclusive.  (If
     * {@code fromIndex} and {@code toIndex} are equal, the returned list is
     * empty.)  The returned list is backed by this list, so non-structural
     * changes in the returned list are reflected in this list, and vice-versa.
     * The returned list supports all of the optional list operations.
     *
     * <p>This method eliminates the need for explicit range operations (of
     * the sort that commonly exist for arrays).  Any operation that expects
     * a list can be used as a range operation by passing a subList view
     * instead of a whole list.  For example, the following idiom
     * removes a range of elements from a list:
     * <pre>
     *      list.subList(from, to).clear();
     * </pre>
     * Similar idioms may be constructed for {@link #indexOf(Object)} and
     * {@link #lastIndexOf(Object)}, and all of the algorithms in the
     * {@link Collections} class can be applied to a subList.
     *
     * <p>The semantics of the list returned by this method become undefined if
     * the backing list (i.e., this list) is <i>structurally modified</i> in
     * any way other than via the returned list.  (Structural modifications are
     * those that change the size of this list, or otherwise perturb it in such
     * a fashion that iterations in progress may yield incorrect results.)
     *
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    public List<E> subList(int fromIndex, int toIndex) {
        subListRangeCheck(fromIndex, toIndex, size);
        return new SubList(this, 0, fromIndex, toIndex);
    }

    static void subListRangeCheck(int fromIndex, int toIndex, int size) {
        if (fromIndex < 0)
            throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
        if (toIndex > size)
            throw new IndexOutOfBoundsException("toIndex = " + toIndex);
        if (fromIndex > toIndex)
            throw new IllegalArgumentException("fromIndex(" + fromIndex +
                                               ") > toIndex(" + toIndex + ")");
    }

    private class SubList extends AbstractList<E> implements RandomAccess {
        private final AbstractList<E> parent;
        private final int parentOffset;
        private final int offset;
        int size;

        SubList(AbstractList<E> parent,
                int offset, int fromIndex, int toIndex) {
            this.parent = parent;
            this.parentOffset = fromIndex;
            this.offset = offset + fromIndex;
            this.size = toIndex - fromIndex;
            this.modCount = ArrayList.this.modCount;
        }

        public E set(int index, E e) {
            rangeCheck(index);
            checkForComodification();
            E oldValue = ArrayList.this.elementData(offset + index);
            ArrayList.this.elementData[offset + index] = e;
            return oldValue;
        }

        public E get(int index) {
            rangeCheck(index);
            checkForComodification();
            return ArrayList.this.elementData(offset + index);
        }

        public int size() {
            checkForComodification();
            return this.size;
        }

        public void add(int index, E e) {
            rangeCheckForAdd(index);
            checkForComodification();
            parent.add(parentOffset + index, e);
            this.modCount = parent.modCount;
            this.size++;
        }

        public E remove(int index) {
            rangeCheck(index);
            checkForComodification();
            E result = parent.remove(parentOffset + index);
            this.modCount = parent.modCount;
            this.size--;
            return result;
        }

        protected void removeRange(int fromIndex, int toIndex) {
            checkForComodification();
            parent.removeRange(parentOffset + fromIndex,
                               parentOffset + toIndex);
            this.modCount = parent.modCount;
            this.size -= toIndex - fromIndex;
        }

        public boolean addAll(Collection<? extends E> c) {
            return addAll(this.size, c);
        }

        public boolean addAll(int index, Collection<? extends E> c) {
            rangeCheckForAdd(index);
            int cSize = c.size();
            if (cSize==0)
                return false;

            checkForComodification();
            parent.addAll(parentOffset + index, c);
            this.modCount = parent.modCount;
            this.size += cSize;
            return true;
        }

        public Iterator<E> iterator() {
            return listIterator();
        }

        public ListIterator<E> listIterator(final int index) {
            checkForComodification();
            rangeCheckForAdd(index);
            final int offset = this.offset;

            return new ListIterator<E>() {
                int cursor = index;
                int lastRet = -1;
                int expectedModCount = ArrayList.this.modCount;

                public boolean hasNext() {
                    return cursor != SubList.this.size;
                }

                @SuppressWarnings("unchecked")
                public E next() {
                    checkForComodification();
                    int i = cursor;
                    if (i >= SubList.this.size)
                        throw new NoSuchElementException();
                    Object[] elementData = ArrayList.this.elementData;
                    if (offset + i >= elementData.length)
                        throw new ConcurrentModificationException();
                    cursor = i + 1;
                    return (E) elementData[offset + (lastRet = i)];
                }

                public boolean hasPrevious() {
                    return cursor != 0;
                }

                @SuppressWarnings("unchecked")
                public E previous() {
                    checkForComodification();
                    int i = cursor - 1;
                    if (i < 0)
                        throw new NoSuchElementException();
                    Object[] elementData = ArrayList.this.elementData;
                    if (offset + i >= elementData.length)
                        throw new ConcurrentModificationException();
                    cursor = i;
                    return (E) elementData[offset + (lastRet = i)];
                }

                @SuppressWarnings("unchecked")
                public void forEachRemaining(Consumer<? super E> consumer) {
                    Objects.requireNonNull(consumer);
                    final int size = SubList.this.size;
                    int i = cursor;
                    if (i >= size) {
                        return;
                    }
                    final Object[] elementData = ArrayList.this.elementData;
                    if (offset + i >= elementData.length) {
                        throw new ConcurrentModificationException();
                    }
                    while (i != size && modCount == expectedModCount) {
                        consumer.accept((E) elementData[offset + (i++)]);
                    }
                    // update once at end of iteration to reduce heap write traffic
                    lastRet = cursor = i;
                    checkForComodification();
                }

                public int nextIndex() {
                    return cursor;
                }

                public int previousIndex() {
                    return cursor - 1;
                }

                public void remove() {
                    if (lastRet < 0)
                        throw new IllegalStateException();
                    checkForComodification();

                    try {
                        SubList.this.remove(lastRet);
                        cursor = lastRet;
                        lastRet = -1;
                        expectedModCount = ArrayList.this.modCount;
                    } catch (IndexOutOfBoundsException ex) {
                        throw new ConcurrentModificationException();
                    }
                }

                public void set(E e) {
                    if (lastRet < 0)
                        throw new IllegalStateException();
                    checkForComodification();

                    try {
                        ArrayList.this.set(offset + lastRet, e);
                    } catch (IndexOutOfBoundsException ex) {
                        throw new ConcurrentModificationException();
                    }
                }

                public void add(E e) {
                    checkForComodification();

                    try {
                        int i = cursor;
                        SubList.this.add(i, e);
                        cursor = i + 1;
                        lastRet = -1;
                        expectedModCount = ArrayList.this.modCount;
                    } catch (IndexOutOfBoundsException ex) {
                        throw new ConcurrentModificationException();
                    }
                }

                final void checkForComodification() {
                    if (expectedModCount != ArrayList.this.modCount)
                        throw new ConcurrentModificationException();
                }
            };
        }

        public List<E> subList(int fromIndex, int toIndex) {
            subListRangeCheck(fromIndex, toIndex, size);
            return new SubList(this, offset, fromIndex, toIndex);
        }

        private void rangeCheck(int index) {
            if (index < 0 || index >= this.size)
                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        private void rangeCheckForAdd(int index) {
            if (index < 0 || index > this.size)
                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        private String outOfBoundsMsg(int index) {
            return "Index: "+index+", Size: "+this.size;
        }

        private void checkForComodification() {
            if (ArrayList.this.modCount != this.modCount)
                throw new ConcurrentModificationException();
        }

        public Spliterator<E> spliterator() {
            checkForComodification();
            return new ArrayListSpliterator<E>(ArrayList.this, offset,
                                               offset + this.size, this.modCount);
        }
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        final int expectedModCount = modCount;
        @SuppressWarnings("unchecked")
        final E[] elementData = (E[]) this.elementData;
        final int size = this.size;
        for (int i=0; modCount == expectedModCount && i < size; i++) {
            action.accept(elementData[i]);
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }

    /**
     * Creates a <em><a href="Spliterator.html#binding">late-binding</a></em>
     * and <em>fail-fast</em> {@link Spliterator} over the elements in this
     * list.
     *
     * <p>The {@code Spliterator} reports {@link Spliterator#SIZED},
     * {@link Spliterator#SUBSIZED}, and {@link Spliterator#ORDERED}.
     * Overriding implementations should document the reporting of additional
     * characteristic values.
     *
     * @return a {@code Spliterator} over the elements in this list
     * @since 1.8
     */
    @Override
    public Spliterator<E> spliterator() {
        return new ArrayListSpliterator<>(this, 0, -1, 0);
    }

    /** Index-based split-by-two, lazily initialized Spliterator */
    static final class ArrayListSpliterator<E> implements Spliterator<E> {

        /*
         * If ArrayLists were immutable, or structurally immutable (no
         * adds, removes, etc), we could implement their spliterators
         * with Arrays.spliterator. Instead we detect as much
         * interference during traversal as practical without
         * sacrificing much performance. We rely primarily on
         * modCounts. These are not guaranteed to detect concurrency
         * violations, and are sometimes overly conservative about
         * within-thread interference, but detect enough problems to
         * be worthwhile in practice. To carry this out, we (1) lazily
         * initialize fence and expectedModCount until the latest
         * point that we need to commit to the state we are checking
         * against; thus improving precision.  (This doesn't apply to
         * SubLists, that create spliterators with current non-lazy
         * values).  (2) We perform only a single
         * ConcurrentModificationException check at the end of forEach
         * (the most performance-sensitive method). When using forEach
         * (as opposed to iterators), we can normally only detect
         * interference after actions, not before. Further
         * CME-triggering checks apply to all other possible
         * violations of assumptions for example null or too-small
         * elementData array given its size(), that could only have
         * occurred due to interference.  This allows the inner loop
         * of forEach to run without any further checks, and
         * simplifies lambda-resolution. While this does entail a
         * number of checks, note that in the common case of
         * list.stream().forEach(a), no checks or other computation
         * occur anywhere other than inside forEach itself.  The other
         * less-often-used methods cannot take advantage of most of
         * these streamlinings.
         */

        private final ArrayList<E> list;
        private int index; // current index, modified on advance/split
        private int fence; // -1 until used; then one past last index
        private int expectedModCount; // initialized when fence set

        /** Create new spliterator covering the given  range */
        ArrayListSpliterator(ArrayList<E> list, int origin, int fence,
                             int expectedModCount) {
            this.list = list; // OK if null unless traversed
            this.index = origin;
            this.fence = fence;
            this.expectedModCount = expectedModCount;
        }

        private int getFence() { // initialize fence to size on first use
            int hi; // (a specialized variant appears in method forEach)
            ArrayList<E> lst;
            if ((hi = fence) < 0) {
                if ((lst = list) == null)
                    hi = fence = 0;
                else {
                    expectedModCount = lst.modCount;
                    hi = fence = lst.size;
                }
            }
            return hi;
        }

        public ArrayListSpliterator<E> trySplit() {
            int hi = getFence(), lo = index, mid = (lo + hi) >>> 1;
            return (lo >= mid) ? null : // divide range in half unless too small
                new ArrayListSpliterator<E>(list, lo, index = mid,
                                            expectedModCount);
        }

        public boolean tryAdvance(Consumer<? super E> action) {
            if (action == null)
                throw new NullPointerException();
            int hi = getFence(), i = index;
            if (i < hi) {
                index = i + 1;
                @SuppressWarnings("unchecked") E e = (E)list.elementData[i];
                action.accept(e);
                if (list.modCount != expectedModCount)
                    throw new ConcurrentModificationException();
                return true;
            }
            return false;
        }

        public void forEachRemaining(Consumer<? super E> action) {
            int i, hi, mc; // hoist accesses and checks from loop
            ArrayList<E> lst; Object[] a;
            if (action == null)
                throw new NullPointerException();
            if ((lst = list) != null && (a = lst.elementData) != null) {
                if ((hi = fence) < 0) {
                    mc = lst.modCount;
                    hi = lst.size;
                }
                else
                    mc = expectedModCount;
                if ((i = index) >= 0 && (index = hi) <= a.length) {
                    for (; i < hi; ++i) {
                        @SuppressWarnings("unchecked") E e = (E) a[i];
                        action.accept(e);
                    }
                    if (lst.modCount == mc)
                        return;
                }
            }
            throw new ConcurrentModificationException();
        }

        public long estimateSize() {
            return (long) (getFence() - index);
        }

        public int characteristics() {
            return Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED;
        }
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        // figure out which elements are to be removed
        // any exception thrown from the filter predicate at this stage
        // will leave the collection unmodified
        int removeCount = 0;
        final BitSet removeSet = new BitSet(size);
        final int expectedModCount = modCount;
        final int size = this.size;
        for (int i=0; modCount == expectedModCount && i < size; i++) {
            @SuppressWarnings("unchecked")
            final E element = (E) elementData[i];
            if (filter.test(element)) {
                removeSet.set(i);
                removeCount++;
            }
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }

        // shift surviving elements left over the spaces left by removed elements
        final boolean anyToRemove = removeCount > 0;
        if (anyToRemove) {
            final int newSize = size - removeCount;
            for (int i=0, j=0; (i < size) && (j < newSize); i++, j++) {
                i = removeSet.nextClearBit(i);
                elementData[j] = elementData[i];
            }
            for (int k=newSize; k < size; k++) {
                elementData[k] = null;  // Let gc do its work
            }
            this.size = newSize;
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            modCount++;
        }

        return anyToRemove;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void replaceAll(UnaryOperator<E> operator) {
        Objects.requireNonNull(operator);
        final int expectedModCount = modCount;
        final int size = this.size;
        for (int i=0; modCount == expectedModCount && i < size; i++) {
            elementData[i] = operator.apply((E) elementData[i]);
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
        modCount++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super E> c) {
        final int expectedModCount = modCount;
        Arrays.sort((E[]) elementData, 0, size, c);
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
        modCount++;
    }
}

```







### 5.1.3 Vector



### 5.1.4 LinkedList



### 5.1.5 CopyOnWriteArrayList



### 5.1.6 ByteList



### 5.1.7 其他





## 5.2 Set





## 5.3 Queue





## 5.4 AbstractCollection







# 6.Map

