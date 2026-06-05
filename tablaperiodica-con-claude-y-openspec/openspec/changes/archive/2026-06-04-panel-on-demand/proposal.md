## Why

El panel de detalles muestra un estado vacío con el mensaje "Haz clic en un elemento para ver sus propiedades" cuando no hay ningún elemento seleccionado, ocupando espacio de forma innecesaria y resultando visualmente redundante. El usuario prefiere que el panel no exista visualmente hasta que se seleccione un elemento.

## What Changes

- El panel de detalles se oculta completamente (no ocupa espacio en el layout) cuando no hay ningún elemento seleccionado
- El panel aparece al hacer clic en un elemento, mostrando directamente sus propiedades
- Se elimina el estado placeholder ("Haz clic en un elemento…")
- Al cerrar el panel (botón ✕), el panel desaparece del layout y la tabla periódica recupera el espacio

## Capabilities

### New Capabilities

### Modified Capabilities

- `element-detail-panel`: El panel ya no se muestra por defecto en el layout. Ahora es un elemento que aparece y desaparece dinámicamente al seleccionar/deseleccionar elementos; la tabla periódica se adapta al espacio disponible.

## Impact

- `index.html`: se elimina o simplifica el markup del panel placeholder
- `styles.css`: el panel pasa de visibilidad oculta con espacio reservado a `display: none` / aparición dinámica
- `app.js`: ajustes en `selectElement()` y `closePanel()` para mostrar/ocultar el panel cambiando el layout
