## ADDED Requirements

### Requirement: Dark Mode Muted Color Palette
When dark mode is active, the system SHALL use a muted, low-saturation color palette for element tile backgrounds instead of the pastel light-mode palette or saturated colors.

#### Scenario: Dark mode uses muted palette
- **WHEN** the `.dark` class is present on `<body>`
- **THEN** each element tile SHALL display a background color from the muted dark palette, keyed by its chemical category

#### Scenario: Light mode unchanged
- **WHEN** the `.dark` class is absent from `<body>`
- **THEN** element tile backgrounds SHALL use the standard pastel palette
