## Why

The periodic table currently only has a light theme. A dark mode toggle lets users switch to a low-light, eye-friendly color scheme, which is especially useful for late-night study sessions.

## What Changes

- Add a toggle button (🌙/☀️) at the top of the page, next to or near the search bar
- Implement dark mode CSS using a `.dark` class on `<body>`, overriding background/text/panel colors
- Store the user's preference in `localStorage` so it persists across visits
- Pastel element tile colors remain unchanged but appear against a dark background
- The detail panel and backdrop also switch to dark styling

## Capabilities

### New Capabilities
- `dark-mode-toggle`: Button and JS logic to toggle dark/light mode, with localStorage persistence

### Modified Capabilities
- `periodic-table-ui`: Add dark mode CSS overrides for body, text, inputs, detail panel, backdrop, and selected element outlines

## Impact

- `index.html` — Add toggle button markup next to the search bar
- `styles.css` — Add `.dark` class CSS overrides for all themed elements
- `app.js` — Add toggle logic, localStorage persistence, and initial mode check
- No changes to `elements.js`
