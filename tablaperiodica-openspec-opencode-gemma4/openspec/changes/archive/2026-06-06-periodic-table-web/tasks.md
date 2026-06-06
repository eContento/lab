## 1. Element Data

- [x] 1.1 Embed complete JSON data for all 118 elements as an inline JavaScript object with fields: atomic number, symbol, name, atomic mass, electron configuration, electronegativity, category, group, period, block
- [x] 1.2 Define pastel color palette mapped to each element category (alkali metal, alkaline earth, transition metal, post-transition metal, metalloid, nonmetal, halogen, noble gas, lanthanide, actinide)

## 2. Periodic Table Grid

- [x] 2.1 Create the main HTML structure: search input, periodic table container, detail panel
- [x] 2.2 Implement 18-column CSS Grid layout with elements positioned by group/period
- [x] 2.3 Position lanthanides (57–71) and actinides (89–103) in separate rows below the main table with blank row separation
- [x] 2.4 Assign pastel background colors to element tiles based on category
- [x] 2.5 Style element tiles to show atomic number, symbol, and name

## 3. Search Functionality

- [x] 3.1 Implement real-time search input that filters elements by name, symbol, or atomic number
- [x] 3.2 Visually de-emphasize non-matching elements (reduce opacity or hide)
- [x] 3.3 Show "No elements found" message when search matches zero results

## 4. Element Detail Panel

- [x] 4.1 Implement click handler on element tiles to show detail panel with all element data
- [x] 4.2 Ensure detail panel does not cause the periodic table grid to resize (use fixed positioning or fixed container dimensions)
- [x] 4.3 Add close button to dismiss the detail panel
- [x] 4.4 Style the detail panel with transitions for smooth open/close

## 5. Polish

- [x] 5.1 Verify the page works by opening the HTML file directly in a browser (no server) — single file, no external deps
- [x] 5.2 Verify all 118 elements are rendered with correct positioning — verified via JS: all elements present, correct group/period grid positions
- [x] 5.3 Verify lanthanides/actinides are separated by at least one blank row — spacer rows at 8 and 10 with 24px height
- [x] 5.4 Verify search filters correctly and detail panel opens without table resize — detail panel uses `position: fixed` (no layout reflow)
