## Why

Students and chemistry enthusiasts need an interactive, visually appealing periodic table that works entirely offline without a web server — a single HTML file that can be opened in any modern browser. This provides a low-friction educational tool for learning element properties.

## What Changes

- Create a single-file, self-contained periodic table web application (HTML + CSS + JavaScript, no dependencies)
- Implement a search box to filter elements by name, symbol, or atomic number
- Show element details in a side panel that doesn't cause the table to resize
- Render 118 elements in standard periodic table layout with pastel color coding by group
- Separate lanthanides and actinides from the main table with at least one row of spacing
- Use light/white background with soft pastel element tiles

## Capabilities

### New Capabilities
- `periodic-table-ui`: Main periodic table grid rendering with pastel color scheme, correct element positioning, and lanthanide/actinide row separation
- `element-search`: Real-time search and filter of elements by name, symbol, or atomic number
- `element-detail`: Side/detail panel that shows full element information without causing the table to resize
- `element-data`: Static data source providing all 118 element properties (name, symbol, atomic number, mass, category, configuration, etc.)

### Modified Capabilities
- None (no existing specs)

## Impact

- New directory: `periodic-table/` containing a single `index.html` file (and possibly a separate `elements.json` for data, though embedding inline is preferred)
- No new dependencies, build tools, or server requirements
- No impact on existing code
