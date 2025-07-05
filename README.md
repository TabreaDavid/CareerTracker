# Job Application Tracker

Spring Boot REST API to track job applications.

## Quick Start

```bash
mvn spring-boot:run
```

Runs on `http://localhost:8080`

## Features

- Manage companies, contacts, and job applications
- Filter by status (SAVED, APPLIED, INTERVIEW, OFFER, REJECTED)
- Pagination and sorting
- Stats endpoint

## Tech

- Java 21
- Spring Boot 3.2
- H2 Database

```bash
# Create company
curl -X POST http://localhost:8080/api/companies \
  -H "Content-Type: application/json" \
  -d '{"name":"Google","location":"CA"}'

# Create application
curl -X POST http://localhost:8080/api/applications \
  -H "Content-Type: application/json" \
  -d '{"title":"SWE","company":{"id":1},"status":"APPLIED"}'
```

## H2 Console

http://localhost:8080/h2-console  
JDBC URL: `jdbc:h2:mem:testdb`  
Username: `sa`

