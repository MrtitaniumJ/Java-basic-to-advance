# Performance and Scaling

Key considerations for scaling Spring Boot services.

## Application level
- Use connection pooling sized to DB capacity.
- Cache heavy reads (Caffeine/Redis) where appropriate.
- Avoid N+1 DB queries; profile endpoints.
- Tune Jackson (ignore unused properties, avoid reflection-heavy patterns).

## JVM level
- Set heap based on container limits; monitor GC pauses.
- Choose GC (G1/ZGC) per latency needs.

## Infrastructure level
- Horizontal scaling behind a load balancer.
- Health/readiness probes for safe rollouts.
- Rate limiting and backpressure for downstream protection.

## Observability
- Measure latency, throughput, errors (RED/USE metrics).
- Trace slow endpoints; fix hotspots before scaling out.

## Checklist
- [ ] Connection pools sized
- [ ] Caching strategy defined
- [ ] GC and heap tuned
- [ ] Metrics driving scaling decisions