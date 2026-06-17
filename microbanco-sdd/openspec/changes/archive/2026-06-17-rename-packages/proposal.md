## Why

El paquete `com.microbanco.account.infrastructure` es una mezcla de responsabilidades con nombres genéricos que no reflejan su propósito. Tras la migración a Active Record de Panache, el modelo de dominio ha quedado diluido entre `domain.model` y `infrastructure.persistence`. La estructura actual (sufijo `Entity`, paquete `application`, `rest.dto`, etc.) añade ruido visual sin aportar claridad. Este cambio unifica la organización en paquetes con nombres semánticos, elimina redundancias y simplifica la navegación del proyecto.

## What Changes

1. **Mover** `infrastructure.persistence` → `entities` — las entidades Panache son el modelo de datos
2. **Renombrar** `AccountEntity` → `Account` y `TransferEntity` → `Transfer` — el sufijo `Entity` sobra
3. **Mover** `application` → `services` — los servicios de aplicación son la capa de servicio
4. **Mover** `infrastructure.rest` → `boundary` — los recursos REST son la frontera del sistema
5. **Mover** `infrastructure.rest.dto` → `dto` — los DTOs son un concepto transversal
6. **Mover** `infrastructure.rest.exception` → `exceptions` y reunificar aquí mappers y excepciones de dominio
7. **Eliminar** `AccountApplication` si es prescindible (no aporta lógica)
8. **Anidar** `AccountStatus` como enum interno de `Account` — simplifica el modelo
9. **Limpiar** imports obsoletos tras los cambios

## Capabilities

### New Capabilities

*(Ninguna — es una refactorización pura, no introduce nueva funcionalidad)*

### Modified Capabilities

*(Ninguna — no hay cambios en requisitos a nivel de especificación, solo reempaquetado)*

## Impact

Todos los archivos Java de `src/main/java/com/microbanco/account/` cambian de paquete o se renombran. Todos los tests se mueven a paquetes equivalentes. El `pom.xml` y la configuración de Quarkus no requieren cambios (el grupo/artifactId no se modifica). No hay cambios en API REST expuesta (las rutas y contratos HTTP se mantienen idénticos).
