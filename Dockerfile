# 使用 OpenJDK 17 作为基础镜像，该镜像包含 JDK 17 环境
# 该镜像适用于需要编译或运行基于 JDK 17 的 Java 应用程序

FROM openjdk:17

# 设置容器中的工作目录为 /app
# 所有后续操作（如文件复制、命令执行等）都会基于该目录进行

ENV TZ=Asia/Shanghai


WORKDIR /app

# 将本地的 JAR 文件 复制到容器的 /app 目录下
# COPY 命令将指定路径的文件从构建上下文复制到镜像中的目标路径

COPY UsTwoBacked-1.0.0-SNAPSHOT.jar /docker/ustwo/UsTwoBacked-1.0.0-SNAPSHOT.jar

# 设置环境变量 JAR_FILE，指向 JAR 文件的名称
# 环境变量可以在容器运行时被应用程序或其他脚本访问
# 这里设置环境变量方便在 Dockerfile 中或运行时引用 JAR 文件

ENV JAR_FILE=UsTwoBacked-1.0.0-SNAPSHOT.jar

# 暴露容器的 8201端口，使得主机能够与容器的指定端口进行通信
# 通常用于 Web 服务或应用程序监听端口
# 可以根据应用需要更改为其他端口号

EXPOSE 1104

# 定义容器启动时的默认命令，使用 ENTRYPOINT 设置为 java -jar 来启动应用
# 这行命令会在容器启动时运行 Java 应用，加载指定的 JAR 文件
# 如果没有其他命令传入，ENTRYPOINT 将执行默认的 java -jar jar包名称

ENTRYPOINT ["java", "-Duser.timezone=Asia/Shanghai", "-jar", "/docker/ustwo/UsTwoBacked-1.0.0-SNAPSHOT.jar"]
