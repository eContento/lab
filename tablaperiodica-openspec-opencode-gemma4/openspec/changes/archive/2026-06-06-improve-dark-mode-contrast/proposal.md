## Why

In dark mode, the pastel element tile colors lack saturation against the dark background, and the white text on them doesn't pop. The detail panel also needs a fully dark background with white text instead of gray tones.

## What Changes

- Add a `DARK_COLORS` palette with highly saturated, vibrant colors for each element category, used when dark mode is active
- Modify `render()` in `app.js` to detect dark mode and switch to `DARK_COLORS` for tile backgrounds and detail panel icon
- Update CSS variables so the detail panel shows solid dark background (`#1e1e32`) with pure white text (`#fff`) in dark mode

## Capabilities

### New Capabilities
- `dark-mode-saturated-colors`: Saturated color palette for element tiles in dark mode for better contrast

### Modified Capabilities
- `periodic-table-ui`: Update rendering to use `DARK_COLORS` when `.dark` class is present
- `element-detail`: Detail panel background and text use proper dark theme colors

## Impact

- `elements.js` — Add `const DARK_COLORS` map with saturated hex values
- `app.js` — Modify `render()` and `showDetail()` to check `.dark` class and select the right palette
- `styles.css` — Update `.dark` CSS variables for panel background (#1e1e32) and text (#fff)
