# Hospital Management System — Cloud-Native Microservices

A distributed, cloud-native hospital management system built with Spring Boot and Docker.

## Architecture

| Service             | Port | Description                        |
|---------------------|------|------------------------------------|
| Config Server       | 8888 | Centralized configuration (dev/prod) |
| Patient Service     | 8081 | Manage patient records              |
| Doctor Service      | 8082 | Manage doctor profiles              |
| Appointment Service | 8083 | Schedule and manage appointments    |
| MySQL               | 3306 | Relational database (separate DBs per service) |

## Prerequisites

- Java 17+
- Maven 3.8+
- Docker & Docker Compose
- MySQL 8 (if running locally without Docker)

---

## Run with Docker (Recommended)

```bash
# Clone the repo
git clone <your-repo-url>
cd hospital-management

# Start all services
docker-compose up --build

# Run in background
docker-compose up --build -d

# Stop all services
docker-compose down
```

All services will start in the correct order. Config server starts first, then MySQL, then the three microservices.

---

## Run Locally (without Docker)

### 1. Start MySQL and create databases

```sql
CREATE DATABASE patient_db_dev;
CREATE DATABASE doctor_db_dev;
CREATE DATABASE appointment_db_dev;
```

### 2. Start Config Server

```bash
cd config-server
mvn spring-boot:run
```

### 3. Start each microservice in separate terminals

```bash
# Terminal 1
cd patient-service
mvn spring-boot:run

# Terminal 2
cd doctor-service
mvn spring-boot:run

# Terminal 3
cd appointment-service
mvn spring-boot:run
```

---

## Switch Profiles (dev / prod)

**Docker:** Edit `docker-compose.yml` and change:
```yaml
environment:
  SPRING_PROFILES_ACTIVE: prod
```

**Local:** Pass as JVM arg:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

> In prod, set environment variables `DB_USERNAME` and `DB_PASSWORD` before running.

---

## API Reference

### Patient Service — `http://localhost:8081`

| Method | Endpoint                        | Description              |
|--------|---------------------------------|--------------------------|
| GET    | /api/patients                   | Get all patients          |
| GET    | /api/patients/{id}              | Get patient by ID         |
| POST   | /api/patients                   | Create a new patient      |
| PUT    | /api/patients/{id}              | Update a patient          |
| DELETE | /api/patients/{id}              | Delete a patient          |
| GET    | /api/patients/search?lastName=X | Search by last name       |

### Doctor Service — `http://localhost:8082`

| Method | Endpoint                              | Description                  |
|--------|---------------------------------------|------------------------------|
| GET    | /api/doctors                          | Get all doctors               |
| GET    | /api/doctors/{id}                     | Get doctor by ID              |
| POST   | /api/doctors                          | Create a new doctor           |
| PUT    | /api/doctors/{id}                     | Update a doctor               |
| DELETE | /api/doctors/{id}                     | Delete a doctor               |
| GET    | /api/doctors/available                | Get all available doctors     |
| GET    | /api/doctors/specialization/{spec}    | Get by specialization         |

### Appointment Service — `http://localhost:8083`

| Method | Endpoint                              | Description                   |
|--------|---------------------------------------|-------------------------------|
| GET    | /api/appointments                     | Get all appointments           |
| GET    | /api/appointments/{id}               | Get appointment by ID          |
| POST   | /api/appointments                     | Create an appointment          |
| PUT    | /api/appointments/{id}               | Update an appointment          |
| PATCH  | /api/appointments/{id}/status?status= | Update status only             |
| DELETE | /api/appointments/{id}               | Delete an appointment          |
| GET    | /api/appointments/patient/{id}        | Get by patient ID              |
| GET    | /api/appointments/doctor/{id}         | Get by doctor ID               |
| GET    | /api/appointments/status/{status}     | Filter by status               |

Appointment statuses: `SCHEDULED`, `CONFIRMED`, `COMPLETED`, `CANCELLED`

---

## Sample Postman Requests

### Create a Patient
```json
POST http://localhost:8081/api/patients
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "555-1234",
  "dateOfBirth": "1990-05-15",
  "gender": "MALE",
  "address": "123 Main St, Houston TX"
}
```

### Create a Doctor
```json
POST http://localhost:8082/api/doctors
Content-Type: application/json

{
  "firstName": "Sarah",
  "lastName": "Smith",
  "email": "sarah.smith@hospital.com",
  "phoneNumber": "555-5678",
  "specialization": "Cardiology",
  "licenseNumber": "TX-12345",
  "yearsOfExperience": 10,
  "department": "Cardiology"
}
```

### Create an Appointment
```json
POST http://localhost:8083/api/appointments
Content-Type: application/json

{
  "patientId": 1,
  "doctorId": 1,
  "appointmentDate": "2026-06-15T10:00:00",
  "reason": "Annual checkup"
}
```
