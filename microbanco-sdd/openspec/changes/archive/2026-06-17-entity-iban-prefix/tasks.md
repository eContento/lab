## 1. Backend: IBAN generation with entity prefix

- [x] 1.1 Modificar `IbanUtils.generateIban()`: BBAN = `00830001` + 12 dígitos aleatorios (ThreadLocalRandom)
- [x] 1.2 Añadir `IbanUtils.isEntityAccount(String iban)` y `generateIbanFromBban(String)`

## 2. Backend: External transfer validation

- [x] 2.1 Modificar `TransferService.executeTransfer()`: saltar validación de existencia en DB para cuentas destino externas (no prefijo `00830001`)
- [x] 2.2 Añadir tests en `TransferServiceTest` para transferencias a cuentas externas

## 3. Backend: Tests

- [x] 3.1 Actualizar `IbanUtilsTest` — verificar nueva estructura: prefijo `00830001`, 12 dígitos, 24 chars
- [x] 3.2 Añadir test `shouldGenerateIbanWithEntityPrefix()` en `IbanUtilsTest`
- [x] 3.3 Añadir test `isEntityAccount()` en `IbanUtilsTest`

## 4. Frontend: IBAN formatting utilities

- [x] 4.1 Añadir `formatIban(iban)` en `api.js` — espacio cada 4 caracteres
- [x] 4.2 Añadir `cleanIban(iban)` en `api.js` — elimina espacios
- [x] 4.3 Añadir `isEntityAccount(iban)` en `api.js` — detecta cuentas de la entidad

## 5. Frontend: IBAN display formatting

- [x] 5.1 Formatear IBAN en `account-list.js` (renderCard)
- [x] 5.2 Formatear IBAN en `account-detail.js` (detalle de cuenta, formulario)

## 6. Frontend: IBAN input with space formatting

- [x] 6.1 Modificar input target-iban en transfer form con auto-espaciado cada 4 caracteres
- [x] 6.2 Limpiar espacios del IBAN al enviar al API

## 7. Verificación

- [x] 7.1 Compilar: `mvn compile`
- [x] 7.2 Tests: `mvn test` — todos verdes (66/66)
