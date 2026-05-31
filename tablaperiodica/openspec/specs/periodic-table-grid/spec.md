## ADDED Requirements

### Requirement: Mostrar todos los elementos en cuadrícula estándar
La aplicación SHALL renderizar los 118 elementos químicos en la disposición estándar de la tabla periódica: 18 grupos (columnas) × 7 períodos (filas), con lantánidos y actínidos en filas separadas debajo de la tabla principal, con una fila vacía de separación visual entre la tabla principal y las series de lantánidos/actínidos.

#### Scenario: Carga inicial de la tabla
- **WHEN** el usuario abre la aplicación
- **THEN** todos los 118 elementos se muestran en sus posiciones correctas (período y grupo)

#### Scenario: Posición correcta de lantánidos y actínidos
- **WHEN** el usuario visualiza la tabla
- **THEN** los lantánidos (Z=57–71) aparecen en la fila 9 del grid y los actínidos (Z=89–103) en la fila 10, con una fila vacía de separación (fila 8) entre la tabla principal y estas series

#### Scenario: Separación visual entre tabla principal y series especiales
- **WHEN** el usuario visualiza la tabla
- **THEN** existe un espacio en blanco visible (mínimo 16 px) entre la última fila de la tabla principal (período 7) y la fila de lantánidos

### Requirement: Colorear elementos por categoría
Cada celda de elemento SHALL tener un color de fondo distintivo según su categoría química (metal alcalino, metal alcalino-térreo, metal de transición, metal post-transición, metaloide, no metal, halógeno, gas noble, lantánido, actínido).

#### Scenario: Distinción visual de categorías
- **WHEN** el usuario visualiza la tabla
- **THEN** cada categoría tiene un color de fondo diferente y existe una leyenda visible que mapea color → nombre de categoría

### Requirement: Mostrar información básica en cada celda
Cada celda SHALL mostrar: número atómico, símbolo del elemento y nombre del elemento.

#### Scenario: Contenido mínimo de celda
- **WHEN** el usuario visualiza cualquier celda de la tabla
- **THEN** la celda muestra el número atómico (arriba), el símbolo (centro, tipografía grande) y el nombre (abajo, tipografía pequeña)

### Requirement: Resaltar elemento al pasar el cursor
La tabla SHALL proporcionar retroalimentación visual al hacer hover sobre una celda.

#### Scenario: Hover sobre celda
- **WHEN** el usuario mueve el cursor sobre una celda de elemento
- **THEN** la celda muestra un efecto visual de resaltado (borde o cambio de brillo) diferente al estado normal
