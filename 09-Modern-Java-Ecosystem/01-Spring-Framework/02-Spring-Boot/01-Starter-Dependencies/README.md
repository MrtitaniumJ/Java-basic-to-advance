# Starter Dependencies

Starters aggregate compatible dependencies with managed versions (via Spring Boot BOM), reducing boilerplate in `pom.xml`/`build.gradle`.

## Common starters
- `spring-boot-starter-web`: Spring MVC + embedded Tomcat + JSON via Jackson
- `spring-boot-starter-data-jpa`: Spring Data JPA + Hibernate + transactions
- `spring-boot-starter-security`: Spring Security
- `spring-boot-starter-validation`: Bean Validation (Jakarta Validation)
- `spring-boot-starter-test`: JUnit, Mockito, AssertJ, Spring Test

## Why it matters
- Version alignment prevents dependency hell.
- Faster project setup; no manual transitive management.

## Tips
- Prefer starters over individual artifacts.
- Use the Spring Initializr to bootstrap projects.
- Inspect the BOM (`spring-boot-dependencies`) for managed versions.

## Checklist
- [ ] Use starters for core features
- [ ] Keep custom dependency overrides minimal
- [ ] Rely on BOM-managed versions