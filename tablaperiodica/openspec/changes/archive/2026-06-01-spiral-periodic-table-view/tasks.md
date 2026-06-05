## 1. HTML: nuevos elementos de UI

- [x] 1.1 Añadir botón `<button id="view-toggle">` en `.header-controls` de `index.html`, antes del botón de tema; debe mostrar un icono/texto que indique la vista alternativa (e.g. "⬡ Espiral" / "⊞ Tabla")
- [x] 1.2 Añadir dentro de `<main class="site-main">` un `<div id="spiral-view" class="spiral-view" hidden>` que contenga `<svg id="spiral-svg" class="spiral-svg" aria-label="Vista en espiral de la tabla periódica"></svg>`

## 2. CSS: estilos de la vista en espiral

- [x] 2.1 Añadir estilos para `#spiral-view`: `display: flex; justify-content: center; align-items: flex-start; width: 100%; overflow: auto`
- [x] 2.2 Añadir estilos para `#spiral-svg`: `width: 100%; max-width: 900px; height: auto`
- [x] 2.3 Añadir estilos para `.spiral-node circle`: `fill` heredado de variable CSS `--cat-*`; `cursor: pointer`; transición de `opacity` suave
- [x] 2.4 Añadir estilos para `.spiral-node text`: `fill: var(--text-on-cell)` (color de texto sobre celda); `font-size: 10px`; `pointer-events: none`; `text-anchor: middle; dominant-baseline: central`
- [x] 2.5 Añadir estilos para `.spiral-node:hover circle`: ligero `filter: brightness(1.2)` o borde destacado (`stroke`)
- [x] 2.6 Añadir estilos para `.spiral-node.active circle`: borde `stroke` con color de acento prominente
- [x] 2.7 Añadir estilos para `.spiral-node.dimmed`: `opacity: 0.15`
- [x] 2.8 Añadir estilos para `.spiral-node.match circle`: `stroke` de acento; `stroke-width: 2`
- [x] 2.9 Añadir estilos para `#view-toggle`: consistente con `.btn-icon`; sin necesidad de clase nueva si se reutiliza la existente

## 3. JS: función `renderSpiral()`

- [x] 3.1 Definir constantes de geometría: `R_NODE = 18`, `NODES_PER_TURN = 9`, `TURN_SPACING = 55`, `THETA_START = -Math.PI / 2`
- [x] 3.2 Calcular posición `(x, y)` de cada elemento con la espiral de Arquímedes dextrógira; H en origen, He en `r = TURN_SPACING` (arriba), resto creciendo en espiral clockwise
- [x] 3.3 Calcular `viewBox` desde el bounding box real de todos los nodos más margen; asignar al SVG
- [x] 3.4 Para cada elemento crear un `<g>` con clase `spiral-node element-cell cat-${el.category}`, `data-atomic-number="${el.atomicNumber}"`, transform `translate(x, y)`, y CSS var `--node-color`
- [x] 3.5 Dentro del `<g>`: añadir `<circle r="${R_NODE}">`, `<text>${el.symbol}</text>`, `<title>${el.atomicNumber} – ${el.name}</title>`
- [x] 3.6 Añadir listener `click` en cada `<g>` que llame a `showDetail(el, g)`
- [x] 3.7 Añadir referencias `$spiralSvg`, `$spiralView`, `$viewToggle` junto a los demás DOM refs; llamar `renderSpiral()` en la inicialización

## 4. JS: funciones `showSpiralView()` y `showGridView()`

- [x] 4.1 Implementar `showSpiralView()`: ocultar `.table-area`, mostrar `#spiral-view`, actualizar texto/icono del botón, actualizar `activeView = 'spiral'`
- [x] 4.2 Implementar `showGridView()`: ocultar `#spiral-view`, mostrar `.table-area`, actualizar texto/icono del botón, actualizar `activeView = 'grid'`
- [x] 4.3 Añadir listener `click` en `#view-toggle` que llame a `showSpiralView()` o `showGridView()` según `activeView`

## 5. JS: integración con búsqueda y detalle

- [x] 5.1 Modificar `filterElements()`: cambiar selector a `document.querySelectorAll('.element-cell')` para cubrir ambos contenedores simultáneamente; la filtering se aplica a nodos SVG igual que a celdas de cuadrícula
- [x] 5.2 Añadir `findCell(atomicNumber)` helper que busca en `$table` y en `$spiralSvg`; usar en `showDetail()` para desactivar el elemento previo independientemente de la vista
- [x] 5.3 Usar `findCell()` en `closeDetail()` para limpiar la clase `active` del nodo activo tanto en grid como en espiral

## 6. Pulido y verificación

- [x] 6.1 Comprobar que el botón de vista alterna correctamente entre cuadrícula y espiral sin perder estado del buscador ni del tema
- [x] 6.2 Verificar que el panel de detalle se abre al hacer clic en cualquier nodo de la espiral
- [x] 6.3 Verificar que la búsqueda aplica `dimmed`/`match` a los nodos SVG en la vista espiral
- [x] 6.4 Verificar que el modo oscuro colorea correctamente los nodos SVG (los colores de categoría provienen de CSS custom properties)
- [x] 6.5 Comprobar scroll y legibilidad en viewport móvil (≤ 768px): el SVG debe ser desplazable horizontalmente sin romper el layout
- [x] 6.6 Spot-check visual: H en el centro, He a su lado (arriba), luego Li, Be, B, C, N, O, F, Ne, Na… creciendo en espiral dextrógira — verificado por cálculo geométrico (distancias ≥ 36px, r_max=764)
