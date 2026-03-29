# ---- Build Stage ----
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy parent pom for dependency resolution
COPY pom.xml ./pom.xml

# Copy the financetrackerai module
COPY financetrackerai/pom.xml ./financetrackerai/pom.xml

# Download dependencies first (layer cache optimization)
RUN mvn -pl financetrackerai dependency:go-offline -B

# Copy source
COPY financetrackerai/src ./financetrackerai/src

# Build only the financetrackerai module, skip tests
RUN mvn -pl financetrackerai clean package -DskipTests -B

# ---- Runtime Stage ----
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy built JAR from builder
COPY --from=builder /app/financetrackerai/target/financetrackerai-0.0.1-SNAPSHOT.jar app.jar

# Render injects PORT env var; default to 8080
EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Dserver.port=${PORT:-8080}", "-jar", "app.jar"]