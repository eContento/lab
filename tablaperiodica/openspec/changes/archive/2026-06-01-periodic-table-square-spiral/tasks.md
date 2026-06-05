## 1. Limpiar espiral archimediana de app.js y styles.css

- [x] 1.1 Eliminar las constantes `R_NODE`, `NODES_PER_TURN`, `TURN_SPACING`, `THETA_START` de `app.js`
- [x] 1.2 Eliminar la función `renderSpiral()` completa de `app.js`
- [x] 1.3 Eliminar `$spiralSvg` de los DOM refs en `app.js` (pasará a ser gestionado por el componente)
- [x] 1.4 Eliminar los estilos de `.spiral-node`, `.spiral-node circle`, `.spiral-node text`, `.spiral-node:hover circle`, `.spiral-node.active circle`, `.spiral-node.dimmed`, `.spiral-node.match circle` de `styles.css`
- [x] 1.5 Verificar que la vista de cuadrícula y el resto de funcionalidad siguen operativos después del borrado

## 2. Crear `periodic-table-spiral.js` — estructura y datos base

- [x] 2.1 Crear el archivo `periodic-table-spiral.js` en la raíz del proyecto con la firma de función: `function initSpiralComponent({ svgEl, elements, onElementClick })`
- [x] 2.2 Definir el array `G_BLOCK` inline con los 18 superactínidos teóricos (Z=121–138): usar símbolos provisionales `E121`…`E138` excepto Z=136 = `Fy` (Feynmanio)
- [x] 2.3 Implementar la función helper `getBlock(el)` que devuelve `'s'|'p'|'d'|'f'` según la categoría y grupo del elemento (s: metal-alcalino, metal-alcalinoterreo, H, He; d: metal-transicion; f: lantanido, actinido; p: resto)
- [x] 2.4 Implementar `getFillingPeriod(el)` que devuelve el período de llenado de Madelung: igual al `period` para bloques s y p; `period - 1` para bloque d (Sc está en período 4 pero llena 3d); `period - 2` para bloque f (La está en período 6 pero llena 4f)

## 3. Implementar `computePositions(allElements)` — layout cuadrado

- [x] 3.1 Definir constantes de layout: `CELL = 36`, anchuras de bloques (`S_COLS = 2`, `P_COLS = 6`, `D_COLS = 10`, `F_COLS = 14`, `G_COLS = 18`), número máximo de filas (períodos 1–7 + extensiones)
- [x] 3.2 Mapear **bloque s** (grupos 1 y 2): `col = totalCols - (group === 1 ? 2 : 1)`, `row = fillingPeriod - 1`
- [x] 3.3 Mapear **bloque p** (grupos 13–18): `col = D_COLS + (group - 13)`, `row = fillingPeriod - 2` (p-block empieza en período 2 → row 0)
- [x] 3.4 Mapear **bloque d** (grupos 3–12, excluyendo La=57 y Ac=89; incluyendo Lu=71 y Lr=103): `col = (group - 3)`, `row = fillingPeriod - 1` (d-block empieza con llenado 3d → fillingPeriod=3)
- [x] 3.5 Mapear **bloque f** (lantánidos Z=57–71 excl. Lu=71; actínidos Z=89–103 excl. Lr=103): `col = F_OFFSET + positionInSeries` donde `positionInSeries` es el índice 0–13 dentro de lantánidos o actínidos; `row` = 0 para lantánidos / 1 para actínidos (marco superior, dos filas)
- [x] 3.6 Mapear **bloque g** (G_BLOCK, Z=121–138): `col = totalCols - 1 - (138 - Z)`, `row = 2 + (Z - 121)` (continúan en el lateral derecho bajo el bloque f)
- [x] 3.7 Verificar ausencia de solapamientos recorriendo el array de posiciones; corregir offsets si hay colisiones
- [x] 3.8 Calcular `viewBox` del SVG: `minX = 0`, `minY = 0`, `width = (totalCols + 1) * CELL`, `height = (totalRows + 1) * CELL`; asignar al `svgEl`

## 4. Renderizar nodos SVG

- [x] 4.1 Limpiar el contenido del `svgEl` antes de renderizar (por si se llama múltiples veces)
- [x] 4.2 Usar `document.createDocumentFragment()` para construir todos los nodos antes de añadirlos al SVG
- [x] 4.3 Para cada elemento en `allElements` (118 de `ELEMENTS` + 18 de `G_BLOCK`), crear: `<g class="spiral-cell spiral-block-{b} [theoretical]" data-atomic-number="{Z}" transform="translate(x, y)">`
- [x] 4.4 Dentro del `<g>`: añadir `<rect width="32" height="32" rx="3">` con `style="--node-bg: var(--block-{b})"`, `<text class="cell-symbol" y="17">{symbol}</text>`, `<text class="cell-number" x="5" y="10">{Z}</text>`, `<title>{Z} – {name}</title>`
- [x] 4.5 Para elementos del bloque g (`.theoretical`), no añadir listener `click` funcional; añadir cursor CSS `default` o `not-allowed` desde la clase
- [x] 4.6 Para los 118 elementos reales, añadir listener `click` → `onElementClick(el, g)`
- [x] 4.7 Añadir el `<text>` o `<line>` del indicador "PERIOD DIVIDE" entre la posición de Og (Z=118) y Z=119

