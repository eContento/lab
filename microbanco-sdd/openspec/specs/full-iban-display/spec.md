## ADDED Requirements

### Requirement: Full IBAN in transfer history
The transfer history table SHALL display the complete IBAN (formatted in blocks of 4) for both source and target accounts.

#### Scenario: Full IBAN shown in table
- **WHEN** the user views the transfer history of an account
- **THEN** the source and target IBAN columns SHALL display the full IBAN with spaces every 4 characters
- **AND** SHALL NOT truncate or abbreviate the IBAN
