## 1. Estructura base del proyecto

- [x] 1.1 Crear `index.html` con estructura semántica: `<header>` (buscador), `<main>` (tabla + panel), `<footer>` (leyenda)
- [x] 1.2 Crear `styles.css` con reset básico, variables CSS para colores pastel de cada categoría y fondo claro
- [x] 1.3 Crear `data/elements.js` como módulo ES6 con array de los 118 elementos (número atómico, símbolo, nombre, masa, categoría, grupo, periodo, config. electrónica, electronegatividad, estado físico)
- [x] 1.4 Crear `app.js` como módulo ES6 principal que importa `data/elements.js`

## 2. Layout de la tabla periódica

- [x] 2.1 Implementar el grid CSS de 18 columnas para la tabla principal (periodos 1-7)
- [x] 2.2 Posicionar cada elemento en su celda correcta usando `grid-column` y `grid-row` desde los datos (grupo y periodo)
- [x] 2.3 Añadir celdas vacías/placeholder para los huecos estándar de la tabla (p.ej. periodo 1 col 2-17)
- [x] 2.4 Añadir celdas especiales "La-Lu" (fila 6, col 3) y "Ac-Lr" (fila 7, col 3) como indicadores del bloque f
- [x] 2.5 Crear el bloque separado de lantánidos y actínidos con grid de 15 columnas, separado de la tabla principal por al menos el espacio de una fila (`margin-top` o fila vacía)

## 3. Estilo visual de las celdas

- [x] 3.1 Aplicar color de fondo pastel a cada celda según su categoría usando clases CSS y variables custom properties
- [x] 3.2 Estilizar la celda con número atómico (pequeño, arriba), símbolo (grande, centrado) y nombre (pequeño, abajo)
- [x] 3.3 Implementar efecto hover en celdas (ligera elevación o cambio de tono)
- [x] 3.4 Añadir leyenda de categorías con cuadro de color y nombre de categoría

## 4. Panel de detalles

- [x] 4.1 Añadir al layout principal una columna fija reservada para el panel de detalles (no altera el tamaño de la tabla al aparecer/desaparecer)
- [x] 4.2 Implementar el panel con todos los campos: nombre, símbolo, número atómico, masa atómica, categoría, periodo, grupo, configuración electrónica, electronegatividad, estado físico
- [x] 4.3 Implementar el evento click en las celdas de elementos para poblar y mostrar el panel de detalles
- [x] 4.4 Añadir indicador visual de selección en la celda activa (borde o sombra destacada)
- [x] 4.5 Implementar cierre del panel (botón cerrar o clic en zona neutral) y limpieza del indicador de selección

## 5. Buscador en tiempo real

- [x] 5.1 Añadir `<input type="search">` en el header con placeholder descriptivo
- [x] 5.2 Implementar listener `input` que filtra elementos por nombre, símbolo y número atómico (insensible a mayúsculas)
- [x] 5.3 Aplicar clase CSS `dimmed` a celdas no coincidentes (reducción de opacidad) sin modificar el DOM ni el layout
- [x] 5.4 Restaurar estilos normales cuando el campo de búsqueda se vacía

## 6. Datos de los 118 elementos

- [x] 6.1 Completar el array en `data/elements.js` con los elementos del periodo 1 al 3 (H, He, Li, Be, B, C, N, O, F, Ne, Na, Mg, Al, Si, P, S, Cl, Ar)
- [x] 6.2 Completar los elementos del periodo 4 (K a Kr, incluyendo metales de transición Z=21-30)
- [x] 6.3 Completar los elementos del periodo 5 (Rb a Xe, incluyendo Z=39-48)
- [x] 6.4 Completar los elementos del periodo 6 (Cs, Ba, lantánidos Z=57-71, Hf-Rn)
- [x] 6.5 Completar los elementos del periodo 7 (Fr, Ra, actínidos Z=89-103, Rf-Og)

## 7. Verificación y ajustes finales

- [x] 7.1 Verificar que abriendo `index.html` directamente con file:// en Chrome y Firefox muestra la tabla completa sin errores de consola
- [x] 7.2 Comprobar que el panel de detalles no desplaza ni redimensiona la tabla al abrirse/cerrarse
- [x] 7.3 Comprobar que el buscador funciona para nombre, símbolo y número atómico, y que el layout no cambia al buscar
- [x] 7.4 Verificar la separación visual de lantánidos/actínidos (al menos una fila de espacio)
- [x] 7.5 Revisar legibilidad y contraste de colores pastel en todos los elementos de la tabla
