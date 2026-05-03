# FinanceTracker AI — Backend

A microservices-based backend for an AI-powered personal finance tracking application. Built with Spring Boot, it provides expense management, JWT authentication, event-driven notifications, AI-driven analytics

---

## Table of Contents

- [Architecture Overview](#architecture-overview)
- [Microservices](#microservices)
  - [FinanceTrackerAI (Auth Service)](#1-financetrackerai-service--port-8080)
  - [UserExpense Service](#2-userexpense-service--port-8081)
  - [Email Service](#3-email-service--port-8082)
  - [UserExpense Chatbot](#4-userexpense-chatbot--port-8084)
- [Tech Stack](#tech-stack)
- [Inter-Service Communication](#inter-service-communication)
- [Authentication & Security](#authentication--security)
- [API Reference](#api-reference)
- [Database Schema](#database-schema)
- [Environment Variables](#environment-variables)
- [Running Locally](#running-locally)
- [Docker & Deployment](#docker--deployment)
- [Production Architecture](#production-architecture)

---

## Architecture Overview

```
┌─────────────────────────────────────────────────────────────────┐
│                         Frontend (Vercel)                       │
│                  finance-tracker-frontend-virid.vercel.app      │
└───────────────────────────┬─────────────────────────────────────┘
                            │ HTTP / JWT
         ┌──────────────────┼
         │                  │                  
         ▼                  ▼                  
┌─────────────────┐ ┌──────────────┐ 
│  FinanceTracker │ │  UserExpense │ 
│   AI Service    │ │   Service    │ 
│   (Port 8080)   │ │  (Port 8081) │ 
└────────┬────────┘ └──────┬───────┘ 
         │                 │
         │   Apache Kafka  │
         │  ┌──────────────┼──────────────────┐
         │  │              │                 
         ▼  ▼              ▼                  What 
     t.user.id      t.user.details       
         │                 │
         ▼                 ▼
 ┌───────────────┐  ┌──────────────┐
 │  UserExpense  │  │ EmailService │
 │  (consumer)   │  │ (Port 8082)  │
 └───────────────┘  └──────────────┘
         │
         ▼
┌──────────────────┐
│ UserExpense      │
│ Chatbot          │
│ (Port 8084)      │
└──────────────────┘

Shared Infrastructure:
  MySQL (primary data)  |  PostgreSQL + pgvector (AI/embeddings)
  Redis (caching)       |  Apache Kafka (messaging)
```

---

## Microservices

### 1. FinanceTrackerAI Service — Port 8080

The core authentication and user management service. It handles user registration, login, JWT issuance, OTP-based password resets, and publishes events to Kafka on user actions.

**Key Responsibilities:**
- User signup / signin with BCrypt password hashing
- JWT token generation and validation (HMAC-SHA256, 24h expiry)
- OTP generation, validation, and password reset flow
- Publishes user data to Kafka topics after signup/login
- Exposes user profile and registered expense types

**Key Classes:**

| Layer | Class | Responsibility |
|---|---|---|
| Controller | `SignUpController` | Handles `/auth/signup` |
| Controller | `SignInController` | Handles `/auth/signin` |
| Controller | `OtpController` | OTP generate / validate / reset |
| Service | `UserService` | User creation logic |
| Service | `OtpService` | OTP lifecycle management |
| Service | `AuthService` | Auth logic + Kafka publishing |
| Security | `WebSecurityConfig` | Security filter chain |
| Security | `JWTAuthenticationFilter` | JWT validation on each request |
| Security | `AuthUtil` | Token generation and parsing |
| Kafka | `Producer` | Publishes to `t.user.id`, `t.user.details` |
| Repository | `UserRepository` | JPA queries for users |
| Repository | `OTPRepository` | JPA queries for OTPs |

**Entities:**

```
UserModel
├── id (Long)
├── firstName (String)
├── lastName (String)
├── emailAddress (String, indexed)
├── password (String, BCrypt hashed)
└── user_expense (List<String>)

PasswordOTP
├── id (Long)
├── userId (Long, unique)
├── otp (String)
└── createdAt (LocalDateTime)
```

---

### 2. UserExpense Service — Port 8081

The expense management and analytics service. It records expenses, provides filtering/sorting by date and payment mode, generates pie/bar chart data, exports to Excel, and uses Spring AI for expense analysis.

**Key Responsibilities:**
- Add, delete, and retrieve expense records
- Filter expenses by month, year, specific date, or payment mode
- Calculate total expenditure across various dimensions
- Generate pie chart and bar chart data for analytics
- Export expense data to Excel (Apache POI)
- Cache frequently accessed data in Redis
- Consume user login events from Kafka to track active users

**Key Classes:**

| Layer | Class | Responsibility |
|---|---|---|
| Controller | `UserExpense` | Core CRUD + filtering endpoints |
| Controller | `TotalExpenditure` | Aggregate totals by period and payment mode |
| Controller | `GraphController` | Pie chart data endpoints |
| Controller | `BarGraphController` | Bar chart data endpoints |
| Controller | `PaymentModeFilterController` | Filter by payment method |
| Service | `UserExpenseServiceImpl` | Business logic for expense operations |
| Service | `TotalExpenditureService` | Aggregation calculations |
| Service | `ExpenseAnalyzerService` | AI-driven expense insights (OpenAI) |
| Service | `GenerateExcelService` | Excel report generation |
| Service | `ReddisService` | Redis caching layer |
| Kafka | `KafkaConsumer` | Consumes `t.user.id` topic |
| Repository | `UserExpenseRepository` | Complex JPQL expense queries |
| Repository | `PaymentModeRepository` | Payment mode queries and filters |

**Entities:**

```
UserExpense
├── id (Long)
├── user_id (Long)
├── ExpenseType (String)
├── Value (Double)
├── Description (String)
├── expenseDate (LocalDate)
└── paymentMode (PaymentMode, ManyToOne)

PaymentMode
├── id (Long)
└── mode (String)

ExpenseScheduler
└── (recurring expense tracking)
```

---

### 3. Email Service — Port 8082

A lightweight, event-driven service that sends welcome emails to newly registered users. It listens to the `t.user.details` Kafka topic and dispatches HTML emails via Mailtrap SMTP.

**Key Responsibilities:**
- Consume user registration events from Kafka
- Send formatted HTML welcome emails with user details
- Stateless — no database required

**Key Classes:**

| Layer | Class | Responsibility |
|---|---|---|
| Kafka Config | `KafkaConsumerConfig` | Consumer for `t.user.details` |
| Service | `EmailService` | Build and send welcome email |
| Model | `UserDetailResponse` | Deserialized Kafka message DTO |

**Kafka Message Flow:**
```
financetrackerai → Kafka (t.user.details) → emailservice → Mailtrap SMTP → User inbox
```

---

### 4. UserExpense Chatbot — Port 8084

An AI-powered chatbot service that converts expense queries into vector embeddings using OpenAI. It stores embeddings in PostgreSQL with the pgvector extension for semantic similarity search.

**Key Responsibilities:**
- Generate text embeddings via OpenAI models (Spring AI)
- Store and query vector embeddings in PostgreSQL (pgvector)
- Enable semantic search over expense records

**Key Classes:**

| Layer | Class | Responsibility |
|---|---|---|
| Controller | `EmbeddingController` | POST `/embeddings` |
| Service | `EmbeddingService` | Calls OpenAI EmbeddingModel |
| Model | `ChatBotQuery` | Query storage entity |

**Database:** PostgreSQL (Supabase) with pgvector extension



## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 / 21 |
| Framework | Spring Boot 3.4.x – 4.0.0 |
| Security | Spring Security, JWT (JJWT 0.11.5), BCrypt |
| Databases | MySQL (primary), PostgreSQL + pgvector (AI/vectors) |
| ORM | Spring Data JPA / Hibernate |
| Messaging | Apache Kafka |
| Caching | Redis (Lettuce client) |
| AI / ML | Spring AI (OpenAI), OpenCV 4.9.0 |
| Email | Spring Mail (Mailtrap SMTP) |
| Excel Export | Apache POI |
| Build | Maven 3.9.6 |
| Containers | Docker (multi-stage builds) |
| Deployment | Railway (backend), Vercel (frontend), Supabase (PostgreSQL), RedisLabs |

---

## Inter-Service Communication

### Kafka Topics

| Topic | Producer | Consumer | Payload |
|---|---|---|---|
| `t.user.id` | financetrackerai (on login) | userexpense | User ID (Long) |
| `t.user.details` | financetrackerai (on signup) | emailservice | `SignupResponsedto` (JSON) |

### JWT Propagation

All protected endpoints require a `Bearer` token in the `Authorization` header. Each service independently validates the JWT using the shared HMAC-SHA256 secret. The `userId` claim is extracted per request to scope queries to the authenticated user.

```
Client → Authorization: Bearer <jwt>
             └── Each service validates independently (no central auth gateway)
```

---

## Authentication & Security

### JWT Flow

```
1. POST /auth/signup  →  BCrypt hash password  →  Store user  →  Kafka event
2. POST /auth/signin  →  Validate credentials  →  Issue JWT (24h)
3. Subsequent requests →  JWTAuthenticationFilter →  Validate token →  Set SecurityContext
```

### JWT Token Structure

```
Header: { alg: HS256 }
Payload:
  sub: <emailAddress>
  userId: <userId>
  exp: <now + 24h>
Signature: HMAC-SHA256(<secret>)
```

### Password Reset (OTP Flow)

```
POST /generateotp   →  Generate OTP  →  Store with timestamp
POST /validateotp   →  Verify OTP & expiry
POST /resetpassword →  BCrypt new password  →  Update DB
POST /deleteotp     →  Clean up OTP record
```

### Security Filter Chain

- CSRF: Disabled (stateless REST API)
- Session: `STATELESS`
- Open endpoints: `/auth/**`, `/public/**`, `/imageembedding`, `/saveUserImage`, `/faceauthentication`, `/error/**`
- All other endpoints: require valid JWT

---

## API Reference

### FinanceTrackerAI Service (`:8080`)

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| POST | `/auth/signup` | No | Register a new user |
| POST | `/auth/signin` | No | Login and receive JWT |
| POST | `/generateotp` | No | Generate OTP for password reset |
| POST | `/validateotp` | No | Validate OTP |
| POST | `/deleteotp` | No | Delete OTP record |
| POST | `/resetpassword` | No | Reset password using OTP |
| GET | `/getUserSignupData` | Yes | Get current user's signup info |
| GET | `/registeredexpensebyuser` | Yes | List registered expense categories |
| POST | `/profile` | Yes | Get user profile details |

### UserExpense Service (`:8081`)

#### Expense Management

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| POST | `/userexpense` | Yes | Record a new expense |
| POST | `/addnewexpense` | Yes | Add a new expense category |
| POST | `/deleteexpense` | Yes | Delete an expense record |
| POST | `/sortexpense` | Yes | Sort expenses |
| GET | `/allexpense` | Yes | Fetch all expenses for user |
| POST | `/userexpensebymonth` | Yes | Filter expenses by month |
| POST | `/userexpensebyyear` | Yes | Filter expenses by year |
| POST | `/indivisualexpense` | Yes | Get totals per category |
| POST | `/currentDayExpense` | Yes | Expenses for today |
| POST | `/expenseOnday` | Yes | Expenses on a specific date |
| GET | `/availableexpense` | Yes | Available expense categories |
| POST | `/deleteuserexpense` | Yes | Delete expense by ID |

#### Totals & Analytics

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| POST | `/totalexpensebymonth` | Yes | Total expenditure by month |
| POST | `/totalexpensebyyear` | Yes | Total expenditure by year |
| GET | `/totalexpennseoncurrentdate` | Yes | Total for today |
| POST | `/totalexpensebyyearpaymentmode` | Yes | Yearly total by payment mode |
| POST | `/totalexpensebymonthpaymentmode` | Yes | Monthly total by payment mode |
| GET | `/totalexpennseoncurrentdatepaymentmode` | Yes | Today's total by payment mode |
| POST | `/toalexpenseondate` | Yes | Total for a specific date |

#### Charts & Filters

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| GET | `/piechartbymonth` | Yes | Pie chart data by month |
| GET | `/piechartbyyear` | Yes | Pie chart data by year |
| POST | `/filterbypaymentmode` | Yes | Filter expenses by payment mode |

### UserExpense Chatbot (`:8084`)

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| POST | `/embeddings` | — | Generate text embedding (OpenAI) |

### Face Recognition Service

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| POST | `/imageembedding` | No | Generate face embedding from image |
| POST | `/saveUserImage` | No | Save face embedding to DB |
| POST | `/faceauthentication` | No | Authenticate via face recognition |

---

## Database Schema

### MySQL (financetrackerai + userexpense)

```
user_model
├── id           BIGINT PK
├── first_name   VARCHAR
├── last_name    VARCHAR
├── email_address VARCHAR (INDEX)
└── password     VARCHAR (BCrypt)

password_otp
├── id           BIGINT PK
├── user_id      BIGINT (UNIQUE)
├── otp          VARCHAR
└── created_at   DATETIME

user_expense
├── id           BIGINT PK
├── user_id      BIGINT
├── expense_type VARCHAR
├── value        DOUBLE
├── description  VARCHAR
├── expense_date DATE
└── payment_mode_id BIGINT (FK → payment_mode)

payment_mode
├── id           BIGINT PK
└── mode         VARCHAR

expense_scheduler
└── (recurring expense tracking fields)
```


chatbot_query
└── (query history fields)
```

---

## Environment Variables

The following environment variables should be set in production. **Never commit credentials to source control.**

### FinanceTrackerAI

| Variable | Description |
|---|---|
| `DB_URL` | MySQL connection URL |
| `DB_USERNAME` | MySQL username |
| `DB_PASSWORD` | MySQL password |
| `JWT_SECRET` | HMAC-SHA256 signing key |
| `KAFKA_BOOTSTRAP_SERVERS` | Kafka broker address |
| `REDIS_HOST` | Redis host |
| `REDIS_PORT` | Redis port |
| `REDIS_PASSWORD` | Redis password |
| `MAIL_HOST` | SMTP host |
| `MAIL_PORT` | SMTP port |
| `MAIL_USERNAME` | SMTP username |
| `MAIL_PASSWORD` | SMTP password |
| `CORS_ALLOWED_ORIGIN` | Frontend URL for CORS |

### UserExpense

| Variable | Description |
|---|---|
| `OPENAI_API_KEY` | OpenAI API key for expense analysis |
| `DB_URL` | MySQL connection URL |
| `KAFKA_BOOTSTRAP_SERVERS` | Kafka broker address |
| `REDIS_HOST` / `REDIS_PORT` | Redis connection |

### Email Service

| Variable | Description |
|---|---|
| `KAFKA_BOOTSTRAP_SERVERS` | Kafka broker address |
| `MAIL_HOST` | SMTP host |
| `MAIL_USERNAME` | SMTP username |
| `MAIL_PASSWORD` | SMTP password |

### UserExpense Chatbot

| Variable | Description |
|---|---|
| `OPENAI_API_KEY` | OpenAI API key |
| `DB_URL` | PostgreSQL (Supabase) connection URL |

---

## Running Locally

### Prerequisites

- Java 21
- Maven 3.9+
- Docker & Docker Compose
- MySQL 8.x
- Redis

### 1. Start Infrastructure

```bash
# From the userexpense directory (includes Kafka + Redis)
cd userexpense
docker-compose up -d
```

This starts:
- Apache Kafka on `:9092`
- Zookeeper on `:2181`
- Kafdrop (Kafka UI) on `:9000`
- Redis on `:6379`

Start MySQL separately or use a managed instance.

### 2. Configure Application Properties

Copy and edit the local `application.properties` for each service:

```
financetrackerai/src/main/resources/application.properties
userexpense/src/main/resources/application.properties
emailservice/src/main/resources/application.properties
```

### 3. Build All Services

```bash
# From root
mvn clean install -DskipTests
```

### 4. Start Each Service

```bash
# Terminal 1 - Auth service
cd financetrackerai
mvn spring-boot:run

# Terminal 2 - Expense service
cd userexpense
mvn spring-boot:run

# Terminal 3 - Email service
cd emailservice
mvn spring-boot:run

# Terminal 4 - Chatbot (optional)
cd userexpensechatbot
mvn spring-boot:run
```

---

## Docker & Deployment

Each service has a multi-stage Dockerfile optimized for minimal image size.

### Build an Image

```bash
# Example for financetrackerai
cd financetrackerai
docker build -t financetrackerai:latest .
```

### Dockerfile Structure (all services)

```dockerfile
# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-Xmx256m", "-Xms128m", "-jar", "app.jar"]
```

### Run with Production Profile

```bash
docker run -e SPRING_PROFILES_ACTIVE=prod \
  -e DB_URL=... \
  -e KAFKA_BOOTSTRAP_SERVERS=... \
  -p 8080:8080 financetrackerai:latest
```

---

## Production Architecture

The backend is deployed on **Railway** with the following setup:

| Service | Platform | URL / Connection |
|---|---|---|
| FinanceTrackerAI | Railway | Port 8080 |
| UserExpense | Railway | Port 8081 |
| EmailService | Railway | Port 8082 |
| Kafka | Railway | `kafka-broker.railway.internal:9092` |
| MySQL | Filess.io | Managed MySQL instance |
| Redis | RedisLabs | Redis Cloud (SSL, US East) |
| PostgreSQL | Supabase | pgvector-enabled instance |
| Frontend | Vercel | finance-tracker-frontend-virid.vercel.app |

### Connection Pooling (Production)

- `max-pool-size: 2`
- `min-idle: 1`

Tuned for Railway's free-tier resource constraints.

---

## Project Structure

```
FinanceTracker_AI/
├── pom.xml                      # Parent POM (multi-module)
├── financetrackerai/            # Auth & user management service
│   ├── src/main/java/com/example/financetrackerai/
│   │   ├── config/              # CORS, Spring config
│   │   ├── controller/          # REST controllers
│   │   ├── kafka/               # Kafka producer
│   │   ├── model/               # JPA entities
│   │   ├── repository/          # Spring Data repos
│   │   ├── security/            # JWT filter, WebSecurityConfig
│   │   └── service/             # Business logic
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   └── application-prod.properties
│   └── Dockerfile
├── userexpense/                 # Expense tracking & analytics service
│   ├── src/main/java/com/example/userexpense/
│   │   ├── config/              # CORS, Redis, Kafka config
│   │   ├── controller/          # Expense, total, graph controllers
│   │   ├── kafka/               # Kafka consumer
│   │   ├── model/               # JPA entities
│   │   ├── repository/          # Complex JPA queries
│   │   └── service/             # Expense, analytics, email services
│   ├── docker-compose.yml       # Local infra (Kafka, Redis)
│   └── Dockerfile
├── emailservice/                # Welcome email service
│   ├── src/main/java/com/example/emailservice/
│   │   ├── config/              # Kafka consumer config
│   │   ├── controller/          # Test endpoint
│   │   ├── model/               # Kafka message DTOs
│   │   └── service/             # Email sending logic
│   └── Dockerfile
├── userexpensechatbot/          # AI chatbot (embeddings)
│   └── src/main/java/com/example/userexpensechatbot/
│       ├── controller/
│       ├── model/
│       └── service/
├── facerecognition/             # Face auth service
│   └── src/main/java/com/example/facerecognition/
│       ├── controller/
│       ├── model/
│       ├── repository/
│       └── service/
└── kafka/                       # Kafka Docker config (KRaft mode)
    └── Dockerfile
```
