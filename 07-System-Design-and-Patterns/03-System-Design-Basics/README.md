# System Design Basics

## Overview

System design is the process of architecting software systems to handle real-world requirements like scale, performance, reliability, and maintainability. This section covers fundamental concepts essential for designing robust, scalable systems used by companies like Netflix, Amazon, and Google.

---

## 1. Scalability Concepts

Scalability is the ability of a system to handle increased load efficiently. It's critical for building systems that grow with user demand.

### Vertical vs. Horizontal Scaling

**Vertical Scaling (Scale Up)**
- Adding more resources (CPU, RAM) to a single machine
- Simpler to implement initially
- Limited by hardware capacity
- Single point of failure
- Example: Upgrading a server from 8GB to 16GB RAM

**Horizontal Scaling (Scale Out)**
- Distributing load across multiple machines
- No hardware limits
- Introduces complexity (distributed systems challenges)
- Provides fault tolerance
- Example: Adding more web servers behind a load balancer

### Load Balancing Strategies

**Round-Robin**
- Distribute requests sequentially across servers
- Simple but doesn't account for server capacity
- Best for homogeneous systems with similar load

**Least Connections**
- Route to the server with fewest active connections
- Better for long-lived connections
- More intelligent than round-robin

**Consistent Hashing**
- Maps requests to servers using hash functions
- Minimizes redistribution when servers are added/removed
- Critical for distributed caching systems
- Used in Amazon DynamoDB, memcached

**Weighted Distribution**
- Assign weights based on server capacity
- Route more requests to powerful machines

### Database Scaling

**Sharding (Horizontal Partitioning)**
- Split data across multiple database instances by a shard key
- Each shard holds subset of data (e.g., users A-M in DB1, N-Z in DB2)
- Enables linear scalability
- Challenge: Shard selection and rebalancing

**Replication**
- Master-slave architecture: Writes to master, reads from slaves
- Improves read performance and fault tolerance
- Introduces consistency challenges (eventual consistency)
- Used by MySQL, MongoDB

**Partitioning**
- Vertical: Split columns across tables/databases
- Horizontal: Split rows (same as sharding)

### Caching Strategies

**Content Delivery Network (CDN)**
- Geographically distributed servers storing copies of content
- Reduces latency by serving from nearest location
- Example: Cloudflare, Akamai, AWS CloudFront
- Used by Netflix for video delivery

**Redis**
- In-memory data store for fast caching
- Supports complex data structures (lists, sets, sorted sets)
- Persistence options available
- Examples: Session caching, leaderboards

**Memcached**
- Simple, fast in-memory caching
- Key-value pairs only
- Lightweight and highly efficient
- Good for horizontal scaling

---

## 2. High Availability & Fault Tolerance

High availability ensures systems remain operational despite failures. Fault tolerance enables systems to continue functioning when components fail.

### Redundancy and Failover

- **Active-Active**: All nodes serve traffic simultaneously
- **Active-Passive**: Standby node takes over when primary fails
- **Automatic Failover**: Detect failures and switch immediately
- **Heartbeat Mechanism**: Periodic health signals to detect failures
- Example: Google Cloud regions maintain replicas for failover

### Circuit Breaker Pattern

Prevents cascading failures by stopping requests to failing services:

```
CLOSED (normal) → OPEN (failing) → HALF_OPEN → CLOSED
                     ↓
              After timeout, try again
```

Benefits:
- Fail fast instead of hanging
- Allow downstream services to recover
- Reduce resource waste

### Health Checks and Monitoring

- **Liveness Probe**: Is the service running?
- **Readiness Probe**: Can it handle requests?
- **Startup Probe**: Has initialization completed?
- **Metrics**: CPU, memory, latency, error rates
- Tools: Prometheus, Datadog, New Relic

### Disaster Recovery Planning

- **RTO (Recovery Time Objective)**: Maximum acceptable downtime
- **RPO (Recovery Point Objective)**: Maximum acceptable data loss
- **Backup Strategy**: Regular snapshots, geographic redundancy
- **Testing**: Conduct regular disaster recovery drills
- Example: Amazon maintains data replicas across multiple regions

---

## 3. Data Storage & Databases

Choosing the right database is crucial. No single database fits all scenarios.

### SQL vs. NoSQL

**SQL (Relational)**
- Structured schemas, ACID guarantees
- Good for: Complex queries, transactions, structured data
- Examples: PostgreSQL, MySQL, Oracle
- Used by: Traditional enterprise systems, financial applications

**NoSQL**
- Flexible schemas, horizontal scalability
- Trade ACID for availability and partition tolerance (BASE)
- Good for: Large-scale, unstructured data
- Examples: MongoDB (document), Cassandra (wide-column), Redis (key-value)
- Used by: Netflix, Facebook, Twitter

