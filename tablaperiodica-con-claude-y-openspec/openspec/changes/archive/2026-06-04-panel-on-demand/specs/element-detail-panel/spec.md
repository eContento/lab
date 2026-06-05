## MODIFIED Requirements

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
