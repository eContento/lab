## Context

La aplicación es una SPA estática vanilla (HTML5 + CSS3 + JS sin framework). La vista en espiral se integró en tres archivos: `periodic-table-spiral.js` (componente independiente), `app.js` (inicialización y lógica de alternancia de vistas), `index.html` (marcado HTML) y `styles.css` (estilos). La eliminación debe revertir todos esos puntos de integración sin afectar la vista de cuadrícula existente.

## Goals / Non-Goals

**Goals:**
- Eliminar todo rastro del código de la vista en espiral
- Mantener la funcionalidad de cuadrícula, búsqueda, detalle y tema intacta
- Dejar `app.js` sin referencias a `spiralAPI`, `activeView`, `showSpiralView`, `showGridView`

**Non-Goals:**
- Refactorizar o reorganizar el código restante más allá de lo necesario para la limpieza
- Cambiar el diseño visual de la vista de cuadrícula
- Añadir ninguna nueva funcionalidad

## Decisions

### Estrategia: eliminación quirúrgica archivo a archivo

Se procesan los cuatro archivos en orden de menor a mayor acoplamiento:

1. **`index.html`** — quitar el `<script>` de carga y los elementos DOM de espiral
2. **`styles.css`** — quitar los bloques de CSS de espiral (dos secciones delimitadas por comentarios)
3. **`app.js`** — quitar variables, funciones, llamadas y listener relacionados con la espiral
4. **`periodic-table-spiral.js`** — eliminar el archivo completo

### Cambios exactos en `app.js`

Las líneas a eliminar o simplificar son:

| Línea(s) | Contenido | Acción |
|----------|-----------|--------|
| 26 | `let activeView = 'grid';` | Eliminar |
| 27 | `let spiralAPI = null;` | Eliminar |
| 35 | `const $viewToggle = document.getElementById('view-toggle');` | Eliminar |
| 42–43 | `$spiralView`, `$spiralSvg` | Eliminar |
| 102–115 | `showSpiralView()` y `showGridView()` | Eliminar ambas funciones |
| 152 | `spiralAPI?.clearActive();` | Eliminar línea |
| 157 | `spiralAPI?.setActive(el.atomicNumber);` | Eliminar línea |
| 185 | `spiralAPI?.clearActive();` | Eliminar línea |
| 205 | `spiralAPI?.filter('');` | Eliminar línea |
| 233 | `spiralAPI?.filter(query);` | Eliminar línea |
| 273 | listener de `$viewToggle` | Eliminar línea |
| 286–290 | bloque `spiralAPI = initSpiralComponent({...})` | Eliminar bloque |

### Cambios exactos en `index.html`

| Línea(s) | Contenido | Acción |
|----------|-----------|--------|
| 20 | `<button id="view-toggle" ...>⬡ Espiral</button>` | Eliminar |
| 38–39 | `<div id="spiral-view">` y `<svg id="spiral-svg">` | Eliminar |
| 88 | `<script src="periodic-table-spiral.js"></script>` | Eliminar |

### Cambios exactos en `styles.css`

Dos bloques delimitados por comentarios:

- Bloque 1 (líneas ~551–587): `/* ── Spiral View ──... */` con `.spiral-view` y `.spiral-svg`
- Bloque 2 (líneas ~589–660+): `/* ── Spiral Cell nodes ──... */` con todos los selectores `.spiral-cell`

Ambos bloques se eliminan íntegramente incluyendo sus comentarios de sección.

## Risks / Trade-offs

- **Riesgo bajo**: la vista de cuadrícula no tiene dependencias en `periodic-table-spiral.js` ni en las funciones de alternancia; su lógica interna no cambia.
- **`filterElements()` en `app.js`**: actualmente itera `.element-cell` en `$table`; con la espiral eliminada, el selector vuelve a ser unívoco — no requiere cambio.
- **Verificación**: después de los cambios, la app debe arrancar sin errores de consola, la cuadrícula debe renderizarse, la búsqueda debe funcionar y el panel de detalle debe abrirse al hacer clic.
