## Context

El microservicio actual identifica las cuentas mediante UUID generados automáticamente. El cambio migra a IBAN (ISO 13616) como identificador de cuenta, un estándar internacional de 34 caracteres alfanuméricos que incluye código de país, dígitos de control y número de cuenta bancaria. El sistema genera el IBAN automáticamente al crear la cuenta.

## Goals / Non-Goals

**Goals:**
- AccountEntity.id pasa de UUID a String (IBAN) con validación ISO 13616
- TransferEntity.sourceAccountId y targetAccountId pasan de UUID a String
- API REST usa IBAN strings en path params y cuerpos de request/response
- El IBAN se genera automáticamente con dígitos de control válidos
- Validación de formato IBAN en todas las operaciones que reciben IBANs externos
- Tests actualizados para usar IBANs en lugar de UUIDs

**Non-Goals:**
- No cambiar comportamiento de negocio (transferencias, cierres, saldos)
- No cambiar la estructura de las DTOs de respuesta (solo el tipo del identificador)
- No añadir nuevas funcionalidades bancarias

## Decisions

### 1. Generación de IBAN
- **Decisión**: Generar IBAN español (ES) usando el algoritmo de dígitos de control ISO 13616
- **Formato**: `ESkk` + `0000` + `0000` + `00` + 20 caracteres derivados de UUID → 24 caracteres totales
- **Algoritmo**: 
  1. Poner dígitos de control a `00`
  2. Mover 4 primeros caracteres al final
  3. Convertir letras a números (A=10, ..., Z=35)
  4. Calcular `98 - (numero % 97)` como dígitos de control
- **Razón**: Produce IBANs válidos según ISO 13616 sin necesidad de un banco real. La parte final usa UUID para garantizar unicidad global.

### 2. Validación de IBAN
- **Decisión**: Validación completa ISO 13616 en un método utilitario `IbanUtils.validate(String iban)`
- **Validaciones**: 
  - Longitud exacta (24 para ES o 34 genérico)
  - Solo caracteres alfanuméricos (sin espacios)
  - Dígitos de control correctos (módulo 97)
- **Razón**: El estándar bancario real requiere dígitos de control. La validación completa previene errores antes de llegar a la BD.

### 3. Tipo de columna en BD
- **Decisión**: `@Column(length = 34)` con `String` (varchar) en lugar de `UUID`
- **Razón**: IBAN es un string alfanumérico. PostgreSQL/JPA maneja varchar correctamente.
- **Índice**: La PK ya tiene índice automático.

### 4. API REST — Path params
- **Decisión**: Los path params `{id}` se mantienen como `{iban}` pero aceptan String en lugar de UUID
- **Razón**: JAX-RS convierte automáticamente path params String. No se requiere cambio de anotaciones en los endpoints.

### 5. AccountNotFoundException
- **Decisión**: El campo `accountId` pasa de `UUID` a `String`
- **Razón**: Coherencia con el nuevo tipo de identificador.

## Risks / Trade-offs

- **[Longitud de IBAN]** 34 caracteres es más largo que 16 de UUID en hex. Las URLs son más largas pero aún manejables. → **Mitigación**: Aceptable para un identificador de dominio bancario real.

- **[IBAN español fijo]** El sistema siempre genera IBANs ES (España). Si en el futuro se soportan otros países, habría que parametrizar el país. → **Mitigación**: El diseño actual asume ES. Se puede extender añadiendo un campo `country` a futuro.

- **[Validación de IBAN duplicado]** El IBAN generado a partir de UUID debería ser único, pero no está garantizado matemáticamente. → **Mitigación**: La columna es PK, por lo que un duplicado lanza `ConstraintViolationException`. Extremadamente improbable con UUID de 20 chars como base.
