## Context

El proyecto tiene 24 fuentes principales y 8 tests distribuidos en 7 paquetes bajo `com.microbanco.account`:
- `application/` — AccountService, TransferService
- `domain/model/` — AccountStatus (enum), AccountNotFoundException, InsufficientBalanceException
- `infrastructure/persistence/` — AccountEntity, TransferEntity (entidades Panache Active Record)
- `infrastructure/rest/` — AccountResource, TransferResource (recursos REST)
- `infrastructure/rest/dto/` — 7 DTOs (AccountRequest, AccountResponse, etc.)
- `infrastructure/rest/exception/` — 6 ExceptionMappers
- `infrastructure/util/` — IbanUtils

Tras la migración a Active Record (PanacheEntityBase), las entidades son el modelo de dominio y no necesitan separación `domain/` vs `persistence/`. Los nombres de paquete actuales (`infrastructure`, `application`) reflejan una arquitectura hexagonal que ya no existe, añadiendo complejidad innecesaria.

## Goals / Non-Goals

**Goals:**
- Reorganizar todos los fuentes en paquetes con nombre semántico: `entities`, `services`, `boundary`, `dto`, `exceptions`, `util`
- Eliminar sufijo `Entity` de las clases de entidad
- Anidar `AccountStatus` como enum interno de `Account`
- Eliminar `AccountApplication.java` si es prescindible
- Mover todos los tests a paquetes equivalentes

**Non-Goals:**
- Cambiar la API REST expuesta (mismas rutas, mismos contratos)
- Cambiar el comportamiento del sistema
- Modificar dependencias externas (pom.xml)

## Decisions

1. **`AccountApplication.java` se elimina** — Extiende `jakarta.ws.rs.core.Application` sin anular ningún método y solo aporta metadatos OpenAPI. La metadata OpenAPI se puede reubicar en un recurso existente o en `application.properties`. No afecta al arranque de Quarkus.

2. **`domain.model` se disuelve en `exceptions` y `entities`** — `AccountStatus` se anida dentro de `Account`. Las excepciones `AccountNotFoundException` e `InsufficientBalanceException` se mueven a `exceptions` donde ya están los mappers. El paquete `domain.model` desaparece.

3. **`infrastructure.util` sigue siendo `util`** — IbanUtils es una utilidad que no encaja en ningún otro paquete. El resto de `infrastructure` se descompone en `entities`, `boundary`, `dto`, `exceptions`.

4. **El cambio es puramente mecánico** — Se puede ejecutar en una sola tanda de refactor. No hay cambios semánticos. Se recomienda usar la refactorización del IDE o comandos `mv` + búsqueda/reemplazo global.

5. **Los tests se mueven al mismo paquete que el código que prueban** — Ventaja: las clases testeadas no necesitan importarse explícitamente, el classpath refleja la estructura de producción.

## Risks / Trade-offs

- [git blame/history] Al mover archivos, git pierde la traza continua. **Mitigación**: Usar `git mv` si se versiona por separado, o aceptar que es un cambio masivo de una sola vez.
- [merge conflicts] Si hay otras ramas tocando estos archivos. **Mitigación**: Coordinar para aplicar este cambio cuando no haya trabajo en curso paralelo.
- [IbanUtils] `infrastructure.util` es el único vestigio del paquete `infrastructure`. **Aceptado**: es un nombre razonable para utilidades sin otra ubicación clara.
