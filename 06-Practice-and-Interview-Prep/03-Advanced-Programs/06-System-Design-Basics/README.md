# System Design Basics

## Overview

System Design focuses on building large-scale, scalable systems that handle millions of requests while maintaining reliability and performance. Understanding system design concepts is essential for:
- Building scalable backends
- Preparing for system design interviews
- Understanding distributed systems
- Optimizing application performance
- Making architectural decisions

## Problem Analysis

### Core Principles of System Design

#### 1. **Scalability**
System's ability to handle increased load

**Vertical Scaling:** Increase resources on single machine
- Pro: Simple, no code changes
- Con: Limited by hardware, single point of failure

**Horizontal Scaling:** Add more machines
- Pro: Unlimited scalability, redundancy
- Con: Increased complexity, consistency issues

#### 2. **Availability**
Percentage of time system is operational

**Formula:** Uptime / (Uptime + Downtime) × 100%

**Nines of Availability:**
- 99.9% = 3 nines = 8.76 hours downtime/year
- 99.99% = 4 nines = 52.6 minutes downtime/year
- 99.999% = 5 nines = 5.26 minutes downtime/year

#### 3. **Performance**
Response time and throughput

**Latency:** Time for single request
**Throughput:** Requests per second

#### 4. **Consistency vs. Availability (CAP Theorem)**
Distributed systems can guarantee at most 2 of 3:
- **Consistency:** All clients see same data
- **Availability:** System always responsive
- **Partition tolerance:** System works despite network failures

---

## Components Covered

### 1. **LRU Cache**

**Problem:** Store frequently accessed data with limited memory

**Use Cases:**
- CPU cache lines
- Database query cache
- Redis/Memcached
- Browser page cache
- CDN edge caches

**Key Properties:**
- Fixed capacity
- O(1) get and put operations
- Automatic eviction of least recently used items

**Implementation:**
```
HashMap: Fast lookups O(1)
Doubly LinkedList: Track order O(1) removal/insertion
```

**Algorithm:**

**GET Operation:**
1. Check if key exists in map
2. If exists, move to head (most recently used)
3. Return value

**PUT Operation:**
1. If key exists, update value and move to head
2. If new key:
   - Add to head
   - Add to map
   - If size exceeds capacity:
     - Remove from tail (least recently used)
     - Remove from map

**Complexity:**
- Time: O(1) for both get and put
- Space: O(capacity)

**Real-World Variations:**
- LFU (Least Frequently Used): Track access count
- TTL Cache: Items expire after time
- Multi-level cache: L1, L2, L3 caches

### 2. **Rate Limiter**

**Problem:** Protect APIs from abuse and control traffic

**Use Cases:**
- API throttling
- DDoS protection
- Load shedding
- Fair resource allocation

**Algorithms:**

**Token Bucket:**
```
- Tokens generated at fixed rate
- Request consumes tokens
- If tokens available, allow; else deny
- Handles bursts well
```

**Leaky Bucket:**
```
- Requests added to queue
- Processed at fixed rate
- Queue has limited capacity
- More fair, less bursting
```

**Fixed Window:**
```
- Count requests in time window
- Reset counter at window boundary
- Simple but has edge case at boundaries
```

**Sliding Window:**
```
- More accurate than fixed window
- Requires more memory
- Smooth rate limiting
```

**Token Bucket Advantages:**
- Allows burst traffic
- Simple to understand
- Industry standard

**Implementation Details:**
```
maxTokens: Maximum token capacity
refillRate: Tokens added per second
tokens: Current available tokens
lastRefillTime: When last refill occurred

Refill logic:
timePassed = now - lastRefillTime
tokensToAdd = (timePassed / 1000) * refillRate
tokens = min(maxTokens, tokens + tokensToAdd)
```

**Complexity:**
- Time: O(1) for allow check
- Space: O(1) per limiter

**Real-World Applications:**
- GitHub API: 60 requests/hour for unauthenticated users
- Twitter: 15 requests/15 minutes per endpoint
- Google Maps: Rate limiting based on API key

### 3. **URL Shortener**

