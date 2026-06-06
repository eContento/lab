## ADDED Requirements

### Requirement: Dark Mode Palette Switching
The render function SHALL detect the active color mode and apply the corresponding palette (pastel for light, saturated for dark) to each element tile.

#### Scenario: Palette selected on render
- **WHEN** `render()` executes
- **THEN** the system SHALL check `document.body.classList.contains('dark')` to determine which palette to use
- **THEN** each element tile SHALL receive the correct background color from the selected palette
