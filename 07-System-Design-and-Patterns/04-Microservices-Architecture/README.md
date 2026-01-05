# Microservices Architecture

## Overview

Microservices architecture is an approach to developing software systems as a collection of loosely coupled, independently deployable services. Each service encapsulates specific business functionality and communicates with others through well-defined APIs or messaging systems.

---

## 1. Microservices Overview

### Monolithic vs. Microservices Architecture

**Monolithic Architecture:**
- Single, unified codebase deployed as one unit
- All components tightly coupled
- Database shared across all features
- Easier to develop initially but becomes complex at scale
- Deployment requires restarting the entire application

**Microservices Architecture:**
- Multiple independent services deployed separately
- Loosely coupled through APIs or events
- Each service has its own database
- Better scalability and team autonomy
- Faster deployment cycles for individual services

### Advantages of Microservices

- **Independent Scalability:** Scale services based on specific demands
- **Technology Flexibility:** Use different tech stacks for different services
- **Team Autonomy:** Different teams can work independently
- **Fault Isolation:** Failure in one service doesn't crash the entire system
- **Continuous Deployment:** Deploy services without affecting others
- **Clear Service Boundaries:** Well-defined responsibilities per service

### Trade-offs & Challenges

- **Increased Complexity:** Distributed systems are inherently complex
- **Network Latency:** Inter-service communication adds delays
- **Data Consistency:** Maintaining consistency across services is challenging
- **Operational Overhead:** Need sophisticated monitoring and deployment tools
- **Testing Complexity:** Integration and end-to-end testing becomes harder

### When to Use Microservices

- Large, complex applications with multiple teams
- Services with different scaling requirements
- Need for independent technology choices
- Frequent, independent deployments required
- **Avoid for:** Small projects, tight latency requirements, strongly consistent data needs

### Real-World Case Studies

**Netflix:**
- Transitioned from monolith to microservices to handle millions of concurrent users
- Uses microservices for recommendations, streaming, user accounts
- Developed Hystrix for circuit breaker pattern
- Benefits: Independent scaling, rapid feature deployment

**Uber:**
- Microservices for different business domains: rides, payments, maps
- Uses service mesh for inter-service communication
- Benefits: Scale surge pricing independently, rapid expansion to new markets

**Amazon:**
- Pioneer of microservices architecture
- Each team owns end-to-end service
- Enabled rapid innovation and global scale

---

## 2. Service Design & Boundaries

### Domain-Driven Design (DDD) and Bounded Contexts

**Key Concepts:**
- **Bounded Context:** Clear boundaries defining a domain's responsibility
- **Ubiquitous Language:** Shared vocabulary within each service
- **Domain Models:** Encapsulate business logic and data per domain

Each microservice should align with a bounded context—for example, separate services for `OrderService`, `PaymentService`, `InventoryService`.

### Service Decomposition Strategies

1. **By Business Capability:** Group services around business functions
   - Example: Payment service, Shipping service, User service

2. **By Subdomain:** Separate core, supporting, and generic domains
   - Core: Competitive advantage (e.g., Recommendation engine)
   - Supporting: Business-specific but not core
   - Generic: Standard functions (e.g., Authentication)

3. **By Data Ownership:** Services own their data
   - Prevents tightly coupled schemas
   - Enables independent evolution

### API Gateway Pattern

Acts as a single entry point for client requests:
- **Request Routing:** Routes requests to appropriate services
- **Authentication:** Centralized auth/authorization
- **Rate Limiting:** Protects backend services
- **Request/Response Transformation:** Adapts between client and service formats
- **Load Balancing:** Distributes requests across service instances

Example: Netflix Zuul, Kong, AWS API Gateway

### Service Registry and Discovery

**Problem:** Services need to find each other dynamically as instances are added/removed.

**Solutions:**
- **Client-Side Discovery:** Client queries registry to find service instances
- **Server-Side Discovery:** Load balancer queries registry

Tools: Consul, Eureka, ZooKeeper, Kubernetes DNS

---

## 3. Inter-Service Communication

### Synchronous Communication

**REST API:**
```
OrderService → PaymentService (HTTP request)
Waits for response before proceeding
```
Simple and widely understood, but tight coupling and latency issues.

**gRPC:**
- High-performance RPC framework
- Binary protocol (protobuf)
- Streaming support
- Better latency than REST

### Asynchronous Messaging

