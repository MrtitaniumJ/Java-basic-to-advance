# Cloud Integrations

Common patterns for running Spring Boot in the cloud.

## Config and secrets
- Externalize config via env vars, config servers, or secret managers (Vault/SM/KMS).

## Storage and messaging
- Use Spring Cloud starters for queues/topics (Kafka, RabbitMQ, SQS, Pub/Sub).
- Use Spring Cloud AWS/GCP/Azure for cloud-native clients.

## Service discovery & gateways
- Eureka/Consul/ZooKeeper for discovery; Spring Cloud Gateway for routing.

## Resilience
- Circuit breakers, retries, and bulkheads (Resilience4j).
- Timeouts on all remote calls.

## Observability
- Correlate logs with trace IDs; export metrics to managed monitoring.

## Checklist
- [ ] Config/secrets externalized
- [ ] Timeouts and retries set
- [ ] Discovery/gateway configured if needed
- [ ] Metrics/traces shipped to cloud observability