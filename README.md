思路是参考[sorry](https://github.com/xtyxtyx/sorry)，原作是ruby写的，我就撸了个java 轮子，加了一点新功能，可以生成小一点的图片

优化点：
* 重构首页，方便移动端和PC端访问
* 首页素材全部CDN，加快加载速度
* 增加精简模式，可以生成几百k的用来做表情
* 将文件剥离出程序，可以动态切换


[Demo点击查看](http://118.24.58.55/)

# sorryJava
sorry的java版本


![](http://ww1.sinaimg.cn/large/6efe8aa1ly1fphaxorc98j211i0nywku.jpg)

centOS7下ffmpeg安装

	yum install epel-release -y
	rpm --import http://li.nux.ro/download/nux/RPM-GPG-KEY-nux.ro
	rpm -Uvh http://li.nux.ro/download/nux/dextop/el7/x86_64/nux-dextop-release-0-5.el7.nux.noarch.rpm
    yum install ffmpeg ffmpeg-devel -y


~~centOS7 gifsicle 压缩gif~~，暂时不适用，待后续优化

    ~~yum install gifsicle~~

centOS7安装字体

	yum groupinstall "fonts"
	
修改application.properties,换成自己的文件夹

	cache.template.tempPath=/opt/site/cache/

在自己的文件夹下建立sorry文件夹
下载[template.mp4](http://118.24.58.55/sorry/template.mp4)跟[template.ftl](http://118.24.58.55/sorry/template.ftl)放进去
	
	
打包并运行
	
	mvn package -DskipTests
	java -jar sorry-java-1.0.0.jar
	
访问 http://ip:8888
	




