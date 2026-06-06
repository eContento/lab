## 1. CSS Dark Mode Overrides

- [x] 1.1 Add CSS custom properties for theme colors on `:root` (light) and `.dark` (dark) for background, text, input, panel, and backdrop
- [x] 1.2 Add `.dark` class overrides for body background/text, search input, detail panel, backdrop, close button hover, and element selected outline
- [x] 1.3 Ensure all dark mode colors provide sufficient contrast

## 2. HTML Toggle Button

- [x] 2.1 Add toggle button (`<button id="theme-toggle">🌙</button>`) to `index.html` near the search bar

## 3. JavaScript Toggle Logic

- [x] 3.1 Add theme toggle logic in `app.js`: query the button, toggle `.dark` class on `<body>`, swap button text between 🌙/☀️, save preference to `localStorage`
- [x] 3.2 Add initial theme detection: check `localStorage.theme`, fall back to `prefers-color-scheme` media query, default to light

## 4. Verify

- [x] 4.1 Open in browser and verify toggle switches between dark and light modes
- [x] 4.2 Verify dark mode has no unreadable text or invisible elements
- [x] 4.3 Verify preference persists after page refresh
- [x] 4.4 Verify clicking the toggle again returns to light mode
