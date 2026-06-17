## ADDED Requirements

### Requirement: Entity IBAN prefix
The system SHALL generate IBANs with a fixed entity prefix `00830001` in the BBAN to identify accounts belonging to the entity.

#### Scenario: IBAN starts with entity prefix
- **WHEN** the system generates a new IBAN
- **THEN** the IBAN SHALL be exactly 24 characters long
- **AND** start with `ES` followed by 2 valid check digits
- **AND** the next 8 characters SHALL be `00830001`
- **AND** the remaining 12 characters SHALL be numeric digits (0-9)

#### Scenario: validateIban accepts new format
- **WHEN** a valid entity IBAN (24 chars, correct check digits) is validated
- **THEN** `validateIban()` SHALL return true

### Requirement: External account transfer validation
The system SHALL only validate account existence in the database for target accounts that belong to the entity.

#### Scenario: Transfer to external account
- **WHEN** a user executes a transfer to a target IBAN whose BBAN does NOT start with `00830001`
- **THEN** the system SHALL NOT verify that the target account exists in the database
- **AND** the transfer SHALL be recorded successfully regardless

#### Scenario: Transfer to entity account not found
- **WHEN** a user executes a transfer to a target IBAN whose BBAN starts with `00830001` but does not exist in the database
- **THEN** the system SHALL throw `AccountNotFoundException`

### Requirement: IBAN display formatting
The UI SHALL display IBANs in blocks of 4 characters separated by spaces.

#### Scenario: Display formatted IBAN
- **WHEN** the system shows an IBAN anywhere in the UI
- **THEN** spaces SHALL be inserted every 4 characters

### Requirement: IBAN input with space formatting
The UI SHALL allow entering IBANs with automatic space insertion and strip spaces before API submission.

#### Scenario: Typing IBAN with auto-format
- **WHEN** the user types an IBAN in a form field
- **THEN** a space SHALL be automatically inserted after every 4 alphanumeric characters

#### Scenario: Submit strips spaces
- **WHEN** the user submits a form with a formatted IBAN
- **THEN** all spaces SHALL be removed before sending to the API
