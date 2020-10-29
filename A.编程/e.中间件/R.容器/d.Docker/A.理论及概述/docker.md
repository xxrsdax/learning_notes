# 1.概述

## 1.1 概述

![](img/image-20200902115814125.png)



## 1.2 术语

- **docker镜像（Images）**: Docker 镜像是用于创建Docker容器的模板。
- **docker容器（Container）**:容器是独立运行的一个或一组应用。
- **docker客户端（client）**: 客户端通过命令行或者其他工具使用Docker API 与 Docker的守护进程通信.
- **docker主机（Host）**:一个物理或者虚拟的机器用于只需Docker守护进程和容器。
- **docker仓库（Registry）**：Docker仓库用来保存镜像，可以理解为代码控制中的代码仓库。
  - DockerHub （Https://hub.docker.com） 提供了庞大的镜像集合供使用。



## 1.3 使用步骤

1.安装Docker

2.去Docker仓库中找到这个软件对应的镜像

3.使用Docker运行这个镜像，这个镜像就会生成一个Docker容器

4.对容器的启动停止就是对软件的停止



# 2.使用Docker



## 2.1 安装Docker

- **查看centos版本**
  - uname  -r
  - Docker要求CentOS系统的内核版本高于 3.10
- **升级软件包及内核**（内核低于3.10时需要）
  - yum update
- **安装docker**
  - yum  install docker
- **启动docker**
  - systemctl  start  docker
- **将docker服务器设置为开机启动**
  - systemctl  enable  docker



**停止docker**

​	systemctl  stop docker



## 2.2 镜像操作

### 2.2.1在client检索镜像

docker  search 镜像名	



![img](img/clipboard-1599019960998.png)



### 2.2.2拉取镜像

docker  pull  镜像:版本号 

![img](img/clipboard-1599023416673.png)



### 2.2.3查询本地镜像

docker  images

![img](img/clipboard-1599028878774.png)



### 2.2.4删除本地镜像

docker  rmi  镜像id

![img](img/clipboard-1599028909098.png)





## 2.3容器操作

### **2.3.1 搜索镜像**

[root@localhost ~]# docker search tomcat



### 2.3.2拉取镜像

[root@localhost ~]# docker pull tomcat



### 2.3.3根据镜像启动容器

docker run --name mytomcat -d tomcat:latest



### 2.3.4查看运行中的容器

docker ps 



### 2.3.5停止运行中的容器

docker stop  容器的id



### 2.3.6查看所有的容器

docker ps -a



### 2.3.7启动容器

docker start 容器id



### 2.3.8删除一个容器

 docker rm 容器id



### 2.3.9查看容器的日志

docker logs container-name/container-id



### 2.3.10关闭linux的防火墙

service firewalld status ；查看防火墙状态

service firewalld stop：关闭防火墙









### 2.3.12 宿主机与容器之间的文件拷贝

#### 1、从容器里面拷文件到宿主机？

   答：在宿主机里面执行以下命令

​       docker cp 容器名：要拷贝的文件在容器里面的路径    要拷贝到宿主机的相应路径 

   示例： 假设容器名为testtomcat,要从容器里面拷贝的文件路为：/usr/local/tomcat/webapps/test/js/test.js, 现在要将test.js从容器里面拷到宿主机的/opt路径下面，那么命令应该怎么写呢？

   答案：在宿主机上面执行命令

```
docker cp testtomcat：/usr/local/tomcat/webapps/test/js/test.js         /opt
```

#### 2、从宿主机拷文件到容器里面

   答：在宿主机里面执行如下命令

​       docker cp 要拷贝的文件路径 容器名：要拷贝到容器里面对应的路径

​    示例：假设容器名为testtomcat,现在要将宿主机/opt/test.js文件拷贝到容器里面的/usr/local/tomcat/webapps/test/js路径下面，那么命令该怎么写呢？

​    答案：在宿主机上面执行如下命令   

```
docker cp /opt/test.js testtomcat：/usr/local/tomcat/webapps/test/js
```



#### 3.需要注意的是，不管容器有没有启动，拷贝命令都会生效。



### 2.3.13 进入容器内部

docker exec -it 容器名 bash







# 3 例子

## **3.1启动一个做了端口映射的tomcat**

[root@localhost ~]# docker run -d -p 8888:8080 tomcat

-d：后台运行

-p: 将主机的端口映射到容器的一个端口   主机端口:容器内部的端口



更多命令参看

https://docs.docker.com/engine/reference/commandline/docker/

可以参考每一个镜像的文档





## 3.1 安装 nginx

准备:docker已安装

- docker pull nginx:版本号
  - 拉取镜像
- docker run --name nginxName  -p  宿主机端口:80    -d        nginx
  - -v /home/docker-nginx/nginx.conf:/etc/nginx/nginx.conf 
  - -v /home/docker-nginx/log:/var/log/nginx 
  - -v /home/docker-nginx/conf.d/default.conf:/etc/nginx/conf.d/default.conf 
  -  -v /home/nginx/www:/usr/share/nginx/html
  - --name 给你启动的容器起个名字，以后可以使用这个名字启动或者停止容器
  - -p 映射端口，将docker宿主机的80端口和容器的80端口进行绑定 
  - -v 挂载文件用的，
  - 第一个-v 表示将你本地的nginx.conf覆盖你要起启动的容器的nginx.conf文件，
  - 第二个表示将日志文件进行挂载，就是把nginx服务器的日志写到你docker宿主机的/home/docker-nginx/log/下面
  - 第三个-v 表示的和第一个-v意思一样的。
  - 第四个-v 将我们自己创建的 www 目录挂载到容器的 /usr/share/nginx/html。
  - -d 表示后台启动
- 先简单启一个容器  然后从容器中cp出配置文件
  - docker cp 容器名:/etc/nginx/nginx.conf    宿主机指定目录
- 删除上一次启动容器,重新以一下命令启动
- 目前电脑最终使用命令  挂载了 根目录  配置文件 默认配置文件 日志

```
docker run  --name nginx  -p 81:80 -v /home/xxrsdax/nginx/nginx-config/nginx.conf:/etc/nginx/nginx.conf  -v /home/xxrsdax/nginx/log:/var/log/nginx  -v /home/xxrsdax/nginx/nginx-config/conf.d/default.conf:/etc/nginx/conf.d/default.conf  -v /home/xxrsdax/nginx/nginx-data:/usr/share/nginx/html -d nginx

```