**Problem:** Generate short, unique URLs for long URLs

**Use Cases:**
- Bit.ly, TinyURL
- QR codes
- Social media posts
- Analytics tracking

**Requirements:**
- Shorten long URLs
- Expand short URLs back
- Handle redirects
- Track analytics
- Customizable aliases

**Design Approaches:**

**Approach 1: Base62 Encoding (Used Here)**
```
- Auto-incrementing ID
- Encode to base62
- Pro: Sequential, predictable
- Con: Guessable, privacy concerns
- Good for: Internal systems
```

**Approach 2: Random Generation**
```
- Generate 6-8 random alphanumeric chars
- Check uniqueness
- Pro: Unguessable, secure
- Con: Collision handling needed
- Good for: Public services
```

**Approach 3: Hashing**
```
- Hash the long URL
- Take first 6 chars
- Pro: Deterministic
- Con: Collisions possible
- Good for: Deduplication
```

**Database Schema:**
```
CREATE TABLE url_mapping (
    id INT PRIMARY KEY AUTO_INCREMENT,
    short_code VARCHAR(10) UNIQUE,
    long_url VARCHAR(2000),
    created_at TIMESTAMP,
    expiry_time TIMESTAMP,
    user_id INT,
    click_count INT
);
```

**Scalability Considerations:**
- Sharding by hash(long_url)
- Caching hot URLs
- Database replication
- CDN for redirects

**Complexity:**
- Time: O(log n) for base conversion or O(1) with table lookup
- Space: O(n) for all mappings

### 4. **Load Balancer**

**Problem:** Distribute incoming requests across multiple servers

**Use Cases:**
- Web server load balancing
- Database connection pooling
- Microservice routing
- API gateway
- Traffic shaping

**Strategies:**

**Round-Robin**
```
Server assignment: 1, 2, 3, 1, 2, 3...
Pros: Simple, fair distribution
Cons: Ignores server capacity, health
Use: Homogeneous servers, light requests
```

**Weighted Round-Robin**
```
Assign weights: Server 1 (2x), Server 2 (1x)
Distribution: 1, 1, 2, 1, 1, 2...
Pros: Respects capacity differences
Cons: Still static, no health awareness
Use: Heterogeneous servers
```

**Least Connections**
```
Send to server with fewest active connections
Pros: Handles variable request duration
Cons: Overhead tracking connections
Use: Long-lived connections, variable load
```

**IP Hash**
```
hash(client_ip) % num_servers = server_id
Pros: Session persistence, simple
Cons: Unbalanced if clients not uniform
Use: Stateful services, session affinity
```

**Least Response Time**
```
Send to server with fastest response time
Pros: Optimal performance
Cons: Complex, high overhead
Use: Performance-critical systems
```

**Implementation:**
```
1. Health checks: Monitor server health
2. Connection draining: Graceful shutdown
3. Sticky sessions: Route to same server
4. Retry logic: Failover on server down
```

**Complexity:**
- Time: O(1) for round-robin, O(n) for least connections
- Space: O(n) for server tracking

**Real-World Examples:**
- nginx: Load balancer, proxy server
- HAProxy: High availability proxy
- AWS ELB: Elastic Load Balancer
- Kubernetes Service: Container orchestration

## Design Decisions

### 1. LRU Cache Implementation
**LinkedList + HashMap** chosen over:
- Array: O(n) for removal
- TreeMap: O(log n) operations
- Custom doubly-linked list: Best performance

### 2. Rate Limiter Algorithm
**Token Bucket** chosen over:
- Leaky Bucket: Less flexible for bursts
- Fixed Window: Edge case issues
- Sliding Window: More memory overhead

### 3. URL Shortener Encoding
**Base62** chosen for simplicity:
- Sequentially distributed
- Good for demonstration
- Real systems prefer randomization for security

### 4. Load Balancer Strategy
**Multiple strategies** demonstrated:
- Round-robin: Simple and common
- Least connections: More sophisticated
- Can be extended with health checks

## Complexity Analysis

### LRU Cache
| Operation | Time | Space |
|-----------|------|-------|
| Get | O(1) | O(capacity) |
| Put | O(1) | - |
| Eviction | O(1) | - |

