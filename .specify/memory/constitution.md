# LangChain-Springboot Constitution

## Core Principles

### I. Spring Boot Conventions First
All code MUST follow Spring Boot conventions and idioms. Use constructor injection, `@Configuration` classes for bean wiring, and `application.yml` for externalized configuration. Auto-configuration MUST be leveraged over manual bean registration wherever possible.

### II. LangChain4j Integration Patterns
AI agent tools, memory, and services MUST be defined using LangChain4j's `@AiService` and `@Tool` annotations. Tool classes MUST be Spring-managed beans. Chat memory and model configuration MUST be centralized in `@Configuration` classes under the `config` package.

### III. Test-Driven Quality
Tests MUST accompany all new features. Unit tests MUST use JUnit 5 and Mockito. Integration tests MUST use `@SpringBootTest` with appropriate test properties. LangChain4j tool classes MUST have unit tests validating their logic independently of the LLM.

### IV. Clean Architecture
Code MUST follow a layered package structure: `controller` (HTTP layer), `dto` (request/response objects), `agent` (AI service interfaces), `tool` (LangChain4j tools), `config` (Spring configuration). No circular dependencies between layers. Controllers MUST NOT contain business logic.

### V. API-First Design
REST endpoints MUST use meaningful HTTP methods and status codes. Request/response DTOs MUST be separate from internal models. All endpoints MUST be documented with clear request/response contracts.

### VI. Security and Configuration
API keys, model URLs, and sensitive configuration MUST be externalized via `application.yml` or environment variables. Secrets MUST NEVER be committed to version control. Ollama base URLs and model names MUST be configurable per environment.

## Technology Stack

- **Language**: Java 17
- **Framework**: Spring Boot 3.4.x
- **AI Framework**: LangChain4j 1.0.x (Ollama integration)
- **Build Tool**: Maven
- **Template Engine**: Thymeleaf
- **Testing**: JUnit 5, Spring Boot Test, Mockito
- **Code Reduction**: Lombok (optional)

## Development Workflow

- Feature branches MUST be created for all new work
- Code MUST compile without warnings before committing
- Maven build (`mvn clean verify`) MUST pass before merging
- Application MUST start successfully with Ollama running locally

## Governance

- This constitution governs all development practices for the LangChain-Springboot project
- Amendments require documentation, review, and version bump
- All code reviews MUST verify compliance with these principles

**Version**: 1.0.0 | **Ratified**: 2026-03-30 | **Last Amended**: 2026-03-30
