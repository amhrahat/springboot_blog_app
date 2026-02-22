# Blog API

## Project Overview
This project is a Spring Boot REST API for a blog platform with JWT authentication, role-based authorization, and CRUD operations for posts, categories, and tags.

The application supports:
- User registration and login
- Draft/published post workflow
- Category and tag management
- Swagger/OpenAPI documentation

## Tech Stack
- Java 21
- Spring Boot 4.0.2
- Spring Web MVC
- Spring Data JPA
- Spring Security
- PostgreSQL
- JWT (`jjwt`)
- Springdoc OpenAPI
- Maven

## Architecture
The codebase follows a layered architecture:

- `controllers`: REST endpoints and HTTP request/response handling
- `services` and `services.impl`: business logic and authorization checks
- `repositories`: JPA data access
- `domain.entities`: JPA entity model
- `domain.dtos` and `domain.commands`: API payloads and service command objects
- `mappers`: conversion between entities and DTOs
- `security` and `config`: JWT handling, security filter chain, OpenAPI config

Request flow:

`HTTP Request -> Controller -> Service -> Repository -> Database`

## What Is Implemented

### Authentication and Authorization
- User registration with password hashing
- Login endpoint issuing JWT access token
- Stateless JWT authentication filter
- Role model: `USER`, `ADMIN`
- Endpoint-level authorization rules:
  - Public read access for posts/categories/tags (`GET`)
  - Authenticated write access for posts
  - Admin-only write access for categories and tags

### Post Management
- Create post
- Get single post
- List visible posts
  - Anonymous users: published posts only
  - Authenticated users: published posts + own drafts
- Update own post
- Delete own post
- Input validation for title/content/category/status

### Category and Tag Management
- Create category/tag
- List categories/tags
- Delete category/tag with ownership check

### Data Model
Core entities and relationships:
- `User` 1..* `Post`
- `User` 1..* `Category`
- `User` 1..* `Tag`
- `Post` *..1 `Category`
- `Post` *..* `Tag` through `post_tag`

## API Documentation
OpenAPI docs are available at:
- `http://localhost:8080/swagger-ui.html`
- `http://localhost:8080/swagger-ui/index.html`
- `http://localhost:8080/v3/api-docs`

Use `Authorization: Bearer <jwt>` for protected endpoints.

## API Endpoints

### Auth
- `POST /api/v1/auth/registration`
- `POST /api/v1/auth/login`

### Posts
- `GET /api/v1/posts` (public)
- `GET /api/v1/posts/{postId}` (public, draft visibility restricted by ownership)
- `POST /api/v1/posts` (authenticated)
- `PUT /api/v1/posts/{postId}` (authenticated, owner only)
- `DELETE /api/v1/posts/{postId}` (authenticated, owner only)

### Categories
- `GET /api/v1/categories` (public)
- `POST /api/v1/categories` (admin)
- `DELETE /api/v1/categories/{categoryId}` (admin + ownership check in service)

### Tags
- `GET /api/v1/tags` (public)
- `POST /api/v1/tags` (admin)
- `DELETE /api/v1/tags/{tagId}` (admin + ownership check in service)

## Local Setup
1. Ensure Java 21 and Maven are installed.
2. Start PostgreSQL and create database `blog_db`.
3. Configure database credentials in `src/main/resources/application.properties`.
4. Run:

```bash
./mvnw spring-boot:run
```

## Configuration Notes
- Current local defaults are configured in `src/main/resources/application.properties`.
- The project currently runs with Hibernate schema update enabled:
  - `spring.jpa.hibernate.ddl-auto=update`
- Liquibase properties exist but are currently commented in `application.properties`.

