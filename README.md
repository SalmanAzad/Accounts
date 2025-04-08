# Account API
This project provides a RESTful API for managing customer current accounts and their transactions using a microservices architecture.

### Features
* Create a new account for a user (customerId) with optional initialCredit.
* Automatically create a transaction if initialCredit > 0.
* Retrieve user information including:
  * Name
  * Surname
  * Account balance
  * List of transactions

### Tech Stack
* Language: Kotlin
* Framework: Spring Boot
* API Docs: OpenAPI (Swagger)
* Database: H2
* Build Tool: Maven
* CI/CD: GitHub Actions
* Architecture: Microservices

### API Endpoints
* POST /v1/current-accounts/create
* GET /v1/current-accounts/customerId/{customerId}

### Run Account API
    Step 1: Run Transactions Service
        ./mvnw clean install
        ./mvnw spring-boot:run
    
    Step 2: Run Accounts Service
        ./mvnw clean install
        ./mvnw spring-boot:run

### Swagger UI
    Accounts: http://localhost:8081/swagger-ui.html
    Transactions: http://localhost:8091/swagger-ui.html

### Connect to Database
    url: http://localhost:8081/h2-console
    username : sa
    password : 



