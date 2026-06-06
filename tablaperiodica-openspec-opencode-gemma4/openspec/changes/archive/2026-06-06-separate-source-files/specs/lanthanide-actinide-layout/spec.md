## ADDED Requirements

### Requirement: Adjacent F-Block Rows
Lanthanide and actinide rows SHALL be positioned adjacent to each other (no gap between them) while remaining separated from the main periodic table grid by at least one blank row.

#### Scenario: Lanthanides and actinides adjacent
- **WHEN** the page loads
- **THEN** lanthanide elements (57–71) SHALL appear in row 9 of the grid
- **THEN** actinide elements (89–103) SHALL appear in row 10 of the grid, directly below the lanthanide row without any spacer row between them

#### Scenario: Spacer before lanthanides
- **WHEN** the page loads
- **THEN** there SHALL be at least one empty row (row 8) between the main table (rows 1–7) and the lanthanide row (row 9)
