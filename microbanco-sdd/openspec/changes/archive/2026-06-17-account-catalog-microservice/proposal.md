## Why

Implementar un microservicio bancario sobre Quarkus LTS que centralice la gestión de cuentas y la operativa de transferencias, sentando las bases de una arquitectura de microservicios para el ecosistema Microbanco. Se requiere una solución moderna, nativa en la nube, con capacidad de compilación nativa mediante GraalVM para optimizar rendimiento y consumo de recursos.

## What Changes

- Nuevo proyecto Maven con Java 25 (GraalVM), Quarkus LTS, groupId `com.microbanco`, artifactId `account-catalog-data`
- Modelo de datos para cuentas bancarias (titular, saldo, estado, divisa)
- API REST para operaciones CRUD sobre cuentas
- API REST para realizar transferencias entre cuentas con validaciones de negocio (saldo suficiente, cuentas activas, prevención de autotransferencias)
- Integración con base de datos relacional vía Panache/Hibernate
- Arquitectura orientada a Hexagonal (puertos y adaptadores)
- Compilación nativa con GraalVM

## Capabilities

### New Capabilities
- `account-management`: Gestión del ciclo de vida de cuentas bancarias — apertura, consulta, actualización de datos, cierre. Incluye validaciones de negocio y consulta de saldo.
- `transfer-operations`: Operativa de transferencias entre cuentas del mismo banco — validación de cuentas origen/destino, comprobación de saldo suficiente, ejecución atómica, registro de movimientos y consulta de historial.

### Modified Capabilities
<!-- No existing capabilities to modify -->

## Impact

- Nuevo repositorio/módulo Maven `account-catalog-data` dentro del workspace
- Dependencias nuevas: Quarkus LTS, Hibernate Panache, base de datos (PostgreSQL para modo dev de quarkus y para prod, H2 para test), RESTEasy Reactive
- Artefacto compilable a nativo vía GraalVM
- API REST expuesta en puerto a definir durante diseño
