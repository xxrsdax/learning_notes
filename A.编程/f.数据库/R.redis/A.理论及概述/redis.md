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





