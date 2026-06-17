## Context

IBAN actual: `ES` + checkDigits + 20 caracteres hex aleatorios (UUID). No hay identificador de entidad. Transferencias siempre validan existencia de destino en DB, impidiendo enviar a cuentas externas.

## Goals / Non-Goals

**Goals:**
- IBAN generado con prefijo `00830001` + 12 dígitos aleatorios (24 chars — misma longitud)
- IBANs mostrados en bloques de 4 separados por espacio
- Input de IBAN con auto-espaciado cada 4 caracteres
- Transferencias a cuentas externas (sin prefijo `00830001`) saltan validación de existencia en DB

**Non-Goals:**
- No cambiar APIs REST (mismos endpoints, DTOs)
- No afectar tests existentes
- No implementar autenticación

## Decisions

### 1. IBAN: 24 caracteres manteniendo longitud
- **Decisión**: `ES`(2) + checkDigits(2) + `00830001`(8) + 12 dígitos(12) = 24
- **Razón**: No cambia `IBAN_LENGTH_ES`, `validateIban()` sigue funcionando sin modificaciones de longitud. 12 dígitos dan suficientes combinaciones (10^12).

### 2. Generación de 12 dígitos aleatorios
- **Decisión**: `ThreadLocalRandom.current().ints(12, 0, 10)` genera 12 dígitos numéricos
- **Razón**: El usuario solicita dígitos. Más simple que UUID, evita caracteres hex.

### 3. Detección de cuentas de la entidad
- **Decisión**: `iban.substring(4).startsWith("00830001")` — verifica BBAN
- **Razón**: El prefijo de entidad va inmediatamente después del código país + check digits.

### 4. Transferencias a cuentas externas
- **Decisión**: En `TransferService.executeTransfer()`:
  - Cuenta origen: siempre validar existencia en DB
  - Cuenta destino con prefijo `00830001`: validar existencia en DB (lanzar `AccountNotFoundException` si no existe)
  - Cuenta destino sin prefijo: saltar validación, aceptar como destino externo
- **Razón**: Las cuentas externas no están en nuestra base de datos.

### 5. Formato de IBAN en frontend
- **Decisión**: `formatIban(iban)` = espacios cada 4 chars; `cleanIban(iban)` = elimina espacios
- **Razón**: UX bancaria estándar, mejora legibilidad.

### 6. Input con auto-espaciado
- **Decisión**: Evento `oninput` que filtra no-alfanuméricos y añade espacio cada 4 caracteres
- **Razón**: El usuario escribe de forma natural y el campo se formatea automáticamente.

## Risks / Trade-offs

- **[External transfers]** Sin confirmación de que la cuenta externa existe realmente. La transferencia se registra como exitosa en nuestro sistema.
- **[Validation]** IBANs externos con check digits correctos pero cuentas inexistentes se aceptarán.
- **[ID compatibility]** Los IBANs existentes en BD (sin prefijo 00830001) no serán identificados como cuentas de la entidad por `isEntityAccount()`, pero esto es correcto al ser cuentas del modelo anterior.
