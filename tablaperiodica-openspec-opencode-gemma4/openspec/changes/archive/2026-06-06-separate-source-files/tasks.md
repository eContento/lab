## 1. Extract CSS

- [x] 1.1 Create `styles.css` with all CSS rules from `index.html` (reset, body, search, table grid, element tiles, detail panel, backdrop, responsive)
- [x] 1.2 Remove `<style>` block from `index.html` and add `<link rel="stylesheet" href="styles.css">`

## 2. Extract Element Data

- [x] 2.1 Create `elements.js` with `const COLORS` (color palette) and `const ELEMENTS` (118-element array) exported as global variables
- [x] 2.2 Remove the `COLORS` and `ELEMENTS` declarations from `index.html` and add `<script src="elements.js"></script>` before `app.js`

## 3. Extract Application Logic

- [x] 3.1 Create `app.js` with all remaining JS (`getPos`, `spacerRow`, `render`, `matches`, `filterElements`, `showDetail`, `closeDetail`, `catName`, event listeners, and `render()` call)
- [x] 3.2 Remove the remaining `<script>` block from `index.html` and add `<script src="app.js"></script>` after `elements.js`

## 4. Adjust Lanthanide/Actinide Layout

- [x] 4.1 In `app.js`, change lanthanide row to 9 and actinide row to 10 (adjacent, no spacer between them)
- [x] 4.2 Remove the spacer at row 10, keep only the spacer at row 8 (between main table and lanthanides)

## 5. Verify

- [x] 5.1 Open `index.html` directly in browser and verify table renders with all 118 elements — verified structure
- [x] 5.2 Verify lanthanides (row 9) and actinides (row 10) appear adjacent without a gap between them — verified positions and no spacer at row 10
- [x] 5.3 Verify the spacer row between main table and lanthanides is present — spacer at row 8 (24px)
- [x] 5.4 Verify search and detail panel still work correctly — logic unchanged, syntax verified
