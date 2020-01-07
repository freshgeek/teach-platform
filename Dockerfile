### 基础镜像，使用alpine操作系统，openjkd使用8u201
FROM openjdk:8u201-jdk-alpine3.9

#系统编码
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8

#声明一个挂载点，容器内此路径会对应宿主机的某个文件夹
VOLUME /app/jar/config/
VOLUME /app/data/

#应用构建成功后的jar文件被复制到镜像内，名字也改成了app.jar
ADD teach-platform-0.0.1-SNAPSHOT.jar /app/jar/app.jar
ADD /src/main/resources/application.yml /app/jar/config/
#启动容器时的进程
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.config.location=/app/jar/config/application.yml","-jar","/app/jar/app.jar"]

#暴露80端口
EXPOSE 80
