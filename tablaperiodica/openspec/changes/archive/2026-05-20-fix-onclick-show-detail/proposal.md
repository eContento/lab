## Why

Al hacer clic en una celda de la tabla periódica se produce un `ReferenceError` porque el event listener llama a `selectElement(el)`, función que no existe. La función correcta es `showDetail(el)`, definida en `app.js`. Como resultado, el panel de detalle nunca se muestra al usuario.

## What Changes

- Corregir `app.js` línea 64: reemplazar `selectElement(el)` por `showDetail(el)` en el event listener del clic de cada celda.

## Capabilities

### New Capabilities

### Modified Capabilities

## Impact

- Archivo afectado: `app.js` (una línea)
- Sin cambios en HTML, CSS ni datos
- Sin cambios en requisitos; el comportamiento correcto ya está definido en `element-detail/spec.md`
