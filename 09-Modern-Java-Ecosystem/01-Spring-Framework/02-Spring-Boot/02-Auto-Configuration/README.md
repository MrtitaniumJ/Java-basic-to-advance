# Auto-Configuration

Auto-configuration conditionally wires beans based on the classpath and configuration properties, giving sensible defaults while allowing overrides.

## How it works
- `@SpringBootApplication` enables component scanning + auto-config.
- `spring.factories` / `AutoConfiguration.imports` list auto-config classes.
- Conditions (`@ConditionalOnClass`, `@ConditionalOnMissingBean`, etc.) guard configuration.

## Working with auto-config
- Override beans by defining your own of the same type/name.
- Use `spring.autoconfigure.exclude` to disable specific configs.
- Inspect `actuator/conditions` to see what matched.

## Best practices
- Let auto-config handle defaults; override only when necessary.
- Keep custom configuration isolated and well-documented.
- Use profiles for environment-specific overrides.

## Checklist
- [ ] Know key conditions for modules you use
- [ ] Override intentionally, not by accident
- [ ] Use Actuator conditions endpoint when debugging