# Repository Guidelines

## Project Structure & Module Organization
- Application entry point lives in `src/main/java/org/example/Integrador_Mutante/IntegradorMutanteApplication.java`; place new features in feature-focused subpackages (e.g., `controller`, `service`, `repository`, `model`) under the same root.
- Configuration files (e.g., `application.properties`, SQL/data seeds) belong in `src/main/resources`.
- Tests mirror the main source tree in `src/test/java/org/example/Integrador_Mutante`, using the same package paths as the code they cover.

## Build, Test, and Development Commands
- `./gradlew.bat clean build` (Windows) or `./gradlew clean build`: compile, run tests, and produce the Spring Boot jar.
- `./gradlew.bat test` / `./gradlew test`: run the JUnit 5 suite without rebuilding everything.
- `./gradlew.bat bootRun` / `./gradlew bootRun`: start the app locally with the current classpath; use for manual verification.
- `./gradlew.bat tasks` / `./gradlew tasks`: list available Gradle tasks if you need additional tooling.

## Coding Style & Naming Conventions
- Java 17, Spring Boot 3.5; use 4-space indentation and keep imports ordered and minimal. Enable annotation processing for Lombok when using `@Getter`, `@Setter`, or `@Builder`.
- Package names stay lowercase with dots; classes use `PascalCase`; methods and variables use `camelCase`.
- REST controllers should be annotated with `@RestController` and mapped under versioned prefixes when applicable (e.g., `/api/v1/...`). Keep DTOs immutable where possible.
- Prefer constructor injection over field injection to simplify testing.

## Testing Guidelines
- Tests use JUnit 5 with Spring Boot test support; class names should end with `*Tests` to be picked up automatically.
- Favor focused unit tests for services/repositories; reserve `@SpringBootTest` for wiring and integration paths.
- Name test methods descriptively (e.g., `shouldDetectMutantSequence()`), and include edge cases and failure paths.
- Run `gradlew test` before opening a PR; add regression tests alongside bug fixes.

## Commit & Pull Request Guidelines
- Write imperative, concise commit messages (e.g., `Add mutant validation service`); group related changes logically rather than in large multi-purpose commits.
- PRs should describe the change, link any issue/requirement, summarize the risk, and note test evidence (`gradlew test` output, manual steps). Include sample requests/responses or screenshots for API or UI changes.
- Keep diffs small and focused; update docs/config samples when adding new endpoints, entities, or configuration flags.

## Configuration & Environment Notes
- The project depends on Spring Data JPA and an H2 runtime database; configure database overrides in `src/main/resources/application.properties` when targeting real environments.
- Avoid committing secrets; prefer environment variables or external config files ignored by Git. For local overrides, create `application-local.properties` and reference it via `--spring.profiles.active=local`.
