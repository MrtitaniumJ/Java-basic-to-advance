# Spring Data JPA

Spring Data JPA streamlines repository creation and query execution on top of JPA/Hibernate.

## Essentials
- Extend `JpaRepository`/`CrudRepository` for CRUD.
- Method-name queries and `@Query` for custom JPQL/SQL.
- Pagination and sorting via `Pageable`/`Sort`.
- Entity mapping: relationships, fetch types, cascades.

## Example
```java
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
  @Query("select u from User u where u.active = true")
  List<User> findActive();
}
```

## Best practices
- Keep business logic in services, not repositories.
- Control fetching to avoid N+1 (fetch joins, entity graphs).
- Use DTO projections for read-heavy endpoints.
- Apply database migrations (Flyway/Liquibase) for schema changes.

## Checklist
- [ ] Clear entity mappings
- [ ] Queries reviewed for N+1
- [ ] Pagination on large result sets
- [ ] Migrations tracked