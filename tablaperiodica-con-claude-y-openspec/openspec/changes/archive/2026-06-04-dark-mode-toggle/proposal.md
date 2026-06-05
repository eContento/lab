## Why

La aplicación solo ofrece un tema claro. Añadir un modo oscuro mejora la comodidad visual en condiciones de poca luz y es una funcionalidad estándar esperada en aplicaciones web modernas.

## What Changes

- Se añade un botón de alternancia (toggle) en el header que permite cambiar entre modo claro y modo oscuro
- El modo oscuro redefine los colores de fondo, texto, celdas y panel manteniendo la paleta pastel pero con tonos adaptados al fondo oscuro
- La preferencia del usuario se persiste en `localStorage` para que se recuerde entre visitas
- Se respeta la preferencia del sistema operativo (`prefers-color-scheme`) como valor inicial si no hay preferencia guardada

## Capabilities

### New Capabilities

- `dark-mode-toggle`: Botón en el header que alterna entre tema claro y oscuro; persiste la preferencia en localStorage; respeta `prefers-color-scheme` como valor por defecto

### Modified Capabilities

## Impact

- `index.html`: añadir el botón toggle en el header
- `styles.css`: añadir variables CSS para el tema oscuro bajo el selector `[data-theme="dark"]` o clase `.dark`
- `app.js`: lógica de toggle, lectura/escritura de `localStorage` y detección de `prefers-color-scheme`
