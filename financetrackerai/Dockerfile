# ---- Build Stage ----
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy parent pom and all module poms (needed for multi-module resolution)
COPY pom.xml ./pom.xml
COPY financetrackerai/pom.xml ./financetrackerai/pom.xml
COPY userexpense/pom.xml ./userexpense/pom.xml
COPY emailservice/pom.xml ./emailservice/pom.xml

# Download dependencies (layer cache optimization)
RUN mvn -pl financetrackerai dependency:go-offline -B

# Copy only financetrackerai source
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

ENTRYPOINT ["java", "-Xmx256m", "-Xms128m", "-Dspring.profiles.active=prod", "-Dserver.port=${PORT:-8080}", "-jar", "app.jar"]