### ACID vs. BASE Properties

**ACID**
- **Atomicity**: All-or-nothing transactions
- **Consistency**: Data validity maintained
- **Isolation**: Concurrent transactions don't interfere
- **Durability**: Data persists after commit

**BASE**
- **Basically Available**: Always available
- **Soft state**: State may change without input (eventual consistency)
- **Eventually Consistent**: Data converges to consistency over time

### Denormalization and Indexing

**Denormalization**
- Intentionally duplicate data across tables
- Improves read performance at the cost of write complexity
- Example: Store user name in posts table to avoid join

**Indexing**
- Create index on frequently queried columns
- Trade-off: Faster reads, slower writes
- Types: B-tree, hash, bitmap indexes
- Example: `CREATE INDEX idx_email ON users(email)`

### Database Selection Guide

| Scenario | Database | Reason |
|----------|----------|--------|
| Financial transactions | PostgreSQL | ACID guarantees |
| User profiles with nested data | MongoDB | Flexible schema |
| Real-time analytics | ClickHouse | Column-oriented |
| Cache with expiration | Redis | In-memory, TTL support |
| Time-series data | InfluxDB | Optimized for metrics |
| Distributed wide-column | Cassandra | High availability, scalability |

---

## 4. Caching & Performance

Caching is essential for system performance. The goal: serve data from the fastest location.

### Cache Invalidation Strategies

**TTL (Time-To-Live)**
- Expire cached data after set duration
- Simple but may serve stale data
- Example: Cache user profile for 1 hour

**LRU (Least Recently Used)**
- Evict least recently accessed item when capacity full
- Good for memory-constrained systems
- Example: Memcached uses LRU by default

**Write-Through Cache**
- Write to cache AND database simultaneously
- Higher consistency, higher latency
- Example: Banking systems

**Write-Behind (Write-Back) Cache**
- Write to cache first, asynchronously to database
- Lower latency, risk of data loss
- Example: Social media activity feeds

**Cache Invalidation on Update**
- Explicitly remove cache entry when data changes
- Immediate consistency but added complexity
- Example: Invalidate user cache when profile updates

### Cache Coherence Problems

- **Stale Reads**: Serving outdated cached data
- **Cache Miss**: Requested data not in cache
- **Cache Stampede**: Sudden spike in cache misses, overwhelming database
- **Double-Checked Locking**: Avoid cache stampede pattern

### When to Use Caching

✅ Good for:
- Frequently accessed, infrequently changed data (user profiles)
- Expensive computations (recommendations)
- Database query results
- Session storage

❌ Bad for:
- Frequently changing data (real-time stock prices)
- Small, fast queries
- Unique queries per user
- Data requiring immediate consistency

---

## 5. Message Queues & Asynchronous Processing

Decouple system components for improved resilience and scalability.

### Producer-Consumer Pattern

**Producer**: Sends messages to queue
**Queue**: Stores messages durably
**Consumer**: Processes messages independently

Benefits:
- Decoupling: Producer and consumer are independent
- Asynchronous processing: Don't wait for results
- Load leveling: Queue absorbs traffic spikes
- Reliability: Messages persist if consumer unavailable

### Message Brokers

**Kafka**
- Distributed event streaming platform
- Horizontal scalability, high throughput
- Persistent log, replay capability
- Used by: LinkedIn, Netflix, Uber

**RabbitMQ**
- Traditional message broker
- Multiple routing patterns (direct, topic, fanout)
- Lower latency than Kafka
- Used by: Booking.com, Zalando

**AWS SQS**
- Managed queue service
- Simple, reliable, serverless
- Good for decoupled microservices

### Async Communication Benefits

- **Resilience**: Failures don't cascade
- **Scalability**: Consumers scale independently
- **Latency**: Users don't wait for slow operations
- **Reliability**: Retry logic handles failures

---

## 6. API Design & Communication

APIs are contracts between systems. Good design ensures scalability and developer experience.

### REST Principles

- **Client-Server**: Separation of concerns
- **Statelessness**: Each request contains all needed information
- **Cacheability**: Responses explicitly marked cacheable
- **Uniform Interface**: Consistent, predictable endpoints
- **Layered**: Clients can't tell if connected directly to end server

### REST Best Practices

- Use HTTP methods semantically: GET (retrieve), POST (create), PUT (update), DELETE (remove)
- Resource-oriented URLs: `/users/123/posts` not `/getUser`
- Version APIs: `/api/v1/users` or header-based
- Use appropriate status codes: 200 (OK), 201 (Created), 400 (Bad Request), 500 (Server Error)

### GraphQL vs. REST

