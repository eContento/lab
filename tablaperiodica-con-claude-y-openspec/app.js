'use strict';

const CAT_LABELS = {
  'alkali-metal':     'Metal alcalino',
  'alkaline-earth':   'Metal alcalino-térreo',
  'transition-metal': 'Metal de transición',
  'post-transition':  'Metal del bloque p',
  'metalloid':        'Metaloide',
  'nonmetal':         'No metal',
  'halogen':          'Halógeno',
  'noble-gas':        'Gas noble',
  'lanthanide':       'Lantánido',
  'actinide':         'Actínido',
  'unknown':          'Prop. desconocidas',
};

const STATE_LABELS = {
  's': 'Sólido',
  'l': 'Líquido',
  'g': 'Gas',
  'u': 'Desconocido',
};

document.addEventListener('DOMContentLoaded', init);

function init() {
  initTheme();
  buildGroupHeader();
  buildMainTable();
  buildFBlock();
  buildLegend();
  setupSearch();
  document.getElementById('btn-close').addEventListener('click', closePanel);
  document.getElementById('theme-toggle').addEventListener('click', toggleTheme);
}

/* ----------------------------------------------------------------
   Tema claro / oscuro
   ---------------------------------------------------------------- */
function initTheme() {
  var saved = localStorage.getItem('theme');
  var prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
  var isDark = saved === 'dark' || (!saved && prefersDark);
  document.documentElement.setAttribute('data-theme', isDark ? 'dark' : 'light');
  updateToggleButton(isDark);
}

function toggleTheme() {
  var isDark = document.documentElement.getAttribute('data-theme') === 'dark';
  var next = isDark ? 'light' : 'dark';
  document.documentElement.setAttribute('data-theme', next);
  localStorage.setItem('theme', next);
  updateToggleButton(next === 'dark');
}

function updateToggleButton(isDark) {
  var btn = document.getElementById('theme-toggle');
  if (!btn) return;
  btn.textContent = isDark ? '☀️ Claro' : '🌙 Oscuro';
  btn.setAttribute('aria-label', isDark ? 'Cambiar a modo claro' : 'Cambiar a modo oscuro');
}

/* ----------------------------------------------------------------
   Cabecera de números de grupo (1-18)
   ---------------------------------------------------------------- */
function buildGroupHeader() {
  const header = document.getElementById('group-header');
  for (let g = 1; g <= 18; g++) {
    const span = document.createElement('div');
    span.className = 'group-label';
    span.style.gridColumn = g;
    span.textContent = g;
    header.appendChild(span);
  }
}

/* ----------------------------------------------------------------
   Tabla principal (bloques s, p, d — periodos 1-7)
   ---------------------------------------------------------------- */
function buildMainTable() {
  const grid = document.getElementById('main-table');

  // Celdas de referencia para el bloque f
  grid.appendChild(createPlaceholder(6, 3, '57-71',  'lanthanide'));
  grid.appendChild(createPlaceholder(7, 3, '89-103', 'actinide'));

  ELEMENTS.forEach(el => {
    if (el.cat === 'lanthanide' || el.cat === 'actinide') return;
    const cell = createCell(el);
    cell.style.gridColumn = el.group;
    cell.style.gridRow    = el.period;
    grid.appendChild(cell);
  });
}

/* ----------------------------------------------------------------
   Bloque f separado (lantánidos + actínidos)
   ---------------------------------------------------------------- */
function buildFBlock() {
  const grid = document.getElementById('f-block');

  // Etiquetas de fila
  grid.appendChild(createFBlockLabel('Lantánidos', 1));
  grid.appendChild(createFBlockLabel('Actínidos',  2));

  ELEMENTS.forEach(el => {
    if (el.cat !== 'lanthanide' && el.cat !== 'actinide') return;
    const cell = createCell(el);
    if (el.cat === 'lanthanide') {
      cell.style.gridColumn = el.z - 57 + 2; // col 2-16, fila 1
      cell.style.gridRow    = 1;
    } else {
      cell.style.gridColumn = el.z - 89 + 2; // col 2-16, fila 2
      cell.style.gridRow    = 2;
    }
    grid.appendChild(cell);
  });
}

/* ----------------------------------------------------------------
   Creación de celdas
   ---------------------------------------------------------------- */
