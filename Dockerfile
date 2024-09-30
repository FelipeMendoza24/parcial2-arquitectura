FROM gradle:8.8.0-jdk17 AS build

WORKDIR /app

COPY build.gradle settings.gradle /app/

COPY src /app/src

RUN gradle assemble --no-daemon

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build build\libs\parcial-0.0.1-SNAPSHOT-plain.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java" ,"-jar", "app.jar"]