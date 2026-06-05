## ADDED Requirements

### Requirement: Botón de alternancia modo claro/oscuro
La aplicación SHALL mostrar un botón en el header que permita al usuario alternar entre el tema claro y el tema oscuro. El botón SHALL indicar visualmente el tema activo (ej. ☀️ para modo claro activo, 🌙 para modo oscuro activo).

#### Scenario: Botón visible en el header
- **WHEN** la página se carga
- **THEN** se muestra un botón de toggle de tema en el header, visible en ambos modos

#### Scenario: Activar modo oscuro
- **WHEN** el usuario hace clic en el botón estando en modo claro
- **THEN** la página cambia al tema oscuro (fondo oscuro, texto claro, colores pastel adaptados) y el botón muestra el icono/texto correspondiente al modo oscuro activo

#### Scenario: Volver a modo claro
- **WHEN** el usuario hace clic en el botón estando en modo oscuro
- **THEN** la página vuelve al tema claro y el botón muestra el icono/texto correspondiente al modo claro activo

### Requirement: Persistencia de la preferencia de tema
La preferencia de tema seleccionada por el usuario SHALL persistirse en `localStorage` y SHALL restaurarse automáticamente en la siguiente visita.

#### Scenario: Preferencia guardada al cambiar
- **WHEN** el usuario alterna el tema
- **THEN** la preferencia se guarda en `localStorage` con la clave `theme`

#### Scenario: Preferencia restaurada al cargar
- **WHEN** el usuario vuelve a abrir la página y tiene una preferencia guardada
- **THEN** la página carga directamente con el tema guardado, sin parpadeo visible

### Requirement: Respeto de la preferencia del sistema operativo
Si el usuario no ha guardado ninguna preferencia explícita, la aplicación SHALL usar `prefers-color-scheme` del sistema operativo como tema inicial.

#### Scenario: Sistema en modo oscuro sin preferencia guardada
- **WHEN** la página se carga por primera vez y el SO tiene `prefers-color-scheme: dark`
- **THEN** la página se muestra en modo oscuro

#### Scenario: Sistema en modo claro sin preferencia guardada
- **WHEN** la página se carga por primera vez y el SO tiene `prefers-color-scheme: light` o no hay preferencia
- **THEN** la página se muestra en modo claro
