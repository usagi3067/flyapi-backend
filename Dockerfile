FROM maven:3.8.1-jdk-8-slim as builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY flyapi-common ./flyapi-common
COPY flyapi-client-sdk ./flyapi-client-sdk
WORKDIR /app/flyapi-common
RUN mvn install -DskipTests

WORKDIR /app/flyapi-client-sdk
RUN mvn install -DskipTests

WORKDIR /app
# Build a release artifact.
RUN mvn package -DskipTests

# Run the web service on container startup.
# 进入flyapi-gateway
CMD ["java","-jar","/app/target/flyapi-backend-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]