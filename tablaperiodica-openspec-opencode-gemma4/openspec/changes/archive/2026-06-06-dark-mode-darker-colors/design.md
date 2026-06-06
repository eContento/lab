## Context

Actualmente `DARK_COLORS` en `elements.js` contiene colores vibrantes/saturados. El usuario prefiere tonos oscuros y apagados (muted) que contrasten con el fondo `#1a1a2e` sin ser llamativos. El modo claro queda intacto con su palette pastel `COLORS`.

## Goals / Non-Goals

**Goals:**
- Reemplazar los valores de `DARK_COLORS` con tonos oscuros/muted para cada categoría
- Mantener legibilidad del texto blanco sobre los nuevos fondos oscuros
- No alterar el modo claro ni la lógica de cambio de tema

**Non-Goals:**
- No cambiar `COLORS` (modo claro)
- No modificar `styles.css`, `app.js` ni `index.html`
- No añadir nuevas categorías ni elementos

## Decisions

1. **Paleta oscura/muted** — Cada color pastel se oscurece reduciendo luminosidad (ej. `#c4e8ff` → `#336699`). Se prioriza que los colores sean distinguibles entre sí y del fondo, sin saturación alta.
2. **Texto blanco se mantiene** — El texto `#fff` ya contrasta bien con los nuevos tonos oscuros.
3. **No tocar `getColors()` ni `render()`** — La función ya selecciona `DARK_COLORS` vs `COLORS` según la clase `.dark`. Solo cambian los valores.

## Risks / Trade-offs

- Si los colores quedan demasiado oscuros, el texto blanco puede tener poco contraste → mitigado probando cada valor contra fondo `#1a1a2e`
- Mantener 10 colores distintos y reconocibles en versión oscura es un reto de diseño visual
