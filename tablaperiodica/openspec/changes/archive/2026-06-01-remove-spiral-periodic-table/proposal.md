## Why

La vista en espiral fue un experimento de visualización alternativa de la tabla periódica. Se ha decidido eliminarla para simplificar la aplicación: reduce la complejidad del código, elimina un archivo de 226 líneas (`periodic-table-spiral.js`), y devuelve la interfaz a una sola vista canónica de cuadrícula sin controles de alternancia.

## What Changes

- Eliminar el archivo `periodic-table-spiral.js` por completo
- Quitar el botón `#view-toggle` ("⬡ Espiral") de la cabecera en `index.html`
- Quitar el contenedor `#spiral-view` y el `<svg id="spiral-svg">` de `index.html`
- Quitar la etiqueta `<script src="periodic-table-spiral.js">` de `index.html`
- Eliminar las variables `activeView`, `spiralAPI`, `$viewToggle`, `$spiralView`, `$spiralSvg` de `app.js`
- Eliminar las funciones `showSpiralView()` y `showGridView()` de `app.js`
- Eliminar el listener del botón de cambio de vista y la inicialización de `initSpiralComponent` de `app.js`
- Eliminar todas las llamadas a `spiralAPI?.clearActive()`, `spiralAPI?.setActive()`, `spiralAPI?.filter()` de `app.js`
- Eliminar los bloques de estilos `.spiral-view`, `.spiral-svg` y `.spiral-cell` de `styles.css`

## Capabilities

### Removed Capabilities

- `spiral-view`: Vista SVG en espiral archimediana — se elimina por completo

### Unchanged Capabilities

- `periodic-table-grid`: Vista de cuadrícula estándar, sin cambios funcionales; pasa a ser la única vista
- `element-detail`: Panel de detalle al hacer clic en un elemento — sin cambios
- `element-search`: Búsqueda y filtrado en tiempo real — sin cambios
- Modo claro/oscuro — sin cambios

## Impact

- Se elimina `periodic-table-spiral.js` (226 líneas)
- Se simplifica `app.js`: se eliminan ~30 líneas de lógica de vista dual
- Se simplifica `index.html`: se eliminan 3 líneas (botón, contenedor SVG y script)
- Se simplifica `styles.css`: se eliminan ~120 líneas de estilos de espiral
- Sin regresiones esperadas en la funcionalidad existente de cuadrícula
