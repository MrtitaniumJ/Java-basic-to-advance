# Deployment and Containers

Spring Boot apps are typically packaged as fat JARs and often deployed in containers.

## Packaging
- `./mvnw package` produces an executable JAR with embedded server.
- Layered jars (`spring-boot:build-image` or `spring-boot-jarmode-layertools`) improve Docker build caching.

## Docker basics
- Use a lightweight JDK base (e.g., eclipse-temurin:17-jre). 
- Set memory constraints and tune JVM for containers (`-XX:+UseContainerSupport`).
- Expose only needed ports; set HEALTHCHECK.

## Environment config
- Use env vars and externalized config; avoid baking secrets into images.

## Checklist
- [ ] Layered jar or multi-stage Docker build
- [ ] JVM tuned for containers
- [ ] Health checks configured
- [ ] Properties externalized for environments