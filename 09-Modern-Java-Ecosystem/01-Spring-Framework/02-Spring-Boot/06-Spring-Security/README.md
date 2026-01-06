# Spring Security

Spring Security secures requests, authenticates users, and authorizes actions.

## Core ideas
- Security filter chain processes every request.
- Authentication: who you are (user details, tokens).
- Authorization: what you can do (roles/authorities, expressions).
- Password encoding with `PasswordEncoder` (e.g., BCrypt).

## Typical setup (HTTP)
- Define a `SecurityFilterChain` bean configuring HTTP security.
- Use `UserDetailsService` or JWT token validators.
- Protect endpoints via ant matchers or method security (`@PreAuthorize`).

## Best practices
- Use HTTPS everywhere; terminate TLS in front or in app.
- Never store raw passwords; use strong encoders.
- Principle of least privilege for roles and scopes.
- Centralize authentication/authorization logic.

## Checklist
- [ ] Filter chain defined
- [ ] Passwords encoded
- [ ] Sensitive endpoints protected
- [ ] CSRF considerations for state-changing requests (enable for browser apps)