## 1. Entidades — mover y renombrar

- [x] 1.1 Crear paquete `entities` y mover `AccountEntity` → `entities/Account` con paquete `com.microbanco.account.entities`
- [x] 1.2 Mover `TransferEntity` → `entities/Transfer` con paquete `com.microbanco.account.entities`
- [x] 1.3 Anidar `AccountStatus` como enum `Status` dentro de `Account` y eliminar `domain/model/AccountStatus.java`
- [x] 1.4 Mover `AccountNotFoundException` e `InsufficientBalanceException` desde `domain/model` a `exceptions`
- [x] 1.5 Eliminar el paquete `domain` y sus subpaquetes vacíos

## 2. Servicios — mover a `services`

- [x] 2.1 Mover `AccountService` a `services` con paquete `com.microbanco.account.services`
- [x] 2.2 Mover `TransferService` a `services` con paquete `com.microbanco.account.services`
- [x] 2.3 Eliminar el paquete `application` vacío

## 3. Recursos REST y DTOs — mover a `boundary` y `dto`

- [x] 3.1 Mover `AccountResource` y `TransferResource` a `boundary` con paquete `com.microbanco.account.boundary`
- [x] 3.2 Mover todos los DTOs (`AccountRequest`, `AccountResponse`, `BalanceResponse`, `ErrorResponse`, `PagedResponse`, `TransferRequest`, `TransferResponse`) a `dto` con paquete `com.microbanco.account.dto`
- [x] 3.3 Eliminar los paquetes `infrastructure.rest` y `infrastructure.rest.dto` vacíos

## 4. Excepciones y mappers — reunificar en `exceptions`

- [x] 4.1 Mover los 6 ExceptionMappers desde `infrastructure.rest.exception` a `exceptions` con paquete `com.microbanco.account.exceptions`
- [x] 4.2 Eliminar `infrastructure.rest.exception` vacío
- [x] 4.3 Destruir `infrastructure` si queda vacío (tras mover `util` también)

## 5. Utilidades

- [x] 5.1 Mover `IbanUtils` de `infrastructure.util` a `util` con paquete `com.microbanco.account.util`
- [x] 5.2 Eliminar `infrastructure.util` e `infrastructure` si quedan vacíos

## 6. AccountApplication — eliminar

- [x] 6.1 Eliminar `AccountApplication.java`
- [x] 6.2 Reubicar la metadata OpenAPI en un recurso existente o en `application.properties`

## 7. Tests — mover a paquetes equivalentes

- [x] 7.1 Mover `application/AccountServiceTest` → `services/AccountServiceTest`
- [x] 7.2 Mover `application/TransferServiceTest` → `services/TransferServiceTest`
- [x] 7.3 Mover `infrastructure/AccountResourceTest` → `boundary/AccountResourceTest`
- [x] 7.4 Mover `infrastructure/TransferResourceTest` → `boundary/TransferResourceTest`
- [x] 7.5 Mover `infrastructure/ConcurrentTransferTest` → `boundary/ConcurrentTransferTest`
- [x] 7.6 Mover `infrastructure/persistence/AccountTest` → `entities/AccountTest`
- [x] 7.7 Mover `infrastructure/persistence/TransferTest` → `entities/TransferTest`
- [x] 7.8 Mover `infrastructure/util/IbanUtilsTest` → `util/IbanUtilsTest`
- [x] 7.9 Eliminar paquetes de test vacíos (`application`, `infrastructure/persistence`, `infrastructure/util`, `infrastructure`)

## 8. Limpieza global

- [x] 8.1 Barrer todos los archivos Java (fuentes y tests) para eliminar imports no utilizados tras los cambios de paquete
- [x] 8.2 Verificar que no quedan referencias a paquetes antiguos

## 9. Verificación

- [x] 9.1 Compilar: `mvn compile`
- [x] 9.2 Tests: `mvn test` — todos verdes
