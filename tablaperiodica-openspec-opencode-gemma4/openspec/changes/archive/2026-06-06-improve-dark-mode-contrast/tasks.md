## 1. Saturated Color Palette

- [x] 1.1 Add `DARK_COLORS` constant in `elements.js` with saturated hex colors for each of the 10 element categories

## 2. Palette Switching in Render

- [x] 2.1 Modify `render()` in `app.js` to detect `.dark` class on `<body>` and use `DARK_COLORS` when dark mode is active
- [x] 2.2 Modify `showDetail()` in `app.js` to use `DARK_COLORS` for the detail icon background in dark mode

## 3. Dark Detail Panel CSS

- [x] 3.1 Update `.dark` CSS variables in `styles.css`: `--panel-bg` to `#1e1e32`, `--text` to `#fff`, `--subtitle` to `#ddd`, `--label` to `#ccc`

## 4. Verify

- [x] 4.1 Toggle to dark mode and verify element tiles show saturated, vibrant colors
- [x] 4.2 Verify white text on element tiles is clearly readable
- [x] 4.3 Open detail panel in dark mode and verify dark background with white text
- [x] 4.4 Toggle back to light mode and verify pastel colors are unchanged
