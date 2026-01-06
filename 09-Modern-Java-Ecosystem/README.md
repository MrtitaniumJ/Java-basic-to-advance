# Modern Java Ecosystem

This module maps the modern Java backend stack with Spring as the anchor. It explains **what to learn**, **why it matters in production**, and **how to stitch components together** to deliver reliable HTTP/REST services.

## Audience & prerequisites
- Comfortable with core Java (collections, exceptions, OOP)
- Basic HTTP/REST familiarity
- Able to run Maven/Gradle and simple `curl`/Postman calls

## What you will learn
- **Spring Core**: IoC/DI, bean lifecycle, scopes, AOP, data access helpers, transactions, MVC fundamentals
- **Spring Boot**: starters, auto-configuration, config properties, REST + validation, Spring Data JPA, Security, Actuator/observability, testing
- **Production concerns**: configuration strategies, health/metrics, security posture, deployment patterns, scaling heuristics
- **Ecosystem choices**: which Spring modules to pick for common backend scenarios

## Suggested learning path (6–8 weeks)
1) Core container: IoC/DI → bean lifecycle → scopes → AOP
2) Persistence: JDBC Template → JPA fundamentals → transactions
3) Web: Spring MVC/REST, validation, exception handling
4) Boot productivity: starters, auto-config, config properties
5) Data access at scale: Spring Data JPA patterns, pagination, N+1 avoidance
6) Security & observability: Spring Security basics, Actuator, metrics/tracing
7) Testing: unit vs slice vs integration, Testcontainers for infra deps
8) Deployment: packaged JAR → Docker layering → basic cloud knobs (env vars, health probes)

## Hands-on mini roadmaps
- **Hello Service**: simple REST with validation + in-memory repo
- **CRUD + DB**: add JPA entities, Flyway/Liquibase migrations, pagination
- **Secure & observable**: add Spring Security (basic/JWT), Actuator health/metrics, structured logging
- **Deployable build**: container image with layered JAR, readiness/liveness probes

## When to choose Spring
- You need to ship REST/HTTP APIs quickly with consistent dependency injection and lifecycle management
- Cross-cutting concerns (logging, metrics, authz, tracing) must be standardized
- You want production-ready defaults (health, metrics, graceful shutdown, config binding)

## Tooling snapshot
- Build: Maven/Gradle + Spring Boot plugins
- Observability: Actuator + Micrometer (Prometheus/Datadog/Grafana)
- Testing: JUnit 5, Mockito, Spring Test slices, Testcontainers
- Packaging: Boot executable JARs and Docker layered builds

## Navigation
- [01-Spring-Framework](01-Spring-Framework/README.md)
  - [01-Spring-Core](01-Spring-Framework/01-Spring-Core/README.md)
  - [02-Spring-Boot](01-Spring-Framework/02-Spring-Boot/README.md)

Use this as a **production-oriented roadmap**. Keep examples small and runnable, favor constructor injection, and add observability/security early rather than late.