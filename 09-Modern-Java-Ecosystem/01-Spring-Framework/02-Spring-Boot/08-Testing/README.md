# Testing Spring Boot Apps

Spring Boot provides testing slices and utilities for fast feedback.

## Test types
- **Unit tests**: plain JUnit/Mockito, no Spring context.
- **Slice tests**: `@WebMvcTest`, `@DataJpaTest`, `@RestClientTest`, etc.
- **Integration tests**: `@SpringBootTest` with real context; use testcontainers for external deps.

## Tips
- Prefer slice tests for speed and focus.
- Mock external calls; use WireMock/MockWebServer when needed.
- Use `@TestConfiguration` for test-only beans.
- Keep fixtures small and intention-revealing.

## Checklist
- [ ] Fast unit tests for business logic
- [ ] Slice tests for web/data layers
- [ ] Integration tests for wiring + happy paths
- [ ] Testcontainers for DB/queues where relevant