### Rate Limiter
| Operation | Time | Space |
|-----------|------|-------|
| Allow Request | O(1) | O(1) |
| Refill Tokens | O(1) | - |

### URL Shortener
| Operation | Time | Space |
|-----------|------|-------|
| Shorten | O(log n)* | O(n) |
| Expand | O(1) | - |
| Lookup | O(1) | - |

*Base conversion or hash lookup

### Load Balancer
| Strategy | Selection Time | Memory |
|----------|---|---|
| Round-Robin | O(1) | O(n) |
| Least Connections | O(n) | O(n) |
| Hash-based | O(1) | O(1) |

## Sample Input/Output

### Running the Program

```powershell
javac SystemDesignDemo.java
java SystemDesignDemo
```

### Expected Output

```
╔════════════════════════════════════════════════════════╗
║        SYSTEM DESIGN BASICS IMPLEMENTATION             ║
╚════════════════════════════════════════════════════════╝

========== LRU CACHE ==========
Capacity: 3

Operations:
put(1, A): Cache[(1:A)]
put(2, B): Cache[(1:A) (2:B)]
put(3, C): Cache[(1:A) (2:B) (3:C)]
get(1): 1
After get(1): Cache[(2:B) (3:C) (1:A)]
put(4, D): Cache[(3:C) (1:A) (4:D)]
(Key 2 evicted - was least recently used)

get(2): -1
(Key 2 not found - was evicted)
put(5, E): Cache[(1:A) (4:D) (5:E)]

Final state: Cache[(1:A) (4:D) (5:E)]

========== RATE LIMITER ==========
Initial tokens: 10
Configuration: Max 10 tokens, refill 2/sec

✓ Allowed: GET /api/users (Remaining: 7)
✓ Allowed: POST /api/data (Remaining: 4)
✓ Allowed: GET /api/status (Remaining: 1)
✗ Blocked: DELETE /api/item - Rate limit exceeded
✗ Blocked: GET /api/search - Rate limit exceeded
✗ Blocked: GET /api/profile - Rate limit exceeded
✗ Blocked: POST /api/upload - Rate limit exceeded

Waiting 1 second for token refill...
Available tokens: 3
✓ Allowed after refill

========== URL SHORTENER ==========
Shortening URLs:

Long:  https://www.example.com/very/long/path/to/some/resource
Short: http://short.url/bi
...
[Additional URLs...]

Expanding shortened URLs:

Short: http://short.url/bi
Long:  https://www.example.com/very/long/path/to/some/resource
Match: true
...

Total URLs stored: 4

========== LOAD BALANCER - ROUND ROBIN ==========
Distributing requests using Round-Robin:

Server Server-1 handling: Request-A (active: 1)
Server Server-2 handling: Request-B (active: 1)
Server Server-3 handling: Request-C (active: 1)
Server Server-1 handling: Request-D (active: 2)
Server Server-2 handling: Request-E (active: 2)
Server Server-3 handling: Request-F (active: 2)

========== LOAD BALANCER - LEAST CONNECTIONS ==========
Distributing requests using Least Connections:

Server Server-1 handling: Request-A (active: 1)
Server Server-2 handling: Request-B (active: 1)
Server Server-3 handling: Request-C (active: 1)
Server Server-1 handling: Request-D (active: 2)
Server Server-2 handling: Request-E (active: 2)
Server Server-3 handling: Request-F (active: 2)

Load Balancer Status:
  Server Server-1 - Active: 2
  Server Server-2 - Active: 2
  Server Server-3 - Active: 2

Releasing connections...
Load Balancer Status:
  Server Server-1 - Active: 0
  Server Server-2 - Active: 1
  Server Server-3 - Active: 2

=======================================================
All demonstrations completed successfully!
=======================================================
```

## Real-World Applications

### 1. **E-Commerce (Amazon, eBay)**
- LRU Cache: Product catalogs, user sessions
- Rate Limiter: API protection
- Load Balancer: Distribute traffic across data centers
- URL Shortener: Marketing links

