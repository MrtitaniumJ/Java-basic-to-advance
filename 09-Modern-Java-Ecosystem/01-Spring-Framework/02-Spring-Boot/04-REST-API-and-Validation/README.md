# REST API and Validation

Build HTTP APIs quickly with Spring Boot Web plus Bean Validation.

## Core pieces
- `@RestController` + `@RequestMapping` for endpoints
- JSON via Jackson message converters
- Input validation with `@Valid` and Jakarta Validation annotations
- Exception handling with `@ControllerAdvice`

## Example
```java
@RestController
@RequestMapping("/api/todos")
class TodoController {
  private final TodoService service;
  TodoController(TodoService service) { this.service = service; }

  @PostMapping
  ResponseEntity<Todo> create(@Valid @RequestBody CreateTodo req) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
  }
}
```

## Best practices
- Use DTOs for requests/responses; avoid exposing entities.
- Return proper status codes; include error payloads.
- Centralize validation messages and error handling.
- Add pagination/filtering for list endpoints.

## Checklist
- [ ] DTOs validated
- [ ] Consistent error model
- [ ] Pagination for collections
- [ ] Tests for happy-path and validation failures