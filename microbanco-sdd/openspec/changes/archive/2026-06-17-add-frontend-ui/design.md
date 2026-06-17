## Context

Microservicio Quarkus que sirve APIs REST para cuentas bancarias y transferencias. No existe interfaz gráfica. Quarkus sirve contenido estático desde `src/main/resources/META-INF/resources/` sin configuración adicional.

## Goals / Non-Goals

**Goals:**
- UI web servida directamente por Quarkus (mismo puerto, mismo proceso)
- Dos pantallas: lista global de cuentas y detalle de cuenta con transferencias
- Llamadas directas a APIs REST existentes mediante `fetch()` desde el navegador
- Sin autenticación, sin builds adicionales, sin dependencias npm/node
- Diseño sobrio, elegante, colores suaves

**Non-Goals:**
- No separar frontend en otro proyecto/proceso
- No añadir frameworks JS con build tooling (React, Vue, Angular con SFC)
- No modificar APIs existentes ni añadir nuevas
- No testing del frontend (PoC)
- No responsive mobile (usabilidad escritorio)

## Decisions

### 1. Arquitectura: SPA con hash-routing en un solo HTML
- **Decisión**: Single Page Application con routing por hash (`#/accounts`, `#/accounts/{iban}`)
- **Razón**: Sin servidor de frontend, el enrutamiento hash no requiere configuración server-side. Quarkus sirve el `index.html` y el JS maneja la navegación.
- **Alternativa**: Múltiples HTML → más complejo, duplicación de layout.
- **Alternativa**: Servir con nginx → rompe el requisito "mismo Quarkus".

### 2. Tecnología frontend: Vanilla JS + CSS moderno
- **Decisión**: HTML5, CSS3 con custom properties (`--color-*`), JavaScript vainilla con `fetch()` + módulos ES6
- **Razón**: Cero dependencias, cero build steps. El navegador moderno (Chrome, Firefox, Edge) soporta todo lo necesario.
- **Alternativa**: Vue.js via CDN → añade dependencia externa (CDN), mayor tamaño de descarga.
- **Alternativa**: Alpine.js via CDN → similar, pero innecesario para dos pantallas simples.

### 3. Estructura de archivos
```
META-INF/resources/
├── index.html          # SPA principal (layout, routing)
├── css/
│   └── style.css       # Estilos (paleta suave, tipografía limpia)
├── js/
│   ├── app.js          # Inicialización, routing, navegación
│   ├── api.js          # Cliente HTTP — wrapper de fetch() para APIs
│   ├── views/
│   │   ├── account-list.js    # Vista: lista de cuentas + crear cuenta
│   │   └── account-detail.js  # Vista: detalle, saldo, transferencias
│   └── components/
│       ├── header.js     # Barra superior con navegación
│       ├── modal.js      # Modal reutilizable (forms)
│       └── toast.js      # Notificaciones toast
```

### 4. Diseño visual: sobrio, elegante, colores suaves
- **Paleta**: Fondos grises muy claros (`#f5f6fa`), cards blancas, azul corporativo suave (`#4a6fa5`), grises para texto.
- **Tipografía**: Sistema nativa (`-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif`)
- **Componentes**: Cards con sombras suaves, bordes redondeados (8px), espaciado generoso.
- **Sin framework CSS**: Custom properties para consistencia, sin Bootstrap/Tailwind.

### 5. Integración con APIs existentes
| Vista | API | Uso |
|---|---|---|
| Account List | `GET /accounts?page=0&size=100` | Cargar todas las cuentas |
| Account List | `POST /accounts` | Crear cuenta nueva |
| Account Detail | `GET /accounts/{iban}` | Mostrar datos de cuenta |
| Account Detail | `GET /accounts/{iban}/balance` | Mostrar saldo |
| Account Detail | `GET /accounts/{iban}/transfers` | Historial de transferencias |
| Account Detail | `POST /transfers` | Ejecutar transferencia |

### 6. Manejo de errores
- Errores 4xx/5xx se muestran como notificaciones toast (esquina superior derecha)
- Validación del lado cliente antes de enviar (importe positivo, IBAN no vacío, etc.)
- `ErrorResponse` de la API se parsea y muestra `message`

## Risks / Trade-offs

- **[Rendimiento]** Vanilla JS sin virtual DOM → en dos pantallas simples no hay problema
- **[Mantenibilidad]** Sin framework → más código manual para estado y DOM. Mitigación: estructura modular en JS, vistas como funciones puras que reciben estado y devuelven HTML
- **[CORS]** Misma origen (mismo host:puerto) → sin CORS. Solo funciona desde Quarkus.
- **[Routing]** Hash URLs (`#/accounts`) → URLs menos limpias, pero funcional sin servidor.
