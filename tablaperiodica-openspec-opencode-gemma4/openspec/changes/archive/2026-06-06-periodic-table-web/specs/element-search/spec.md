## ADDED Requirements

### Requirement: Search Input
The system SHALL provide a text input field that allows users to search elements by name, symbol, or atomic number.

#### Scenario: Search by element name
- **WHEN** the user types "oxygen" into the search field
- **THEN** the element tile for oxygen SHALL remain visible
- **THEN** all non-matching element tiles SHALL be visually de-emphasized or hidden

#### Scenario: Search by symbol
- **WHEN** the user types "Fe" into the search field
- **THEN** the element tile for iron SHALL remain visible

#### Scenario: Search by atomic number
- **WHEN** the user types "79" into the search field
- **THEN** the element tile for gold SHALL remain visible

#### Scenario: No results
- **WHEN** the user types a query that matches no elements
- **THEN** the system SHALL display a "No elements found" message

### Requirement: Case-Insensitive Search
The search SHALL be case-insensitive.

#### Scenario: Case insensitive matching
- **WHEN** the user types "HE" or "he" or "He"
- **THEN** the element tile for helium SHALL remain visible in all cases

### Requirement: Live Filtering
Filtering SHALL update in real time as the user types, without requiring a button press.

#### Scenario: Real-time filter
- **WHEN** the user types in the search field
- **THEN** the visible elements SHALL update on each keystroke with no noticeable delay
