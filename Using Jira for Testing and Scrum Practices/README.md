# Lab 4 - Using Jira, Xray, and Zephyr for Software Testing and Scrum Practices

This repository contains the setup for Lab 4, including the Spring Boot Book Management API and Express.js User Management API used in the tutorial.

## Projects

### Spring Boot Book Management API
- Location: `spring-boot-book-api/`
- Port: 8080
- Endpoints:
  - GET /api/books - Get all books or search by title (?title=...)
  - GET /api/books/{id} - Get book by ID
  - POST /api/books - Add a new book
  - PUT /api/books/{id} - Update a book
  - DELETE /api/books/{id} - Delete a book

### Express.js User Management API
- Location: `express-user-api/`
- Port: 3001
- Endpoints:
  - GET /api/users - Get all users
  - GET /api/users/{id} - Get user by ID
  - POST /api/users - Add a new user
  - PUT /api/users/{id} - Update a user
  - DELETE /api/users/{id} - Delete a user

## Setup and Running

### Prerequisites
- Java 17
- Node.js
- Maven

### Running the Spring Boot API
1. Navigate to `spring-boot-book-api/`
2. Run `mvn spring-boot:run`
3. API will be available at http://localhost:8080

### Running the Express.js API
1. Navigate to `express-user-api/`
2. Run `npm install`
3. Run `npm start`
4. API will be available at http://localhost:3001

## Tutorial
Follow the detailed tutorial in the user query or the provided document for using Jira, Xray, and Zephyr with these APIs.