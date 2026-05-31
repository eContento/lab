## 1. Estructura del proyecto

- [x] 1.1 Crear los archivos del proyecto: `index.html`, `styles.css`, `elements.js`, `app.js`
- [x] 1.2 Configurar `index.html` con `<meta charset>`, `<meta name="viewport">`, links a `styles.css` y scripts a `elements.js` y `app.js`

## 2. Datos de los elementos (`elements.js`)

- [x] 2.1 Crear `elements.js` con `const ELEMENTS = [...]` para los 118 elementos, incluyendo los campos: `atomicNumber`, `symbol`, `name`, `atomicMass`, `category`, `period`, `group`, `electronConfiguration`, `standardState`, `meltingPoint`, `boilingPoint`, `summary`
- [x] 2.2 Usar categorías normalizadas en español: `metal-alcalino`, `metal-alcalinoterreo`, `metal-transicion`, `metal-post-transicion`, `metaloide`, `no-metal`, `halogeno`, `gas-noble`, `lantanido`, `actinido`, `desconocido`
- [x] 2.3 Lantánidos (Z=57–71): `group: null`, se posicionan en fila separada; actínidos (Z=89–103): `group: null`, fila separada
- [x] 2.4 Verificar que existen exactamente 118 entradas con `atomicNumber` del 1 al 118, y hacer spot-check de H, Fe, Au, Og

## 3. Estilos base y variables de tema (`styles.css`)

- [x] 3.1 Definir variables CSS custom properties en `:root` para el tema claro: colores de fondo, texto, celdas, panel, borde, sombra
- [x] 3.2 Definir variables para el tema oscuro en `[data-theme="dark"]` sobreescribiendo las mismas custom properties
- [x] 3.3 Definir variables de color por categoría de elemento (una variable por categoría, usada en `.category-*`)
- [x] 3.4 Añadir estilos base: reset mínimo, `box-sizing`, tipografía, layout general de la página

## 4. Cuadrícula de la tabla periódica (`periodic-table-grid`)

- [x] 4.1 Implementar el CSS Grid de la tabla: 18 columnas, con filas 1–7 para la tabla principal y filas 9–10 para lantánidos/actínidos (fila 8 como separador visual)
- [x] 4.2 Crear `renderTable()` en `app.js` que genera dinámicamente las celdas `.element-cell` en el grid usando `grid-column` y `grid-row` según `period`/`group` de cada elemento
- [x] 4.3 Posicionar lantánidos en la fila 9 (columnas 3–17) y actínidos en la fila 10 (columnas 3–17), con marcadores "57–71" y "89–103" en las posiciones La/Ac de la tabla principal
- [x] 4.4 Cada celda muestra: número atómico (esquina superior izquierda, pequeño), símbolo (centro, grande), nombre (inferior, pequeño truncado)
- [x] 4.5 Aplicar clase CSS `.cat-<categoria>` a cada celda para el color de fondo por categoría
- [x] 4.6 Añadir efecto hover en celdas (`:hover`) con ligero brillo o borde destacado
- [x] 4.7 Crear leyenda de categorías debajo de la tabla (color swatch + nombre legible de la categoría)

## 5. Panel de detalle (`element-detail`)

- [x] 5.1 Añadir HTML del panel de detalle en `index.html`: `<aside id="detail-panel">` con todos los campos y botón de cierre `×`
- [x] 5.2 Implementar `showDetail(element)` en `app.js`: rellena los campos del panel y añade clase `open` al panel
- [x] 5.3 Implementar `closeDetail()`: elimina clase `open` del panel y quita clase `active` de la celda seleccionada; enlazar al botón ×, tecla Escape y clic fuera del panel
- [x] 5.4 Marcar la celda seleccionada con clase `active` (borde destacado con color de categoría)
- [x] 5.5 Mostrar "N/A" para campos con valor `null`
- [x] 5.6 CSS del panel: sidebar fijo a la derecha en viewport ≥ 1024px; bottom sheet deslizante en viewport < 1024px

## 6. Buscador en tiempo real (`element-search`)

- [x] 6.1 Añadir `<input type="search">` y botón limpiar (×) en la cabecera del HTML
- [x] 6.2 Implementar `filterElements(query)` en `app.js`: compara query insensible a mayúsculas/acentos contra `name`, `symbol` y `atomicNumber` (como string)
- [x] 6.3 Aplicar clase `dimmed` (opacity 0.2–0.3) a celdas que no coincidan y clase `match` a las que sí coincidan durante búsqueda activa
- [x] 6.4 Restaurar estado normal de todas las celdas al vaciar el campo de búsqueda
- [x] 6.5 Mostrar mensaje "No se encontraron elementos" bajo la tabla si ningún elemento coincide

## 7. Modo claro / oscuro (`dark-light-mode`)

- [x] 7.1 Añadir botón toggle (icono ☀️/🌙) en la cabecera del HTML
- [x] 7.2 Implementar `toggleTheme()` en `app.js`: alterna `data-theme="light"|"dark"` en `<html>` y guarda en `localStorage`
- [x] 7.3 Al cargar la página, leer `localStorage` para aplicar el tema guardado; si no existe, detectar preferencia del sistema con `prefers-color-scheme`

## 8. Pulido y verificación

- [x] 8.1 Revisar scroll horizontal en viewport móvil (< 768px): el contenedor de la tabla debe tener `overflow-x: auto`
- [x] 8.2 Verificar que la aplicación funciona completamente abriendo `index.html` directamente desde el sistema de archivos (sin servidor)
- [x] 8.3 Comprobar que el modo oscuro y claro se aplican correctamente en todos los componentes (tabla, celdas, panel, cabecera, leyenda)
- [x] 8.4 Spot-check de datos: H (1), Fe (26), Au (79), Og (118) tienen valores correctos en todos los campos
