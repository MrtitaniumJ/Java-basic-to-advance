# Spring Framework

Spring is the mainstream Java platform for backend services. It supplies dependency injection, declarative transactions, data access helpers, AOP for cross-cutting concerns, and a mature web stack. Spring Boot sits on top with opinionated defaults, embedded servers, and production features (health/metrics/security/testing) out of the box.

## What this module covers
- **Spring Core**: IoC container, bean lifecycle & scopes, AOP, MVC fundamentals
- **Data access & transactions**: JdbcTemplate, JPA setup, transaction managers
- **Web**: controllers, validation, message conversion, exception handling
- **Boot layer**: starters, auto-config, config properties, Actuator, Security, testing

## Study roadmap (4–6 weeks)
1) Core container: IoC/DI → lifecycle → scopes → AOP
2) Persistence: JdbcTemplate → JPA basics → transaction boundaries
3) Web: REST controllers, validation, global error handling
4) Boot productivity: starters, auto-config, config properties
5) Observability & security: Actuator + Micrometer, Spring Security basics
6) Testing: slice tests, integration tests, Testcontainers for infra deps

## Build + ship checklist
- Constructor injection only; avoid field injection
- Clear bean scopes; no state in singletons that should be request-scoped
- Transactions at service boundaries; no remote I/O inside long transactions
- DTOs for API contracts; validate inputs and centralize errors
- Actuator health/metrics enabled and secured
- Security filter chain defined; passwords encoded; least privilege roles
- Tests per layer: unit → slice → integration

## Subfolders
- [01-Spring-Core](01-Spring-Core/README.md)
- [02-Spring-Boot](02-Spring-Boot/README.md)

Use these layers together to build production-grade services with minimal boilerplate while keeping observability and security in the first iteration, not the last.