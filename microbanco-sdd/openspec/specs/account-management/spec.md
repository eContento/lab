## Purpose
Gestionar el ciclo de vida completo de las cuentas bancarias: apertura, consulta, listado, cierre y consulta de saldo.
## Requirements
### Requirement: Apertura de cuenta
El sistema SHALL permitir la apertura de una cuenta bancaria proporcionando los datos del titular, una divisa y un saldo inicial (puede ser cero). El sistema SHALL generar automáticamente un IBAN válido (ISO 13616) para la nueva cuenta.

#### Scenario: Apertura exitosa
- **WHEN** se envía una solicitud POST /accounts con titular, divisa y saldo inicial
- **THEN** el sistema crea la cuenta, genera un IBAN válido y devuelve HTTP 201 con los datos de la cuenta creada (incluyendo el IBAN)

#### Scenario: Apertura con saldo negativo
- **WHEN** se envía una solicitud POST /accounts con saldo inicial negativo
- **THEN** el sistema rechaza la operación con HTTP 400

#### Scenario: Apertura con datos incompletos
- **WHEN** se envía una solicitud POST /accounts sin titular o sin divisa
- **THEN** el sistema rechaza la operación con HTTP 400

### Requirement: Consulta de cuenta
El sistema SHALL permitir consultar los datos de una cuenta por su IBAN.

#### Scenario: Consulta de cuenta existente
- **WHEN** se envía una solicitud GET /accounts/{iban}
- **THEN** el sistema devuelve HTTP 200 con los datos completos de la cuenta

#### Scenario: Consulta de cuenta inexistente
- **WHEN** se envía una solicitud GET /accounts/{iban} con un IBAN que no existe
- **THEN** el sistema devuelve HTTP 404

#### Scenario: Consulta con IBAN inválido
- **WHEN** se envía una solicitud GET /accounts/{iban} con un IBAN con formato inválido
- **THEN** el sistema devuelve HTTP 400

### Requirement: Listado de cuentas
El sistema SHALL permitir listar todas las cuentas existentes con paginación.

#### Scenario: Listado exitoso
- **WHEN** se envía una solicitud GET /accounts
- **THEN** el sistema devuelve HTTP 200 con una lista paginada de cuentas

### Requirement: Cierre de cuenta
El sistema SHALL permitir cerrar una cuenta existente, siempre que su saldo sea cero y no tenga transferencias pendientes.

#### Scenario: Cierre exitoso
- **WHEN** se envía una solicitud DELETE /accounts/{iban} y la cuenta tiene saldo cero
- **THEN** el sistema cierra la cuenta y devuelve HTTP 204

#### Scenario: Cierre de cuenta con saldo positivo
- **WHEN** se envía una solicitud DELETE /accounts/{iban} y la cuenta tiene saldo distinto de cero
- **THEN** el sistema rechaza la operación con HTTP 409 (Conflict)

#### Scenario: Cierre de cuenta inexistente
- **WHEN** se envía una solicitud DELETE /accounts/{iban} con un IBAN que no existe
- **THEN** el sistema devuelve HTTP 404

### Requirement: Consulta de saldo
El sistema SHALL permitir consultar el saldo actual de una cuenta.

#### Scenario: Consulta de saldo exitosa
- **WHEN** se envía una solicitud GET /accounts/{iban}/balance
- **THEN** el sistema devuelve HTTP 200 con el saldo actual y la divisa

#### Scenario: Consulta de saldo de cuenta inexistente
- **WHEN** se envía una solicitud GET /accounts/{iban}/balance con un IBAN que no existe
- **THEN** el sistema devuelve HTTP 404

