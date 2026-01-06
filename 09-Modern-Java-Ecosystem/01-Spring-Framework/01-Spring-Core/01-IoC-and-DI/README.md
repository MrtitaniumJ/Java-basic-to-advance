# IoC and Dependency Injection

## What and why
- Inversion of Control (IoC): the container creates and wires objects. 
- Dependency Injection (DI): dependencies are provided (constructor/setter/field), improving testability and decoupling.

## Core concepts
- Beans: managed objects; defined via annotations or XML.
- Injection styles: constructor (preferred), setter, field (avoid in production).
- Configuration: `@Configuration`, `@Bean`, component scanning with `@Component`, `@Service`, `@Repository`, `@Controller`.
- Profiles: `@Profile` to switch beans per environment.

## Minimal example (constructor DI)
```java
@Configuration
class AppConfig {
  @Bean MessageService messageService() { return new EmailService(); }
  @Bean NotificationFacade notificationFacade(MessageService svc) {
    return new NotificationFacade(svc);
  }
}
```

## Best practices
- Prefer constructor injection for required dependencies.
- Keep configuration thin; move logic into beans.
- Use profiles for env-specific wiring (dev/test/prod).
- Avoid static state and singletons not managed by Spring.

## Common pitfalls
- Circular dependencies; fix by refactoring responsibilities.
- Overusing field injection; reduces testability.
- Component scanning large packages; scope scanning to needed modules.

## Checklist
- [ ] Constructor DI by default
- [ ] Clear bean scopes
- [ ] Profiles for environment differences
- [ ] No business logic in configuration classes