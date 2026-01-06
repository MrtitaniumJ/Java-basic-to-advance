# Actuator and Observability

Actuator exposes operational endpoints; observability adds metrics, health, logs, and traces for production readiness.

## Actuator endpoints
- `/actuator/health`, `/actuator/info`, `/actuator/metrics`, `/actuator/env`, `/actuator/loggers`
- Secure these endpoints; expose minimal set publicly.

## Metrics & tracing
- Micrometer integrates with Prometheus, Datadog, etc.
- Distributed tracing via OpenTelemetry/Brave.
- Add custom metrics with `MeterRegistry`.

## Best practices
- Enable health, info, metrics, and readiness/liveness probes for containers.
- Tag metrics with low-cardinality labels.
- Mask sensitive config values in exposed endpoints.

## Checklist
- [ ] Actuator enabled and secured
- [ ] Health/readiness checks configured
- [ ] Metrics exported to monitoring system
- [ ] Tracing enabled in distributed systems