### 2. **Social Media (Twitter, Facebook)**
- LRU Cache: Feed caching, user profiles
- Rate Limiter: Prevent spam, API limits
- Load Balancer: Multi-region distribution
- URL Shortener: Link sharing

### 3. **Video Streaming (Netflix, YouTube)**
- LRU Cache: Video segments, metadata
- Rate Limiter: Bandwidth throttling
- Load Balancer: CDN edge servers
- URL Shortener: Sharing links

### 4. **Financial Systems**
- LRU Cache: Stock prices, account balances
- Rate Limiter: Transaction throttling
- Load Balancer: Multi-zone trading systems
- URL Shortener: Share research links

## Variations and Challenges

### Challenge 1: Distributed LRU Cache
Extend LRU cache across multiple machines.

**Challenges:**
- Cache invalidation (hardest problem in CS)
- Consistency across replicas
- Network latency

**Solutions:**
- Consistent hashing for shard selection
- Write-through vs write-back policies
- TTL for expiration

### Challenge 2: Sliding Window Rate Limiter
More accurate than token bucket for precise limits.

**Implementation:**
- Store request timestamps in queue
- Check oldest request > window
- Remove expired requests

### Challenge 3: URL Shortener with Expiry
Add time-based expiration for short URLs.

**Implementation:**
- Store expiry timestamp
- Cleanup expired URLs
- Notify users of expiry

### Challenge 4: Weighted Load Balancer
Distribute based on server weights.

**Implementation:**
- Maintain weight for each server
- Probabilistic selection
- Adjust weights dynamically

### Challenge 5: Health-Aware Load Balancer
Route away from failing servers.

**Implementation:**
- Periodic health checks
- Mark unhealthy servers
- Skip in rotation

### Challenge 6: Auto-Scaling Load Balancer
Add/remove servers based on load.

**Implementation:**
- Monitor utilization metrics
- Threshold-based scaling
- Graceful shutdown

## Interview Tips

### When Discussing System Design

1. **Define Requirements First:**
   - Functional: What system must do
   - Non-functional: Scale, latency, consistency
   - Constraints: Budget, technology, team

2. **Design for Scale:**
   - Single machine limits
   - Network considerations
   - Database bottlenecks
   - Cache strategies

3. **Handle Failures:**
   - Redundancy
   - Failover mechanisms
   - Data replication
   - Monitoring and alerts

4. **Optimize Iteratively:**
   - Measure bottlenecks
   - Profile hot paths
   - Trade-off analysis
   - Cost vs. performance

### Common Questions

**Q: How to scale LRU cache?**
A: Consistent hashing to shard cache across servers, replicate for redundancy.

**Q: What if rate limit is exceeded?**
A: Queue requests, return 429 status, exponential backoff for clients.

**Q: How to generate unique short URLs?**
A: Random generation with collision checking or snowflake ID algorithm.

**Q: How to handle load balancer failure?**
A: Redundant load balancers with health checks and failover.

## Key Learnings

1. **Scalability requires multiple strategies** - no single solution
2. **Trade-offs are fundamental** - consistency vs availability, cost vs complexity
3. **Performance optimization is iterative** - measure, profile, optimize
4. **Failure handling is critical** - design for failures
5. **Monitoring is essential** - can't optimize what you don't measure

## Further Exploration

1. **Distributed Systems:** Consensus algorithms, eventual consistency
2. **Databases:** Sharding, replication, indexing
3. **Caching Strategies:** Multi-level caches, cache invalidation
4. **Message Queues:** Async processing, event streaming
5. **Monitoring:** Logging, metrics, alerting, tracing

## Recommended Resources

- **Designing Data-Intensive Applications** (Kleppmann)
- **System Design Interview** (Xu)
- **Release It!** (Nygard) - Production concerns
- **Web Scalability for Startup Engineers** (Limoncelli)

---

**Compilation**: `javac SystemDesignDemo.java`
**Execution**: `java SystemDesignDemo`
**Difficulty**: Advanced
**Topics**: System Design, Distributed Systems, Scalability
**Prerequisites**: Java, Data Structures, Algorithms, Concurrency basics
