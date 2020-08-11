# 一、启动项目

1.下载代码

2.npm install        //记得将 npm 的仓库源设置成淘宝的   并且记得  注意网速 网速过慢会导致 install 失败

3.npm run dev





# 2、杂集

## 2. 1目录讲解

### 2.1.1 api 目录

该目录下，存在都是 js文件 ，都是一些与后台进行交互的方法，使用时 只需将对应的js文件引入到需要使用的地方。

常用使用方式:



js 文件内容格式

```js
import  文件别名  from  "路径";

export const function 方法名(参数列表){
    
    //.... 此处一般进行ajax调用，但一般都会使用被封装的方法，所以此处不进行详细描述，可以参考网上内容，或以后补充
   
    //一般将整个响应都进行返回，交由调用方进行处理
    return 调用结果; 
    
}

```



调用方式

```vue
import 名称  from 文件地址
文件地址有两种写法
../xxxx/xx   ./xxxx/xxx
@views    或者其他
```





### 2.1.2 router 目录

该目录下都是 js文件，其中都是 路由配置



项目中分为静态路由、动态路由



注意：不是很清楚这个静态路由和动态路由的区别	







## 2.2 使用

### 2.2.1 路由跳转

此处参考的经验源于 车辆管理项目，改项目将路由划分为两个大模块

- dynamicRoutes:
  - 改目录下的路由都是直接配置，相当于菜单 或者 菜单项，结合权限校验，判断指定菜单是否可以显示，是否可以访问 
- statisRoutes
  - 该目录下的路由，可以配置子路由， 

 

案例一：

1. 在 dynamicRoutes 目录下，配置指定页面的路由在 ruleManagement.js 文件中，并在资源管理中配置菜单

2.  在staticRoutes 目录下创建一个js文件，在指定菜单路由 配置子路由，状态设置为隐藏，具体内容参考 ModifyExpirationRule.js

3. 在index.js 中，引入步骤2中创建的文件，并整合到路由中，完毕。

   

   注意：有可能有些路由或者菜单 被配置过权限 或者页面有很大的错误，会出现不允许访问的现象

   如果还不懂，可以参考 dynamicRoutes下的 ruleManagement.js 和 staticRoutes 下的 ModifyExpirationRule.js 



```
export default [
  {
    path: "/systemmanage",
    component: () => import("@/views/layout/Layout"),
    redirect: "/systemmanage",
    meta: {
      title: "系统管理"
    },
    hidden: true,
    children: [
      {
        path: "modifyExpirationRule",
        component: () =>
          import("@/views/Remind/ModifyExpirationRule.vue"),
        hidden: true,
        name: "modifyExpirationRule",
        meta: {
          title: "到期提醒-规则"
        }
      }
    ]
  }
];

```

```
                 this.$router.push({
                     path: "/systemmanage/modifyExpirationRule",
                     query: { t: +new Date() }
                 });

```







# 四、element-ui







