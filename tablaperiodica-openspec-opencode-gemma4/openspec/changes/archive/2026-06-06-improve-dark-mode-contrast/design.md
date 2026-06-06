## Context

The dark mode currently uses the same pastel `COLORS` palette as light mode. Pastel colors on a dark background appear washed out and don't contrast well with white element text. The detail panel uses `#2a2a3e` background with `#e0e0e0` text, which is gray-on-darkgray — not ideal.

## Goals / Non-Goals

**Goals:**
- Saturated, vibrant element tile colors when dark mode is active
- White text on element tiles stays clearly readable
- Detail panel has a dark (`#1e1e32`) background with pure white (`#fff`) text
- Dark mode detection for palette switching uses the existing `.dark` class on `<body>`

**Non-Goals:**
- No changes to light mode colors
- No changes to the toggle button or localStorage logic
- No CSS-only approach (needs JS to pick palette)

## Decisions

1. **`DARK_COLORS` parallel palette** — A new `DARK_COLORS` object in `elements.js` mirrors `COLORS` but with saturated hex values (e.g., alkali metals: `#ff4444` instead of `#ffc4c4`). The `render()` and `showDetail()` functions check `document.body.classList.contains('dark')` and pick the appropriate palette. This is simpler than CSS filter approaches and gives precise control over each color.
2. **CSS variable tweaks only** — `--panel-bg` goes from `#2a2a3e` to `#1e1e32`, `--text`, `--subtitle`, `--label` go to `#fff` for the detail panel. Element tile text is white regardless of mode (it already has good contrast on both pastel and saturated colors).
3. **Same text color in dark mode** — The element tile text (number, symbol, name) stays white (`#fff`) in both modes since it needs to contrast with the saturated dark-mode tile backgrounds.

## Risks / Trade-offs

- [JS class check on every render] → `render()` already iterates all 118 elements, checking `body.classList.contains('dark')` is negligible overhead.
- [Dual palette maintenance] → Adding a color to light mode means adding the matching saturated version to dark mode. Low risk since the palette rarely changes.
