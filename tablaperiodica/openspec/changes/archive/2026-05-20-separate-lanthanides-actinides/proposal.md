## Why

Las filas de lantánidos y actínidos aparecen inmediatamente pegadas a la tabla principal (fila 7), sin separación visual. Esto hace difícil distinguir a simple vista que son series especiales que "flotan" fuera de la tabla principal. Añadir un espacio en blanco entre la fila 7 y los lantánidos/actínidos mejora la legibilidad y sigue la convención habitual en tablas periódicas impresas y digitales.

## What Changes

- Insertar una fila vacía de separación entre la tabla principal (período 7) y las filas de lantánidos/actínidos en el CSS Grid.
- Ajustar los números de fila en `app.js` para que lantánidos queden en fila 9 y actínidos en fila 10 (actualmente 8 y 9).
- Actualizar `grid-template-rows` en CSS para incluir la fila de separación con altura reducida (≈ 20 px).

## Capabilities

### New Capabilities

### Modified Capabilities

- `periodic-table-grid`: cambia el requisito de separación visual entre la tabla principal y las series de lantánidos/actínidos.

## Impact

- Archivos afectados: `styles.css` (grid-template-rows) y `app.js` (números de fila de lantánidos y actínidos)
- Sin cambios en HTML, datos ni en el resto de la lógica
