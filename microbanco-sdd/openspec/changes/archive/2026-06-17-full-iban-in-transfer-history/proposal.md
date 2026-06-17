## Why

El historial de transferencias muestra los IBANs truncados (primeros 4 y últimos 4 caracteres), lo que dificulta identificar las cuentas involucradas. El usuario necesita ver el IBAN completo para tener visibilidad total.

## What Changes

- **account-detail.js**: En la tabla de transferencias, mostrar el IBAN completo formateado en bloques de 4 en lugar de la versión truncada (`shortIban`)

## Capabilities

### New Capabilities
- *(ninguna — cambio puramente visual)*

### Modified Capabilities
- *(ninguna — no cambian requisitos funcionales)*

## Impact

- **account-detail.js**: Reemplazar `shortIban()` por `formatIban()` en `renderTransferRow` para las columnas origen y destino
- Sin cambios en backend, APIs, o tests
