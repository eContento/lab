## 1. HTML — Botón toggle y script anti-FOUC

- [x] 1.1 Añadir en `<head>` un `<script>` inline que aplica el tema antes del primer render (lee `localStorage` y `prefers-color-scheme`)
- [x] 1.2 Añadir el botón `<button id="theme-toggle">` en el header (`.site-header`), con `aria-label` y texto inicial

## 2. CSS — Variables para el tema oscuro

- [x] 2.1 Añadir el bloque `html[data-theme="dark"]` en `styles.css` con las variables de fondo y texto oscuros
- [x] 2.2 Definir los colores pastel adaptados al modo oscuro (versiones más oscuras/saturadas de cada categoría) dentro del mismo bloque
- [x] 2.3 Añadir estilos del botón toggle (aspecto coherente con el header en ambos modos)

## 3. JS — Lógica de toggle y persistencia

- [x] 3.1 En `app.js`, añadir la función `initTheme()` que lee `localStorage` o `prefers-color-scheme` y aplica el atributo `data-theme` al elemento `<html>`
- [x] 3.2 Añadir la función `toggleTheme()` que alterna `data-theme`, actualiza `localStorage` y actualiza el texto/icono del botón
- [x] 3.3 Llamar a `initTheme()` al inicio de `init()` y conectar el evento `click` del botón a `toggleTheme()`

## 4. Verificación

- [x] 4.1 Comprobar que al cargar la página sin preferencia guardada se respeta `prefers-color-scheme`
- [x] 4.2 Hacer clic en el botón: verificar que la página cambia de tema y el botón actualiza su icono
- [x] 4.3 Recargar la página: verificar que el tema persiste sin parpadeo
- [x] 4.4 Comprobar legibilidad de los colores pastel sobre el fondo oscuro