**GraphQL**
- Query exactly what you need
- Single endpoint, flexible queries
- Better for mobile (less data transfer)
- Steeper learning curve

**REST**
- Simple, HTTP-native
- Stateless, cacheable
- Can over/under-fetch data
- More mature ecosystem

### Versioning Strategies

- **URL Path**: `/api/v1/users`, `/api/v2/users`
- **Query Parameter**: `/users?version=2`
- **Header**: `Accept: application/vnd.api+json;version=2`
- **Semantic Versioning**: Major.Minor.Patch (breaking, compatible, patch changes)

### Rate Limiting and Throttling

- **Token Bucket**: Refill tokens at rate; requests consume tokens
- **Sliding Window**: Track requests in time window
- **Fixed Window**: Count requests per time period
- **Adaptive**: Dynamically adjust based on server capacity
- Benefits: Prevent abuse, ensure fair usage, protect infrastructure

---

## 7. Monitoring, Logging & Tracing

Observability is critical for understanding system behavior in production.

### Distributed Tracing

**OpenTelemetry**
- Open standard for collecting telemetry
- Traces, metrics, logs in single platform
- Language-agnostic
- Integrates with Jaeger, Datadog, AWS X-Ray

**Jaeger**
- Open-source distributed tracing
- Visualize request flows across services
- Identify latency bottlenecks
- Used by: Uber, Netflix

Tracing helps answer: "What happened to my request?" by tracking it through all services.

### Centralized Logging

- **ELK Stack**: Elasticsearch, Logstash, Kibana
- **Splunk**: Enterprise logging platform
- **CloudWatch**: AWS managed logging
- Benefits: Aggregate logs from multiple services, search, correlate events
- Log levels: DEBUG, INFO, WARN, ERROR, FATAL

### Metrics Collection and Monitoring

Key metrics:
- **Application**: Response time, error rate, throughput
- **Infrastructure**: CPU, memory, disk, network
- **Business**: User signups, revenue, conversion rate

Tools: Prometheus, Grafana, Datadog, New Relic

### Alerting Strategies

- **Threshold-based**: Alert if metric > threshold
- **Anomaly Detection**: Alert on unusual patterns
- **Composite Alerts**: Multiple conditions must trigger
- **Error Budget**: Track acceptable error rate
- Avoid alert fatigue: Set meaningful thresholds

---

## Real-World Examples

**Netflix Architecture**
- Microservices with autonomous teams
- Chaos engineering to ensure resilience
- Multi-region deployments for availability
- CDN for global video delivery

**Amazon's Scale**
- Sharding strategy for massive data
- DynamoDB for serverless scalability
- CloudFront CDN for content delivery
- Automated scaling groups

**Google's Designs**
- MapReduce for distributed processing
- Bigtable for structured data at scale
- Chubby for distributed coordination
- Multiple data centers for fault tolerance

---

## Common Interview Questions

1. **Design Twitter**: Handle tweets, followers, feed (consider fanout strategies)
2. **Design YouTube**: Video storage, streaming, recommendations
3. **Design Uber**: Location tracking, matching drivers to riders
4. **Design Instagram**: Photo storage, feed generation, notifications
5. How would you scale a system experiencing sudden traffic spike?
6. Explain trade-offs between consistency and availability (CAP theorem)
7. How would you implement eventual consistency?

---

## Common Pitfalls

❌ **Premature Optimization**: Don't optimize before identifying bottlenecks
❌ **Ignoring Failures**: Assume components fail; design for resilience
❌ **Single Points of Failure**: Eliminate hard dependencies
❌ **Over-caching**: Cache invalidation is notoriously hard
❌ **Inadequate Monitoring**: "You can't improve what you don't measure"
❌ **Ignoring Network Partitions**: Design for network failures (CAP theorem)
❌ **Synchronous Everything**: Use async for non-critical operations

---

## Key Takeaways

1. **Scalability requires horizontal thinking**: Distribute load, data, and processing
2. **Trade-offs are unavoidable**: Choose based on requirements (consistency vs. availability, complexity vs. reliability)
3. **Observability is non-negotiable**: Monitor, log, trace everything
4. **Fail gracefully**: Design systems expecting failures
5. **Test at scale**: Chaos engineering, load testing, disaster recovery drills
6. **Document decisions**: Architecture Decision Records (ADRs) explain why

---

## Resources for Further Learning

- System Design Interview book (Alex Xu)
- Designing Data-Intensive Applications (Martin Kleppmann)
- High Performance Browser Networking (Ilya Grigorik)
- Papers: Amazon's Dynamo, Google's Bigtable, Google's MapReduce
- YouTube: Tech talks from Netflix, Uber, Airbnb engineering channels

---

**Last Updated**: January 2026
