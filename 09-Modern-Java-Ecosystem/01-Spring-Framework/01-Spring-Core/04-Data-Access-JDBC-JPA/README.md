# Data Access (JDBC / JPA)

## JDBC with Spring
- Use `JdbcTemplate` for concise CRUD with prepared statements.
- Handles resource management (connections, statements, result sets).
- RowMapper for mapping rows to objects.

## JPA with Spring
- Use `EntityManager` or Spring Data JPA repositories.
- Annotations: `@Entity`, `@Id`, `@GeneratedValue`, relationships.
- Persistence contexts manage entity lifecycle and caching.

## Transaction integration
- Configure a DataSource and PlatformTransactionManager.
- Use `@Transactional` on service methods for consistency.

## Example (JdbcTemplate)
```java
@Repository
class UserRepo {
  private final JdbcTemplate jdbc;
  UserRepo(JdbcTemplate jdbc) { this.jdbc = jdbc; }
  List<User> findAll() {
    return jdbc.query("select id, name from users", (rs, i) -> new User(rs.getLong(1), rs.getString(2)));
  }
}
```

## Best practices
- Keep SQL in repositories; keep services orchestration-focused.
- Prefer named parameters (`NamedParameterJdbcTemplate`) for clarity.
- For JPA, minimize N+1 queries (fetch joins, batch fetching).
- Validate schema alignment (migrations via Flyway/Liquibase).

## Checklist
- [ ] Clear separation: repo (data) vs service (business)
- [ ] Transactions on service boundaries
- [ ] Connection pooling configured
- [ ] Migrations automated