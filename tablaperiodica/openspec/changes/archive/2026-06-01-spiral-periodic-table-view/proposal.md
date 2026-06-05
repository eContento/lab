## Why

La tabla periódica en disposición de cuadrícula es la forma canónica pero no la única forma de visualizar la secuencia de los elementos. Una vista en espiral permite contemplar la progresión continua del número atómico de forma orgánica, haciendo visibles patrones de periodicidad y agrupación categórica que la cuadrícula estándar puede ocultar. Es además una visualización estéticamente llamativa que invita a explorar la tabla de una manera diferente.

## What Changes

- Nuevo botón en la cabecera que alterna entre la vista de cuadrícula estándar y la vista en espiral
- Vista en espiral SVG: el Hidrógeno (Z=1) en el centro, con los elementos desplegándose hacia afuera en sentido dextrógiro (clockwise) por número atómico hasta el Oganesson (Z=118)
- Cada elemento se representa como un círculo coloreado por categoría, con su símbolo visible
- Al hacer clic en cualquier elemento de la espiral se abre el mismo panel de detalle existente
- El buscador y el modo claro/oscuro siguen funcionando en ambas vistas

## Capabilities

### New Capabilities

- `spiral-view`: Vista SVG en espiral archimediana centrada en H (Z=1), creciendo en sentido dextrógiro por número atómico, con nodos coloreados por categoría e interactividad completa (click → detalle, hover, búsqueda)

### Modified Capabilities

- `periodic-table-grid`: Se mantiene sin cambios funcionales; el contenedor de la tabla se oculta/muestra al alternar vistas

## Impact

- Se añaden funciones nuevas a `app.js` (`renderSpiral`, `showSpiralView`, `showGridView`) sin modificar las existentes
- Se añaden estilos para el contenedor SVG y el botón de vista en `styles.css`
- Se añade el botón de cambio de vista y el contenedor `<svg>` en `index.html`
- Sin dependencias externas; la espiral se genera con matemáticas puras en SVG
