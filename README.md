# Event-Driven Employee Management API

A modern event-driven REST API for managing employee data, built using Vert.x, Java 8, and in-memory storage. This project showcases the principles of event-driven architecture, futures, and lambda expressions, while adhering to best coding practices. It includes robust global exception handling and is equipped with comprehensive JUnit 5 tests.

---

## Features
- **Event-Driven Architecture:** Powered by Vert.x EventBus for asynchronous communication.
- **RESTful API:** Supports `GET`, `POST`, `PUT`, and `DELETE` operations.
- **In-Memory Storage:** A simple, dependency-free in-memory database for development and testing.
- **Global Exception Handling:** Consistent error responses across the API.
- **JUnit 5 Tests:** Comprehensive unit tests for handlers and database operations.
- **Future and Lambda Expressions:** Efficient and concise code.
- **Lightweight and Fast:** Built with Vert.x for high-performance non-blocking operations.

---

## Project Structure



project-root/ ├── src/ │ ├── main/ │ │ ├── java/ │ │ │ ├── com/ │ │ │ │ ├── example/ │ │ │ │ │ ├── config/ │ │ │ │ │ ├── controller/ │ │ │ │ │ ├── db/ │ │ │ │ │ ├── handler/ │ │ │ │ │ ├── model/ │ │ │ │ │ ├── verticle/ │ │ │ │ │ ├── util/ │ │ │ │ │ ├── MainVerticle.java │ ├── test/ │ ├── java/ │ ├── com/ │ ├── example/ │ ├── handler/ │ ├── verticle/ ├── pom.xml



---

## Getting Started

### Prerequisites
- **Java 8** or later.
- **Maven** (build tool).

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/event-driven-employee-api.git
   cd event-driven-employee-api


2. Build the project:
   ```bash
   mvn clean install

3. Run the application:
   ```bash
   mvn exec:java -Dexec.mainClass="com.example.MainVerticle"

4. The API will be available at http://localhost:8080.



### API Endpoints

Method	Endpoint	Description
POST	/employees	Add a new employee.
GET	/employees/:id	Retrieve employee by ID.
PUT	/employees/:id	Update employee details.
DELETE	/employees/:id	Delete an employee by ID.


### Testing

1. Run all tests:
   ```bash
      mvn test

2. JUnit 5 test coverage includes:

   EmployeeHandlerTest: Tests for request handling and validation.
   EmployeeDBVerticleTest: Tests for database operations.


### Event Bus Communication

The following addresses are used for internal event communication:

employee.add: Add a new employee.
employee.get: Retrieve employee data.
employee.update: Update employee data.
employee.delete: Delete employee data.

### Exception Handling

Centralized error handling ensures consistency:

400: Bad request (e.g., missing/invalid payload).
404: Resource not found.
500: Internal server error.

### Future Enhancements
Integration with an external database (e.g., PostgreSQL, MongoDB).
API Authentication and Authorization.
CI/CD pipeline for automated builds and deployments.
