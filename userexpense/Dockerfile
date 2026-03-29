# ---- Build Stage ----
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy parent pom and all module poms (needed for multi-module resolution)
COPY pom.xml ./pom.xml
COPY financetrackerai/pom.xml ./financetrackerai/pom.xml
COPY userexpense/pom.xml ./userexpense/pom.xml
COPY emailservice/pom.xml ./emailservice/pom.xml

# Download dependencies (layer cache optimization)
RUN mvn -pl userexpense dependency:go-offline -B

# Copy only userexpense source
COPY userexpense/src ./userexpense/src

# Build only the userexpense module, skip tests
RUN mvn -pl userexpense clean package -DskipTests -B

# ---- Runtime Stage ----
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy built JAR from builder
COPY --from=builder /app/userexpense/target/userexpense-0.0.1-SNAPSHOT.jar app.jar

# Render injects PORT env var; default to 8081
EXPOSE 8081

ENTRYPOINT ["java", "-Xmx256m", "-Xms128m", "-Dspring.profiles.active=prod", "-Dserver.port=${PORT:-8081}", "-jar", "app.jar"]