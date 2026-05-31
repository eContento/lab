## Why

No existe un recurso visual interactivo propio para explorar los elementos químicos. Se quiere construir una aplicación web que presente la tabla periódica de forma clara, atractiva e interactiva, permitiendo a los usuarios consultar información detallada de cada elemento.

## What Changes

- Nueva aplicación web estática con la tabla periódica completa (118 elementos)
- Vista principal con cuadrícula de la tabla periódica con colores por categoría de elemento
- Panel de detalle por elemento: número atómico, símbolo, nombre, masa atómica, configuración electrónica, propiedades físicas y descripción
- Filtrado y búsqueda de elementos por nombre, símbolo o número atómico
- Resaltado visual por grupos/familias (metales alcalinos, halógenos, gases nobles, etc.)

## Capabilities

### New Capabilities

- `periodic-table-grid`: Cuadrícula visual de la tabla periódica con todos los elementos, coloreados por categoría y organizados en la disposición estándar (períodos y grupos)
- `element-detail`: Panel o modal con información detallada de un elemento al seleccionarlo
- `element-search`: Funcionalidad de búsqueda y filtrado de elementos por nombre, símbolo o número atómico
- `element-data`: Capa de datos con la información completa de los 118 elementos (fuente estática JSON o JS)

### Modified Capabilities

## Impact

- Proyecto nuevo: no hay código existente afectado
- Dependencias: HTML, CSS y JavaScript vanilla (o framework ligero como React/Vue si se decide en diseño)
- Sin backend ni base de datos; datos embebidos en el cliente
