## ADDED Requirements

### Requirement: Periodic Table Grid Layout
The system SHALL render a periodic table using an 18-column CSS Grid layout with all 118 chemical elements positioned according to their standard group and period.

#### Scenario: Element at correct position
- **WHEN** the page loads
- **THEN** hydrogen (atomic number 1) SHALL be in column 1, row 1 of the grid
- **THEN** helium (atomic number 2) SHALL be in column 18, row 1

#### Scenario: Lanthanides and actinides separated
- **WHEN** the page loads
- **THEN** lanthanide elements (atomic numbers 57–71) SHALL appear in rows positioned below the main table with at least one empty row of separation
- **THEN** actinide elements (atomic numbers 89–103) SHALL appear in rows below the lanthanides with at least one empty row of separation

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

## ADDED Requirements

### Requirement: Dark Mode Styling
When dark mode is active, the system SHALL apply dark-themed CSS overrides for the page background, text, search input, detail panel, backdrop, and element tile text.

#### Scenario: Dark mode page background
- **WHEN** dark mode is active
- **THEN** the page background SHALL be a dark color (e.g., #1a1a2e or similar)
- **THEN** the text color SHALL be light (e.g., #eee or similar)

#### Scenario: Dark mode input
- **WHEN** dark mode is active
- **THEN** the search input SHALL have a dark background and light text

#### Scenario: Dark mode detail panel
- **WHEN** dark mode is active
- **THEN** the detail panel SHALL have a dark background with light text

#### Scenario: Dark mode backdrop
- **WHEN** dark mode is active
- **THEN** the backdrop overlay SHALL use a semi-transparent dark color appropriate for dark mode

## ADDED Requirements

### Requirement: Dark Mode Palette Switching
The render function SHALL detect the active color mode and apply the corresponding palette (pastel for light, saturated for dark) to each element tile.

#### Scenario: Palette selected on render
- **WHEN** `render()` executes
- **THEN** the system SHALL check `document.body.classList.contains('dark')` to determine which palette to use
- **THEN** each element tile SHALL receive the correct background color from the selected palette
