## Context

El microservicio actual implementa una arquitectura Hexagonal (puertos y adaptadores) con una separación explícita entre: modelo de dominio puro (`domain/model/`), interfaces de puerto (`domain/port/`), entidades JPA (`infrastructure/persistence/`), y repositorios Panache que actúan como adaptadores implementando los puertos. Esta estructura, si bien válida, introduce una duplicación de capas para un microservicio de alcance reducido.

Quarkus + Panache promueven el patrón Active Record como aproximación preferente: las entidades de dominio extienden `PanacheEntity` (o `PanacheEntityBase`) con campos públicos, fusionando la definición del modelo, las reglas de negocio y las operaciones de persistencia en una única clase. Esta aproximación reduce el código boilerplate y simplifica la navegación del proyecto.

## Goals / Non-Goals

**Goals:**
- Migrar a Active Record con Panache: entidades JPA con campos públicos que contienen la lógica de negocio
- Eliminar las interfaces de puerto (`AccountRepositoryPort`, `TransferRepositoryPort`)
- Eliminar los repositorios adaptadores (`PanacheAccountRepository`, `PanacheTransferRepository`)
- Eliminar el modelo de dominio independiente (`domain/model/Account.java`, `domain/model/Transfer.java`) tras fusionar su lógica en las entidades
- Mantener compatibilidad total de la API REST externa (mismos endpoints, mismos códigos HTTP, mismas DTOs de respuesta)
- Mantener los tests de integración REST sin cambios

**Non-Goals:**
- No cambiar el comportamiento observable del sistema
- No añadir nuevas funcionalidades
- No cambiar la estructura de base de datos (tablas, columnas)
- No cambiar dependencias Maven — Panache ya está incluido
- No eliminar los DTOs de REST ni los mappers de excepciones

## Decisions

### 1. `PanacheEntityBase` en lugar de `PanacheEntity`
- **Decisión**: Usar `PanacheEntityBase` con ID `UUID` gestionado manualmente
- **Razón**: Las entidades actuales usan `UUID` como identificador y las APIs REST exponen estos UUIDs. Cambiar a `Long` auto-generado rompería la API y requeriría migración de datos. `PanacheEntityBase` proporciona los métodos Active Record (`persist()`, `findById()`, `listAll()`, etc.) sin prescribir el tipo de ID.
- **Alternativa**: `PanacheEntity` con `Long` ID — requeriría cambiar todos los IDs de UUID a Long y modificar la API REST. Descartado por impacto en API.

### 2. Campos públicos con lógica de dominio en la entidad
- **Decisión**: Los campos serán públicos (como recomienda Panache) y la lógica de dominio (validaciones de débito/crédito/cierre) se implementa como métodos de instancia en la propia entidad
- **Razón**: Es la aproximación Active Record canónica de Panache. Reduce la duplicación de estado entre objeto de dominio y entidad JPA. Elimina la necesidad de métodos `fromDomain()`/`toDomain()`.
- **Alternativa**: Mantener campos privados con getters/setters — más verbose, contrario a la recomendación de Panache.

### 3. Servicios operan directamente sobre entidades
- **Decisión**: `AccountService` y `TransferService` usan los métodos estáticos y de instancia de Panache (`AccountEntity.persist()`, `AccountEntity.findById()`, etc.) en lugar de inyectar repositorios
- **Razón**: Elimina la capa de indirección del repositorio. Los servicios orquestan llamadas a las entidades Active Record.
- **Alternativa**: Mantener servicios con inyección de repositorios — iría en contra del objetivo del cambio.

### 4. `@Transactional` se mantiene en los servicios
- **Decisión**: Los métodos de servicio que modifican múltiples entidades mantienen `@Transactional`
- **Razón**: `PanacheEntityBase.persist()` y `flush()` operan dentro de la transacción gestionada por Quarkus/Arc. La atomicidad de las transferencias (débito + crédito en misma transacción) sigue siendo responsabilidad del servicio.

### 5. Optimistic locking se mantiene vía `@Version`
- **Decisión**: El campo `version` con `@Version` se mantiene en `AccountEntity` para control de concurrencia
- **Razón**: Las transferencias concurrentes necesitan optimistic locking. Active Record no cambia este mecanismo — Panache respeta `@Version`.

### 6. Tests unitarios migrados a pruebas con entidades reales
- **Decisión**: Los tests unitarios que mockeaban repositorios ahora usan entidades directamente (operaciones en memoria)
- **Razón**: Con Active Record no hay repositorios que mockear. Las validaciones de dominio se prueban invocando métodos directamente sobre las entidades.
- **Alternativa**: Mockear métodos estáticos de Panache con Mockito (possible pero frágil). Mejor probar contra una base de datos H2 con @QuarkusTest para los tests de integración y tests de entidad puros para lógica de dominio.

## Risks / Trade-offs

- **[Acoplamiento]** Active Record acopla la lógica de negocio al framework de persistencia (Panache/Hibernate). Si en el futuro se quisiera cambiar de ORM, la migración sería más costosa. → **Mitigación**: Para un microservicio de este alcance, es poco probable. Si surgen necesidades complejas, se puede extraer la lógica a servicios.

- **[Testabilidad]** Los métodos estáticos de Panache (`findById()`, `listAll()`, etc.) no son triviales de mockear. → **Mitigación**: Los tests de integración con `@QuarkusTest` y H2 cubren los escenarios de persistencia. La lógica pura de dominio se prueba directamente sin infraestructura.

- **[Versión]** `@Version` con campos públicos: si un consumidor modifica directamente el campo `version`, puede corromper el optimistic locking. → **Mitigación**: Documentar que `version` no debe manipularse externamente. Es un riesgo menor ya que el código interno es el único consumidor.

- **[UUID + Panache]** `PanacheEntityBase` con UUID requiere gestionar manualmente el ID (no es auto-generado por Panache, aunque Hibernate puede hacerlo vía `@GeneratedValue`). → **Mitigación**: Mantener `@GeneratedValue` con generador UUID estándar de Hibernate.
