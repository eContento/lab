## Context

SPA estática vanilla (HTML5 + CSS3 + JS sin framework). Archivos: `index.html`, `app.js`, `styles.css`, `elements.js` (array global `ELEMENTS`, 118 objetos con `atomicNumber`, `symbol`, `name`, `category`, `period`, `group`, etc.). La aplicación ya tiene un toggle de vista grid/espiral; este cambio reemplaza únicamente la lógica de renderizado de la espiral manteniendo el toggle intacto.

Stakeholders: desarrollador único, usuario final (estudiante/curioso de química).

## Goals / Non-Goals

**Goals:**
- `periodic-table-spiral.js` como módulo autónomo sin variables globales salvo lo que recibe por parámetro
- Layout cuadrado concéntrico (bloques electrónicos), no espiral archimediana
- API pública: `{ filter(query), setActive(Z), clearActive() }`
- Colores por bloque s/p/d/f/g vía CSS custom properties; soporte modo claro/oscuro heredado automáticamente
- Superactínidos teóricos Z=121–138 (bloque g) con estilo diferenciado (borde discontinuo)
- Indicador "PERIOD DIVIDE" entre Z=118 y Z=119

**Non-Goals:**
- Animación de transición al cambiar de vista
- Zoom/pan interactivo
- Mostrar bloque g en la vista de cuadrícula
- Añadir Z=121–138 a `elements.js`

## Decisions

### Arquitectura: función init con API pública

`periodic-table-spiral.js` expone una única función global:

```
initSpiralComponent({ svgEl, elements, onElementClick })
  → { filter(query), setActive(atomicNumber), clearActive() }
```

- `svgEl`: `<svg>` del DOM donde renderizar
- `elements`: array `ELEMENTS` (118 objetos)
- `onElementClick(el, nodeEl)`: callback hacia `app.js` para abrir el panel de detalle

El componente no accede a ningún global; `app.js` mantiene el control del ciclo de vida. Esta firma hace el componente testeable y reutilizable.

### Layout: espiral cuadrada concéntrica por bloques cuánticos

Los elementos se colocan en una cuadrícula 2D donde cada posición `(col, row)` se determina por el bloque electrónico del elemento y su período de llenado (Madelung):

```
         COL_F (14 cols)     COL_D (10 cols)   COL_P (6)  COL_S (2)
ROW 0:   ┌──────────────────────────────────────────────────────────┐
ROW 1:   │  f-block (La–Lu / Ac–Lr)                       │        │
ROW ...  │  marco superior y lateral derecho              │  s     │
ROW n:   │                         d-block (Sc–Hg)        │  blk   │
ROW n+1: │                         (metales transición)   ├────────┤
ROW ...  │                                       p-block  │  s     │
ROW max: └─────────────────────────────────────── (B–Rn)──┴────────┘
         ← bloque g teórico (Z=121–138) más a la derecha que f-block
```

Reglas de asignación `(col, row)` — se parte de un sistema donde:
- `CELL = 36` px por celda
- El origen (col=0, row=0) es la esquina superior-izquierda del viewBox
- El bloque f ocupa las columnas más externas (mayor índice col si está a la derecha, menor si está a la izquierda del d-block)

**Bloque s** (grupos 1 y 2 + H y He):
- `col = totalCols - (group === 1 ? 2 : 1)`  (dos columnas más a la derecha)
- `row = fillingPeriod(el)` donde `fillingPeriod` = `period` para s-block

**Bloque p** (grupos 13–18):
- `col = dStart + dWidth + (group - 13)` (justo a la derecha del bloque d)
- `row = fillingPeriod(el) - 1` (comienza en el período 2)

**Bloque d** (grupos 3–12, excluyendo La, Ac; incluyendo Lu=71 y Lr=103 siguiendo IUPAC 2021):
- `col = dStart + (group - 3)` (10 columnas)
- `row = fillingPeriod(el) - 3` (d-block empieza en período 4 → row 1)

**Bloque f** (lantánidos Z=57–71 excluyendo Lu; actínidos Z=89–103 excluyendo Lr):
- Distribuidos en el marco: filas superiores y/o lateral derecho
- `col = fStart + positionWithinSeries` (conteo 0–13 dentro de lantánidos o actínidos)
- `row = 0 para lantánidos` / `row = 1 para actínidos` (las dos filas del marco superior)

**Bloque g** (superactínidos teóricos Z=121–138, definidos inline):
- `col = totalCols - 1 - (138 - el.atomicNumber)` (lateral derecho, más externo que f)
- `row = 2 + (el.atomicNumber - 121)` (continúan hacia abajo desde donde termina el bloque f)