**Event-Driven Architecture:**
- Services publish events when something happens
- Other services subscribe to relevant events
- Decouples services; enables scalability

**Message Brokers:** RabbitMQ, Apache Kafka, AWS SQS/SNS

Example: When order is placed, `OrderService` publishes `OrderCreated` event. `NotificationService` and `InventoryService` subscribe independently.

**Pub-Sub Pattern:**
- Publishers don't know about subscribers
- Excellent for loose coupling
- Enables complex event flows

### Service-to-Service Authentication

**Approaches:**
1. **OAuth2 / JWT:** Each service gets JWT token, validates with issuer
2. **mTLS (Mutual TLS):** Encrypted communication with certificate validation
3. **API Keys:** Simple but less secure
4. **Service Mesh:** Handles authentication transparently

### Resilience Patterns

**Circuit Breaker:** Prevents cascading failures
- Open: Reject requests immediately
- Half-Open: Allow test request to check if service recovered
- Closed: Normal operation

**Retry with Exponential Backoff:** Automatic retry with increasing delays

**Timeout:** Prevent indefinite waiting

**Bulkhead Pattern:** Isolate critical resources to prevent resource exhaustion

---

## 4. Data Management in Microservices

### Database per Service Pattern

Each service owns its database—enables independence and prevents tight coupling.

**Challenges:** Querying across services, maintaining data consistency

### Distributed Transactions

**Two-Phase Commit (2PC):**
- Coordinator prepares all services
- If all agree, commits; otherwise rolls back
- Problematic: locks resources, poor availability

**Saga Pattern (Preferred):**
- Long-running transaction split into local transactions
- Each step publishes event triggering next step
- Handles failures with compensating transactions

Example: Payment → Inventory Deduction → Shipping (if payment fails, compensate all)

### Event Sourcing

Store all state changes as immutable events:
```
[OrderCreated] → [PaymentProcessed] → [OrderShipped]
```
Benefits: Complete audit trail, temporal queries
Drawback: Complexity, eventual consistency

### CQRS (Command Query Responsibility Segregation)

Separate read and write models:
- **Command Side:** Handles mutations
- **Query Side:** Optimized for reads (eventual consistency)

Enables independent scaling and optimization.

### Data Consistency Challenges

- **Eventual Consistency:** Accept that systems won't be immediately consistent
- **Compensation:** Use sagas for distributed rollbacks
- **Idempotency:** Design operations that are safe to retry

---

## 5. Deployment & Orchestration

### Containerization (Docker)

