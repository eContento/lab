## MODIFIED Requirements

### Requirement: Periodic Table Grid Layout
The system SHALL render a periodic table using an 18-column CSS Grid layout with all 118 chemical elements positioned according to their standard group and period.

#### Scenario: Element at correct position
- **WHEN** the page loads
- **THEN** hydrogen (atomic number 1) SHALL be in column 1, row 1 of the grid
- **THEN** helium (atomic number 2) SHALL be in column 18, row 1

#### Scenario: Lanthanides and actinides separated
- **WHEN** the page loads
- **THEN** lanthanide elements (atomic numbers 57–71) SHALL appear in row 9, below the main table with one blank spacer row (row 8) of separation
- **THEN** actinide elements (atomic numbers 89–103) SHALL appear in row 10, immediately below the lanthanide row with no blank row between them

### Requirement: Pastel Color Scheme
The system SHALL assign pastel background colors to element tiles based on their chemical category (alkali metal, alkaline earth metal, transition metal, post-transition metal, metalloid, nonmetal, halogen, noble gas, lanthanide, actinide).

#### Scenario: Same category same color
- **WHEN** an element tile is rendered
- **THEN** its background color SHALL be a soft pastel hue determined by its element category
- **THEN** all elements in the same category SHALL share the same background color

### Requirement: Light Background
The system SHALL use a light or white background for the page and the table container.

#### Scenario: Page background is light
- **WHEN** the page loads
- **THEN** the page background color SHALL be white or a very light neutral shade
