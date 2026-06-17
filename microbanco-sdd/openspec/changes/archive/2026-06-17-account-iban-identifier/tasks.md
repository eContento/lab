## 1. Utilidad IBAN — Generación y Validación

- [x] 1.1 Crear `IbanUtils` con método `generateIban()`: genera IBAN ES válido (24 caracteres) usando UUID + dígitos de control ISO 13616
- [x] 1.2 Implementar `validateIban(String iban)`: validación completa (longitud, charset, dígitos de control módulo 97)
- [x] 1.3 Escribir tests unitarios para IbanUtils (generación produce IBAN válido, validación acepta/rechaza casos correctos)

## 2. Entidades — IBAN en lugar de UUID

- [x] 2.1 AccountEntity: cambiar `id` de `UUID` a `String` (IBAN), actualizar constructor y métodos
- [x] 2.2 TransferEntity: cambiar `sourceAccountId` y `targetAccountId` de `UUID` a `String`
- [x] 2.3 Actualizar AccountEntity.findAllPaged y TransferEntity.findByAccountIdPaged para usar String

## 3. Excepciones — AccountNotFoundException

- [x] 3.1 AccountNotFoundException: cambiar `accountId` de `UUID` a `String`
- [x] 3.2 Actualizar AccountNotFoundExceptionMapper para reflejar el cambio

## 4. Servicios — IBAN en lugar de UUID

- [x] 4.1 AccountService: cambiar firmas de UUID a String, usar `IbanUtils.generateIban()` en openAccount
- [x] 4.2 TransferService: cambiar firmas de UUID a String para sourceAccountId/targetAccountId
- [x] 4.3 Añadir validación de IBAN en TransferService.executeTransfer (ambos IBANs)
- [x] 4.4 Añadir validación de IBAN en AccountService.getAccount (para búsquedas por IBAN)

## 5. DTOs y REST API — IBAN en lugar de UUID

- [x] 5.1 AccountRequest: eliminar campo id (opcional, ya no se provee — el sistema genera IBAN)
- [x] 5.2 AccountResponse: cambiar `id` de `UUID` a `String` (IBAN)
- [x] 5.3 TransferRequest: cambiar `sourceAccountId` y `targetAccountId` de `UUID` a `String`
- [x] 5.4 TransferResponse: cambiar `id`, `sourceAccountId`, `targetAccountId` de `UUID` a `String`
- [x] 5.5 BalanceResponse: cambiar `accountId` de `UUID` a `String`
- [x] 5.6 AccountResource: path params `{id}` → `{iban}` String, referencias a UUID eliminadas
- [x] 5.7 TransferResource: path params y referencias a String
- [x] 5.8 PagedResponse: sin cambios (genérico)

## 6. Tests

- [x] 6.1 AccountTest (entity): actualizar a IBAN string en lugar de UUID
- [x] 6.2 TransferTest (entity): actualizar a IBAN string
- [x] 6.3 AccountServiceTest: actualizar a IBAN string, verificar generación de IBAN en openAccount
- [x] 6.4 TransferServiceTest: actualizar a IBAN string
- [x] 6.5 AccountResourceTest (integración REST): actualizar request/responses a IBAN string
- [x] 6.6 TransferResourceTest (integración REST): actualizar a IBAN string
- [x] 6.7 ConcurrentTransferTest: actualizar a IBAN string

## 7. Verificación

- [x] 7.1 Compilar: ./mvnw compile
- [x] 7.2 Tests: ./mvnw test — todos verdes
