## ADDED Requirements

### Requirement: Dark/Light Mode Toggle
The system SHALL provide a button that toggles the page between dark mode and light mode.

#### Scenario: Toggle to dark mode
- **WHEN** the user clicks the toggle button
- **THEN** the page SHALL switch to dark mode (dark background, light text)
- **THEN** the button icon SHALL change to indicate the current mode

#### Scenario: Toggle back to light mode
- **WHEN** the user clicks the toggle button while in dark mode
- **THEN** the page SHALL switch back to light mode

### Requirement: Preference Persistence
The system SHALL persist the user's theme preference using localStorage.

#### Scenario: Preference saved
- **WHEN** the user toggles the theme
- **THEN** the preference SHALL be saved to localStorage

#### Scenario: Preference restored on reload
- **WHEN** the page loads after a theme toggle was performed
- **THEN** the page SHALL render with the previously selected theme

#### Scenario: OS preference as default
- **WHEN** the page loads and no localStorage preference exists
- **THEN** the system SHALL respect the OS-level `prefers-color-scheme` setting as the initial theme
