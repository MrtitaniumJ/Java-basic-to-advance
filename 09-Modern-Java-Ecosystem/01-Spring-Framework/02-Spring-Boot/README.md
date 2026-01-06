# Spring Boot

Spring Boot reduces boilerplate in Spring apps through starters, auto-configuration, embedded servers, and production defaults. It emphasizes fast start, clear configuration, and out-of-the-box observability/testing support.

## What Boot adds
- **Starters**: curated dependency sets with managed versions (BOM) to avoid conflicts.
- **Auto-configuration**: conditional beans wired from the classpath and properties.
- **Configuration binding**: type-safe `@ConfigurationProperties` with validation.
- **Embedded servers**: Tomcat/Jetty/Undertow; easy to package and run.
- **Actuator**: health, metrics, env, loggers, readiness/liveness probes.
- **Testing slices**: focused web/data client tests plus full `@SpringBootTest`.

## Suggested learning path
1) [Starter Dependencies](01-Starter-Dependencies/README.md)
2) [Auto-Configuration](02-Auto-Configuration/README.md)
3) [Configuration Properties](03-Configuration-Properties/README.md)
4) [REST API & Validation](04-REST-API-and-Validation/README.md)
5) [Spring Data JPA](05-Spring-Data-JPA/README.md)
6) [Spring Security](06-Spring-Security/README.md)
7) [Actuator & Observability](07-Actuator-and-Observability/README.md)
8) [Testing](08-Testing/README.md)
9) [Deployment & Containers](09-Deployment-and-Containers/README.md)
10) [Performance & Scaling](10-Performance-and-Scaling/README.md)
11) [Security & Hardening](11-Security-and-Hardening/README.md)
12) [Cloud Integrations](12-Cloud-Integrations/README.md)

## Hands-on path
- Start with a minimal REST service using `spring-boot-starter-web`.
- Add validation and global error handling; expose health/metrics via Actuator.
- Persist data with Spring Data JPA + Flyway/Liquibase migrations.
- Secure endpoints with Spring Security (basic/JWT) and encode passwords.
- Containerize with a layered JAR; add readiness/liveness probes.

## Operational checklist
- Externalize configuration; never commit secrets.
- Lock down Actuator; expose only health/readiness publicly.
- Set timeouts and connection pools; monitor with Micrometer.
- Use structured logging with correlation IDs; enable tracing in distributed systems.
- Keep tests fast with slice tests; use Testcontainers for DB/queue dependencies.

Boot is best learned by building a small service end-to-end and enabling features incrementally—add observability and security early, not at the end.