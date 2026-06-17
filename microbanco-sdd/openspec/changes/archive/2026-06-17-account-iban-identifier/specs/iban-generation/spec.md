## ADDED Requirements

### Requirement: Generación de IBAN
El sistema SHALL generar IBANs válidos según ISO 13616 al crear una nueva cuenta bancaria. El IBAN generado SHALL tener dígitos de control correctos y SHALL ser único en el sistema.

#### Scenario: Generación de IBAN válido
- **WHEN** el sistema crea una nueva cuenta
- **THEN** el IBAN generado tiene formato ES seguido de 22 caracteres alfanuméricos (24 caracteres totales) y pasa la validación de dígitos de control (módulo 97)

### Requirement: Validación de IBAN
El sistema SHALL validar el formato IBAN (ISO 13616) en todas las operaciones que reciben IBANs de entrada (transferencias, consultas). La validación SHALL incluir la verificación de dígitos de control.

#### Scenario: IBAN con formato válido
- **WHEN** se envía una operación con un IBAN de 24 caracteres (ES) que pasa la validación de dígitos de control
- **THEN** el sistema acepta el IBAN como válido

#### Scenario: IBAN con formato inválido
- **WHEN** se envía una operación con un IBAN de longitud incorrecta o caracteres no alfanuméricos
- **THEN** el sistema rechaza la operación con HTTP 400

#### Scenario: IBAN con dígitos de control incorrectos
- **WHEN** se envía una operación con un IBAN de formato correcto pero dígitos de control inválidos
- **THEN** el sistema rechaza la operación con HTTP 400
