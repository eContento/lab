function getPos(el) {
  if (el.cat === "lanthanide") return { col: el.n - 57 + 3, row: 9 };
  if (el.cat === "actinide")   return { col: el.n - 89 + 3, row: 10 };
  return { col: el.g, row: el.p };
}

const container = document.getElementById("periodic-table");
const searchInput = document.getElementById("search");
const noResults = document.getElementById("no-results");
const detailPanel = document.getElementById("detail-panel");
const backdrop = document.getElementById("detail-backdrop");
let selectedEl = null;
let currentFilter = "";

ELEMENTS.forEach(el => {
  const pos = getPos(el);
  el._col = pos.col;
  el._row = pos.row;
});

function spacerRow(row, h) {
  const d = document.createElement("div");
  d.style.gridColumn = "1 / 19";
  d.style.gridRow = row;
  d.style.height = h;
  d.style.pointerEvents = "none";
  container.appendChild(d);
}

function getColors() {
  return document.body.classList.contains("dark") ? DARK_COLORS : COLORS;
}

function render() {
  container.innerHTML = "";
  spacerRow(8, "24px");
  const palette = getColors();
  ELEMENTS.forEach(el => {
    const div = document.createElement("div");
    div.className = "element" + (currentFilter && !matches(el, currentFilter) ? " hidden" : "");
    div.style.gridColumn = el._col;
    div.style.gridRow = el._row;
    div.style.background = palette[el.cat] || "#eee";
    div.dataset.n = el.n;
    div.innerHTML = `<span class="number">${el.n}</span><span class="symbol">${el.s}</span><span class="ename">${el.en}</span>`;
    div.addEventListener("click", () => showDetail(el));
    container.appendChild(div);
  });
  noResults.style.display = ELEMENTS.some(e => !currentFilter || matches(e, currentFilter)) ? "none" : "block";
}

function matches(el, q) {
  const lq = q.toLowerCase();
  return el.s.toLowerCase().includes(lq) || el.en.toLowerCase().includes(lq) || String(el.n).includes(lq);
}

function filterElements(q) {
  currentFilter = q;
  if (selectedEl) closeDetail();
  document.querySelectorAll(".element").forEach(el => {
    const n = parseInt(el.dataset.n);
    const elem = ELEMENTS.find(e => e.n === n);
    const match = !q || matches(elem, q);
    el.classList.toggle("hidden", !match);
  });
  noResults.style.display = ELEMENTS.some(e => !q || matches(e, q)) ? "none" : "block";
}

function showDetail(el) {
  selectedEl = el;
  document.querySelectorAll(".element").forEach(e => e.classList.remove("selected"));
  const tile = document.querySelector(`.element[data-n="${el.n}"]`);
  if (tile) tile.classList.add("selected");

  const palette = getColors();
  detailPanel.innerHTML = `
    <button class="close-btn">&times;</button>
    <div class="detail-header">
      <div class="detail-icon" style="background:${palette[el.cat] || '#eee'}">
        <span class="dnum">${el.n}</span>
        <span class="dsym">${el.s}</span>
        <span class="dname">${el.en}</span>
      </div>
      <div class="detail-header-info">
        <h2>${el.en}</h2>
        <div class="dsubtitle">${el.s} &middot; N° ${el.n} &middot; ${catName(el.cat)}</div>
      </div>
    </div>
    <div class="detail-body">
      <div><div class="dlabel">Masa atómica</div><div class="dvalue">${el.m}</div></div>
      <div><div class="dlabel">Configuración</div><div class="dvalue">${el.cfg}</div></div>
      <div><div class="dlabel">Electronegatividad</div><div class="dvalue">${el.eneg !== null ? el.eneg : "—"}</div></div>
      <div><div class="dlabel">Grupo</div><div class="dvalue">${el.g}</div></div>
      <div><div class="dlabel">Periodo</div><div class="dvalue">${el.p}</div></div>
      <div><div class="dlabel">Bloque</div><div class="dvalue">${el.b}</div></div>
    </div>`;
  detailPanel.querySelector(".close-btn").addEventListener("click", closeDetail);
  detailPanel.classList.add("open");
  backdrop.classList.add("open");
  backdrop.onclick = closeDetail;
}

function closeDetail() {
  detailPanel.classList.remove("open");
  backdrop.classList.remove("open");
  document.querySelectorAll(".element").forEach(e => e.classList.remove("selected"));
  selectedEl = null;
}

function catName(cat) {
  const names = {
    "nonmetal":"No metal","noble-gas":"Gas noble","alkali-metal":"Metal alcalino",
    "alkaline-earth-metal":"Metal alcalinotérreo","metalloid":"Metaloide",
    "halogen":"Halógeno","post-transition-metal":"Metal del bloque p",
    "transition-metal":"Metal de transición","lanthanide":"Lantánido","actinide":"Actínido"
  };
  return names[cat] || cat;
}

searchInput.addEventListener("input", e => filterElements(e.target.value));

function getPreferredTheme() {
  const saved = localStorage.getItem("theme");
  if (saved === "dark" || saved === "light") return saved;
  return window.matchMedia("(prefers-color-scheme: dark)").matches ? "dark" : "light";
}

function applyTheme(theme) {
  document.body.classList.toggle("dark", theme === "dark");
  toggleBtn.textContent = theme === "dark" ? "☀️" : "🌙";
}

function toggleTheme() {
  const next = document.body.classList.contains("dark") ? "light" : "dark";
  applyTheme(next);
  localStorage.setItem("theme", next);
  render();
}

const toggleBtn = document.getElementById("theme-toggle");
toggleBtn.addEventListener("click", toggleTheme);
applyTheme(getPreferredTheme());

render();
