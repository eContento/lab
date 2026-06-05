## Context

Aplicación web estática de tabla periódica que debe funcionar abriendo directamente `index.html` en el navegador (file:// protocol). No hay backend, build tools ni dependencias externas. El stack es HTML5 + CSS3 + Vanilla JavaScript ES6+.

## Goals / Non-Goals

**Goals:**
- Tabla periódica completa (118 elementos) con disposición estándar IUPAC
- Colores pastel diferenciados por categoría (metales alcalinos, halógenos, gases nobles, etc.)
- Fondo claro general; tipografía legible
- Lantánidos y actínidos en bloque separado con al menos una fila de espacio visual
- Buscador en tiempo real (nombre, símbolo, número atómico)
- Panel de detalles fijo (no reescala la tabla) al seleccionar un elemento
- Compatible con Chrome/Firefox/Safari modernos sin instalación

**Non-Goals:**
- Soporte a navegadores legacy (IE, Edge antiguo)
- Internacionalización / i18n
- Animaciones complejas o transiciones pesadas
- Backend, base de datos ni API externa
- Tests automatizados

## Decisions

### Estructura de ficheros: monorepo estático
Un único directorio con `index.html`, `styles.css`, `app.js` y `data/elements.js`.
Los datos de los 118 elementos se almacenan como array JS exportado en `data/elements.js` e importado con `<script type="module">`.

**Alternativa descartada:** JSON externo cargado con fetch — no funciona con `file://` protocol sin flag especial en el navegador.

### Layout: CSS Grid para la tabla
La tabla periódica se modela con CSS Grid de 18 columnas × 7 filas para el bloque principal.
Los lantánidos y actínidos van en un grid separado con 15 columnas, con un gap/margin de al menos una fila de altura entre ambos bloques.

**Alternativa descartada:** `<table>` HTML — menos flexible para el posicionamiento de celdas vacías y separación del bloque f.

### Panel de detalles: posición fija lateral
El panel de detalles ocupa una columna fija a la derecha de la tabla usando un layout flex/grid en el contenedor principal. La tabla nunca redimensiona su grid; el panel se superpone como sidebar o aparece en una columna reservada.

**Alternativa descartada:** modal/overlay — cubre la tabla y hace difícil la comparación.

### Datos de elementos: objeto JS inline
Los datos (número atómico, símbolo, nombre, masa, categoría, grupo, periodo, configuración electrónica, electronegatividad, etc.) se definen como array de objetos en `data/elements.js`.

**Alternativa descartada:** CSV o JSON externo — requiere fetch que falla en file://.

### Colores por categoría: variables CSS custom properties
Cada categoría de elemento (metal alcalino, metal alcalino-térreo, metal de transición, metaloide, no metal, halógeno, gas noble, lantánido, actínido, otros metales) tiene una CSS custom property con color pastel.

## Risks / Trade-offs

- [Riesgo: datos de 118 elementos son muchos a mantener manualmente] → Mitigation: generar el array una sola vez; es suficientemente estable.
- [Riesgo: panel de detalles fijo puede causar overflow en pantallas pequeñas] → Mitigation: diseño responsivo mínimo con media query para móvil (stacked layout).
- [Riesgo: búsqueda en tiempo real puede ser lenta con highlight DOM] → Mitigation: la búsqueda opera sobre clases CSS (toggle), no re-renderiza el DOM.
