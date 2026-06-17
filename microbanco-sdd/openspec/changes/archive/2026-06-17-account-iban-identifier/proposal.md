## Why

El uso de UUID como identificador de cuenta bancaria es inapropiado para el dominio bancario real. En banca, las cuentas se identifican mediante IBAN (International Bank Account Number, ISO 13616), un estándar internacional que permite la interoperabilidad entre entidades y países. Migrar a IBAN acerca el modelo de datos a la realidad del negocio y permite futuras integraciones con sistemas bancarios externos.

## What Changes

- **BREAKING**: El identificador de cuenta cambia de `UUID` a `String` (IBAN) en todas las APIs, servicios y persistencia
- POST /accounts: el sistema genera automáticamente un IBAN válido (ISO 13616 con dígitos de control) — el cliente no lo especifica
- GET /accounts/{iban}: path param cambia de UUID a String
- DELETE /accounts/{iban}: path param cambia de UUID a String
- GET /accounts/{iban}/balance: path param cambia de UUID a String
- GET /accounts/{iban}/transfers: path param cambia de UUID a String
- POST /transfers: `sourceAccountId` y `targetAccountId` pasan de UUID a String (IBAN)
- Se añade validación de formato IBAN (ISO 13616) en las operaciones que reciben IBANs
- Se añade utilidad de generación de IBANs con dígitos de control válidos

## Capabilities

### New Capabilities
- `iban-generation`: Generación y validación de IBANs conforme a ISO 13616

### Modified Capabilities
- `account-management`: El identificador de cuenta cambia de UUID a IBAN en todos los escenarios
- `transfer-operations`: Las referencias a cuentas (source/destination) usan IBAN en lugar de UUID

## Impact

- **Modelo de datos**: `AccountEntity.id` pasa de `UUID` a `String` (34 chars, IBAN). `TransferEntity.sourceAccountId` y `targetAccountId` pasan de `UUID` a `String`
- **API REST**: Todos los path params `{id}` pasan a ser strings IBAN. Los DTOs `AccountRequest`/`AccountResponse`/`TransferRequest`/`TransferResponse` cambian tipos UUID → String
- **Servicios**: `AccountService` y `TransferService` usan String en lugar de UUID para identificar cuentas
- **Excepciones**: `AccountNotFoundException` acepta String (IBAN) en lugar de UUID
- **Tests**: Todos los tests que usan UUIDs deben actualizarse a IBANs
- **Base de datos**: Las columnas `id`, `source_account_id`, `target_account_id` pasan de `uuid` a `varchar(34)`
