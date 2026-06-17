## Why

Las excepciones `AccountNotFoundException` e `InsufficientBalanceException` extienden `RuntimeException` (que implementa `Serializable`) pero carecen de `serialVersionUID`, lo que puede causar advertencias e inconsistencias en entornos distribuidos. Además, dos archivos arrastran imports no utilizados que añaden ruido al código.

## What Changes

1. **Añadir `serialVersionUID`** a `AccountNotFoundException` e `InsufficientBalanceException` — valor `1L` (primera versión)
2. **Eliminar import no usado** `PagedResponse` en `TransferResource.java`
3. **Eliminar imports no usados** (`BigDecimal`, `Assertions.*`) en `AccountResourceTest.java`

## Capabilities

### New Capabilities

*(Ninguna — es una limpieza de código, no introduce nueva funcionalidad)*

### Modified Capabilities

*(Ninguna — no hay cambios en requisitos a nivel de especificación)*

## Impact

Afecta a 4 archivos: 2 excepciones en `exceptions/`, 1 resource en `boundary/`, 1 test en `boundary/`. No hay cambios en API REST, comportamiento, ni dependencias externas.
