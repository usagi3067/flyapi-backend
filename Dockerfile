FROM maven:3.8.1-jdk-8-slim as builder

# Copy local code to the container image.
# 镜像中的工作目录  新建一个app目录
WORKDIR /app
# 把pom.xml  flyapi-common  flyapi-client-sdk  src  复制到镜像中的app目录下
COPY pom.xml .
COPY src ./src
COPY flyapi-common ./flyapi-common
COPY flyapi-client-sdk ./flyapi-client-sdk
WORKDIR /app/flyapi-common
RUN mvn install -DskipTests
WORKDIR /app/flyapi-client-sdk
RUN mvn install -DskipTests

WORKDIR /app
# Build a release artifact. -D  define定义
RUN mvn package -DskipTests

# Run the web service on container startup.
# 进入flyapi-gateway

# run   如何不执行这个命令
# CMD ["java","-jar","/app/target/flyapi-backend-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]