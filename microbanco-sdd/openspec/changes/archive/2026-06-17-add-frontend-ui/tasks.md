## 1. CSS y estructura base

- [x] 1.1 Crear estructura de directorios `META-INF/resources/{css,js,js/views,js/components}`
- [x] 1.2 Crear `style.css` con paleta de colores suaves y diseño de cards
- [x] 1.3 Crear `index.html` con layout base (header, main container, toast container)

## 2. Módulos JS base

- [x] 2.1 Crear `api.js` — wrapper fetch() para todas las APIs REST
- [x] 2.2 Crear `components/header.js` — barra de navegación superior
- [x] 2.3 Crear `components/toast.js` — notificaciones toast para errores/éxitos
- [x] 2.4 Crear `components/modal.js` — modal reutilizable para formularios
- [x] 2.5 Crear `app.js` — inicialización, hash-routing, carga de vistas

## 3. Vista: Account List

- [x] 3.1 Crear `views/account-list.js` — listar cuentas en cards con saldo y estado
- [x] 3.2 Añadir formulario modal para crear nueva cuenta
- [x] 3.3 Añadir paginación (anterior/siguiente)

## 4. Vista: Account Detail

- [x] 4.1 Crear `views/account-detail.js` — datos de cuenta, saldo, botón volver
- [x] 4.2 Añadir historial de transferencias paginado
- [x] 4.3 Añadir formulario modal para ejecutar transferencia
- [x] 4.4 Añadir botón "Cerrar cuenta" con confirmación y manejo de errores

## 5. Integración y verificación

- [x] 5.1 Arrancar Quarkus (`mvn quarkus:dev`) y verificar UI servida en http://localhost:8080 — todos los assets 200
- [ ] 5.2 Prueba manual: crear cuenta, ver en lista, ver detalle, hacer transferencia (Requiere PostgreSQL local accesible con el schema actualizado a IBAN)
