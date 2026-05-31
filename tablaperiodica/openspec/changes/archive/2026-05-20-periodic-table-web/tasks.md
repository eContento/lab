## 1. Estructura del proyecto

- [x] 1.1 Crear la estructura de archivos: `index.html`, `styles.css`, `elements.js`, `app.js`
- [x] 1.2 Configurar el `index.html` base con head (meta viewport, charset, links a CSS/JS)

## 2. Datos de los elementos (element-data)

- [x] 2.1 Crear `elements.js` con el array de los 118 elementos incluyendo todos los campos definidos en spec: `atomicNumber`, `symbol`, `name`, `atomicMass`, `category`, `period`, `group`, `electronConfiguration`, `standardState`, `meltingPoint`, `boilingPoint`, `summary`
- [x] 2.2 Verificar que las categorías usan los valores normalizados (`metal-alcalino`, `gas-noble`, etc.) y que lantánidos/actínidos tienen `group: null`
- [x] 2.3 Verificar que existen exactamente 118 entradas con `atomicNumber` del 1 al 118

## 3. Cuadrícula de la tabla periódica (periodic-table-grid)

- [x] 3.1 Implementar el CSS Grid de la tabla con 18 columnas y posicionamiento explícito por `grid-column`/`grid-row`
- [x] 3.2 Crear la función `renderTable()` en `app.js` que genera las celdas del DOM a partir de `elements.js`
- [x] 3.3 Posicionar lantánidos (Z=57–71) en fila 8 y actínidos (Z=89–103) en fila 9 con separación visual
- [x] 3.4 Aplicar colores de fondo por categoría mediante clases CSS (una clase por categoría)
- [x] 3.5 Añadir leyenda de categorías debajo de la tabla con color + nombre
- [x] 3.6 Mostrar en cada celda: número atómico (arriba), símbolo (centro grande) y nombre (abajo pequeño)
- [x] 3.7 Añadir efecto hover en celdas (CSS `:hover`)

## 4. Panel de detalle (element-detail)

- [x] 4.1 Crear el HTML del panel de detalle en `index.html` (sidebar + botón de cerrar)
- [x] 4.2 Implementar `showDetail(element)` en `app.js` que rellena y muestra el panel con todos los campos
- [x] 4.3 Implementar `closeDetail()` y enlazarlo al botón de cerrar y a la tecla Escape
- [x] 4.4 Marcar visualmente la celda activa (clase CSS `active`) al seleccionar un elemento
- [x] 4.5 Añadir CSS responsivo: sidebar lateral en viewport ≥ 1024px, bottom sheet en viewport < 1024px
- [x] 4.6 Mostrar "N/A" para campos con valor `null` en el panel

## 5. Búsqueda y filtrado (element-search)

- [x] 5.1 Añadir campo de búsqueda (`<input>`) y botón de limpiar en el HTML
- [x] 5.2 Implementar `filterElements(query)` en `app.js` que compara query (insensible a mayúsculas) contra nombre, símbolo y número atómico
- [x] 5.3 Atenuar celdas que no coincidan con la búsqueda (clase CSS `dimmed`) y resaltar las que sí coincidan
- [x] 5.4 Mostrar mensaje "No se encontraron elementos" cuando la búsqueda no tiene resultados
- [x] 5.5 Restaurar el estado normal de la tabla al vaciar el campo de búsqueda

## 6. Pulido y verificación

- [x] 6.1 Revisar responsividad en viewport móvil (< 768px): tabla con scroll horizontal si es necesario
- [x] 6.2 Verificar que la aplicación funciona completamente sin conexión (abrir `index.html` directamente desde el sistema de archivos)
- [x] 6.3 Revisar que todos los campos de todos los elementos tienen valores correctos (spot-check: H, Fe, Au, Og)
