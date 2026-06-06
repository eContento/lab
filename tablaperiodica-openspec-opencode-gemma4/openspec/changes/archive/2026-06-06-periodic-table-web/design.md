## Context

A single-file periodic table web application for offline educational use. The entire application must work by opening `index.html` in a browser — no server, build tools, or frameworks allowed. The target audience is students and chemistry enthusiasts who need quick access to element data.

## Goals / Non-Goals

**Goals:**
- Self-contained single HTML file with embedded CSS and JS
- Render all 118 elements in standard periodic table grid using CSS Grid
- Pastel color scheme by element category (alkali metals, noble gases, etc.)
- Real-time search filtering by name, symbol, or atomic number
- Detail panel for selected element that doesn't cause table layout shifts
- Lanthanides (57-71) and actinides (89-103) positioned in separate rows below the main table, offset by at least one blank row

**Non-Goals:**
- Server-side rendering or dynamic data fetching
- External dependencies, frameworks, or build tools
- Interactive 3D visualizations or animations
- Printing or PDF export
- Accessibility for screen readers (basic semantic HTML only)

## Decisions

1. **CSS Grid for table layout** — Use a fixed 18-column CSS Grid to position elements. Each element's `grid-column` and `grid-row` are computed from its periodic table position. Lanthanides/actinides get rows 10+ (main table ends at row 9). This avoids JavaScript-based absolute positioning and keeps the layout declarative.
2. **Inline JSON data** — Embed all 118 element records as a JavaScript object inside a `<script>` tag. This keeps the file self-contained. Alternatives (separate JSON file or API call) were rejected because they break offline use.
3. **Category-based pastel colors** — Define a palette of pastel hues keyed to element categories (e.g., alkali metals → soft pink, noble gases → lavender). Colors are assigned in JS when rendering each tile.
4. **Fixed-dimension detail panel** — The element detail panel uses `position: fixed` or `position: absolute` with a set width, placed beside or overlaid on the table. The main grid container has a fixed height/width so adding/removing the panel doesn't cause reflow. The panel slides in/out via CSS transitions.
5. **Search as live filter** — The search box filters elements by checking if the query matches any of name/symbol/number. Matching elements stay fully opaque; non-matching tiles reduce opacity (or show a "no results" state). Filtering happens on `input` event with no debounce needed (small data set).
6. **No JavaScript frameworks** — Vanilla JS only. The periodic table is a small, self-contained UI that doesn't benefit from React/Vue overhead. DOM manipulation is simple enough with `document.createElement` and event delegation.

## Risks / Trade-offs

- [Large single file] → The HTML file could become large (~300KB) due to inline element data. This is acceptable since it's loaded only once and has zero network requests.
- [CSS Grid in very old browsers] → CSS Grid is supported in all modern browsers (Chrome 57+, Firefox 52+, Safari 10.1+). No graceful degradation needed for this use case.
- [No server means no analytics] → Acceptable for an educational tool. Usage tracking is out of scope.
