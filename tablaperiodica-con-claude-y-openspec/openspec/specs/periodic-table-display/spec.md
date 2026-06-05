## ADDED Requirements

### Requirement: Tabla periódica completa con disposición estándar
La aplicación SHALL mostrar los 118 elementos de la tabla periódica en la disposición estándar IUPAC de 18 columnas × 7 periodos (bloques s, p, d). Cada celda SHALL mostrar el número atómico, símbolo y nombre del elemento.

#### Scenario: Todos los elementos visibles al cargar
- **WHEN** el usuario abre el fichero index.html en el navegador
- **THEN** se muestran los 118 elementos correctamente posicionados en la tabla

#### Scenario: Información mínima en cada celda
- **WHEN** la tabla está renderizada
- **THEN** cada celda de elemento muestra número atómico, símbolo y nombre

### Requirement: Colores pastel por categoría
Cada elemento SHALL tener un color de fondo pastel que corresponda a su categoría química. Las categorías SHALL incluir al menos: metal alcalino, metal alcalino-térreo, metal de transición, otros metales, metaloide, no metal, halógeno, gas noble, lantánido, actínido.

#### Scenario: Color diferenciado por categoría
- **WHEN** la tabla está renderizada
- **THEN** todos los elementos de la misma categoría tienen el mismo color de fondo pastel

#### Scenario: Fondo claro general
- **WHEN** la página se carga
- **THEN** el fondo de la página es de color claro (blanco o gris muy claro) y los colores de los elementos son tonos pastel distinguibles

### Requirement: Lantánidos y actínidos separados visualmente
Los lantánidos (Z=57-71) y actínidos (Z=89-103) SHALL renderizarse en un bloque independiente debajo de la tabla principal. SHALL existir al menos una fila de separación visual (espacio equivalente a la altura de una celda) entre la tabla principal y el bloque de lantánidos/actínidos.

#### Scenario: Bloque f separado de la tabla principal
- **WHEN** la tabla está renderizada
- **THEN** los lantánidos y actínidos aparecen en un bloque separado con al menos el espacio de una fila entre la tabla principal y dicho bloque

#### Scenario: Indicadores en la tabla principal
- **WHEN** la tabla está renderizada
- **THEN** las posiciones de los lantánidos (fila 6, grupos 3+) y actínidos (fila 7, grupos 3+) en la tabla principal muestran una celda especial de referencia ("La-Lu" / "Ac-Lr") que indica dónde encajan en la estructura

### Requirement: Leyenda de categorías
La aplicación SHALL mostrar una leyenda que relacione cada color pastel con su categoría de elemento.

#### Scenario: Leyenda visible
- **WHEN** la página está cargada
- **THEN** se muestra una leyenda con el color y nombre de cada categoría de elemento
