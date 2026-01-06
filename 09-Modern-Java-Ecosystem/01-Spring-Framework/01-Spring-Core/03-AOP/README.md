# Aspect-Oriented Programming (AOP)

## Why
Cross-cutting concerns (logging, metrics, security, transactions) should be applied consistently without tangling business code.

## Core pieces
- **Join point**: a point during execution (method call/return).
- **Pointcut**: expression that matches join points.
- **Advice**: code run at matched join points (before/after/around/after-throwing).
- **Aspect**: module grouping pointcuts + advices.
- **Proxy**: wrapper that applies advice (JDK dynamic proxy or CGLIB).

## Example (logging)
```java
@Aspect
@Component
class LoggingAspect {
  @Pointcut("within(com.acme.service..*)")
  void serviceLayer() {}

  @Around("serviceLayer()")
  Object log(ProceedingJoinPoint pjp) throws Throwable {
    long start = System.nanoTime();
    try { return pjp.proceed(); }
    finally {
      long ms = (System.nanoTime() - start) / 1_000_000;
      System.out.println(pjp.getSignature() + " took " + ms + " ms");
    }
  }
}
```

## Best practices
- Use AOP for true cross-cutting concerns only.
- Keep pointcuts narrow; avoid broad `execution(* *(..))` patterns.
- Favor `@Around` for metrics/tracing; `@Before/@After` for validation/cleanup.
- Remember proxies only apply to Spring-managed beans.

## Pitfalls
- Self-invocation bypasses proxies.
- Final methods/classes cannot be proxied by CGLIB in some cases.
- Be mindful of performance; avoid heavy logic in advice.

## Checklist
- [ ] Pointcuts scoped to packages
- [ ] Advice lightweight and side-effect aware
- [ ] Cross-cutting concerns centralized