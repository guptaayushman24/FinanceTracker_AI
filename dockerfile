# -------------------------
# Build Stage
# -------------------------
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy parent pom
COPY pom.xml .

# Copy module poms
COPY userexpense/pom.xml ./userexpense/pom.xml
COPY emailservice/pom.xml ./emailservice/pom.xml
COPY financetrackerai/pom.xml ./financetrackerai/pom.xml

# Download dependencies
RUN mvn dependency:go-offline -pl userexpense -am

# Copy source code
COPY userexpense/src ./userexpense/src
COPY emailservice/src ./emailservice/src
COPY financetrackerai/src ./financetrackerai/src

# Build only userexpense
RUN mvn clean package -pl userexpense -am -DskipTests

# -------------------------
# Runtime Stage
# -------------------------
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/userexpense/target/userexpense-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
