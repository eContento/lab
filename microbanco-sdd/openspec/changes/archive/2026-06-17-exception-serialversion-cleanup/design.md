## Context

Dos excepciones (`AccountNotFoundException`, `InsufficientBalanceException`) en `exceptions/` carecen de `serialVersionUID`. Dos archivos (`TransferResource.java`, `AccountResourceTest.java`) contienen imports no referenciados.

## Goals / Non-Goals

**Goals:**
- Añadir `private static final long serialVersionUID = 1L` a ambas excepciones
- Eliminar imports no usados identificados en el barrido

**Non-Goals:**
- No se añade `-Xlint` al compilador (cambio separado si se desea)
- No se revisan otros archivos con imports sobrantes (solo los identificados)

## Decisions

1. **`serialVersionUID = 1L`** — Valor inicial estándar. Si la clase cambia estructuralmente en el futuro, se incrementa.
2. **Eliminación directa de imports** — No tienen efecto en tiempo de compilación ni ejecución.

## Risks / Trade-offs

- [serialización distribuida] Si existen instancias serializadas con versiones anteriores (sin UID), la deserialización con la nueva versión podría fallar. **Mitigación**: No hay datos serializados persistentes de estas excepciones en el sistema actual.
