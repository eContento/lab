## 1. HTML: quitar elementos de la vista espiral

- [x] 1.1 Eliminar de `index.html` la línea con `<button id="view-toggle" class="btn-icon" aria-label="Cambiar a vista espiral">⬡ Espiral</button>`
- [x] 1.2 Eliminar de `index.html` el bloque `<div id="spiral-view" class="spiral-view" hidden>` junto con el `<svg id="spiral-svg">` que contiene
- [x] 1.3 Eliminar de `index.html` la línea `<script src="periodic-table-spiral.js"></script>`

## 2. CSS: quitar estilos de espiral

- [x] 2.1 Eliminar de `styles.css` el bloque completo `/* ── Spiral View ── */` (incluye estilos de `.spiral-view` y `.spiral-svg`)
- [x] 2.2 Eliminar de `styles.css` el bloque completo `/* ── Spiral Cell nodes ── */` (incluye todos los selectores `.spiral-cell` y sus variantes hover, active, dimmed, match, theoretical)

## 3. JS: limpiar variables y referencias en `app.js`

- [x] 3.1 Eliminar la variable `let activeView = 'grid';`
- [x] 3.2 Eliminar la variable `let spiralAPI = null;`
- [x] 3.3 Eliminar la referencia DOM `const $viewToggle = document.getElementById('view-toggle');`
- [x] 3.4 Eliminar las referencias DOM `const $spiralView = document.getElementById('spiral-view');` y `const $spiralSvg = document.getElementById('spiral-svg');`

## 4. JS: eliminar funciones de alternancia de vista en `app.js`

- [x] 4.1 Eliminar la función `showSpiralView()` completa
- [x] 4.2 Eliminar la función `showGridView()` completa

## 5. JS: eliminar llamadas a `spiralAPI` en `app.js`

- [x] 5.1 Eliminar la llamada `spiralAPI?.clearActive();` en `showDetail()` (línea ~152)
- [x] 5.2 Eliminar la llamada `spiralAPI?.setActive(el.atomicNumber);` en `showDetail()` (línea ~157)
- [x] 5.3 Eliminar la llamada `spiralAPI?.clearActive();` en `closeDetail()` (línea ~185)
- [x] 5.4 Eliminar la llamada `spiralAPI?.filter('');` en el bloque de limpieza de búsqueda (línea ~205)
- [x] 5.5 Eliminar la llamada `spiralAPI?.filter(query);` en `filterElements()` (línea ~233)
- [x] 5.6 Eliminar el listener `$viewToggle.addEventListener('click', ...)` (línea ~273)
- [x] 5.7 Eliminar el bloque de inicialización `spiralAPI = initSpiralComponent({...})` al final del archivo

## 6. Eliminar archivo del componente espiral

- [x] 6.1 Eliminar el archivo `periodic-table-spiral.js` del directorio raíz del proyecto

## 7. Verificación

- [x] 7.1 Abrir la aplicación en el navegador y comprobar que no hay errores en la consola
- [x] 7.2 Verificar que la tabla periódica de cuadrícula se renderiza correctamente con todos los elementos
- [x] 7.3 Verificar que la búsqueda en tiempo real filtra los elementos correctamente
- [x] 7.4 Verificar que al hacer clic en un elemento se abre el panel de detalle
- [x] 7.5 Verificar que el modo claro/oscuro sigue funcionando
- [x] 7.6 Comprobar que no aparece ningún botón de cambio de vista en la cabecera
