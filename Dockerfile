# Multi-stage Dockerfile for cricket-scorer
# Stage 1: build with Maven (uses Eclipse Temurin JDK 17)
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /workspace

# Copy pom and source to leverage Docker layer caching
COPY pom.xml ./
COPY src ./src

# Build the application (skip tests for faster image builds)
RUN mvn -B -DskipTests package

# Stage 2: runtime image using Temurin JRE 17
FROM eclipse-temurin:17-jre

ARG JAR_FILE=target/cricket-scorer-1.0.0.jar
WORKDIR /app

# Copy the jar produced in the build stage
COPY --from=build /workspace/${JAR_FILE} /app/app.jar

# Expose the default Spring Boot port
EXPOSE 8080
EXPOSE 5005

# Start the Spring Boot application
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005","-jar", "/app/app.jar"]
