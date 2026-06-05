## Why

Se necesita una página web interactiva de la tabla periódica que funcione completamente offline (sin servidor), con una experiencia visual atractiva y funcionalidades de búsqueda y detalle de elementos. No existe actualmente ninguna implementación en el proyecto.

## What Changes

- Creación de una aplicación web estática (HTML + CSS3 + Vanilla JS) que muestra la tabla periódica completa
- Diseño visual con fondo claro y colores pastel diferenciados por categoría de elemento
- Buscador en tiempo real para filtrar elementos por nombre, símbolo o número atómico
- Panel de detalles que se muestra al seleccionar un elemento sin alterar el tamaño de la tabla
- Lantánidos y actínidos ubicados en una sección separada de la tabla principal con al menos una fila de separación visual

## Capabilities

### New Capabilities

- `periodic-table-display`: Renderizado de la tabla periódica completa con disposición estándar, colores pastel por categoría y lantánidos/actínidos separados por al menos una fila
- `element-search`: Buscador en tiempo real que filtra elementos por nombre, símbolo o número atómico, resaltando los resultados en la tabla
- `element-detail-panel`: Panel de detalles del elemento seleccionado que muestra propiedades completas (masa atómica, configuración electrónica, categoría, etc.) sin redimensionar la tabla

### Modified Capabilities

## Impact

- Archivos nuevos: `index.html`, `styles.css`, `app.js` (o equivalente estructura de ficheros estáticos)
- Sin dependencias externas: todo funciona con HTML5, CSS3 y JavaScript estándar
- Compatible con navegadores modernos sin necesidad de build tools ni servidor web
