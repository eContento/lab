## ADDED Requirements

### Requirement: Apertura de cuenta
El sistema SHALL permitir la apertura de una cuenta bancaria proporcionando los datos del titular, una divisa y un saldo inicial (puede ser cero).

#### Scenario: Apertura exitosa
- **WHEN** se envía una solicitud POST /accounts con titular, divisa y saldo inicial
- **THEN** el sistema crea la cuenta y devuelve HTTP 201 con los datos de la cuenta creada (incluyendo identificador único)

#### Scenario: Apertura con saldo negativo
- **WHEN** se envía una solicitud POST /accounts con saldo inicial negativo
- **THEN** el sistema rechaza la operación con HTTP 400

#### Scenario: Apertura con datos incompletos
- **WHEN** se envía una solicitud POST /accounts sin titular o sin divisa
- **THEN** el sistema rechaza la operación con HTTP 400

### Requirement: Consulta de cuenta
El sistema SHALL permitir consultar los datos de una cuenta por su identificador.

#### Scenario: Consulta de cuenta existente
- **WHEN** se envía una solicitud GET /accounts/{id}
- **THEN** el sistema devuelve HTTP 200 con los datos completos de la cuenta

#### Scenario: Consulta de cuenta inexistente
- **WHEN** se envía una solicitud GET /accounts/{id} con un identificador que no existe
- **THEN** el sistema devuelve HTTP 404

### Requirement: Listado de cuentas
El sistema SHALL permitir listar todas las cuentas existentes con paginación.

#### Scenario: Listado exitoso
- **WHEN** se envía una solicitud GET /accounts
- **THEN** el sistema devuelve HTTP 200 con una lista paginada de cuentas

### Requirement: Cierre de cuenta
El sistema SHALL permitir cerrar una cuenta existente, siempre que su saldo sea cero y no tenga transferencias pendientes.

#### Scenario: Cierre exitoso
- **WHEN** se envía una solicitud DELETE /accounts/{id} y la cuenta tiene saldo cero
- **THEN** el sistema cierra la cuenta y devuelve HTTP 204

#### Scenario: Cierre de cuenta con saldo positivo
- **WHEN** se envía una solicitud DELETE /accounts/{id} y la cuenta tiene saldo distinto de cero
- **THEN** el sistema rechaza la operación con HTTP 409 (Conflict)

#### Scenario: Cierre de cuenta inexistente
- **WHEN** se envía una solicitud DELETE /accounts/{id} con un identificador que no existe
- **THEN** el sistema devuelve HTTP 404

### Requirement: Consulta de saldo
El sistema SHALL permitir consultar el saldo actual de una cuenta.

#### Scenario: Consulta de saldo exitosa
- **WHEN** se envía una solicitud GET /accounts/{id}/balance
- **THEN** el sistema devuelve HTTP 200 con el saldo actual y la divisa

#### Scenario: Consulta de saldo de cuenta inexistente
- **WHEN** se envía una solicitud GET /accounts/{id}/balance con un identificador que no existe
- **THEN** el sistema devuelve HTTP 404
