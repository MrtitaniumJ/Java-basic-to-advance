# Spring MVC Basics

## Overview
Spring MVC provides request handling with controllers, data binding, validation, view rendering (or JSON via message converters).

## Key pieces
- **DispatcherServlet**: front controller routing requests.
- **Controllers**: `@Controller` or `@RestController` with `@RequestMapping` methods.
- **Data binding & validation**: `@RequestBody`, `@Valid`, `BindingResult`.
- **Message converters**: JSON/XML via Jackson/others.
- **Exception handling**: `@ControllerAdvice` + `@ExceptionHandler`.

## Example (REST)
```java
@RestController
@RequestMapping("/api/users")
class UserController {
  private final UserService service;
  UserController(UserService service) { this.service = service; }

  @PostMapping
  ResponseEntity<UserDto> create(@Valid @RequestBody CreateUserRequest req) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
  }
}
```

## Best practices
- Keep controllers thin; delegate to services.
- Validate inputs; return meaningful status codes.
- Centralize error handling in advice classes.
- Version APIs when breaking changes occur.

## Checklist
- [ ] Clear request mappings and HTTP semantics
- [ ] Validation on inputs
- [ ] Consistent error model
- [ ] No business logic in controllers