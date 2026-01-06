# Security and Hardening

Secure Spring Boot apps by default and reduce attack surface.

## Essentials
- Enforce HTTPS; redirect HTTP to HTTPS.
- Secure Actuator endpoints; restrict to ops networks.
- Validate all inputs; sanitize outputs when rendering.
- Use JWT/OAuth2/OpenID Connect for auth when applicable.
- CSRF protection for browser-based sessions.

## Configuration tips
- Set secure headers (HSTS, X-Content-Type-Options, X-Frame-Options).
- Disable directory listings and unnecessary endpoints.
- Limit error detail in production.
- Rotate secrets; never commit them.

## Checklist
- [ ] TLS enforced
- [ ] Actuator locked down
- [ ] Headers hardened
- [ ] Secrets externalized and rotated