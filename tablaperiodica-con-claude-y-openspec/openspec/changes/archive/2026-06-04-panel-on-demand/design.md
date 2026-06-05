## Context

El panel de detalles actual ocupa una columna fija en el layout (300px) incluso cuando no hay elemento seleccionado. En ese estado vacío muestra un mensaje placeholder que el usuario considera visualmente innecesario. El cambio busca que el panel sea un elemento que surge solo cuando se necesita.

## Goals / Non-Goals

**Goals:**
- El panel de detalles no ocupa espacio ni es visible cuando no hay ningún elemento seleccionado
- No hay texto placeholder ni área vacía en el lado derecho de la tabla
- El panel aparece al hacer clic en un elemento, mostrando sus datos directamente
- Al cerrar el panel, desaparece completamente y la tabla recupera el ancho completo

**Non-Goals:**
- Cambiar el contenido o el estilo visual del panel cuando está abierto
- Introducir animaciones de entrada/salida (puede añadirse después)
- Cambiar el comportamiento de búsqueda, categorías o leyenda

## Decisions

### El panel se controla mediante una clase CSS en el contenedor padre

Se añade/quita la clase `panel-open` al `.page-layout`. Cuando no está presente, el grid usa solo `1fr` (tabla a ancho completo). Cuando está presente, cambia a `minmax(0, 1fr) var(--panel-w)` (tabla + panel).

**Alternativa descartada:** `display: none` directamente en el panel — no cambia el grid del padre, así que el espacio de la columna sigue reservado.

**Alternativa descartada:** Overlay/fixed position — cubre parte de la tabla, menos limpio.

### Se elimina el markup del placeholder

El bloque `<div id="panel-placeholder">` se elimina del HTML. El panel solo contiene el `panel-content` (oculto por defecto con `hidden`), que se muestra al seleccionar un elemento.

### La tabla periódica PUEDE ajustar su ancho al abrir/cerrar el panel

Dado que la columna del panel desaparece del layout al cerrarse, la tabla se expande. Este comportamiento es intencional y respeta la petición del usuario: el panel no existe hasta que se necesita.

## Risks / Trade-offs

- [Pequeño salto de layout al abrir/cerrar el panel] → Aceptable; puede mitigarse con una transición CSS en la propiedad `grid-template-columns` si se desea en el futuro.
- [Si el usuario tiene un elemento seleccionado y hace scroll, el panel sticky puede quedar fuera de vista en pantallas pequeñas] → Sin cambio respecto al estado actual; no es un riesgo nuevo.
