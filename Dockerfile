# 第一阶段：构建 jar（如果你已经有 jar，这一段可以忽略）
# FROM gradle:7.4.2-jdk17 AS builder
# WORKDIR /app
# COPY . .
# RUN gradle build --no-daemon

# 第二阶段：运行 jar
FROM gcr.io/distroless/java17-debian11

# 设置工作目录
WORKDIR /app

# 拷贝 jar 包（已构建好的 jar）
COPY target/UsTwoBacked-1.0.0-SNAPSHOT.jar app.jar

# 暴露端口（供参考，对 distroless 没有实际作用，文档性为主）
EXPOSE 1104

# 设置容器启动命令
ENTRYPOINT ["java", "-Duser.timezone=Asia/Shanghai", "-jar", "app.jar"]
