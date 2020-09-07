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



![img](img/clipboard-1599028909098.png)