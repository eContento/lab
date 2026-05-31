## ADDED Requirements

### Requirement: Búsqueda de elementos por nombre, símbolo o número atómico
La aplicación SHALL proporcionar un campo de búsqueda que filtre los elementos visibles en la tabla en tiempo real según el texto introducido, comparando contra nombre, símbolo y número atómico.

#### Scenario: Búsqueda por nombre parcial
- **WHEN** el usuario escribe "hid" en el campo de búsqueda
- **THEN** solo el elemento "Hidrógeno" (o elementos cuyo nombre contenga "hid") aparece resaltado o los demás se atenúan

#### Scenario: Búsqueda por símbolo
- **WHEN** el usuario escribe "Fe" en el campo de búsqueda
- **THEN** solo el elemento Hierro (Fe) aparece resaltado/activo en la tabla

#### Scenario: Búsqueda por número atómico
- **WHEN** el usuario escribe "79" en el campo de búsqueda
- **THEN** solo el elemento con número atómico 79 (Oro) aparece resaltado

#### Scenario: Sin resultados
- **WHEN** el usuario escribe texto que no coincide con ningún elemento
- **THEN** todos los elementos se atenúan y se muestra un mensaje "No se encontraron elementos"

### Requirement: Limpiar búsqueda restaura la tabla completa
Al borrar el texto del campo de búsqueda o hacer clic en un botón de limpiar, la tabla SHALL volver a su estado normal con todos los elementos visibles sin atenuado.

#### Scenario: Campo de búsqueda vacío
- **WHEN** el usuario borra todo el texto del campo de búsqueda
- **THEN** todos los elementos vuelven a su estado visual normal (sin atenuado ni resaltado de búsqueda)

### Requirement: La búsqueda es insensible a mayúsculas/minúsculas
La comparación de búsqueda SHALL realizarse sin distinguir entre mayúsculas y minúsculas.

#### Scenario: Búsqueda en mayúsculas
- **WHEN** el usuario escribe "ORO" en el campo de búsqueda
- **THEN** se encuentra y resalta el elemento Oro igual que si hubiera escrito "oro" u "Oro"
