1、删除文件
git rm -r xx.java  //强制删除指定文件 xx.java
git rm -r src/test //强制删除指定文件夹

2、恢复删除的文件
git reset HEAD xx.java  //将删除的文件找回来
git checkout xx.java   //文件就被恢复

git reset HEAD src/test  //将删除的文件夹找回来
git checkout src/test   //将删除的文件恢复过来


3.添加文件
git  add xx.java   //增加文件到暂存区
git  add .         //增加所有文件
git  add  src/test  //增加一个文件夹

一、新建代码库
#当前目录创建一个代码库
 git init
#新建一个目录并且使用这个目录作为代码库
 git init  [project-name]

二、配置
#显示当前的git配置
git config --list
#编辑git配置
git config -e [--global]

#设置提交代码时的用户信息
git config [--global] user.name "[username]"
git config [--global] user.email "[email address]"

三、增加、删除文件
#增加文件到暂存区
git add [file1] [file2] ...

#增加指定目录到暂存区，包括子目录
git add [dir]

#添加当前目录的所有文件到暂存区
git add .

#添加每个变化前，都会要求确认
#对于同一文件的多处变化，可以实现分次提交
git add -p

#改名文件，并将这个改名放入暂存区
git mv  file-original   file-renamed
eg. git mv readme.txt  rdm.txt


四、代码提交
#提交暂存区到仓库区
git commit -m [此次提交的描述]

#提交暂存区指定文件到仓库区
git commit [file1] [file2] [file3]  ... -m [此次提交的描述]

#提交工作去自上次commit之后的变化，直接到仓库区
git commit -a

#提交时显示所有diff 信息
git commit -v

#使用一次新的commit，替换上一次提交
#如果代码没有任何变化，则用来改写上一次commit的提交信息
git commit --amend -m [此次提交信息]

#重做上一次commit，并包括制定文件的新变化
git commit --amend [file1] [file2]

五、分支
#列出所有分支
git branch
#列出所有远程分支
git branch -r

#列出所有本地和远程分支
git branch -a

#新建一个分支，并切换到该分支
git checkout -b [branch]
e.g  git checkout -b dev #创建一个dev分支，并切换到该分支
#新建一个分支，指向制定commit
git branch [分支名称] [commit]

#新建一个分支，与远程分支建立追踪关系
git branch --track [分支名] [远程分支名]
e.g  git branch --track dev  remotedev

#切换到指定分支，并更新工作区
git checkout [branch-name]

#切换到上一个分支
git checkout -

#合并指定分支到当前分支
git merge [branch-name]

#选择一个commit，合并到当前分支
git cherry-pick  [commit]

#删除分支
git branch -d [分支名称]
或者
git branch -D [分支名称]

#删除远程分支
git push origin  --delete  [branch-name]
e.g  git push origin --delete master #删除远程origin仓库的master分支

git branch -dr [remote/branch-name]
e.g  git branch -dr remote/master

六、标签
#列出所有tag
git tag

#新建一个tag在当前commit
git tag [tag-name]