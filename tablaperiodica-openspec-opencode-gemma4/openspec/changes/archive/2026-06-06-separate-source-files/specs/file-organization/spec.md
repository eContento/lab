## ADDED Requirements

### Requirement: Separate Source Files
The system SHALL distribute its source code across four files: `index.html` (HTML structure), `styles.css` (presentation styles), `elements.js` (element data), and `app.js` (application logic).

#### Scenario: HTML loads external resources
- **WHEN** the browser opens `index.html`
- **THEN** it SHALL load `styles.css` via a `<link>` element
- **THEN** it SHALL load `elements.js` via a `<script>` element
- **THEN** it SHALL load `app.js` via a `<script>` element

#### Scenario: Offline operation
- **WHEN** `index.html` is opened from the local filesystem (`file://` protocol)
- **THEN** the page SHALL render correctly without any server or HTTP request

### Requirement: Script Load Order
`elements.js` SHALL be loaded and executed before `app.js`.

#### Scenario: Variables available at runtime
- **WHEN** `app.js` executes
- **THEN** the `ELEMENTS` and `COLORS` constants from `elements.js` SHALL be available in the global scope
