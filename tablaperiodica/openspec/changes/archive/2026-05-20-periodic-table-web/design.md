## Context

Proyecto web nuevo desde cero. No hay base de código existente. El objetivo es una aplicación cliente puro (sin backend) que muestre la tabla periódica de los 118 elementos con interactividad básica: selección, detalle y búsqueda.

Stakeholders: usuario final (estudiante/curioso), desarrollador único.

## Goals / Non-Goals

**Goals:**
- Renderizar la tabla periódica completa en disposición estándar (18 grupos × 7 períodos + lantánidos/actínidos)
- Mostrar detalle de cada elemento al hacer clic
- Permitir búsqueda por nombre, símbolo o número atómico
- Colorear elementos por categoría (metales alcalinos, metales de transición, halógenos, gases nobles, etc.)
- Sin dependencias de servidor; funciona como archivo estático

**Non-Goals:**
- Visualizaciones 3D o animaciones de electrones
- Comparación multi-elemento simultánea
- Internacionalización (i18n)
- Modo oscuro/claro configurable (puede añadirse después)
- Accesibilidad avanzada (WCAG AA) en esta versión

## Decisions

### Stack: HTML + CSS + JavaScript vanilla (sin framework)

La aplicación es una única página estática sin necesidad de estado complejo ni routing. Vanilla JS elimina tiempo de build, dependencias y complejidad de tooling.

Alternativas consideradas:
- **React**: añade complejidad de setup (bundler, JSX) sin beneficio claro para un proyecto de una sola vista
- **Vue CDN**: viable, pero las reactividades simples que se necesitan (selección de elemento, filtro) se resuelven igual de bien con DOM directo

### Datos: JSON estático embebido en JS

Un archivo `elements.js` exporta un array con los 118 elementos. Los datos incluyen: número atómico, símbolo, nombre, masa atómica, categoría, grupo, período, configuración electrónica, estado a temperatura ambiente, punto de fusión/ebullición y descripción corta.

Fuente de referencia: datos basados en IUPAC 2021.

Alternativas consideradas:
- **Fetch a JSON externo**: añade latencia y falla en modo offline
- **Base de datos SQLite vía WASM**: sobreingeniería para 118 registros estáticos

### Layout de la tabla: CSS Grid

La cuadrícula se implementa con CSS Grid usando `grid-column` y `grid-row` explícitos por elemento. Esto permite posicionar exactamente cada elemento en su celda estándar (período/grupo) sin JavaScript de layout.

Los lantánidos y actínidos se ubican en filas separadas debajo de la tabla principal (filas 8-9), siguiendo la convención más extendida.

### Panel de detalle: sidebar fijo + overlay en móvil

En escritorio: panel lateral derecho que se muestra al seleccionar un elemento.
En móvil: bottom sheet / modal superpuesto.
El estado de selección se gestiona con una variable JS `selectedElement` y re-renderizado parcial del DOM del panel.

## Risks / Trade-offs

- **Mantenimiento de datos**: los datos de los 118 elementos se escriben a mano o se obtienen de una fuente pública. Error → Mitigación: usar una fuente consolidada (e.g., Bowserinator/Periodic-Table-JSON en GitHub) y documentar la versión usada.
- **CSS Grid en IE11**: no hay soporte. Mitigación: no soportar IE11 (mercado < 0.5%).
- **Accesibilidad básica**: la navegación por teclado no está en scope. Mitigación: añadir `tabindex` y roles ARIA mínimos en iteración futura.
