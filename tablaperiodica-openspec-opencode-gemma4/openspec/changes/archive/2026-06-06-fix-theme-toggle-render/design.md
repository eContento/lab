## Context

`toggleTheme()` cambia la clase `.dark` en `<body>` y guarda la preferencia en localStorage, pero no re-renderiza la tabla. Como `render()` lee el palette activo mediante `getColors()` (que chequea `document.body.classList.contains('dark')`), basta con llamar `render()` después de cambiar el tema.

## Goals / Non-Goals

**Goals:**
- Al hacer clic en el botón de tema, los colores de las celdas se actualizan inmediatamente
- Mantener la lógica existente de `getColors()` y `render()`

**Non-Goals:**
- No cambiar la estructura del HTML ni CSS
- No modificar cómo se guarda/recupera la preferencia de tema

## Decisions

1. **Llamar `render()` en `toggleTheme()`** — Es el lugar mínimo y preciso: solo se re-renderiza cuando el usuario cambia el tema activamente. No se añade a `applyTheme()` porque esa se ejecuta en carga de página, donde `render()` ya se llama una vez al final.
2. **No optimizar con diff** — Se re-renderiza la tabla completa. Son solo 118 elementos, el impacto en performance es imperceptible.

## Risks / Trade-offs

- `render()` recrea todo el DOM de la tabla, perdiendo el scroll position → mínimo, la tabla no tiene scroll propio
- `render()` también ejecuta `filterElements()`, manteniendo el filtro activo → correcto
