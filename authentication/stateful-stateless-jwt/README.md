# Stateful, Stateless and JWT Authentication

A practical Java and Spring Boot experiment exploring stateful and stateless application behavior and the fundamentals of JWT-based authentication.

## Learning Objectives

- Understand stateful and stateless application behavior.
- Explore HTTP sessions using `HttpSession`.
- Understand Spring Security configuration.
- Learn JWT structure and claims.
- Generate and validate signed JWTs.
- Implement a JWT authentication filter.
- Protect REST API endpoints using bearer tokens.

## Implemented Concepts

### 1. Stateful Endpoint

Implemented a visit counter using `HttpSession` to demonstrate server-side session state.

### 2. Stateless Endpoint

Implemented a simple addition endpoint that processes request parameters without depending on session state.

### 3. Spring Security

Configured request authorization and stateless session management using `SecurityFilterChain`.

### 4. JWT Generation

Generated signed JWTs using the JJWT library.

### 5. JWT Validation

Implemented token signature verification, expiration checking, and username extraction.

### 6. JWT Authentication Filter

Created a custom `OncePerRequestFilter` to extract bearer tokens and establish authentication in Spring Security's `SecurityContext`.

### 7. API Testing

Tested token generation and protected endpoint access using Postman.

## Technology Stack

- Java
- Spring Boot
- Spring Security
- Maven
- JJWT
- Postman

## Current Limitations

This is an educational prototype, not a production-ready authentication system.

- Token generation currently accepts a username without password verification.
- User persistence is not implemented.
- Role-based authorization is not implemented.
- Refresh-token handling is not implemented.

## Future Improvements

- Implement username and password authentication.
- Integrate Spring Security's `AuthenticationManager`.
- Add `UserDetailsService`.
- Implement BCrypt password hashing.
- Integrate database-backed users.
- Add role-based authorization.
- Implement refresh-token handling.
- Add automated security tests.

## Purpose

This module documents the initial practical exploration of authentication and security concepts as part of the Backend Engineering Lab.