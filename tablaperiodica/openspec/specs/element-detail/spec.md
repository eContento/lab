## ADDED Requirements

### Requirement: Mostrar panel de detalle al seleccionar un elemento
Al hacer clic en una celda, la aplicación SHALL mostrar un panel con la información completa del elemento seleccionado.

#### Scenario: Clic en una celda de elemento
- **WHEN** el usuario hace clic en cualquier celda de la tabla
- **THEN** se muestra un panel de detalle con la información del elemento seleccionado y la celda queda visualmente marcada como activa

#### Scenario: Seleccionar un elemento diferente
- **WHEN** el usuario hace clic en una celda distinta estando ya un elemento seleccionado
- **THEN** el panel de detalle se actualiza con la información del nuevo elemento

#### Scenario: Cerrar el panel de detalle
- **WHEN** el usuario hace clic en el botón de cerrar del panel o presiona la tecla Escape
- **THEN** el panel de detalle se oculta y ningún elemento queda marcado como activo

### Requirement: Contenido del panel de detalle
El panel SHALL mostrar los siguientes campos del elemento: nombre completo, símbolo, número atómico, masa atómica, categoría, período, grupo, configuración electrónica, estado estándar (sólido/líquido/gas), punto de fusión (°C), punto de ebullición (°C) y una descripción breve.

#### Scenario: Campos presentes en el panel
- **WHEN** se muestra el panel de detalle de cualquier elemento
- **THEN** todos los campos definidos están visibles; los campos sin dato disponible muestran "N/A" o "Desconocido"

### Requirement: Diseño responsivo del panel de detalle
En pantallas de escritorio el panel SHALL aparecer como sidebar lateral; en pantallas móviles SHALL aparecer como bottom sheet o modal superpuesto.

#### Scenario: Panel en escritorio (viewport ≥ 1024px)
- **WHEN** el usuario selecciona un elemento en un dispositivo de escritorio
- **THEN** el panel de detalle aparece como columna lateral a la derecha de la tabla

#### Scenario: Panel en móvil (viewport < 1024px)
- **WHEN** el usuario selecciona un elemento en un dispositivo móvil
- **THEN** el panel de detalle aparece como overlay desde la parte inferior de la pantalla