## 5. Implementar la API pública

- [x] 5.1 Guardar internamente un `Map<atomicNumber, nodeEl>` al crear los nodos para acceso O(1)
- [x] 5.2 Implementar `filter(query)`: si `query` vacío, quitar `dimmed`/`match` de todos; si no, para cada nodo buscar el elemento en `ELEMENTS` o `G_BLOCK` y comparar `normalize(name)`, `normalize(symbol)`, `String(Z).startsWith(q)`; aplicar `.dimmed` o `.match`
- [x] 5.3 Implementar `setActive(atomicNumber)`: quitar `.active` del nodo activo previo (si lo hay), añadir `.active` al nuevo nodo del `atomicNumber` dado
- [x] 5.4 Implementar `clearActive()`: quitar `.active` del nodo activo y resetear el puntero interno
- [x] 5.5 Retornar `{ filter, setActive, clearActive }` al final de `initSpiralComponent`

## 6. CSS: estilos de la espiral cuadrada

- [x] 6.1 Añadir en `:root`: variables `--block-s: #f97316`, `--block-p: #ec4899`, `--block-d: #3b82f6`, `--block-f: #84cc16`, `--block-g: #d1d5db`
- [x] 6.2 Añadir `.spiral-cell rect`: `fill: var(--node-bg, #ccc)`, `cursor: pointer`, `transition: opacity 0.15s`
- [x] 6.3 Añadir `.spiral-cell:hover rect`: `filter: brightness(1.15)`
- [x] 6.4 Añadir `.spiral-cell.active rect`: `stroke: var(--accent)`, `stroke-width: 2`
- [x] 6.5 Añadir `.spiral-cell.dimmed`: `opacity: 0.12`
- [x] 6.6 Añadir `.spiral-cell.match rect`: `stroke: var(--accent)`, `stroke-width: 2`
- [x] 6.7 Añadir `.spiral-cell.theoretical rect`: `stroke-dasharray: 3 2`, `opacity: 0.65` (estado normal); `cursor: default`
- [x] 6.8 Añadir `.spiral-cell text.cell-symbol`: `font-size: 9px`, `text-anchor: middle`, `dominant-baseline: central`, `pointer-events: none`, `fill: var(--text-on-cell)`
- [x] 6.9 Añadir `.spiral-cell text.cell-number`: `font-size: 6px`, `text-anchor: start`, `pointer-events: none`, `fill: var(--text-on-cell)`, `opacity: 0.8`

## 7. Integrar en app.js

- [x] 7.1 Añadir `let spiralAPI = null` junto a `let activeView = 'grid'` en la sección de estado
- [x] 7.2 Asegurarse de que `$spiralSvg` sigue siendo accesible en `app.js` (puede mantenerse como DOM ref aunque su contenido lo gestione el componente)
- [x] 7.3 En la sección de init (al final del fichero), reemplazar la llamada a `renderSpiral()` por `spiralAPI = initSpiralComponent({ svgEl: $spiralSvg, elements: ELEMENTS, onElementClick: showDetail })`
- [x] 7.4 En `filterElements()`, añadir `spiralAPI?.filter(query)` al final de la función (se aplica independientemente de la vista activa para mantener el estado sincronizado)
- [x] 7.5 En `showDetail()`, reemplazar la búsqueda de nodo en `$spiralSvg` por `spiralAPI?.setActive(el.atomicNumber)`
- [x] 7.6 En `closeDetail()`, reemplazar la búsqueda en `$spiralSvg` por `spiralAPI?.clearActive()`
- [x] 7.7 Simplificar `findCell()` para que solo busque en `$table` (eliminar la rama de `$spiralSvg`)

## 8. HTML: añadir script del componente

- [x] 8.1 Añadir `<script src="periodic-table-spiral.js"></script>` en `index.html` justo antes del `<script src="app.js">` existente

## 9. Pulido y verificación

- [x] 9.1 Verificar visualmente que los bloques s, p, d, f se ubican en sus posiciones canónicas (s esquina inferior derecha, p inferior centro, d izquierda, f marco superior/derecho)
- [x] 9.2 Verificar que lantánidos y actínidos aparecen integrados en el marco f (no separados como filas)
- [x] 9.3 Verificar que el click en cualquier nodo de los 118 elementos reales abre el panel de detalle
- [x] 9.4 Verificar que los nodos del bloque g muestran el estilo `theoretical` (borde discontinuo, opacity reducida)
- [x] 9.5 Verificar que la búsqueda aplica `dimmed`/`match` en ambas vistas simultáneamente
- [x] 9.6 Verificar que el modo oscuro colorea correctamente los nodos (CSS vars sin re-render)
- [x] 9.7 Verificar que alternar entre vista cuadrícula y vista espiral no pierde el estado del buscador ni del elemento seleccionado
- [x] 9.8 Verificar que el indicador "PERIOD DIVIDE" es visible y está correctamente posicionado
