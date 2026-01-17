# Technical Test - User API

This project is a technical test built with **Java 21** and **Spring Boot**.  
It exposes a simple REST API to **register a user** and **retrieve user details**.

---

## Features

### API endpoints
- **Register user**: `POST /api/users/register`
- **Get user details**: `GET /api/users/{username}`

### Eligibility
A user can register only if:
- the user is **adult** (age >= 18)
- the user is **French resident** (country = `FRANCE`)
- `username` must be **unique**

### Embedded database
- Uses an embedded **H2 in-memory database**
- No external database installation required
- Makes the project easy to run and test locally

### Tests
- Unit tests 
- Integration tests

### Bonus implemented
- **AOP logging** to log inputs, outputs, errors and processing time

---

## Requirements
- Java 21
- Maven 3.9+
- Git

---

## How to clone

```bash
git clone https://github.com/Ayoub-Fatmi/technical-test-user-api.git
cd technical-test-user-api
```

---

## How to build 

### 1) Run tests
```bash
mvn clean test
```

### 2) Build the project
```bash
mvn clean package
```

---

## How to run

Run the application with Maven:
```bash
mvn spring-boot:run
```

The API runs on:
- `http://localhost:8080`

---

## API responses

### 1) Register user
**POST** `/api/users/register`

**Success**
- Status: `201 Created`
- Body (JSON): the created user

Example response:
```json
{
  "username": "clement",
  "birthdate": "1995-03-10",
  "country": "FRANCE",
  "phone": "06 12 34 56 78",
  "gender": "OTHER"
}
```

**Possible errors**
- `400 Bad Request`
    - validation errors (example: blank username, null birthdate)
    - eligibility rules (not French resident / minor)
- `409 Conflict`
    - username already exists

---

### 2) Get user details
**GET** `/api/users/{username}`

**Success**
- Status: `200 OK`
- Body (JSON): the user details

Example response:
```json
{
  "username": "clement",
  "birthdate": "1995-03-10",
  "country": "FRANCE",
  "phone": "06 12 34 56 78",
  "gender": "OTHER"
}
```

**Possible errors**
- `404 Not Found`
    - user not found
---

## How to use / test the API

This project provides request samples using Postman.

A Postman collection is available in:
- `postman_collection/user-api.json` \
Ready to be Imported in Postman