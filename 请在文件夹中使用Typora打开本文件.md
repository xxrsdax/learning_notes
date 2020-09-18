#  1.拉取笔记仓库

根据您获得的仓库地址使用 git或任意其他工具拉取分支代码

目前分支有

- master: 由管理员维护
- develop：日常提交
- 可以自己创建其他分支，尽量公用分支

```
//克隆仓库
git  clone  仓库地址

//查看本地分支
git branch

//切换分支
git checkout  分支名

//创建本地分支
git branch 分支名

//代码提交类
git add .
git commit -m"提交信息"
git pull 远程地址名称  分支名称
git push 远程地址名称  分支名称

//合并     将指定分支 合并到当前所在分支
git merage 指定分支名    


```



![image-20200918155235077](img/image-20200918155235077.png)



安装git后  在指定目录下 右键->点击 Git Bash Here  进行git命令面板