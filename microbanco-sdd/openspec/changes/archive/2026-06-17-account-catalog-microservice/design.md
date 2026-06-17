## Context

Se necesita un microservicio bancario dentro del ecosistema Microbanco que gestione cuentas y permita realizar transferencias. El proyecto debe construirse sobre Quarkus LTS con Java 25 (GraalVM) para habilitar compilación nativa. Forma parte de una iniciativa mayor de migración a microservicios, por lo que debe priorizarse la separación de dominios y la facilidad de integración futura.

## Goals / Non-Goals

**Goals:**
- Microservicio REST autónomo con modelo de datos de cuentas bancarias
- API para gestión de cuentas (crear, consultar, listar, cerrar)
- API para realizar transferencias entre cuentas con validaciones de negocio
- Arquitectura Hexagonal (puertos/adaptadores) para desacoplar dominio de infraestructura
- Compilación nativa con GraalVM
- Base de datos relacional con Hibernate Panache
- Tests unitarios y de integración

**Non-Goals:**
- Autenticación/authorización de usuarios (se abordará en cambio separado)
- Integración con bancos externos o sistemas legacy
- UI web o frontend
- Despliegue en producción (infraestructura fuera de alcance)
- Auditoría avanzada o trazabilidad distribuida

## Decisions

| Decisión | Opción Elegida | Alternativas | Razón |
|---|---|---|---|
| Framework | Quarkus LTS | Spring Boot | Menor footprint, compilación nativa nativa, mejor soporte para GraalVM |
| ORM | Hibernate ORM + Panache | jOOQ, raw JDBC | Familiaridad del equipo, integración estrecha con Quarkus, productividad con Panache |
| Base de datos (modo dev y prod) | PostgreSQL local | PostgreSQL local | Simplicidad para desarrollo; en producción se usará PostgreSQL vía profile |
| Base de datos (test) | H2 en memoria | PostgreSQL local | Simplicidad para desarrollo; en producción se usará PostgreSQL vía profile |
| Arquitectura | Hexagonal (puertos y adaptadores) | MVC tradicional, Capas | Separación clara de dominio, facilidad de test, adaptabilidad a cambios de infraestructura |
| API style | REST sobre RESTEasy Reactive | gRPC, GraphQL | Simplicidad, madurez del ecosistema, alineado con estándar bancario |
| Formato API | JSON | XML | Ligereza, universalidad |
| Testing | JUnit 5 + REST Assured | Mockito + wiremock | Testing end-to-end de API REST con REST Assured |

## Risks / Trade-offs

- **Riesgo de consistencia en transferencias** → Mitigación: usar transacciones JTA para garantizar atomicidad (débito + crédito en misma transacción)
- **Rendimiento en compilación nativa con GraalVM** → Mitigación: evitar reflection dinámica no configurada, usar extensiones Quarkus compatibles con native
- **H2 para test vs PostgreSQL en dev/prod** → Mitigación: usar dialecto Hibernate portable, probar integración con PostgreSQL en CI
- **Concurrencia en transferencias simultáneas** → Mitigación: usar `@Version` para optimistic locking en cuenta y reintentos

## Open Questions

- Puerto de exposición del servicio (por definir: 8080 por defecto Quarkus)
- Formato de número de cuenta (UUID vs secuencial vs IBAN-like)
- Límite de transferencia máxima por operación (configurable vía application.properties)
