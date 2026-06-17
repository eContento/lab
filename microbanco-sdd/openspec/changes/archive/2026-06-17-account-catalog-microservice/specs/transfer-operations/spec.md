## ADDED Requirements

### Requirement: Realizar transferencia
El sistema SHALL permitir realizar una transferencia de fondos entre dos cuentas del mismo banco. La operación SHALL ser atómica: el débito de la cuenta origen y el crédito de la cuenta destino deben ocurrir en la misma transacción.

#### Scenario: Transferencia exitosa
- **WHEN** se envía una solicitud POST /transfers con identificador de cuenta origen, cuenta destino, importe y descripción opcional
- **THEN** el sistema debita el importe de la cuenta origen, acredita el mismo importe en la cuenta destino, registra el movimiento y devuelve HTTP 201

#### Scenario: Transferencia con saldo insuficiente
- **WHEN** se envía una solicitud POST /transfers donde la cuenta origen no tiene saldo suficiente
- **THEN** el sistema rechaza la operación con HTTP 400 y la transacción no se ejecuta

#### Scenario: Transferencia a cuenta inexistente
- **WHEN** se envía una solicitud POST /transfers donde la cuenta destino no existe
- **THEN** el sistema rechaza la operación con HTTP 404

#### Scenario: Transferencia desde cuenta inexistente
- **WHEN** se envía una solicitud POST /transfers donde la cuenta origen no existe
- **THEN** el sistema rechaza la operación con HTTP 404

#### Scenario: Autotransferencia (origen = destino)
- **WHEN** se envía una solicitud POST /transfers donde la cuenta origen es la misma que la cuenta destino
- **THEN** el sistema rechaza la operación con HTTP 400

#### Scenario: Transferencia con importe negativo o cero
- **WHEN** se envía una solicitud POST /transfers con importe negativo o cero
- **THEN** el sistema rechaza la operación con HTTP 400

#### Scenario: Transferencia desde cuenta cerrada
- **WHEN** se envía una solicitud POST /transfers desde una cuenta en estado cerrado
- **THEN** el sistema rechaza la operación con HTTP 400

#### Scenario: Transferencia a cuenta cerrada
- **WHEN** se envía una solicitud POST /transfers hacia una cuenta en estado cerrado
- **THEN** el sistema rechaza la operación con HTTP 400

### Requirement: Consulta de historial de transferencias
El sistema SHALL permitir consultar el historial de transferencias de una cuenta, ordenado por fecha descendente y con paginación.

#### Scenario: Consulta de historial exitosa
- **WHEN** se envía una solicitud GET /accounts/{id}/transfers
- **THEN** el sistema devuelve HTTP 200 con una lista paginada de transferencias ordenadas por fecha descendente

#### Scenario: Consulta de historial de cuenta inexistente
- **WHEN** se envía una solicitud GET /accounts/{id}/transfers con un identificador que no existe
- **THEN** el sistema devuelve HTTP 404

### Requirement: Concurrent transfer atomicity
The system SHALL handle concurrent transfers that involve the same account using optimistic locking.

#### Scenario: Concurrent transfers from same account
- **WHEN** two concurrent POST /transfers requests attempt to transfer from the same account with combined amount exceeding the balance
- **THEN** exactly one transfer SHALL succeed and the other SHALL fail with HTTP 409 (Conflict)
