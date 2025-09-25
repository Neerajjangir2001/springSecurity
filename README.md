# springSecurity


---

# 🏥 Hospital Management System (HMS) – Microservices with Spring Boot Security

## 📌 Overview

This project is a **Hospital Management System** built with **Spring Boot (Microservices architecture)**.
It demonstrates **JWT-based authentication, role-based authorization, refresh token handling, logout with blacklist**, and **Spring Cloud components** like **Eureka Discovery** and **API Gateway**.

---

## 🏗 Architecture

```
hospital-management-system/
 ├── discovery-server/       → Eureka service registry
 ├── api-gateway/            → Spring Cloud Gateway (JWT filter here)
 ├── auth-service/           → Login, signup, JWT issue/refresh/logout
 ├── user-service/           → Patients, doctors, admins
 ├── appointment-service/    → Appointment booking, reschedule, cancel
 ├── report-service/         → Reports & analytics (admin only)
```

---

## 🔑 Security Flow

1. **Login/Signup** → via `auth-service`.
2. **JWT issued** → returned to frontend.
3. **Frontend calls Gateway** with `Authorization: Bearer <token>`.
4. **Gateway Filter** validates JWT.

   * ✅ Valid → request forwarded to correct service.
   * ❌ Invalid → `401 Unauthorized`.
5. **Microservices** trust Gateway and don’t re-verify tokens.

---

## ⚙️ Tech Stack

* **Spring Boot 3.x** (microservices)
* **Spring Security (JWT, Refresh Tokens, Logout)**
* **Spring Cloud Gateway** (API Gateway)
* **Spring Cloud Netflix Eureka** (Service Discovery)
* **Spring Data JPA + Hibernate**
* **MySQL / H2 (dev)**
* **Lombok**
* **Docker (optional for containerization)**

---

## 🚀 How to Run

1. Clone repo

   ```bash
   git clone https://github.com/your-username/hms-microservices.git
   cd hms-microservices
   ```
2. Start **Discovery Server**

   ```bash
   cd discovery-server
   mvn spring-boot:run
   ```
3. Start **API Gateway**

   ```bash
   cd api-gateway
   mvn spring-boot:run
   ```
4. Start **Auth Service** (port `8081`)

   ```bash
   cd auth-service
   mvn spring-boot:run
   ```
5. Start other services (`user-service`, `appointment-service`, `report-service`).

---

## 📌 Example Endpoints

### 🔐 Auth Service (`localhost:8081`)

* `POST /auth/register` → Register new user
* `POST /auth/login` → Authenticate user, return JWT
* `POST /auth/refresh` → Get new JWT using refresh token
* `POST /auth/logout` → Invalidate JWT

### 👤 User Service (`localhost:8082`)

* `GET /users/me` → Get logged-in user profile

### 📅 Appointment Service (`localhost:8083`)

* `POST /appointments` → Book appointment
* `PUT /appointments/{id}` → Reschedule appointment
* `DELETE /appointments/{id}` → Cancel appointment

### 📊 Report Service (`localhost:8084`)

* `GET /reports/daily` → Daily summary
* `GET /reports/monthly` → Monthly summary

---

## 🔮 Future Enhancements

* Add **API Gateway rate limiting**
* Integrate **Spring Cloud Config Server**
* Add **Docker + Kubernetes deployment**
* Add **frontend (React/Angular)**

---


