## Why

La implementación actual de la vista en espiral está incrustada en `app.js` como funciones globales (`renderSpiral`, `showSpiralView`, `showGridView`) y usa una geometría archimediana que coloca los 118 elementos en espiral circular sin reflejar la estructura de bloques electrónicos. Los lantánidos y actínidos quedan fuera del recorrido de la espiral porque siguen la lógica de separación de la cuadrícula clásica.

Se quiere un componente separado con un layout radicalmente diferente: la **tabla periódica en espiral cuadrada concéntrica**, donde los elementos se organizan en capas cuadradas que reflejan el número cuántico principal, con los bloques s, p, d, f y g en sus posiciones canónicas y los lantánidos/actínidos integrados sin separación.

## What Changes

- Eliminar funciones `renderSpiral`, `showSpiralView`, `showGridView` y constantes de geometría de `app.js`
- Nuevo archivo `periodic-table-spiral.js` con la función `initSpiralComponent(options)` que encapsula todo el renderizado y la lógica del componente
- Layout cuadrado concéntrico: bloques s (esquina inferior derecha), p (inferior centro), d (izquierda), f (marco superior y derecho), g (extensión derecha teórica)
- Lantánidos y actínidos integrados dentro del marco f, sin filas separadas
- Bloque g teórico con los 18 superactínidos (Z=121–138), incluyendo el símbolo provisional Fy (Z=136)
- Indicador visual de "PERIOD DIVIDE" entre Z=118 (Og) y Z=119
- `app.js` orquesta la inicialización y delega búsqueda y estado activo a la API pública del componente
- Actualización de `styles.css` para colores por bloque electrónico (reemplaza estilos de espiral archimediana)
- `index.html`: añadir `<script>` del nuevo componente

## Capabilities

### New Capabilities

- `periodic-table-spiral`: Componente SVG autónomo que renderiza la tabla periódica como espiral cuadrada concéntrica. Organiza los elementos en capas por número cuántico principal con bloques s/p/d/f/g en posiciones canónicas. Incluye superactínidos teóricos (bloque g, Z=121–138). API pública: `filter(query)`, `setActive(atomicNumber)`, `clearActive()`.

### Modified Capabilities

- `periodic-table-grid`: Sin cambios funcionales. El toggle de vista sigue ocultando/mostrando la cuadrícula como antes.

## Impact

- Se crea `periodic-table-spiral.js` (~250–350 LOC)
- `app.js` reduce ~80 LOC (se elimina lógica de espiral archimediana) y añade ~15 LOC de integración con la API del nuevo componente
- `styles.css`: ~25 líneas reemplazadas (`.spiral-node` → `.spiral-cell` + `.spiral-block-*` + `.theoretical`)
- `index.html`: cambio mínimo (añadir tag `<script>`)
- Sin nuevas dependencias externas
