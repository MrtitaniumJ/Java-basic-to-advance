# Transactions

## Concepts
- **ACID** properties for consistency.
- **Transaction manager** coordinates commits/rollbacks.
- Boundaries typically at service layer.

## Declarative with `@Transactional`
- Apply at class or method level.
- Propagation: REQUIRED (default), REQUIRES_NEW, MANDATORY, SUPPORTS, NOT_SUPPORTED, NEVER, NESTED.
- Isolation: READ_COMMITTED (default), REPEATABLE_READ, SERIALIZABLE.
- Rollback rules: runtime exceptions by default; configure for checked exceptions if needed.

## Example
```java
@Service
class TransferService {
  @Transactional
  public void transfer(long from, long to, BigDecimal amount) {
    debit(from, amount);
    credit(to, amount);
  }
}
```

## Best practices
- Keep transactional work small and fast.
- Do not perform remote calls inside long transactions.
- Avoid catching exceptions that prevent rollbacks unless you rethrow.
- Choose isolation per use case to balance correctness vs contention.

## Pitfalls
- `@Transactional` on private methods or self-calls is ignored (proxy limitation).
- Mixing different transaction managers without clarity.
- Long transactions holding locks unnecessarily.

## Checklist
- [ ] Service-layer boundaries
- [ ] Appropriate propagation/isolation
- [ ] Rollback rules explicit when deviating from defaults
- [ ] No remote I/O inside long transactions