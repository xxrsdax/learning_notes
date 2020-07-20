# 1.数据结构与对象

## 1.1 简单动态字符串 SDS





## 1.2  链表

### 1.2.1 概述

​	链表提供了高效的节点重排能力，以及顺序性的节点访问方式，并且可以通过赠删节点来灵活地调整链表的长度。



### 1.2.2 作用

- ​	列表的底层实现之一就是链表:

  ​             当一个列表键包含了数量比较多的元素，又或者列表中包含的元素都是比较长的字符串时，redis就会使用链表作为列表键的底层实现

- 发布订阅

- 慢查询

- 监视器

- redis用链表来构建客户端输出缓冲区（output buffer ）



### 1.2.3 实现

```c
typedef struct listNode{
    //前置节点
    struct listNode *prev;
    
    //后置节点
    struct listNode *next;
    
    //节点的值
    void  * value;
    
}listNode;
```

```c
typedef struct list{
    //表头节点
    listNode *head;
    
    //后置节点
    listNode *tail;
    
    //链表所包含的节点数量
    unsigned long len;
    
    //节点值赋值函数
    void *(*dup)(void *ptr);
    
    //节点值释放函数
    void *(*free)(void *ptr);
    
    //节点值对比函数
    int  (*match)(void *ptr,void *key);
    
}list;
```

虽然仅仅使用多个listNode结构就可以组成链表，但使用list来持有链表，操作会更方便。

### 1.2.4 特性

- 双端
- 无环：表头节点的prev指针和表尾节点的next指针都指向NULL，对链表的访问以NULL为终点。
- 可直接操作表头指针
- 可直接操作表尾指针
- 带链表长度计数器
- 多态：链表节点使用 void* 指针来保存节点值，并且可以通过list结构的 dup、free、match三个属性为节点值设置类型特定函数，所以链表可以用于保存各种不同类型的值。







## 1.3 字典

### 1.3.1 概述

字典，又称为符号表（symbol table）、关联数组 （ associative array ）或映射（map）, 是一种用于保存键值对（key-value pair）的抽象数据结构。



哈希节点：键值对

哈希表：数组+链表  

字典：对哈希表进一步封装



### 1.3.2  作用

- **Redis的数据库就是使用字典来作为底层实现的**：对数据库的增、删、查、改操作也是构建在对字典的操作之上的，简单的说保存在redis的数据是以键值对的形式保存在名为“数据库”的字典里。
- **字典还是哈希键的底层实现之一**：当一个哈希键包含的键值对比较多，又或者键值对中的元素都是比较长的字符串时，Redis就会使用字典作为哈希键的底层实现。



### 1.3.3 实现



![image-20200717223709385](img/image-20200717223709385.png)



哈希表

```c
typedef struct dictht{
    
    //哈希表数组
    dictEnty **table;
    
    //哈希表大小
    unsigned long size;
    
    //哈希表大小掩码，用于计算索引值 总是等于 size-1
    unsigned long sizemask;
    
    //该哈希表已有节点的数量
    unsigned long used;
    
}dictht;
```



table 属性是一个数组，数组中的每个元素都是一个指向 dict.h/dictEntry 结构的指针，每个dictEntry结构保存着一个键值对。



哈希表节点

```c
typedef struct dictEntry{
    
    //键
    void *key;
    
    //值
    union{
        void *val;
        uint64_tu64;
        int64_ts64;
    }v;
    
    //指向下个哈希表节点，形成链表
    struct dictEntry *next;
     
}dictEntry;
```



v属性保存着键值对中的值，其中键值对的值可以是一个指针，或者是一个uint64_t整数，又或者是一个int64_t整数

next 属性是指向另一个哈希表节点的指针，这个指针可以将多个哈希值相同的键值对连接在一起，以此来解决键冲突（collision）的问题。



字典

```c
typedef struct dict{
    
    //类型特定函数
    dictType *type;
    
    //私有数据
    void *privdata;
    
    //哈希表
    dictht ht[2];
    
    //索引  当rehash 不在进行时，值为-1
    int rehashidx;
    
}dict;
```



type属性和privdata属性是针对不同类型的键值对，为创建多态字典而设置的：

​		type 属性是一个指向dictType结构的指针，每个dictType结构保存了一簇用于操作特定类型键值对的函数，Redis 会为用途不同的字典设置不同的类型特定函数。

​		privdata属性则保存了需要传给哪些类型特定函数的可选参数。



```c
typedef struct dictType {
    
    //计算哈希值的函数
    unsigned  int (*hashFunction)(const void *key);
    
    //复制键的函数
    void *(keyDup)(void *privdata,const void *key);
    
    //复制值的函数
    void *(*valDup)(void *privdata,const void *obj);
    
    //对比键的函数
    int (*keyCompare)(void *privdata,const void *key1,const void *key2);
    
    //销毁键的函数
    void (*keyDestructor)(void *privdata,void *key);
    
    //销毁值的函数
    void (*valDestructor)(void *privdata,void *obj);
         
}dictType;
```



ht属性： ht属性是一个包含两个项的数组，数组中的每个项都是一个dictht哈希表，一般情况下，字典只使用ht[0]哈希表，ht[1]哈希表只会对ht[0]哈希表进行rehash时使用



rehash属性： 它记录了rehash目前的进度，如果目前没有在进行rehash，那么它的值为 -1。





### 1.3.4 rehash

为了让哈希表的负载因子（load factor） 维持在一个合理的范围之内，当哈希表保存的键值对数量太多或者太少时，程序需要对哈希表的大小进行相应的扩展或者收缩。



扩展和收缩哈希表的工作可以通过执行rehash（重新散列）操作来完成，Redis对字典的哈希表执行rehash的步骤如下：

1.  为字典的 ht[1] 哈希表分配空间，这个哈希表的空间大小取决于要执行的操作，以及ht[0]当前包含的键值对数量（也即是 ht[0].used属性的值）
   -   如果扩展，ht[1] 的大小为第一个大于等于ht[0].used*2 的2^n （2的n次方幂（mi））
   -   如果收缩，ht[1] 的大小为第一个小于等于ht[0].used的2^n.
2. 将保存在ht[0]中的所有键值对rehash到ht[1]上面：rehash指的是重新计算键的哈希值和索引值，然后将键值对放置到ht[1]哈希表的指定位置上。
3. 当ht[0]包含的所有键值对都迁移到了ht[1]之后（ht[0]变为空表），释放ht[0],将ht[1]设置为ht[0],并在ht[1]新创建一个空白哈希表，为下一次rehash做准备。





//TODO  待续

 
