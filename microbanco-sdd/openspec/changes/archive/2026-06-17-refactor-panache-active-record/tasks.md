## 1. Entities — Migrar a Active Record (Campos Públicos + PanacheEntityBase)

- [x] 1.1 Refactorizar AccountEntity: extender PanacheEntityBase, convertir campos a públicos, eliminar constructor privado con getters/setters, añadir métodos de dominio (debit, credit, canClose, close)
- [x] 1.2 Refactorizar TransferEntity: extender PanacheEntityBase, convertir campos a públicos, mantener solo validación básica en constructor
- [x] 1.3 Eliminar anotaciones @RegisterForReflection de entidades (Panache lo gestiona automáticamente en modo nativo)

## 2. Eliminar Capa de Repositorio y Puertos

- [x] 2.1 Eliminar interfaz AccountRepositoryPort
- [x] 2.2 Eliminar interfaz TransferRepositoryPort
- [x] 2.3 Eliminar clase PanacheAccountRepository
- [x] 2.4 Eliminar clase PanacheTransferRepository

## 3. Eliminar Modelo de Dominio Independiente

- [x] 3.1 Eliminar domain/model/Account.java (lógica trasladada a AccountEntity)
- [x] 3.2 Eliminar domain/model/Transfer.java (lógica trasladada a TransferEntity)
- [x] 3.3 Verificar que AccountStatus se mantiene (referenciado desde AccountEntity)
- [x] 3.4 Verificar que AccountNotFoundException e InsufficientBalanceException se mantienen (usadas por servicios y mappers)

## 4. Refactorizar Servicios para Active Record

- [x] 4.1 Refactorizar AccountService: reemplazar AccountRepositoryPort por métodos estáticos Panache (findById, listAll, count, persist) sobre AccountEntity
- [x] 4.2 Refactorizar TransferService: reemplazar AccountRepositoryPort y TransferRepositoryPort por métodos estáticos Panache sobre AccountEntity y TransferEntity
- [x] 4.3 Verificar @Transactional en métodos de mutación (openAccount, closeAccount, executeTransfer)
- [x] 4.4 Verificar optimistic locking: AccountEntity.flush() antes de commit para detectar conflictos

## 5. Actualizar Tests

- [x] 5.1 Migrar AccountTest (unit): probar métodos de dominio directamente sobre AccountEntity (debit, credit, close)
- [x] 5.2 Migrar TransferTest (unit): probar creación de TransferEntity directamente
- [x] 5.3 Migrar AccountServiceTest: reemplazar mocks de repositorio por operaciones con entidades reales (con H2 @QuarkusTest)
- [x] 5.4 Migrar TransferServiceTest: reemplazar mocks de repositorio por operaciones con entidades reales (con H2 @QuarkusTest)
- [x] 5.5 Verificar que AccountResourceTest (integración REST) sigue funcionando sin cambios
- [x] 5.6 Verificar que TransferResourceTest (integración REST) sigue funcionando sin cambios
- [x] 5.7 Verificar que ConcurrentTransferTest sigue funcionando sin cambios
- [x] 5.8 Eliminar configuración experimental de ByteBuddy del pom.xml si ya no es necesaria (depende de si Mockito sigue necesitándolo con Java 25)

## 6. Limpieza

- [x] 6.1 Eliminar el paquete domain/port/ (directorio vacío)
- [x] 6.2 Verificar que compila: ./mvnw compile
- [x] 6.3 Verificar tests: ./mvnw test — todos verdes
