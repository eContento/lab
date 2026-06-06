## ADDED Requirements

### Requirement: Element Selection
The system SHALL allow the user to click on any element tile to select it and view its details.

#### Scenario: Click to select
- **WHEN** the user clicks on an element tile
- **THEN** a detail panel SHALL appear showing information for that element

### Requirement: Detail Panel Content
The detail panel SHALL display at minimum: atomic number, symbol, name, atomic mass, electron configuration, electronegativity, and category.

#### Scenario: Full detail display
- **WHEN** an element is selected
- **THEN** the detail panel SHALL show the element's atomic number, symbol, name, atomic mass, electron configuration, electronegativity, and category

### Requirement: No Table Resize
The detail panel SHALL appear without causing the periodic table grid to resize or reflow.

#### Scenario: Fixed table layout on detail open
- **WHEN** the detail panel opens
- **THEN** the periodic table grid dimensions (width and height) SHALL remain unchanged from before the panel opened

#### Scenario: Fixed table layout on detail close
- **WHEN** the detail panel closes
- **THEN** the periodic table grid dimensions SHALL remain unchanged

### Requirement: Detail Panel Close
The detail panel SHALL have a close button or mechanism.

#### Scenario: Close detail panel
- **WHEN** the user clicks the close button on the detail panel
- **THEN** the panel SHALL close and the element SHALL be deselected

## ADDED Requirements

### Requirement: Dark Detail Panel Background
When dark mode is active, the detail panel SHALL display a dark background color with white text.

#### Scenario: Dark panel in dark mode
- **WHEN** dark mode is active and the detail panel opens
- **THEN** the detail panel background SHALL be a solid dark color (e.g., #1e1e32)
- **THEN** all text inside the detail panel SHALL be pure white (#fff)

#### Scenario: Detail icon uses dark palette
- **WHEN** dark mode is active and the detail panel opens
- **THEN** the detail icon background SHALL use the saturated dark palette color for the selected element's category
