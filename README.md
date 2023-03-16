# dreamland
安卓梦境app


## dreamland 前台

环境: jdk11
UI组件库: https://m3.material.io/components

## api 后台
待开发
 
## 克隆仓库代码步骤

1. 配置ssh，若有则跳过https://blog.csdn.net/weixin_42310154/article/details/118340458
2. 在电脑找到一个合适的文件夹，执行 `git clone git@github.com:weiweiyi189/dreamland.git`
 ## 开发步骤

项目地址 https://github.com/weiweiyi189/dreamland

去本项目领取或新建issue，查看该issue对应的分支， 比如 #7。

在idea或者你的编译器的terminal终端， 使用`git checkout -b number` number 是该issue对应的分支。

然后进行代码开发

## 提交代码步骤
 
在 idea 的terminal终端中，进入到 dreamland 下

1.`git status` 查看目前自己修改的文件

2.`git add .` 注意有点 

3.`git commit -m 提交`  提交两字可换成任意文字， 比如 “登陆页面”， 来表达自己所提交的代码功能

4.`git push origin 7` 提交该代码到仓库中, 7 为自己所在的分支， 请注意替换

之后项目管理人员，进行代码审阅， 如无问题，进行合并


## 拉取代码步骤

1. `git status` 查看目前自己修改的文件。 若无文件修改，进行第二步。若有文件修改。 则先进行“提交代码步骤的第2,3步”,或者将该文件修改恢复。。
2. `git fetch --all` 拉取仓库代码
3. `git merge origin/main` 合并仓库代码


