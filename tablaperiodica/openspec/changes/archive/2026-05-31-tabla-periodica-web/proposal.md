## Why

Se necesita una herramienta visual interactiva para explorar la tabla periódica directamente desde el navegador, sin necesidad de servidor web ni conexión a internet. La aplicación debe ser autocontenida, atractiva y funcional para estudiantes o cualquier persona interesada en química.

## What Changes

- Nueva aplicación web estática con la tabla periódica completa (118 elementos) en HTML5, CSS3 y JavaScript vanilla
- Vista principal con cuadrícula de la tabla periódica coloreada por categoría de elemento
- Lantánidos y actínidos separados de la tabla principal por una fila vacía
- Panel lateral de detalle por elemento: nombre, símbolo, número atómico, masa atómica, configuración electrónica, estado estándar, puntos de fusión/ebullición y descripción
- Buscador en tiempo real que resalta coincidencias por nombre, símbolo o número atómico, oscureciendo los no coincidentes
- Modo claro y modo oscuro conmutable por el usuario
- Datos de los 118 elementos en un archivo JSON estático

## Capabilities

### New Capabilities

- `periodic-table-grid`: Cuadrícula CSS Grid de la tabla periódica con posicionamiento explícito por período/grupo, colores por categoría y leyenda, con lantánidos/actínidos en filas separadas
- `element-detail`: Panel lateral con información completa del elemento seleccionado, cierre con botón o tecla Escape
- `element-search`: Buscador en tiempo real con resaltado de coincidencias y atenuado de no coincidentes
- `element-data`: Archivo JSON estático con los 118 elementos y todos sus campos relevantes
- `dark-light-mode`: Alternancia de tema claro/oscuro persistida en localStorage, con botón de toggle

### Modified Capabilities

## Impact

- Proyecto nuevo: no hay código existente afectado
- Sin dependencias externas ni de build; funciona abriendo `index.html` directamente en el navegador
- Sin backend ni base de datos
