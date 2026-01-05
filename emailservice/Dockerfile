# ----------- BUILD STAGE -----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
FROM --platform=linux/amd64 python:3.7-alpine
WORKDIR /app
# Copy pom first for dependency caching
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the JAR
RUN mvn clean package -DskipTests


# ----------- RUNTIME STAGE -----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# COPY from build stage (IMPORTANT PATH)
COPY --from=build /emailservice/target/*.jar app.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]