> **Nota**: las constantes exactas (`dStart`, `fStart`, `totalCols`) se afinarán durante la implementación para obtener una distribución sin solapamientos. Si el algoritmo puro produce solapamientos en casos borde, se parchea con una tabla de posiciones overrides para esos elementos.

### Nodos: `<g>` con `<rect>` (no círculos)

El layout es ortogonal → `<rect width="CELL-4" height="CELL-4" rx="3">` (margen interior de 2px). Más legible y coherente con la naturaleza cuadrícula del layout.

Cada nodo:
```html
<g class="spiral-cell spiral-block-{s|p|d|f|g} [theoretical]"
   data-atomic-number="{Z}"
   transform="translate(x, y)">
  <rect width="32" height="32" rx="3" />
  <text class="cell-symbol">{symbol}</text>
  <text class="cell-number">{Z}</text>
  <title>{Z} – {name}</title>
</g>
```

### Coloreado: CSS custom properties por bloque

```css
:root {
  --block-s: #f97316;   /* naranja  */
  --block-p: #ec4899;   /* rosa     */
  --block-d: #3b82f6;   /* azul     */
  --block-f: #84cc16;   /* verde    */
  --block-g: #d1d5db;   /* gris     */
}
[data-theme="dark"] {
  /* mismas variables con ligero ajuste si es necesario */
}
```

El `fill` de cada `<rect>` se asigna vía `style.setProperty('--node-bg', 'var(--block-s)')` para que los nodos respondan automáticamente al cambio de tema.

### Superactínidos teóricos (G_BLOCK)

```js
const G_BLOCK = [
  { atomicNumber: 121, symbol: 'E121', name: 'Unbiunio',     block: 'g' },
  ...
  { atomicNumber: 136, symbol: 'Fy',   name: 'Feynmanio',    block: 'g' },
  ...
  { atomicNumber: 138, symbol: 'E138', name: 'Untrioctio',   block: 'g' },
];
```

Los nodos del bloque g reciben `.theoretical`, que aplica `stroke-dasharray: 3 2` y `opacity: 0.65` para indicar su naturaleza hipotética. No tienen un `onElementClick` funcional (sin datos en `ELEMENTS`); al hacer click se puede mostrar un tooltip o ignorar el evento.

### Indicador "PERIOD DIVIDE"

Un elemento SVG `<text>` o `<line>` se añade entre la columna de Og (Z=118) y la de Z=119 para indicar el fin del período 7. Posición calculada al finalizar el layout.

### Integración en app.js

```js
// Al inicializar:
const spiralAPI = initSpiralComponent({
  svgEl: $spiralSvg,
  elements: ELEMENTS,
  onElementClick: showDetail,
});

// En filterElements():
spiralAPI.filter(query);

// En showDetail():
spiralAPI.setActive(el.atomicNumber);

// En closeDetail():
spiralAPI.clearActive();
```

`findCell()` en `app.js` ya no necesita buscar en `$spiralSvg`; puede simplificarse a solo buscar en `$table`.

### Estructura de archivos

| Archivo | Acción |
|---------|--------|
| `periodic-table-spiral.js` | NUEVO |
| `app.js` | Eliminar ~80 LOC de espiral archimediana; añadir ~15 LOC de integración |
| `styles.css` | Reemplazar `.spiral-node*` por `.spiral-cell*` + `.spiral-block-*` + `.theoretical` |
| `index.html` | Añadir `<script src="periodic-table-spiral.js">` antes de `app.js` |

## Risks / Trade-offs

- **Complejidad del layout**: el mapeo de bloques a posiciones (col, row) tiene casos borde (La/Ac/Lu/Lr, período de llenado de los d-block en período 4 que es `3d` pero período 4 en la tabla clásica). Se recomienda hacer un dry-run en papel/spreadsheet antes de codificarlo.
- **Lu/Lr en bloque d (IUPAC 2021)**: algunos usuarios esperan La/Ac en grupo 3. Se puede añadir un comentario en el código y quizás una nota visual pequeña en la leyenda.
- **Nodos del bloque g sin datos**: el click no puede abrir el panel de detalle para Z=121–138. Opciones: deshabilitar el click con cursor `not-allowed`, o mostrar un pequeño tooltip inline con "Elemento teórico (no confirmado)".
- **Legibilidad a escala reducida**: 136 nodos de 32px en un SVG de ancho total ~1000px puede ser denso. El SVG es zoomable; si la escala es un problema real se puede reducir a dos líneas de texto por nodo en vez de símbolo + número.
