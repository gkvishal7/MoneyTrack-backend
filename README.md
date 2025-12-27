# MoneyTrack Backend

Expense tracking application backend built with Spring Boot.

## Prerequisites

- Java 21
- Docker and Docker Compose
- Gradle

## Getting Started

### 1. Start PostgreSQL Database

Start the PostgreSQL container using Docker Compose:

```bash
docker-compose up -d
```

This will:
- Start a PostgreSQL 16 container named `moneytrack-postgres`
- Create a database named `moneytrack`
- Expose PostgreSQL on port `5432`
- Create a persistent volume for data storage

### 2. Verify Database is Running

Check if the container is healthy:

```bash
docker-compose ps
```

You should see the postgres service with a "healthy" status.

### 3. Run the Application

```bash
./gradlew bootRun
```

Or on Windows:

```bash
gradlew.bat bootRun
```

The application will automatically:
- Connect to the PostgreSQL database
- Create/update database tables based on your entities
- Start on port 8080 (default)

### 4. Stop the Database

When you're done, stop the PostgreSQL container:

```bash
docker-compose down
```

To remove the data volume as well:

```bash
docker-compose down -v
```

## Database Configuration

The application is configured to connect to PostgreSQL with the following defaults:

- **Host**: localhost
- **Port**: 5432
- **Database**: moneytrack
- **Username**: moneytrack_user
- **Password**: moneytrack_password

These can be changed in `docker-compose.yml` and `src/main/resources/application.properties`.

## API Endpoints

### Authentication

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and get JWT tokens
- `POST /api/auth/refresh` - Refresh access token
- `POST /api/auth/logout` - Logout and revoke refresh token
- `DELETE /api/auth/account` - Delete user account

## Development

The application uses:
- Spring Boot 3.5.7-SNAPSHOT
- PostgreSQL 16
- JWT for authentication
- JPA/Hibernate for data persistence

