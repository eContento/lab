## 1. HTML — Eliminar el placeholder del panel

- [x] 1.1 En `index.html`, eliminar el bloque `<div id="panel-placeholder">` y su contenido

## 2. CSS — Panel oculto por defecto, visible con clase `.panel-open`

- [x] 2.1 En `styles.css`, cambiar el layout por defecto de `.page-layout` a una sola columna (`grid-template-columns: 1fr`)
- [x] 2.2 Añadir la regla `.page-layout.panel-open` que activa las dos columnas (`minmax(0, 1fr) var(--panel-w)`)
- [x] 2.3 En `.detail-panel`, añadir `display: none` por defecto
- [x] 2.4 Añadir `.page-layout.panel-open .detail-panel { display: block }` para mostrarlo cuando el panel está activo

## 3. JS — Gestión dinámica de la clase `.panel-open`

- [x] 3.1 En `selectElement()`, añadir la clase `panel-open` al elemento `.page-layout` al abrir el panel
- [x] 3.2 En `closePanel()`, quitar la clase `panel-open` del elemento `.page-layout` al cerrar el panel

## 4. Verificación

- [x] 4.1 Abrir `index.html`: comprobar que no se muestra ningún panel ni área vacía en la carga inicial
- [x] 4.2 Hacer clic en un elemento: comprobar que el panel aparece con los detalles correctos
- [x] 4.3 Cerrar el panel (botón ✕): comprobar que desaparece y la tabla ocupa el ancho completo
