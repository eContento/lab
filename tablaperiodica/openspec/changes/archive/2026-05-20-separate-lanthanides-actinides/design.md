## Context

El CSS Grid de la tabla periódica usa `grid-template-rows: repeat(9, 64px)`, lo que da 9 filas de igual altura. Las filas 8 y 9 (lantánidos y actínidos) quedan visualmente pegadas a la fila 7 (último período de la tabla principal). No hay ninguna fila de separación.

## Goals / Non-Goals

**Goals:**
- Insertar un espacio en blanco visible entre la tabla principal y las filas de lantánidos/actínidos.

**Non-Goals:**
- Cambiar el diseño general ni los tamaños de celda.
- Añadir etiquetas o líneas de separación (solo espacio vacío).

## Decisions

### Mecanismo de separación: fila implícita de altura reducida en CSS Grid

Cambiar `grid-template-rows` de `repeat(9, 64px)` a `repeat(7, 64px) 20px repeat(2, 64px)`.

Esto convierte la fila 8 del grid en una fila de 20 px vacía (separador), desplazando lantánidos a la fila 9 y actínidos a la fila 10. No requiere ningún elemento DOM adicional.

Alternativas consideradas:
- **`margin-top` en cada celda lantánido/actínido**: requiere modificar el JS para añadir una clase o estilo a cada celda; más frágil y verboso.
- **Elemento DOM separador vacío**: funciona, pero añade nodos innecesarios al markup solo para cubrir algo que CSS Grid resuelve directamente.
- **`row-gap` entre filas**: el `gap` de CSS Grid es uniforme entre todas las filas; no permite un gap solo entre fila 7 y fila 8 sin afectar el resto.

La opción elegida (fila implícita en `grid-template-rows`) es la más limpia: un único cambio en CSS y dos números en JS.

## Risks / Trade-offs

- **Desplazamiento de números de fila**: los lantánidos pasan de fila 8 → 9 y los actínidos de fila 9 → 10 en `app.js`. Hay que actualizar las dos asignaciones `cell.style.gridRow`. Riesgo bajo; es un cambio puntual y verificable.
