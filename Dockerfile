FROM openjdk:8-alpine
MAINTAINER beckli <li24361@163.com>
WORKDIR /app
RUN apk upgrade -U \
 && apk add ffmpeg libva-intel-driver \
 && rm -rf /var/cache/*

RUN mkdir /opt/site/log
EXPOSE 8888
COPY ./template/template.ftl /opt/site/cache/template.ftl
COPY ./template/template.mp4 /opt/site/cache/template.mp4
COPY ./target/sorry-java-1.4.0.jar ./app.jar
CMD java -jar ./app.jar
