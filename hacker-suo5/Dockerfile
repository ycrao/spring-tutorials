# a application dockerfile using eclipse temurin jdk/jre
# AdoptOpenJDK is moving to the Eclipse Foundation and rebranding.
# see: https://blog.adoptopenjdk.net/2021/03/transition-to-eclipse-an-update/
FROM registry.cn-hangzhou.aliyuncs.com/go-to-mirror/eclipse-temurin:8-jre

LABEL MAINTAINER="raoyc"
LABEL DESCRIPTION="hacker-suo-five application dockerfile based on eclipse-temurin jre8"

# timezone
ENV TZ=Asia/Shanghai
# java options params
ENV JAVA_OPTS="-Xmx256m -Xms256m -Xmn128m"

RUN cp /usr/share/zoneinfo/${TZ} /etc/localtime \
    && echo ${TZ} > /etc/timezone

VOLUME /tmp

ADD target/hacker-suo5-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar --server.port=9000

EXPOSE 9000