Packages service with dependencies into containers:
```dockerfile
FROM openjdk:11
COPY target/payment-service.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Benefits: Environment consistency, rapid deployment, resource efficiency

### Container Orchestration (Kubernetes Basics)

**Kubernetes Components:**
- **Pods:** Smallest deployable units
- **Services:** Expose pods with stable endpoints
- **Deployments:** Manage pod replicas and updates
- **ConfigMaps/Secrets:** Manage configuration and sensitive data

Handles: Auto-scaling, self-healing, rolling updates, service discovery

### Service Mesh (Istio, Linkerd)

Manages service-to-service communication without changing application code:
- **Traffic Management:** Canary deployments, A/B testing
- **Security:** mTLS, authorization policies
- **Observability:** Automatic tracing and metrics
- **Resilience:** Retries, timeouts, circuit breakers

### CI/CD Pipelines for Microservices

**Pipeline Stages:**
1. **Build:** Compile, test, create Docker image
2. **Push:** Registry deployment
3. **Deploy to Dev:** Automated deployment
4. **Test:** Integration and smoke tests
5. **Deploy to Staging:** Pre-production validation
6. **Deploy to Production:** Blue-green or canary deployment

Benefits: Rapid feedback, reduced manual errors, rapid rollback capability

---

## 6. Observability in Microservices

### Distributed Tracing

Tracks request flow across services:
- Assigns unique trace ID to each request
- Logs trace ID at each service
- Visualizes entire request path

Tools: Jaeger, Zipkin, AWS X-Ray

### Centralized Logging

Aggregates logs from all services into single location:
- **Search and Filter:** Find issues across services
- **Correlation IDs:** Link related logs

Tools: ELK Stack (Elasticsearch, Logstash, Kibana), Splunk, CloudWatch

### Metrics and Monitoring

**Key Metrics:**
- **RED:** Request rate, Errors, Duration
- **USE:** Utilization, Saturation, Errors

Tools: Prometheus, Grafana, Datadog

### Debugging Distributed Systems

Strategies:
1. **Correlation IDs:** Track request through system
2. **Structured Logging:** JSON logs with consistent fields
3. **Distributed Tracing:** Visual request flows
4. **Feature Flags:** Enable detailed logging for specific users
5. **Synthetic Monitoring:** Proactive testing

---

## 7. Security in Microservices

### OAuth2 and JWT Authentication

**JWT (JSON Web Token):**
- Self-contained token with user information
- Signed by issuer, validated by services
- Stateless—no session lookup needed
- Enable SSO across services

### Service-to-Service Security

**mTLS (Mutual TLS):**
- Encrypt communication between services
- Verify service identity with certificates
- Often handled by service mesh

**OAuth2 Client Credentials Flow:** Services authenticate using credentials

### API Security Best Practices

- **Rate Limiting:** Prevent abuse and DDoS
- **Input Validation:** Sanitize all inputs
- **CORS:** Control cross-origin access
- **API Versioning:** Maintain backward compatibility
- **Deprecation Policies:** Communicate breaking changes

### Secrets Management

**Challenges:** API keys, database passwords, certificates

**Solutions:**
- **Vault:** Centralized secret storage with encryption
- **AWS Secrets Manager:** Cloud-native secrets
- **Kubernetes Secrets:** Built into Kubernetes
- Rotate secrets regularly, audit access

---

## 8. Common Challenges & Solutions

### Network Latency and Timeouts

- **Problem:** Inter-service calls add milliseconds; cascading delay
- **Solutions:** 
  - Aggressive timeouts to fail fast
  - Batch requests
  - Cache frequently accessed data
  - Use async messaging for non-critical paths

### Cascading Failures

- **Problem:** One service's slowness affects dependent services
- **Solutions:**
  - Circuit breakers
  - Bulkhead pattern (isolate resources)
  - Graceful degradation
  - Health checks and auto-recovery

### Distributed Debugging

- **Problem:** Reproducing issues across multiple services is hard
- **Solutions:**
  - Correlation IDs through entire stack
  - Distributed tracing
  - Request replay tools
  - Comprehensive logging
  - Staging environment that mimics production

### Testing Strategies for Microservices

1. **Unit Tests:** Test service logic in isolation
2. **Integration Tests:** Test service with dependencies (mocked)
3. **Contract Tests:** Verify API contracts between services
4. **End-to-End Tests:** Test complete user workflows (minimal, slow)
5. **Chaos Engineering:** Intentionally break systems to test resilience

---

## Transition Strategy: From Monolith to Microservices

### Gradual Approach (Recommended)

1. **Identify Service Boundaries:** Use DDD to identify bounded contexts
2. **Create Anti-Corruption Layer:** Adapter between old and new systems
3. **Extract Services Incrementally:** Extract one service at a time
4. **Parallel Routing:** Route requests to both monolith and new service
5. **Full Migration:** Once service stable, fully migrate

### Key Considerations

- **Team Readiness:** Microservices require cultural and operational changes
- **Infrastructure Maturity:** Need containerization and orchestration ready
- **Monitoring First:** Set up observability before splitting services
- **Database Refactoring:** Separate databases gradually
- **API Contracts:** Define and version APIs carefully

---

## Interview Preparation Tips

**Common Questions:**
1. "When would you recommend microservices vs. monolith?" → Discuss tradeoffs and organizational context
2. "How would you handle distributed transactions?" → Explain saga pattern with examples
3. "Describe cascading failure and how to prevent it" → Circuit breakers, bulkhead pattern
4. "How do you ensure data consistency?" → Eventual consistency, saga pattern, CQRS
5. "What monitoring would you set up?" → Distributed tracing, centralized logs, metrics

**Best Practices to Mention:**
- Service independence and clear boundaries
- Event-driven communication for loose coupling
- Observability from day one
- Gradual transition from monolith
- Automation in deployment and operations

---

## Key Takeaways

Microservices enable independent scaling, deployment, and team autonomy but introduce operational complexity and distributed system challenges. Success requires:
- Clear service boundaries aligned with business domains
- Robust inter-service communication patterns
- Comprehensive observability and monitoring
- Strong focus on resilience and failure handling
- Cultural shift toward ownership and automation
