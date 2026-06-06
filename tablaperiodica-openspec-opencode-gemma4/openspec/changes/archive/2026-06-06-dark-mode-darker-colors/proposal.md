## Why

El modo oscuro actual usa colores pastel, que se ven deslavados sobre el fondo oscuro. La implementación previa (`improve-dark-mode-contrast`) usó colores vibrantes/saturados, pero el objetivo es usar tonos más oscuros y sobrios que mantengan legibilidad sin ser llamativos.

## What Changes

- Reemplazar el palette `DARK_COLORS` en `elements.js` con versiones oscuras/muted de los colores pastel (en lugar de saturadas)
- Mantener el texto blanco en las celdas para contraste
- No tocar el modo claro (sigue usando `COLORS` pastel)

## Capabilities

### New Capabilities
- `dark-mode-muted-palette`: Define una paleta de colores oscuros y apagados para el modo oscuro, una por cada categoría de elemento

## Impact

- `elements.js`: modificar los valores de `DARK_COLORS`
- No afecta `styles.css`, `app.js`, `index.html`