function createCell(el) {
  const cell = document.createElement('div');
  cell.className = `element-cell cat-${el.cat}`;
  cell.dataset.z   = String(el.z);
  cell.dataset.name = el.name.toLowerCase();
  cell.dataset.sym  = el.sym.toLowerCase();
  cell.innerHTML = `
    <span class="el-number">${el.z}</span>
    <span class="el-symbol">${el.sym}</span>
    <span class="el-name">${el.name}</span>
  `;
  cell.addEventListener('click', () => selectElement(el, cell));
  return cell;
}

function createPlaceholder(period, group, text, cat) {
  const cell = document.createElement('div');
  cell.className = `element-cell placeholder cat-${cat}`;
  cell.style.gridColumn = group;
  cell.style.gridRow    = period;
  cell.innerHTML = `<span class="el-symbol" style="font-size:10px;line-height:1.3">${text}</span>`;
  return cell;
}

function createFBlockLabel(text, row) {
  const div = document.createElement('div');
  div.className = 'fblock-label';
  div.style.gridColumn = 1;
  div.style.gridRow    = row;
  div.textContent = text;
  return div;
}

/* ----------------------------------------------------------------
   Leyenda de categorías
   ---------------------------------------------------------------- */
function buildLegend() {
  const legend = document.getElementById('legend');
  Object.entries(CAT_LABELS).forEach(([key, label]) => {
    const item = document.createElement('div');
    item.className = 'legend-item';
    item.innerHTML = `<div class="legend-swatch cat-${key}"></div><span>${label}</span>`;
    legend.appendChild(item);
  });
}

/* ----------------------------------------------------------------
   Buscador en tiempo real
   Las celdas no coincidentes reciben la clase "dimmed" (solo opacidad),
   el DOM y el layout permanecen inalterados.
   ---------------------------------------------------------------- */
function setupSearch() {
  document.getElementById('search').addEventListener('input', e => {
    const q = e.target.value.toLowerCase().trim();
    document.querySelectorAll('.element-cell:not(.placeholder)').forEach(cell => {
      if (!q) {
        cell.classList.remove('dimmed');
        return;
      }
      const match = cell.dataset.name.includes(q)
                 || cell.dataset.sym.includes(q)
                 || cell.dataset.z.includes(q);
      cell.classList.toggle('dimmed', !match);
    });
  });
}

/* ----------------------------------------------------------------
   Panel de detalles
   El panel no ocupa espacio hasta que se selecciona un elemento.
   La clase .panel-open en .page-layout activa la columna del panel.
   ---------------------------------------------------------------- */
function selectElement(el, clickedCell) {
  // Quitar selección previa
  document.querySelectorAll('.element-cell.selected')
    .forEach(c => c.classList.remove('selected'));
  clickedCell.classList.add('selected');

  // Rellenar campos del panel
  document.getElementById('d-number').textContent = el.z;
  document.getElementById('d-symbol').textContent = el.sym;
  document.getElementById('d-name').textContent   = el.name;
  document.getElementById('d-mass').textContent   = el.mass + ' u';
  document.getElementById('d-category').textContent = CAT_LABELS[el.cat] || el.cat;
  document.getElementById('d-period').textContent = el.period;
  document.getElementById('d-group').textContent  = el.group !== null ? el.group : '—';
  document.getElementById('d-config').textContent = el.config;
  document.getElementById('d-en').textContent     = el.en !== null ? el.en.toFixed(2) : '—';
  document.getElementById('d-state').textContent  = STATE_LABELS[el.state] || '—';

  // Aplicar color de categoría a la cabecera del panel
  const panel = document.getElementById('detail-panel');
  panel.querySelector('.panel-header').className = `panel-header cat-${el.cat}`;

  // Mostrar panel: activar columna en el layout y mostrar contenido
  document.querySelector('.page-layout').classList.add('panel-open');
  document.getElementById('panel-content').hidden = false;
}

function closePanel() {
  document.querySelectorAll('.element-cell.selected')
    .forEach(c => c.classList.remove('selected'));

  // Ocultar panel: desactivar columna en el layout y ocultar contenido
  document.querySelector('.page-layout').classList.remove('panel-open');
  document.getElementById('panel-content').hidden = true;

  document.getElementById('detail-panel').querySelector('.panel-header').className = 'panel-header';
}
