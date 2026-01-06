# Spring Core

Spring Core is the foundation: the IoC container, dependency injection, bean lifecycle, scopes, AOP, and the basic data/transaction primitives that everything else builds on.

## Topics you should master
- **IoC & DI**: container-managed object graphs, constructor injection as the default
- **Bean lifecycle**: instantiation → dependency injection → post-processing → init → ready → destruction hooks
- **Scopes**: singleton, prototype, request/session (web), and when to define custom scopes
- **AOP**: proxies, pointcuts, advice types; applying cross-cutting concerns safely
- **Data access helpers**: JdbcTemplate and a minimal JPA setup
- **Transactions**: declarative vs programmatic, propagation/isolation basics

## Learning path (Core → Web-ready)
1) [IoC and DI](01-IoC-and-DI/README.md)
2) [Bean Lifecycle and Scopes](02-Bean-Lifecycle-and-Scopes/README.md)
3) [AOP](03-AOP/README.md)
4) [Data Access (JDBC/JPA)](04-Data-Access-JDBC-JPA/README.md)
5) [Transactions](05-Transactions/README.md)
6) [Spring MVC Basics](06-Spring-MVC/README.md)

## Hands-on checkpoints
- Wire a small service using constructor DI only; swap implementations without touching consumers.
- Add lifecycle hooks to allocate/release an external resource; verify they fire.
- Apply an AOP interceptor for logging/metrics to a single package.
- Build a tiny JDBC repo and a JPA repo; wrap them in a transactional service.

## Best practices & pitfalls
- Prefer constructor injection; avoid field injection to keep tests clean.
- Keep singletons stateless; avoid storing request/user state in singletons.
- AOP: keep pointcuts narrow and advice lightweight to prevent surprises.
- Transactions: keep them short; avoid remote calls inside long transactions.

Use the container for testability and composability: swap implementations, mock dependencies, and keep object graphs explicit.