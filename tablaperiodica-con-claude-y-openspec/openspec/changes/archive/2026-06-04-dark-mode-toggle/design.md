## Context

La aplicación usa CSS custom properties (variables) en `:root` para colores. Esto hace trivial añadir un tema oscuro: basta con redefinir las mismas variables bajo un selector alternativo y aplicarlo al elemento `<html>` o `<body>`. No hay framework, ni bundler, ni dependencias externas.

## Goals / Non-Goals

**Goals:**
- Botón visible en el header que alterna entre ☀️ (claro) y 🌙 (oscuro)
- Modo oscuro: fondo oscuro, texto claro, colores pastel más oscuros/saturados para mantener la legibilidad
- Persistencia en `localStorage` (clave `theme`)
- Al cargar, leer `localStorage`; si no hay valor, usar `prefers-color-scheme`

**Non-Goals:**
- Transiciones animadas de cambio de tema (puede añadirse después con `transition: background-color`)
- Temas adicionales (solo claro/oscuro)
- Sincronización entre pestañas

## Decisions

### CSS custom properties sobre selector `html[data-theme="dark"]`

Se añade el atributo `data-theme="dark"` al elemento `<html>` cuando el modo oscuro está activo. Las variables CSS se redefinen bajo ese selector.

**Ventaja:** Sin JavaScript para toggle de clases específicas; un solo `setAttribute`/`removeAttribute`. El selector es más específico y no interfiere con clases de componentes.

**Alternativa descartada:** clase `.dark` en `<body>` — funciona igual de bien, pero el atributo `data-theme` es más semántico y permite valores futuros ("high-contrast", etc.).

### Colores del modo oscuro

Las variables de fondo pasan a tonos oscuros (`#1a1a2e`, `#16213e`) y los colores pastel de categorías se ajustan: se mantiene la misma hue pero con menor luminosidad y mayor saturación para que sigan siendo distinguibles sobre fondo oscuro.

### Botón con emoji + texto

Botón simple con `☀️ Claro` / `🌙 Oscuro` sin librería de iconos. Accesible con `aria-label` apropiado.

## Risks / Trade-offs

- [Flash of unstyled content (FOUC) al cargar] → Mitigación: aplicar el tema en un `<script>` inline en el `<head>` antes de que el DOM se pinte, leyendo `localStorage` sincrónicamente.
- [Colores pastel sobre fondo oscuro pueden perder legibilidad] → Mitigación: usar versiones más oscuras de los pastel con mayor contraste de texto.
