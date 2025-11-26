# Mutant Detector API

[![Java 17](https://img.shields.io/badge/Java-17-blue.svg)](#) [![Spring Boot 3](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](#) [![Coverage >90%](https://img.shields.io/badge/Coverage-%3E90%25-success.svg)](#)

API para detectar mutantes a partir de secuencias de ADN NxN, almacenando resultados y exponiendo estadísticas.

## Instrucciones de Ejecución
- Tests: `./gradlew test`
- Cobertura (JaCoCo): `./gradlew test jacocoTestReport` (reporte HTML en `build/reports/jacoco/test/html/index.html`)
- Iniciar la app: `./gradlew bootRun`

## Uso de la API
- Base URL local: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Consola H2: `http://localhost:8080/h2-console`

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

## Tecnologías
- Java 17
- Spring Boot 3
- H2 Database
- Lombok
- JUnit 5
- Mockito
- JaCoCo
