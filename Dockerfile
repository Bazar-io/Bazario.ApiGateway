FROM ubuntu:latest
LABEL authors="pr0f1t0"

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY Bazario.ApiGateway/target/Bazario.ApiGateway-0.0.1.jar app.jar

EXPOSE 443

ENTRYPOINT ["java", "-jar", "app.jar"]
