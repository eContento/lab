## ADDED Requirements

### Requirement: Panel de detalles al seleccionar un elemento
Al hacer clic en un elemento de la tabla, la aplicación SHALL mostrar un panel de detalles con información completa del elemento. El panel SHALL incluir: nombre, símbolo, número atómico, masa atómica, categoría, periodo, grupo, configuración electrónica, electronegatividad (si disponible) y estado físico a temperatura ambiente.

#### Scenario: Mostrar detalles al hacer clic
- **WHEN** el usuario hace clic en la celda de un elemento
- **THEN** el panel de detalles muestra la información completa de ese elemento

#### Scenario: Contenido mínimo del panel
- **WHEN** el panel de detalles está visible
- **THEN** muestra al menos: nombre, símbolo, número atómico, masa atómica, categoría y configuración electrónica

### Requirement: La tabla periódica no se redimensiona al mostrar el panel
Cuando el panel de detalles se muestra o se oculta, el tamaño y disposición de la tabla periódica SHALL permanecer invariables. El panel SHALL ocupar espacio reservado (sidebar fijo) o solaparse sin desplazar la tabla.

#### Scenario: Tabla estable al abrir el panel
- **WHEN** el usuario selecciona un elemento y el panel de detalles aparece
- **THEN** la tabla periódica no cambia de tamaño, no se desplaza ni se reescala

#### Scenario: Tabla estable al cerrar el panel
- **WHEN** el usuario cierra el panel de detalles (haciendo clic fuera o en botón de cierre)
- **THEN** la tabla periódica no cambia de tamaño ni de posición

### Requirement: Indicador visual del elemento seleccionado
El elemento actualmente seleccionado SHALL tener un indicador visual diferenciado (borde, sombra o resaltado) que persiste mientras el panel de detalles está abierto.

#### Scenario: Resaltado del elemento activo
- **WHEN** el usuario hace clic en un elemento
- **THEN** la celda de ese elemento muestra un indicador visual de selección (ej. borde destacado) y el panel de detalles está visible

#### Scenario: Limpiar selección
- **WHEN** el usuario cierra el panel de detalles
- **THEN** ningún elemento aparece con el indicador de selección activa
