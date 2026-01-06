# Bean Lifecycle and Scopes

## Lifecycle
1) Instantiation by container
2) Dependency injection
3) Post-processing (BeanPostProcessor)
4) Initialization callbacks (`@PostConstruct`, InitializingBean)
5) Ready for use
6) Destruction callbacks (`@PreDestroy`, DisposableBean)

## Scopes
- **singleton** (default): one instance per container
- **prototype**: new instance per request to container
- **request/session/application**: web scopes
- **custom scopes**: define for special lifetimes

## Hooks
- `@PostConstruct` / `@PreDestroy` for init/cleanup
- `SmartLifecycle` for startup/shutdown ordering
- `BeanPostProcessor` to intercept bean creation

## Example
```java
@Component
@Scope("prototype")
class TaskRunner {
  @PostConstruct void init() { /* prepare resources */ }
  @PreDestroy void cleanup() { /* release resources */ }
}
```

## Best practices
- Default to singleton; use prototype for stateful, short-lived objects.
- Avoid heavy work in constructors; use init methods.
- Always release external resources in destroy callbacks.
- Be careful with request/session scopes outside web contexts.

## Checklist
- [ ] Scope chosen per bean intent
- [ ] Init/cleanup hooks defined when holding resources
- [ ] Avoid storing request data in singletons