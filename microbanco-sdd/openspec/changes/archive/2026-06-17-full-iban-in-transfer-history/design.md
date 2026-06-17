## Context

La tabla de transferencias en la vista de detalle de cuenta usa `shortIban()` para truncar IBANs a `ES00...7890`. El usuario necesita ver el IBAN completo.

## Goals / Non-Goals

**Goals:**
- Mostrar IBAN completo formateado (espacios cada 4 chars) en origen y destino de cada transferencia

**Non-Goals:**
- No cambiar backend, APIs, tests, ni otras partes del frontend

## Decisions

### 1. Reemplazar shortIban por formatIban
- **Decisión**: En `renderTransferRow()`, cambiar `shortIban(t.sourceAccountIban)` → `formatIban(t.sourceAccountIban)` y lo mismo para target. La función `formatIban()` ya existe localmente.
- **Razón**: `formatIban()` muestra el IBAN completo con formato legible (espacios cada 4). `shortIban()` se manteniene disponible pero deja de usarse en la tabla.

### 2. Ajuste de estilo de columna
- **Decisión**: Mantener `font-family: monospace; font-size: 12px;` — el IBAN completo (32 chars formateados como ~39 chars) cabe bien en la tabla.

## Risks / Trade-offs

- **[Anchura de tabla]** IBAN completo ocupa más espacio. Mitigación: la tabla ya tiene `overflow-x: auto` para scroll horizontal si es necesario.
