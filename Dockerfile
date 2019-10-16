FROM openjdk:8-alpine
MAINTAINER beckli <li24361@163.com>
WORKDIR /opt/site
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.ustc.edu.cn/g' /etc/apk/repositories
RUN apk upgrade -U \
 && apk add ffmpeg libva-intel-driver \
 && rm -rf /var/cache/* \
 && mkdir /opt/site/log \
 && mkdir /opt/site/cache
EXPOSE 8888
COPY ./template/template.ftl /opt/site/cache/sorry/template.ftl
COPY ./template/template.mp4 /opt/site/cache/sorry/template.mp4
COPY ./target/sorry-java-1.4.0.jar /opt/site/app.jar
CMD java -jar /opt/site/app.jar
