1、删除文件
git rm -r xx.java  //强制删除指定文件 xx.java
git rm -r src/test //强制删除指定文件夹

2、恢复删除的文件
git reset HEAD xx.java  //将删除的文件找回来
git checkout xx.java   //文件就被恢复

git reset HEAD src/test  //将删除的文件夹找回来
git checkout src/test   //将删除的文件恢复过来