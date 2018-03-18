思路是参考[sorry](https://github.com/xtyxtyx/sorry)，原作是ruby写的，我就撸了个java 轮子


[Java版本点击查看](http://txtxtx.com.cn/)

# sorryJava
sorry的java版本


![](http://ww1.sinaimg.cn/large/6efe8aa1ly1fphaxorc98j211i0nywku.jpg)

centOS7下ffmpeg安装

	yum install epel-release -y
	rpm --import http://li.nux.ro/download/nux/RPM-GPG-KEY-nux.ro
	rpm -Uvh http://li.nux.ro/download/nux/dextop/el7/x86_64/nux-dextop-release-0-5.el7.nux.noarch.rpm
    yum install ffmpeg ffmpeg-devel -y

centOS7 gifsicle 压缩gif ，暂时不用下载

    yum install gifsicle

centOS7安装字体

	yum groupinstall "fonts"
	
修改application.properties,换成自己的文件夹

	cache.template.tempPath=/opt/site/cache/

在自己的文件夹下建立sorry文件夹
下载[template.mp4](http://txtxtx.com.cn/sorry/template.mp4)跟[template.ftl](http://txtxtx.com.cn/sorry/template.ftl)放进去
	
	
打包并运行
	
	mvn package -DskipTests
	java -jar sorry-1.0.0.jar
	




