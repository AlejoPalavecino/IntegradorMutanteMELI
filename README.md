# Mutant Detector API

[![Java 17](https://img.shields.io/badge/Java-17-blue.svg)](#) [![Spring Boot 3](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](#) [![Coverage >90%](https://img.shields.io/badge/Coverage-%3E90%25-success.svg)](#)

API para detectar mutantes a partir de secuencias de ADN NxN, persistiéndolas con hash único y exponiendo estadísticas en tiempo real.

## Características
- Detección O(n) con terminación temprana (MutantDetector) en 4 direcciones (horizontal, vertical, diagonal ↘, diagonal ↙).
- Cache por hash SHA-256 en base de datos (evita reprocesar ADN repetido).
- Validación custom `@ValidDnaSequence` (NxN, solo A/T/C/G) y manejo global de errores (400/500).
- Persistencia en H2 en memoria con unicidad de `dna_hash` y registro de `created_at`.
- Swagger UI y H2 console disponibles en local.

## Estructura del Proyecto
```
src/main/java/org/example
├─ MutantDetectorApplication.java (bootstrap)
├─ controller/MutantController.java
├─ service/MutantDetector.java | MutantService.java | StatsService.java
├─ repository/DnaRecordRepository.java
├─ entity/DnaRecord.java
├─ dto/DnaRequest.java | StatsResponse.java
├─ validator/ValidDnaSequence.java
└─ config/SwaggerConfig.java | GlobalExceptionHandler.java
```

## Ejecución y Build
- Tests: `./gradlew test`
- Cobertura (JaCoCo): `./gradlew test jacocoTestReport` (HTML en `build/reports/jacoco/test/html/index.html`)
- Iniciar la app: `./gradlew bootRun`

## Uso de la API
- Base URL local: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Consola H2: `http://localhost:8080/h2-console` (jdbc:h2:mem:testdb, user `sa`, pass vacío)

### Ejemplos cURL
Detectar mutante:
```bash
curl -X POST "http://localhost:8080/mutant" \
  -H "Content-Type: application/json" \
  -d '{"dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]}'
```

Obtener estadísticas:
```bash
curl -X GET "http://localhost:8080/stats"
```

## Arquitectura y Componentes
- Controlador: `MutantController` expone `/mutant` (200 mutante, 403 humano) y `/stats`.
- Servicios: `MutantService` (cache y persistencia), `StatsService` (conteos y ratio seguro sin división por cero), `MutantDetector` (algoritmo).
- Validación y errores: `@ValidDnaSequence` + `GlobalExceptionHandler` para 400 y fallback 500.
- Persistencia: `DnaRecordRepository` con `findByDnaHash` y `countByIsMutant`.
- Documentación: `SwaggerConfig` con OpenAPI 3.

## Pruebas y Cobertura
- Unit tests de algoritmo puro (MutantDetectorTest) con casos mutantes, humanos, validaciones y matrices grandes.
- Tests de servicios con Mockito (MutantServiceTest, StatsServiceTest) y de capa web con `@WebMvcTest` (MutantControllerTest).
- Cobertura >90% con JaCoCo; reporte HTML en `build/reports/jacoco/test/html/index.html`.

## Tecnologías
- Java 17
- Spring Boot 3
- H2 Database
- Lombok
- JUnit 5
- Mockito
- JaCoCo

## Conclusión
Proyecto desarrollado por Alejo Palavecino - Legajo 51018 para detección eficiente de ADN mutante, con documentación, pruebas y métricas de cobertura listas para evaluación.
