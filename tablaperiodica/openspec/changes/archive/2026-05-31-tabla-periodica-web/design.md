## Context

Proyecto web nuevo desde cero. Aplicación cliente puro (sin backend) que muestra la tabla periódica de los 118 elementos con interactividad: selección de elemento, panel de detalle, búsqueda en tiempo real y alternancia de tema claro/oscuro.

Stakeholders: usuario final (estudiante/curioso), desarrollador único.

## Goals / Non-Goals

**Goals:**
- Renderizar la tabla periódica completa en disposición estándar (18 grupos × 7 períodos + lantánidos/actínidos separados por una fila vacía)
- Colorear elementos por categoría con leyenda visual
- Mostrar detalle completo de cada elemento al hacer clic
- Búsqueda en tiempo real por nombre, símbolo o número atómico con resaltado/atenuado
- Modo claro y modo oscuro con toggle persistido en localStorage
- Funcionar sin servidor: abrir `index.html` directamente desde el sistema de archivos
- Datos de 118 elementos en JSON estático

**Non-Goals:**
- Visualizaciones 3D o animaciones de electrones
- Comparación multi-elemento simultánea
- Internacionalización (i18n)
- Accesibilidad avanzada (WCAG AA) en esta versión
- Soporte a Internet Explorer

## Decisions

### Stack: HTML5 + CSS3 + JavaScript vanilla (sin framework)

La aplicación es una única página estática sin routing ni estado complejo. Vanilla JS elimina tiempo de build, dependencias y complejidad de tooling, y el archivo funciona abriéndolo directamente en el navegador sin `file://` CORS issues.

Alternativas consideradas:
- **React/Vue**: añaden complejidad de setup (bundler, npm) sin beneficio para un proyecto de una sola vista sin build step
- **Web Components**: viables pero añaden boilerplate innecesario para este scope

### Datos: archivo `elements.json` estático

Un archivo `elements.json` contiene un array con los 118 elementos. Se carga con `fetch()` desde JS (funciona en la mayoría de navegadores modernos vía `file://` o se incrusta directamente como variable JS). Campos por elemento:

```
atomicNumber, symbol, name, atomicMass, category, period, group,
electronConfiguration, standardState, meltingPoint, boilingPoint, summary
```

Categorías normalizadas (en español, para mostrar en UI):
`metal-alcalino`, `metal-alcalinoterreo`, `metal-transicion`, `metal-post-transicion`,
`metaloide`, `no-metal`, `halogeno`, `gas-noble`, `lantanido`, `actinido`, `desconocido`

Para lantánidos (Z=57–71) y actínidos (Z=89–103): `group: null`, `period: 8` o `9` respectivamente.

### Layout de la tabla: CSS Grid con posicionamiento explícito

CSS Grid de 18 columnas. Cada celda se posiciona con `grid-column` y `grid-row` explícitos según el período y grupo del elemento. Las filas 8 y 9 se reservan para lantánidos y actínidos con un `gap` o fila vacía (fila 7.5) como separador visual entre la tabla principal y las series separadas.

Referencia del espacio vacío estándar: en la fila 6 (grupo 3) se muestra un marcador "57–71 →" y en la fila 7 (grupo 3) otro "89–103 →", enlazando visualmente con las filas inferiores.

### Panel de detalle: sidebar fijo en desktop, bottom sheet en móvil

- Desktop (≥ 1024px): panel lateral derecho, siempre visible al seleccionar
- Móvil (< 1024px): panel deslizante desde abajo (bottom sheet) superpuesto sobre la tabla
- Cierre: botón ×, clic fuera del panel (en móvil), o tecla Escape
- La celda activa lleva clase `active` con borde destacado

Campos mostrados en el panel:
1. Número atómico
2. Símbolo (grande, coloreado con el color de su categoría)
3. Nombre
4. Masa atómica
5. Categoría
6. Período / Grupo
7. Configuración electrónica
8. Estado estándar (sólido/líquido/gas/desconocido)
9. Punto de fusión (K)
10. Punto de ebullición (K)
11. Descripción (summary)

Valores `null` se muestran como "N/A".

### Búsqueda en tiempo real

Input de texto en la cabecera. Al escribir:
- Se compara la query (insensible a mayúsculas/diacríticos normalizados) contra `name`, `symbol` y `atomicNumber` (como string)
- Las celdas que coinciden reciben clase `match`; las que no, clase `dimmed` (opacity reducida)
- Al borrar la query, se restaura el estado normal
- Si no hay coincidencias, mensaje textual bajo la tabla

### Modo claro / oscuro

Toggle en la cabecera (icono sol/luna). El tema se aplica cambiando el atributo `data-theme="light"|"dark"` en `<html>`. Las variables CSS custom properties definen todos los colores de ambos temas.

Persistencia: `localStorage.getItem('theme')` al cargar; `localStorage.setItem('theme', ...)` al cambiar.

Por defecto: detectar preferencia del sistema con `prefers-color-scheme`.

### Estructura de archivos

```
index.html        — estructura HTML, referencias a CSS y JS
styles.css        — todos los estilos, variables de tema, grid, responsive
elements.js       — datos de los 118 elementos como constante JS (evita CORS en file://)
app.js            — lógica: renderTable, showDetail, filterElements, toggleTheme
```

Se usa `elements.js` (no `.json`) para evitar el problema CORS que impide `fetch()` desde `file://` en algunos navegadores. El archivo exporta `const ELEMENTS = [...]` usado directamente por `app.js`.

## Risks / Trade-offs

- **Datos manuales**: 118 elementos escritos a mano son propensos a errores. Mitigación: basarse en una fuente pública consolidada (Bowserinator/Periodic-Table-JSON) y hacer spot-checks de H, Fe, Au, Og.
- **fetch() y file://**: algunos navegadores bloquean fetch desde `file://`. Mitigación: usar `elements.js` con variable global en lugar de JSON + fetch.
- **Responsive de la tabla**: la tabla periódica es intrínsecamente ancha. Mitigación: scroll horizontal en móvil con `overflow-x: auto` en el contenedor.
- **CSS Grid IE11**: no soportado. Decisión: no soportar IE11 (cuota < 0.5%).
