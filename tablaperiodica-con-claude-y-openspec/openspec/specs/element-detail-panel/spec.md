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
El panel de detalles SHALL ser invisible y no ocupar espacio en el layout cuando no hay ningún elemento seleccionado. La aplicación NO SHALL mostrar ningún estado placeholder, área vacía o mensaje de instrucción en el espacio del panel cuando no hay selección activa. Cuando el usuario selecciona un elemento, el panel SHALL aparecer y el layout se adapta para incluirlo. Cuando se cierra el panel, SHALL desaparecer completamente del layout y la tabla ocupará de nuevo el ancho completo disponible.

#### Scenario: Tabla estable al abrir el panel
- **WHEN** el usuario selecciona un elemento y el panel de detalles aparece
- **THEN** la tabla periódica no se desplaza ni se reescala internamente; el layout añade la columna del panel junto a la tabla

#### Scenario: Panel ausente al inicio
- **WHEN** la página se carga por primera vez sin ningún elemento seleccionado
- **THEN** el panel de detalles no es visible ni ocupa espacio en el layout; la tabla periódica ocupa todo el ancho disponible

#### Scenario: Panel ausente tras cerrar
- **WHEN** el usuario cierra el panel de detalles (botón ✕)
- **THEN** el panel desaparece del layout, la tabla periódica vuelve a ocupar todo el ancho disponible y no se muestra ningún placeholder ni área vacía

### Requirement: Indicador visual del elemento seleccionado
El elemento actualmente seleccionado SHALL tener un indicador visual diferenciado (borde, sombra o resaltado) que persiste mientras el panel de detalles está abierto.

#### Scenario: Resaltado del elemento activo
- **WHEN** el usuario hace clic en un elemento
- **THEN** la celda de ese elemento muestra un indicador visual de selección (ej. borde destacado) y el panel de detalles está visible

#### Scenario: Limpiar selección
- **WHEN** el usuario cierra el panel de detalles
- **THEN** ningún elemento aparece con el indicador de selección activa
