## 1. Project Setup

- [x] 1.1 Create Maven project structure with Quarkus LTS, Java 25, groupId com.microbanco, artifactId account-catalog-data
- [x] 1.2 Configure Quarkus extensions: resteasy-reactive, hibernate-orm-panache, hibernate-validator, jackson, h2 (test), postgresql (prod)
- [x] 1.3 Configure application.properties for dev profile (H2, port 8080) and prod profile (PostgreSQL)
- [x] 1.4 Configure GraalVM native compilation in pom.xml (native profile)
- [x] 1.5 Add Maven wrapper and verify project compiles

## 2. Domain Model

- [x] 2.1 Create Account entity with fields: id (UUID), ownerName, currency, balance, status (ACTIVE/CLOSED), createdAt, updatedAt
- [x] 2.2 Create Transfer entity with fields: id (UUID), sourceAccountId, targetAccountId, amount, description, timestamp
- [x] 2.3 Add optimistic locking (@Version) to Account entity
- [x] 2.4 Create AccountRepository and TransferRepository using PanacheRepository
- [x] 2.5 Create database migration script or let Hibernate auto-create schema (dev mode)

## 3. Account Management — Domain & Application

- [x] 3.1 Create Account domain entity (rich domain model with business methods: canClose, debit, credit)
- [x] 3.2 Create AccountService (application service) with: openAccount, getAccount, listAccounts, closeAccount, getBalance
- [x] 3.3 Implement account opening validation (titular required, currency required, non-negative initial balance)
- [x] 3.4 Implement account closing validation (balance must be zero, account must be ACTIVE)
- [x] 3.5 Implement balance consultation logic

## 4. Transfer Operations — Domain & Application

- [x] 4.1 Create Transfer domain entity with transfer validation rules
- [x] 4.2 Create TransferService with: executeTransfer, getTransferHistory
- [x] 4.3 Implement transfer validation: both accounts must exist and be ACTIVE, source != destination, amount > 0, sufficient balance
- [x] 4.4 Implement atomic transfer execution (debit + credit in single JTA transaction)
- [x] 4.5 Implement optimistic locking retry for concurrent transfer scenarios
- [x] 4.6 Implement transfer history query (paginated, ordered by date desc)

## 5. REST API — Account Management

- [x] 5.1 Create AccountResource with POST /accounts (open account)
- [x] 5.2 Create AccountResource with GET /accounts/{id} (get account)
- [x] 5.3 Create AccountResource with GET /accounts (list accounts with pagination)
- [x] 5.4 Create AccountResource with DELETE /accounts/{id} (close account)
- [x] 5.5 Create AccountResource with GET /accounts/{id}/balance (get balance)
- [x] 5.6 Add DTOs: AccountRequest, AccountResponse, BalanceResponse
- [x] 5.7 Add proper HTTP status codes and error responses (400, 404, 409)

## 6. REST API — Transfer Operations

- [x] 6.1 Create TransferResource with POST /transfers (execute transfer)
- [x] 6.2 Create AccountResource with GET /accounts/{id}/transfers (transfer history)
- [x] 6.3 Add DTOs: TransferRequest, TransferResponse
- [x] 6.4 Add proper HTTP status codes and error responses (400, 404, 409)

## 7. Error Handling

- [x] 7.1 Create exception hierarchy: AccountNotFoundException, InsufficientBalanceException
- [x] 7.2 Implement global exception mapper (ExceptionMapper) for consistent error JSON responses
- [x] 7.3 Add constraint validation on DTOs (@NotNull, @Positive, @Size, etc.)

## 8. Hexagonal Architecture — Adapters

- [x] 8.1 Create domain-level port interfaces: AccountRepositoryPort, TransferRepositoryPort
- [x] 8.2 Create infrastructure adapters implementing the ports using Panache
- [x] 8.3 Configure CDI beans to wire adapters to ports
- [x] 8.4 Ensure domain layer has zero dependency on Quarkus/Hibernate annotations

## 9. Testing

- [x] 9.1 Write unit tests for Account domain entity (business rules)
- [x] 9.2 Write unit tests for Transfer domain entity (validation rules)
- [x] 9.3 Write unit tests for AccountService (mocked repository)
- [x] 9.4 Write unit tests for TransferService (mocked repository)
- [x] 9.5 Write integration tests for AccountResource REST endpoints with @QuarkusTest
- [x] 9.6 Write integration tests for TransferResource REST endpoints with @QuarkusTest
- [x] 9.7 Write concurrent transfer test (two simultaneous transfers from same account)

## 10. Native Compilation

- [x] 10.1 Verify all serialization paths are registered for reflection (or use @RegisterForReflection)
- [ ] 10.2 Run native compilation test with `mvn clean package -Pnative`
- [ ] 10.3 Fix any native compilation issues (reflection, proxies, resource bundles)
