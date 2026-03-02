# Blog API

## Project Overview
This project is a Spring Boot REST API for a modern blog platform with JWT authentication, role-based authorization, email verification via OTP, and CRUD operations for posts, categories, and tags.

The application supports:
- User registration with email verification
- Secure login with JWT token authentication
- Draft/published post workflow
- Category and tag management
- CSRF protection
- Comprehensive Swagger/OpenAPI documentation
- Database migrations via Liquibase

## Tech Stack
- **Java 21**
- **Spring Boot 4.0.2**
- Spring Web MVC
- Spring Data JPA
- Spring Security
- Spring Mail (Gmail SMTP)
- PostgreSQL
- JWT (jjwt 0.12.5)
- Springdoc OpenAPI (v3.0.1)
- Liquibase
- Maven
- Docker & Docker Compose

## Architecture
The codebase follows a layered, modular architecture:

- **controllers**: REST endpoints and HTTP request/response handling
- **services** & **services.impl**: business logic and authorization enforcement
- **repositories**: JPA data access layer
- **domain.entities**: JPA entity models
- **domain.dtos** & **domain.commands**: API payloads and service command objects
- **mappers**: conversion between entities and DTOs
- **security** & **config**: JWT handling, security filter chain, OpenAPI configuration

**Request flow:**
```
HTTP Request → Controller → Service → Repository → PostgreSQL Database
```

## What Is Implemented

### Authentication & Authorization
- User registration with email validation
- Email verification via OTP (6-digit code sent to email)
- Secure login with JWT token generation
- Stateless JWT authentication filter
- Role-based access control: `USER`, `ADMIN`
- Endpoint-level authorization:
  - Public read access for posts, categories, tags
  - Authenticated users can create/edit/delete own posts
  - Admin-only write access for categories and tags

### Email Verification
- OTP generation and validation
- Email sending via Gmail SMTP
- Verification token table tracking
- Email-based user account activation workflow

### Post Management
- Create new posts (title, content, category, status)
- Retrieve single post by ID
- List posts with pagination support
  - Anonymous users: published posts only
  - Authenticated users: published posts + own draft posts
- Update own posts
- Delete own posts
- Input validation for all fields (title, content, category, status)
- Draft/Published status workflow

### Category and Tag Management
- Create categories and tags (admin only)
- List all available categories and tags (public)
- Delete categories and tags with ownership verification
- Association with posts through relationships

### Data Model
Core entities and relationships:
```
User (1..* Post, 1..* Category, 1..* Tag)
Post (*..*City Category, *..* Tag through post_tag)
Category (*..*User, *..*Post)
Tag (*..*User, *..*Post)
VerificationToken (*..*User)
```

## API Documentation
OpenAPI/Swagger documentation is available at:
- `http://localhost:8080/swagger-ui.html` (preferred UI)
- `http://localhost:8080/swagger-ui/index.html`
- `http://localhost:8080/v3/api-docs` (JSON spec)

For protected endpoints, include JWT token in the Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

## API Endpoints

### Authentication (`/api/v1/auth`)
- `POST /registration` - Register new user with email and password
- `POST /login` - Login and receive JWT token
- `POST /verify` - Verify email with OTP

### Posts (`/api/v1/posts`)
- `GET /` - List published posts (public)
- `GET /{postId}` - Get post by ID (public, draft visibility controlled)
- `POST /` - Create new post (authenticated)
- `PUT /{postId}` - Update post (authenticated, owner only)
- `DELETE /{postId}` - Delete post (authenticated, owner only)

### Categories (`/api/v1/categories`)
- `GET /` - List all categories (public)
- `POST /` - Create category (admin only)
- `DELETE /{categoryId}` - Delete category (admin only)

### Tags (`/api/v1/tags`)
- `GET /` - List all tags (public)
- `POST /` - Create tag (admin only)
- `DELETE /{tagId}` - Delete tag (admin only)

### Security (`/api/v1/csrf-token`)
- `GET /csrf-token` - Get CSRF token for state-changing operations

## Database Setup

### Using Docker (Recommended)
The project includes a `docker-compose.yml` with PostgreSQL and Adminer for easy local development:

```bash
# Start database and admin panel
docker-compose up -d

# Access database through Adminer at http://localhost:8888
# Database: blog_db | User: rahat | Password: password123
# Port: 5431 (mapped to 5432 inside container)
```

### Manual Setup
1. Install and start PostgreSQL
2. Create database: `blog_db`
3. Create user: `rahat` with password `password123`
4. Update connection string in `application.properties` if using different credentials

## Running the Application

### Prerequisites
- Java 21
- Maven 3.6+
- PostgreSQL (or use Docker)

### Start the Application
```bash
# Build and run
./mvnw spring-boot:run

# Or build and run with Maven
mvn clean spring-boot:run
```

The API will be available at `http://localhost:8080`

### Build for Production
```bash
./mvnw clean package

# Run the JAR
java -jar target/blog-0.0.1-SNAPSHOT.jar
```

## Environment Configuration

The application uses `src/main/resources/application.properties` for environment configuration. Key settings:

**Database:**
- PostgreSQL on localhost:5431
- Default credentials: `rahat` / `password123`
- Schema validation enabled with Liquibase-managed migrations

**Security:**
- JWT tokens expire after 24 hours
- Email verification via OTP required for account activation
- CSRF tokens available for form submissions

**Email Service:**
- Gmail SMTP used for sending verification codes
- Requires Gmail app-specific password for authentication

**Feature Flags:**
- Liquibase migrations: Enabled
- Hibernate schema update: Disabled (use Liquibase instead)

## Database Migrations

Liquibase automatically applies versioned SQL migrations from `src/main/resources/db/changelog/`:

1. **001-create-initial-tables.sql** - Initial database schema
2. **002-add-verified-to-users.sql** - Email verification column
3. **003-create-verification-tokens.sql** - OTP tracking table

Migrations run automatically on application startup with validation mode enabled.

## Development Features

### Swagger UI
- Full API documentation at `/swagger-ui.html`
- "Try it out" feature for testing endpoints directly
- Complete request/response examples

### Database Admin
- Access PostgreSQL via Adminer at `http://localhost:8888` when using Docker
- Visual inspection and management of all tables
- Query execution directly in browser

### Email Testing
- OTP codes generated and sent to configured email address
- Check email inbox for verification codes
- Configurable expiration time in code

## Security Features

- **Password Hashing**: BCrypt hashing via Spring Security
- **JWT Authentication**: Stateless token-based authentication
- **CSRF Protection**: Token-based CSRF prevention
- **Role-Based Access**: USER and ADMIN role separation
- **Email OTP Verification**: Multi-factor like activation
- **Input Validation**: Bean validation on all DTOs
- **Authorization Checks**: Service-level ownership validation

