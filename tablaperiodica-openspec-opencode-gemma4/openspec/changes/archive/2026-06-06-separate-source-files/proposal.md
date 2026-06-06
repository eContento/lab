## Why

The current periodic table is a single monolithic HTML file. Splitting it into separate files improves maintainability, readability, and separation of concerns. This also prepares the project for future enhancements by making each concern independently editable.

## What Changes

- Create `styles.css` with all CSS styles extracted from `index.html`
- Create `elements.js` with the 118-element data array (`ELEMENTS`) and color palette (`COLORS`)
- Create `app.js` with all application logic (render, search, detail panel)
- Modify `index.html` to load the three external files via `<link>` and `<script src="...">` instead of inline code
- Change lanthanide/actinide positioning: maintain a spacer row between the main table and the lanthanides, but remove the gap between the lanthanide and actinide rows (they should be adjacent)

**BREAKING**: The file structure changes from a single `index.html` to four files. The page must still work by opening `index.html` in a browser.

## Capabilities

### New Capabilities
- `file-organization`: Multi-file project structure with HTML, CSS, and JS separated into dedicated files
- `lanthanide-actinide-layout`: Lanthanides and actinides positioned as two adjacent rows below the main table, with a single spacer row between the main table and the lanthanide row

### Modified Capabilities
- `periodic-table-ui`: Update to reference external CSS/JS files instead of inline code

## Impact

- `index.html` — Stripped of inline `<style>` and `<script>`, replaced with `<link rel="stylesheet" href="styles.css">` and two `<script src="...">` tags
- New file: `styles.css` — All CSS from the former `<style>` block
- New file: `elements.js` — `const COLORS` + `const ELEMENTS` arrays
- New file: `app.js` — All JS logic (`render`, `matches`, `filterElements`, `showDetail`, `closeDetail`, event listeners)
- `elements.js` must define variables before `app.js` references them (load order matters)
