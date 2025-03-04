# Chugs Application

## Overview
Chugs is a Spring Boot application that provides user management and order processing functionalities.

## Technologies Used
- Java 21
- Groovy 4.0
- Spring Boot 3.4.2
- Spring Data JPA
- Flyway
- MySQL 8
- Gradle
- Spock 2.4-M5

## Getting Started

### Prerequisites
- Java 21
- Gradle 7.6 or higher
- MySQL 8

### Installation
1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/chugs-back.git
    cd chugs-back
    ```

2. Build the project:
    ```sh
    ./gradlew build
    ```

3. Run the application:
    ```sh
    ./gradlew bootRun
    ```

### Configuration
The application configuration can be found in `application.yml`.

### Environment Variables
The following environment variables need to be set for the application to run:

- `DB_NAME`: The name of the MySQL database.
- `DB_USERNAME`: The username for the MySQL database.
- `DB_PASSWORD`: The password for the MySQL database.
- `DB_PORT`: The port number for the MySQL database (default is 3306).

Example `.env` file:
```.env
DB_NAME=dbname
DB_USERNAME=root
DB_PASSWORD=password
DB_PORT=3306
```


## Detailed Configuration
The application configuration can be found in `src/main/resources/application.yml`. Here is an example configuration:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:${DB_PORT}/${DB_NAME}?useSSL=false&allowPublicKeyRetrieval=true
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      dialect: org.hibernate.dialect.MySQL8Dialect
    show-sql: true
  flyway:
    enabled: true
    baseline-on-migrate: true
    validate-on-migrate: true
```

## Database Migration
Database migrations are managed using Flyway with SQL scripts located in `src/main/resources/db/migration`.

## Running Tests
To run the tests, execute the following command:
```sh
./gradlew test
```

## Usage
Once the application is running, you can access the following endpoints:

> **Note:** Replace `localhost:8080` with the appropriate host and port.
> **TODO:** Add the list of endpoints here.

