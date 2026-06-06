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
