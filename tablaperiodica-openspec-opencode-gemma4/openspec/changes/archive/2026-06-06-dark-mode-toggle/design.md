## Context

The periodic table uses a light theme (#f8f9fa background, #333 text). Adding dark mode requires CSS overrides for all themed elements and a small JS controller with localStorage persistence.

## Goals / Non-Goals

**Goals:**
- Toggle button at the top of the page, clearly visible
- Dark mode: dark background (#1a1a2e or similar), light text (#eee), adapted panel/input styles
- Element tile pastel colors remain the same but "pop" against the dark background
- Preference saved to `localStorage` under key `theme` (values: `"dark"` | `"light"`)
- Respect `prefers-color-scheme` media query as initial default
- Smooth CSS transitions between modes

**Non-Goals:**
- No custom color picker or theme configuration UI
- No server-side persistence (localStorage only)
- No animation of the toggle itself beyond CSS transitions

## Decisions

1. **CSS custom properties (variables)** — Define all theme colors as `--var-name` on `:root` (light) and `.dark` (dark). This keeps overrides in one place and avoids repetitive selector chains. Fallback to hardcoded values for browsers without CSS var support.
2. **Class on `<body>`** — The `.dark` class is toggled on `<body>`. All dark rules use `body.dark ...` or `.dark ...` selectors. This is simpler than `data-theme` attributes and works identically.
3. **localStorage + media query** — On load, check `localStorage.theme`. If absent, use `window.matchMedia('(prefers-color-scheme: dark)')`. This respects the OS setting as a default while allowing manual override.
4. **Button as emoji toggle** — A `<button>` with `🌙` / `☀️` text, swapped on toggle. No icon library needed. Placed next to the search bar.

## Risks / Trade-offs

- [CSS specificity conflicts] → Using `.dark` as a parent selector requires the same specificity for all overrides. Risk is low since all dark rules are explicitly namespaced under `.dark`.
- [localStorage unavailable] → If localStorage is blocked (private browsing), the toggle still works but the preference won't persist. Graceful degradation: the feature simply defaults to light mode on each page load.
