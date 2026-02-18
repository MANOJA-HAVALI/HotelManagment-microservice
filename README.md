# Hotel Management Microservice

A comprehensive hotel management system built using Spring Boot microservices architecture with service discovery, authentication, and inter-service communication.

## 🏨 Architecture Overview

This project consists of four main microservices:

1. **Eureka Service** - Service Discovery Server (Port: 8761)
2. **Auth Service** - Authentication & Authorization Service (Port: 8083)
3. **Hotel Service** - Hotel Management Service (Port: 8081)
4. **Rating Service** - Hotel Rating Service (Port: 8082)

## 🚀 Services

### 1. Eureka Service
- **Port**: 8761
- **Purpose**: Service registration and discovery
- **Technology**: Spring Cloud Eureka Server
- **Status**: Standalone service (doesn't register with itself)

### 2. Auth Service
- **Port**: 8083
- **Purpose**: User authentication, JWT token generation, and authorization
- **Database**: MySQL (microservice_auth)
- **Features**:
  - JWT-based authentication
  - User management
  - Token validation endpoint for other services
- **Dependencies**: Spring Security, JWT, MySQL, Eureka Client

### 3. Hotel Service
- **Port**: 8081
- **Purpose**: Hotel data management and operations
- **Database**: PostgreSQL (microservice)
- **Features**:
  - CRUD operations for hotels
  - Integration with Auth Service for security
  - Service discovery via Eureka
- **Dependencies**: Spring Data JPA, PostgreSQL, Eureka Client, OpenFeign

### 4. Rating Service
- **Port**: 8082
- **Purpose**: Hotel rating and review management
- **Database**: MySQL (microservice_rating)
- **Features**:
  - Rating CRUD operations
  - Integration with Auth Service for token validation
  - Service discovery via Eureka
- **Dependencies**: Spring Data JPA, MySQL, Eureka Client, OpenFeign

## 🛠 Technology Stack

- **Java**: 17-21
- **Spring Boot**: 3.1.5 - 3.2.10
- **Spring Cloud**: 2023.0.3
- **Databases**:
  - MySQL (Auth Service, Rating Service)
  - PostgreSQL (Hotel Service)
- **Service Discovery**: Eureka Server
- **Inter-service Communication**: OpenFeign
- **Authentication**: JWT (JSON Web Tokens)
- **Security**: Spring Security
- **Documentation**: Swagger/OpenAPI 3
- **Build Tool**: Maven

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- PostgreSQL 12+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## 🗄 Database Setup

### MySQL Setup
```sql
-- Create databases
CREATE DATABASE microservice_auth;
CREATE DATABASE microservice_rating;

-- User credentials (as per application.yml)
Username: root
Password: MySql
```

### PostgreSQL Setup
```sql
-- Create database
CREATE DATABASE microservice;

-- User credentials (as per application.yml)
Username: postgres
Password: admin
```

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd HotelManagment-microservice-master
```

### 2. Start Services in Order

#### Step 1: Start Eureka Service
```bash
cd EurekaService
./mvnw spring-boot:run
```
Access: http://localhost:8761

#### Step 2: Start Auth Service
```bash
cd AuthService
./mvnw spring-boot:run
```
Access: http://localhost:8083

#### Step 3: Start Hotel Service
```bash
cd HotelService
./mvnw spring-boot:run
```
Access: http://localhost:8081

#### Step 4: Start Rating Service
```bash
cd RatingService
./mvnw spring-boot:run
```
Access: http://localhost:8082

## 📊 Service Endpoints

### Auth Service (Port: 8083)
- `POST /auth/login` - User login and JWT generation
- `POST /auth/register` - User registration
- `POST /auth/validate` - Token validation (used by other services)
- Swagger UI: http://localhost:8083/swagger-ui.html

### Hotel Service (Port: 8081)
- `GET /hotels` - Get all hotels
- `GET /hotels/{id}` - Get hotel by ID
- `POST /hotels` - Create new hotel
- `PUT /hotels/{id}` - Update hotel
- `DELETE /hotels/{id}` - Delete hotel
- Swagger UI: http://localhost:8081/swagger-ui.html

### Rating Service (Port: 8082)
- `GET /ratings` - Get all ratings
- `GET /ratings/{id}` - Get rating by ID
- `GET /ratings/hotel/{hotelId}` - Get ratings by hotel
- `POST /ratings` - Create new rating
- `PUT /ratings/{id}` - Update rating
- `DELETE /ratings/{id}` - Delete rating
- Swagger UI: http://localhost:8082/swagger-ui.html

### Eureka Dashboard (Port: 8761)
- http://localhost:8761 - View all registered services

## 🔐 Security Configuration

### JWT Configuration
- **Secret Key**: Configured in Auth Service application.yml
- **Expiration**: 24 hours (86400000 ms)
- **Algorithm**: HS256

### Authentication Flow
1. User logs in via Auth Service
2. Auth Service generates JWT token
3. Token is passed in Authorization header: `Bearer <token>`
4. Other services validate token via Auth Service's validation endpoint

## 🌐 Inter-Service Communication

Services communicate using:
- **Eureka**: For service discovery
- **OpenFeign**: For declarative REST client communication
- **JWT Tokens**: For secure inter-service calls

### Communication Flow
```
Client → Auth Service (Login) → JWT Token
Client → Hotel/Rating Service (with JWT) → Token Validation → Business Logic
```

## 📝 Configuration Files

Each service has its own configuration:
- `EurekaService/src/main/resources/application.properties`
- `AuthService/src/main/resources/application.yml`
- `HotelService/src/main/resources/application.yml`
- `RatingService/src/main/resources/application.yml`

## 🐛 Troubleshooting

### Common Issues

1. **Service Registration Failed**
   - Ensure Eureka Service is running first
   - Check network connectivity
   - Verify Eureka server URL in application.yml

2. **Database Connection Issues**
   - Verify database is running
   - Check credentials in application.yml
   - Ensure database exists

3. **JWT Token Validation Errors**
   - Ensure Auth Service is running
   - Check JWT secret key consistency
   - Verify token format in Authorization header

### Port Conflicts
If ports are already in use, modify the `server.port` in respective application files:
- Eureka: 8761
- Auth: 8083
- Hotel: 8081
- Rating: 8082

## 📚 API Documentation

Each service provides Swagger/OpenAPI documentation:
- Auth Service: http://localhost:8083/swagger-ui.html
- Hotel Service: http://localhost:8081/swagger-ui.html
- Rating Service: http://localhost:8082/swagger-ui.html

## 🔧 Development

### Building All Services
```bash
# Build each service individually
cd EurekaService && ./mvnw clean install
cd ../AuthService && ./mvnw clean install
cd ../HotelService && ./mvnw clean install
cd ../RatingService && ./mvnw clean install
```

### Running Tests
```bash
cd <service-name>
./mvnw test
```

## 📈 Monitoring

### Eureka Dashboard
- View all registered services
- Monitor service health
- Check service instances

### Application Logs
Each service provides detailed logging for:
- Service registration
- Database operations
- Security events
- Inter-service communication

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License.

## 📞 Support

For issues and questions:
- Check the troubleshooting section
- Review service logs
- Verify all prerequisites are met
