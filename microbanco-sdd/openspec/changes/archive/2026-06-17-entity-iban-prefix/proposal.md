## Why

Diferenciar las cuentas de nuestra entidad mediante un prefijo fijo en el IBAN (`00830001`), permitiendo identificar fácilmente cuentas propias vs externas. Esto habilita la validación selectiva de existencia de cuenta destino en transferencias, posibilitando transferencias a cuentas de otros bancos.

## What Changes

- **IBAN generation**: IBAN generado como `ES` + checkDigits + `00830001` + 12 dígitos aleatorios. La longitud total se mantiene en 24 caracteres.
- **TransferService**: Cuando el IBAN destino **no** comienza con el prefijo de entidad (cuenta externa), saltar la validación de existencia en base de datos. La cuenta externa se trata como destino válido sin verificar.
- **Frontend**: Al introducir un IBAN en el formulario de transferencia, permitir entrada en bloques de 4 caracteres separados por espacio. Los espacios se eliminan antes de enviar al API.
- **Frontend**: Mostrar IBANs en formato legible (bloques de 4 separados por espacio) en toda la UI.

## Capabilities

### New Capabilities
- `entity-iban-prefix`: Prefijo `00830001` fijo en IBAN de la entidad y validación selectiva de cuentas externas en transferencias

### Modified Capabilities
- *(ninguna — el comportamiento de las APIs no cambia)*

## Impact

- **IbanUtils.java**: `generateIban()` usa `00830001` + 12 dígitos aleatorios; nuevo método `isEntityAccount(iban)`
- **IbanUtilsTest.java**: Tests actualizados para nueva estructura IBAN
- **TransferService.java**: Validación condicional de existencia de cuenta destino (solo para cuentas con prefijo de entidad)
- **TransferServiceTest.java**: Tests para transferencias a cuentas externas
- **Frontend JS**: `api.js` — utilidades `formatIban`, `cleanIban`, `isEntityAccount`; vistas — display formateado y auto-espaciado en input
