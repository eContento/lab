## Context

La aplicación es una SPA estática vanilla (HTML5 + CSS3 + JS sin framework). Ya existe una vista de cuadrícula con `renderTable()` en `app.js`, un array `ELEMENTS` (118 objetos), un panel de detalle (`showDetail()`), búsqueda en tiempo real y modo claro/oscuro. Todo el estado visual está en el DOM; no hay framework de estado.

Stakeholders: usuario final curioso/estudiante, desarrollador único.

## Goals / Non-Goals

**Goals:**
- Añadir un botón de toggle en la cabecera para cambiar entre vista cuadrícula y vista espiral
- Renderizar los 118 elementos en espiral archimediana SVG, dextrógira, centrada en H (Z=1)
- Cada nodo muestra el símbolo del elemento, coloreado por categoría
- Click en nodo → `showDetail()` existente (sin duplicar lógica)
- Hover muestra nombre + número atómico en tooltip (title SVG nativo es suficiente)
- El buscador aplica `dimmed`/`match` a los nodos SVG igual que a las celdas de cuadrícula
- Modo claro/oscuro afecta al SVG a través de CSS custom properties
- La vista en espiral es responsive: el SVG tiene `viewBox` fijo y escala con `width: 100%`

**Non-Goals:**
- Animación de despliegue de la espiral (no requerida)
- Exportar la espiral como imagen
- Disposición alternativa de la espiral (logarítmica, hexagonal, etc.)
- Accesibilidad WCAG AA completa para el SVG

## Decisions

### Geometría: espiral de Arquímedes con separación adaptada al tamaño de nodo

La espiral de Arquímedes `r = a + b·θ` coloca puntos a distancias iguales del centro conforme aumenta el ángulo. Se elige este modelo porque la distancia radial entre vueltas es constante, lo que da separación visual uniforme entre nodos.

Parámetros:
- Radio de nodo: `R_NODE = 18` (px en espacio SVG)
- Separación entre vueltas: `STEP = R_NODE * 2.6` (≈ 46.8 px, evita solapamiento)
- Ángulo por elemento: `Δθ = 2π / 13` (≈ 27.7°; 13 nodos por vuelta, primo, evita alineaciones radiales)
- Posición del elemento i (0-indexed): `θ = i · Δθ`, `r = STEP · θ / (2π)`
- Offset angular: `θ_start = -π/2` para que el Hidrógeno arranque en la parte superior del centro
- Sentido dextrógiro: θ crece en sentido positivo con eje Y invertido (sistema SVG), lo que resulta en clockwise

Centro del SVG: `(cx, cy)`. El SVG se dimensiona para contener todos los nodos con margen.

Alternativas consideradas:
- **Espiral logarítmica**: la distancia entre vueltas crece, dejando espacio vacío en el exterior. Descartada.
- **Hexagonal/radial por períodos**: mapear período al radio. Rompe la continuidad Z=1..118 y no refleja la petición de ordenar estrictamente por número atómico.

### Renderizado: SVG inline generado dinámicamente desde JS

SVG inline (no Canvas) porque:
- Los nodos son elementos DOM → se pueden estilizar con CSS custom properties (modo claro/oscuro sin re-render)
- Se puede añadir `data-atomic-number` a cada nodo para que el filtro de búsqueda existente funcione sin modificación de su lógica central
- No requiere librería externa

Cada nodo es un `<g>` con:
- `<circle>` con `fill` desde variable CSS `--cat-<categoria>`
- `<text>` con el símbolo centrado
- `title` para tooltip nativo
- `data-atomic-number` para que `filterElements` lo reconozca (la función ya itera `.element-cell`; los nodos SVG usarán la misma clase `element-cell` y `data-atomic-number` para compatibilidad)

### Toggle de vistas: clase CSS en el contenedor

Se añade una clase `view-spiral` al `<main>` (o al wrapper) cuando la espiral está activa. Los contenedores de cuadrícula y espiral se muestran/ocultan con `display: none` según la clase activa. No se destruye ni re-renderiza el DOM de cuadrícula al cambiar de vista; la espiral se renderiza una sola vez al cargar y se muestra/oculta.

### Integración con búsqueda existente

`filterElements()` itera `$table.querySelectorAll('.element-cell')`. Los nodos SVG tendrán clase `element-cell` para que el mismo selector los incluya cuando la espiral está visible. Se reutiliza la misma lógica de `dimmed`/`match`; en SVG `opacity` se puede controlar con la misma clase CSS (`opacity: 0.15` para `dimmed`).

### Estructura de archivos y cambios

| Archivo | Cambio |
|---------|--------|
| `index.html` | Añadir botón `#view-toggle` en `.header-controls`; añadir `<div id="spiral-view">` con `<svg id="spiral-svg">` dentro de `<main>` |
| `styles.css` | Estilos para `#spiral-view`, `#view-toggle`, `.spiral-node` (hover, active), `dimmed`/`match` en SVG |
| `app.js` | Funciones `renderSpiral()`, `showSpiralView()`, `showGridView()`; modificar `filterElements()` para incluir nodos SVG; listener del botón toggle |

### Tamaño del SVG y responsive

El SVG usará un `viewBox` calculado para contener todos los nodos (radio máximo + margen). El elemento `<svg>` tendrá `width="100%"` y `height` automático mediante el `viewBox`. El contenedor `#spiral-view` centrará el SVG con `display: flex; justify-content: center`.

## Risks / Trade-offs

- **Texto legible a escalas pequeñas**: con 118 nodos en espiral, los símbolos pueden ser pequeños en pantallas reducidas. El SVG es zoomable en navegadores modernos como mitigación. Se puede explorar en el futuro un zoom interactivo.
- **Tooltip nativo (`<title>`)**: en móvil no hay hover, por lo que el tooltip con nombre completo no se ve; el click ya abre el panel de detalle, que es suficiente.
- **`filterElements` y SVG**: la función actual usa `cell.dataset.atomicNumber` para buscar en `ELEMENTS`; los nodos SVG deben tener el mismo atributo `data-atomic-number` en el elemento raíz `<g>` del nodo.
