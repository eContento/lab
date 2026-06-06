## Context

The periodic table web app currently lives in a single `index.html` file with inline CSS and JS. This was fine for rapid prototyping but makes it harder to navigate, edit, and extend. The lanthanides and actinides have a spacer row between them, but the user wants them adjacent (only separated from the main table, not from each other).

## Goals / Non-Goals

**Goals:**
- Split `index.html` into four files: `index.html` (structure), `styles.css` (presentation), `elements.js` (data), `app.js` (logic)
- All files served from the same directory, loaded via `<link>` and `<script src="...">`
- Page works by opening `index.html` in any browser (no server needed)
- Lanthanides (row 9) and actinides (row 10) adjacent with no gap between them
- Single spacer row (row 8) between main table (row 7) and lanthanides (row 9)

**Non-Goals:**
- No build tools, bundlers, or module systems (keep it simple: regular script tags)
- No behavior changes to existing functionality
- No changes to element data content

## Decisions

1. **Load order: elements.js before app.js** — `app.js` references `ELEMENTS` and `COLORS`, which are defined in `elements.js`. Both are `<script>` tags in `index.html` with no `defer`/`module` attributes, ensuring synchronous top-down execution. `elements.js` defines globals, then `app.js` uses them.
2. **No ES modules** — Using `<script type="module">` would require a server due to CORS restrictions on `file://`. Regular `<script>` tags keep offline functionality intact.
3. **Lanthanides at row 9, actinides at row 10** — The spacer row (row 8) separates the main table from the lanthanide row. Actinides go directly at row 10, immediately below lanthanides at row 9, with no spacer between them. The grid gap (3px) is the only visual separation between the two F-block rows.
4. **CSS unchanged** — The exact same CSS rules are moved verbatim to `styles.css`. No refactoring or renaming of classes/IDs.
5. **JS unchanged** — The exact same JS logic is split between `elements.js` (data + colors) and `app.js` (everything else). No refactoring.

## Risks / Trade-offs

- [Script load order] → If script tags are reordered, `app.js` will throw `ReferenceError` for undefined `ELEMENTS`/`COLORS`. Mitigation: load `elements.js` first in `<head>` or before `app.js` in `<body>`.
