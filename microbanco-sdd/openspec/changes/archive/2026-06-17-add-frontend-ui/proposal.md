## Why

El microservicio actual solo expone APIs REST, sin interfaz gráfica. Para validar funcionalidad en demo y desarrollo, necesitamos una UI web sencilla que se sirva desde el propio Quarkus, sin servidores adicionales ni autenticación.

## What Changes

- Añadir frontend web estático (HTML + CSS + JS) servido desde `src/main/resources/META-INF/resources/`
- Dos pantallas: lista global de cuentas (posición global) y detalle de cuenta con saldo y transferencias
- Llamadas directas a las APIs REST existentes (sin capa adicional de backend)
- Sin autenticación, sin build tooling frontend, sin dependencias npm
- Diseño sobrio, elegante, colores suaves (grises, azules claros, blancos)

## Capabilities

### New Capabilities
- `web-ui`: Interfaz web de usuario para operaciones bancarias básicas (listar cuentas, crear cuentas, ver saldo, hacer transferencias)

### Modified Capabilities
- *(ninguna — las APIs existentes no cambian)*

## Impact

- **Nuevo directorio**: `src/main/resources/META-INF/resources/` con los assets estáticos
- **Sin cambios** en código Java existente, APIs, dependencias, o configuración
- Sin nuevas dependencias Maven/Gradle
- Sin impacto en tests existentes
