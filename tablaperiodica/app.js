/* ── Constants ─────────────────────────────────────────────────── */

const CATEGORY_LABELS = {
  'metal-alcalino':         'Metal alcalino',
  'metal-alcalinoterreo':   'Metal alcalinoterreo',
  'metal-transicion':       'Metal de transición',
  'metal-post-transicion':  'Metal post-transición',
  'metaloide':              'Metaloide',
  'no-metal':               'No metal',
  'halogeno':               'Halógeno',
  'gas-noble':              'Gas noble',
  'lantanido':              'Lantánido',
  'actinido':               'Actínido',
};

const STATE_LABELS = {
  solid:   'Sólido',
  liquid:  'Líquido',
  gas:     'Gas',
  unknown: 'Desconocido',
};

/* ── State ─────────────────────────────────────────────────────── */

let selectedElement = null;

/* ── DOM Refs ──────────────────────────────────────────────────── */

const $table       = document.getElementById('periodic-table');
const $search      = document.getElementById('search');
const $clearBtn    = document.getElementById('clear-search');
const $themeBtn    = document.getElementById('theme-toggle');
const $noResults   = document.getElementById('no-results');
const $legend      = document.getElementById('legend');
const $panel       = document.getElementById('detail-panel');
const $backdrop    = document.getElementById('panel-backdrop');
const $closeBtn    = document.getElementById('close-detail');

/* ── Render Table ──────────────────────────────────────────────── */

function renderTable() {
  const fragment = document.createDocumentFragment();

  /* Placeholders for lanthanide/actinide series in main table */
  const laPlaceholder = makePlaceholder('57–71', 6, 3);
  const acPlaceholder = makePlaceholder('89–103', 7, 3);
  fragment.appendChild(laPlaceholder);
  fragment.appendChild(acPlaceholder);

  ELEMENTS.forEach(el => {
    if (el.group === null) {
      /* Lanthanide (period 6) or actinide (period 7) */
      const isLanthanide = el.period === 6;
      const gridRow  = isLanthanide ? 9 : 10;
      const gridCol  = isLanthanide
        ? el.atomicNumber - 54   /* La=57 → col 3 … Lu=71 → col 17 */
        : el.atomicNumber - 86;  /* Ac=89 → col 3 … Lr=103 → col 17 */
      fragment.appendChild(makeCell(el, gridRow, gridCol));
    } else {
      fragment.appendChild(makeCell(el, el.period, el.group));
    }
  });

  $table.appendChild(fragment);
}

function makeCell(el, gridRow, gridCol) {
  const cell = document.createElement('div');
  cell.className = `element-cell cat-${el.category}`;
  cell.setAttribute('role', 'gridcell');
  cell.setAttribute('aria-label', `${el.name} (${el.symbol}), número atómico ${el.atomicNumber}`);
  cell.dataset.atomicNumber = el.atomicNumber;
  cell.style.cssText = `grid-row:${gridRow};grid-column:${gridCol}`;

  cell.innerHTML = `
    <span class="cell-number">${el.atomicNumber}</span>
    <span class="cell-symbol">${el.symbol}</span>
    <span class="cell-name">${el.name}</span>
  `;

  cell.addEventListener('click', () => showDetail(el, cell));
  return cell;
}

function makePlaceholder(label, period, group) {
  const el = document.createElement('div');
  el.className = 'placeholder-cell';
  el.style.cssText = `grid-row:${period};grid-column:${group}`;
  el.textContent = label;
  el.setAttribute('aria-hidden', 'true');
  return el;
}

/* ── Legend ────────────────────────────────────────────────────── */

function renderLegend() {
  const fragment = document.createDocumentFragment();
  Object.entries(CATEGORY_LABELS).forEach(([key, label]) => {
    const item = document.createElement('div');
    item.className = 'legend-item';

    const swatch = document.createElement('span');
    swatch.className = 'legend-swatch';
    swatch.style.background = getCategoryColor(key);

    const text = document.createElement('span');
    text.textContent = label;

    item.appendChild(swatch);
    item.appendChild(text);
    fragment.appendChild(item);
  });
  $legend.appendChild(fragment);
}

function getCategoryColor(category) {
  const style = getComputedStyle(document.documentElement);
  return style.getPropertyValue(`--cat-${category}`).trim();
}

/* ── Detail Panel ──────────────────────────────────────────────── */

