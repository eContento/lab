## ADDED Requirements

### Requirement: Buscador en tiempo real
La aplicación SHALL proporcionar un campo de texto de búsqueda que filtre los elementos de la tabla en tiempo real mientras el usuario escribe. La búsqueda SHALL funcionar por nombre del elemento, símbolo químico y número atómico.

#### Scenario: Filtrado por nombre
- **WHEN** el usuario escribe parte del nombre de un elemento (ej. "Ox")
- **THEN** los elementos cuyo nombre contiene esa cadena (insensible a mayúsculas) se resaltan o mantienen visibles, y el resto se atenúa

#### Scenario: Filtrado por símbolo
- **WHEN** el usuario escribe el símbolo de un elemento (ej. "Fe")
- **THEN** el elemento correspondiente se resalta y el resto se atenúa

#### Scenario: Filtrado por número atómico
- **WHEN** el usuario escribe un número (ej. "26")
- **THEN** el elemento con ese número atómico se resalta y el resto se atenúa

#### Scenario: Búsqueda vacía muestra todos los elementos
- **WHEN** el campo de búsqueda está vacío o el usuario borra el texto
- **THEN** todos los elementos se muestran con su estilo normal sin atenuación

### Requirement: La tabla no se redimensiona durante la búsqueda
La estructura y tamaño de la tabla periódica SHALL permanecer invariables durante la búsqueda. Los elementos que no coinciden se atenúan visualmente pero no se eliminan del DOM ni cambian el layout.

#### Scenario: Layout estable al buscar
- **WHEN** el usuario escribe en el buscador y hay elementos que no coinciden
- **THEN** la tabla periódica mantiene exactamente el mismo tamaño y disposición que antes de la búsqueda; solo cambia la opacidad/estilo de las celdas no coincidentes
