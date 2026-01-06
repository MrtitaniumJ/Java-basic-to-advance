# Configuration Properties

Spring Boot binds external configuration (YAML/properties/env vars) into typed objects for safer, centralized settings.

## Usage
- `application.yml` / `application.properties` for defaults
- `@ConfigurationProperties(prefix = "app")` to bind to POJOs (enable via `@EnableConfigurationProperties` or `@SpringBootApplication`)
- Type-safe validation with `@Validated`

## Example
```java
@ConfigurationProperties(prefix = "app.mail")
@Validated
public class MailProps {
  @NotBlank private String host;
  private int port = 25;
  @NotBlank private String from;
}
```

## Best practices
- Prefer `@ConfigurationProperties` over scattered `@Value`.
- Keep secrets out of source; use env vars/secret stores.
- Provide sane defaults; document required fields.

## Checklist
- [ ] Typed properties classes for major modules
- [ ] Validation on required fields
- [ ] No secrets committed to source control