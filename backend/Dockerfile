# syntax=docker/dockerfile:1

# ---------- Build stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -q -e -B -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -q -e -B -DskipTests package

# ---------- Runtime stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

RUN useradd -ms /bin/bash appuser

COPY --from=build /app/target/app.jar app.jar

RUN mkdir -p /app/uploads && chown -R appuser:appuser /app
USER appuser

ENV JAVA_OPTS=""
ENV SERVER_PORT=8085
EXPOSE 8085

CMD ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar --server.port=${SERVER_PORT}"]
