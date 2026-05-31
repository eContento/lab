## Context

Corrección de typo en `app.js`: el event listener del clic en celda llama a `selectElement(el)` (función inexistente) en lugar de `showDetail(el)`. El error provoca que el panel de detalle nunca se abra y se lanza un `ReferenceError` en consola al hacer clic.

## Goals / Non-Goals

**Goals:**
- Que el clic en cualquier celda de la tabla abra el panel de detalle.

**Non-Goals:**
- Cualquier otro cambio en la lógica de selección o en el panel de detalle.

## Decisions

Un único cambio de una línea en `app.js`. No hay alternativas que evaluar.

## Risks / Trade-offs

Sin riesgos: el comportamiento esperado ya está implementado en `showDetail`; solo faltaba la referencia correcta.
