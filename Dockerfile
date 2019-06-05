FROM frolvlad/alpine-java:jdk8-slim
ARG appname
ARG version
ARG versionType
ARG builddir

VOLUME ["/tmp"]
MAINTAINER tom "to_more@mailoo.org"
ADD ./$builddir/$appname-$version.jar /app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS ""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]
