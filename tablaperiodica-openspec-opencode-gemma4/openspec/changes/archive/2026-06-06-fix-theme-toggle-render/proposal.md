## Why

Al alternar entre modo claro y oscuro, el fondo y el texto cambian correctamente, pero los colores de fondo de las celdas de los elementos no se actualizan hasta que se recarga la página. Esto ocurre porque `toggleTheme()` cambia la clase `.dark` en `<body>` pero no vuelve a ejecutar `render()`, por lo que los tiles mantienen el palette que tenían al cargar la página.

## What Changes

- Agregar llamada a `render()` dentro de `toggleTheme()` para que los colores de las celdas se actualicen al cambiar de tema
- No hay cambios en HTML ni CSS

## Capabilities

### New Capabilities
- `theme-toggle-re-render`: Al cambiar de tema, se debe re-renderizar la tabla para aplicar el palette correcto

## Impact

- `app.js`: agregar `render();` en `toggleTheme()`
