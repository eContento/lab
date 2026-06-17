## Why

El microservicio actual usa arquitectura Hexagonal con puertos y adaptadores, incluyendo repositorios Panache separados del modelo de dominio. Esta aproximación duplica la estructura de datos (domain model + JPA entity + repositorio) y añade complejidad innecesaria para un microservicio de este tamaño. Quarkus y Panache recomiendan el patrón Active Record: entidades que extienden PanacheEntity con campos públicos, fusionando modelo y persistencia en una única clase.

## What Changes

- Eliminar el paquete `domain/model/` y `domain/port/` — la lógica de dominio se traslada directamente a las entidades JPA
- Convertir `AccountEntity` y `TransferEntity` a Active Record: extender `PanacheEntityBase`, campos públicos, usar métodos de persistencia propios (`persist()`, `findById()`, `listAll()`, etc.)
- Eliminar `PanacheAccountRepository`, `PanacheTransferRepository` y las interfaces `AccountRepositoryPort`, `TransferRepositoryPort`
- Refactorizar `AccountService` y `TransferService` para operar directamente sobre las entidades Active Record
- Migrar tests: reemplazar mocks de repositorios con operaciones reales sobre entidades o simplificar tests
- Las API REST se mantienen idénticas — no hay cambios en contratos ni comportamientos externos

## Capabilities

### New Capabilities
*(ninguna — es una refactorización interna)*

### Modified Capabilities
*(ninguna — los requisitos de comportamiento no cambian)*

## Impact

- **Código eliminado**: ~8 archivos (ports, repositorios, domain model puro)
- **Código modificado**: `AccountEntity`, `TransferEntity`, `AccountService`, `TransferService`, tests
- **API REST**: Sin cambios — mismos endpoints, mismos códigos de respuesta
- **Tests**: Los tests de integración (REST) no requieren cambios. Los unitarios (Mockito) requieren actualización para trabajar con Active Record en lugar de repositorios mockeados
- **Dependencias**: Ningún cambio en pom.xml — Panache ya está incluido
