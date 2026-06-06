## ADDED Requirements

### Requirement: Saturated Dark Mode Colors
When dark mode is active, the system SHALL use a saturated, high-contrast color palette for element tile backgrounds instead of the pastel light-mode palette.

#### Scenario: Dark mode uses saturated palette
- **WHEN** the `.dark` class is present on `<body>`
- **THEN** each element tile SHALL display a background color from the saturated dark palette, keyed by its chemical category

#### Scenario: Light mode unchanged
- **WHEN** the `.dark` class is absent from `<body>`
- **THEN** element tile backgrounds SHALL use the standard pastel palette
