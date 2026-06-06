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
