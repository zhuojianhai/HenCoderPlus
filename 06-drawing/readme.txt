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