function showDetail(el, cellEl) {
  /* Deactivate previous */
  if (selectedElement) {
    const prev = $table.querySelector(`[data-atomic-number="${selectedElement.atomicNumber}"]`);
    if (prev) prev.classList.remove('active');
  }

  selectedElement = el;
  cellEl.classList.add('active');

  /* Fill panel fields */
  document.getElementById('d-atomic-number').textContent = `Nº ${el.atomicNumber}`;
  document.getElementById('d-symbol').textContent = el.symbol;
  document.getElementById('d-name').textContent = el.name;

  const badge = document.getElementById('d-category-badge');
  badge.textContent = CATEGORY_LABELS[el.category] || el.category;
  badge.style.background = `color-mix(in srgb, var(--cat-${el.category}) 60%, transparent)`;

  document.getElementById('d-mass').textContent = el.atomicMass != null ? el.atomicMass + ' u' : 'N/A';
  document.getElementById('d-period-group').textContent =
    el.group != null ? `Período ${el.period} / Grupo ${el.group}` : `Período ${el.period} / —`;
  document.getElementById('d-config').textContent = el.electronConfiguration || 'N/A';
  document.getElementById('d-state').textContent = STATE_LABELS[el.standardState] || 'N/A';
  document.getElementById('d-melting').textContent = el.meltingPoint != null ? el.meltingPoint + ' K' : 'N/A';
  document.getElementById('d-boiling').textContent = el.boilingPoint != null ? el.boilingPoint + ' K' : 'N/A';
  document.getElementById('d-summary').textContent = el.summary || '';

  $panel.hidden = false;
  $backdrop.hidden = false;
}

function closeDetail() {
  if (selectedElement) {
    const prev = $table.querySelector(`[data-atomic-number="${selectedElement.atomicNumber}"]`);
    if (prev) prev.classList.remove('active');
    selectedElement = null;
  }
  $panel.hidden = true;
  $backdrop.hidden = true;
}

/* ── Search / Filter ───────────────────────────────────────────── */

function normalize(str) {
  return str.normalize('NFD').replace(/[̀-ͯ]/g, '').toLowerCase();
}

function filterElements(query) {
  const q = normalize(query.trim());
  const cells = $table.querySelectorAll('.element-cell');
  let matchCount = 0;

  if (!q) {
    cells.forEach(cell => { cell.classList.remove('dimmed', 'match'); });
    $noResults.hidden = true;
    $clearBtn.hidden = true;
    return;
  }

  $clearBtn.hidden = false;

  cells.forEach(cell => {
    const atomicNumber = cell.dataset.atomicNumber;
    const el = ELEMENTS.find(e => e.atomicNumber === +atomicNumber);
    if (!el) return;

    const matches =
      normalize(el.name).includes(q) ||
      normalize(el.symbol).includes(q) ||
      String(el.atomicNumber).startsWith(q);

    if (matches) {
      cell.classList.remove('dimmed');
      cell.classList.add('match');
      matchCount++;
    } else {
      cell.classList.add('dimmed');
      cell.classList.remove('match');
    }
  });

  $noResults.hidden = matchCount > 0;
}

/* ── Theme ─────────────────────────────────────────────────────── */

function applyTheme(theme) {
  document.documentElement.setAttribute('data-theme', theme);
  localStorage.setItem('theme', theme);
  /* Re-render legend swatches so CSS vars are read after theme switch */
  $legend.innerHTML = '';
  renderLegend();
}

function toggleTheme() {
  const current = document.documentElement.getAttribute('data-theme');
  applyTheme(current === 'dark' ? 'light' : 'dark');
}

function initTheme() {
  const saved = localStorage.getItem('theme');
  if (saved) {
    applyTheme(saved);
  } else {
    const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
    applyTheme(prefersDark ? 'dark' : 'light');
  }
}

/* ── Event Listeners ───────────────────────────────────────────── */

$search.addEventListener('input', () => filterElements($search.value));

$clearBtn.addEventListener('click', () => {
  $search.value = '';
  filterElements('');
  $search.focus();
});

$themeBtn.addEventListener('click', toggleTheme);
$closeBtn.addEventListener('click', closeDetail);
$backdrop.addEventListener('click', closeDetail);

document.addEventListener('keydown', e => {
  if (e.key === 'Escape' && !$panel.hidden) closeDetail();
});

/* ── Init ──────────────────────────────────────────────────────── */

initTheme();
renderTable();
renderLegend();
