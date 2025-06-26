FROM ubuntu:latest
LABEL authors="pr0f1t0"

FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /build

COPY Bazario.ApiGateway/pom.xml .

RUN mvn dependency:go-offline

COPY Bazario.ApiGateway/src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=build /build/target/Bazario.ApiGateway-0.0.1.jar app.jar

EXPOSE 443
EXPOSE 80

ENTRYPOINT ["java", "-jar", "app.jar"]