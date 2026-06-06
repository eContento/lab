## ADDED Requirements

### Requirement: Theme Toggle Re-renders Table
When the user changes the theme, the system SHALL re-render the periodic table to apply the correct color palette.

#### Scenario: Toggle from light to dark
- **WHEN** the user clicks the theme toggle button while in light mode
- **THEN** the table SHALL re-render with element tile backgrounds from the dark palette

#### Scenario: Toggle from dark to light
- **WHEN** the user clicks the theme toggle button while in dark mode
- **THEN** the table SHALL re-render with element tile backgrounds from the light palette
