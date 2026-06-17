import * as api from '../api.js';
import { showError } from '../components/toast.js';
import { openModal, closeModal } from '../components/modal.js';

let currentPage = 0;
const PAGE_SIZE = 50;

export async function renderAccountList() {
  const main = document.getElementById('main');
  main.innerHTML = '<div class="loading"><div class="spinner"></div><p>Cargando cuentas...</p></div>';

  try {
    const data = await api.listAccounts(currentPage, PAGE_SIZE);
    render(main, data);
  } catch (err) {
    showError(err);
    main.innerHTML = '<div class="empty-state"><p>Error al cargar cuentas</p><button class="btn btn-primary" onclick="location.reload()">Reintentar</button></div>';
  }
}

function render(main, data) {
  const items = data.items || [];

  main.innerHTML = `
    <div class="toolbar">
      <h2>Posición Global</h2>
      <button class="btn btn-primary" id="btn-new-account">+ Nueva Cuenta</button>
    </div>
    ${items.length === 0
      ? '<div class="empty-state"><p>No hay cuentas todavía</p></div>'
      : `<div class="account-grid">${items.map(renderCard).join('')}</div>`
    }
    ${renderPagination(data)}
  `;

  document.getElementById('btn-new-account').onclick = showCreateForm;

  document.querySelectorAll('.account-card').forEach(el => {
    el.onclick = () => { window.location.hash = `#!/accounts/${el.dataset.iban}`; };
  });

  setupPagination(data);
}

function renderCard(acc) {
  const statusClass = acc.status === 'ACTIVE' ? 'badge-active' : 'badge-closed';
  return `
    <div class="card account-card" data-iban="${acc.iban}">
      <div class="account-card-header">
        <div>
          <div class="account-owner">${escHtml(acc.ownerName)}</div>
          <div class="account-iban">${api.formatIban(acc.iban)}</div>
        </div>
        <span class="badge ${statusClass}">${acc.status}</span>
      </div>
      <div class="account-balance">${formatAmount(acc.balance)} <span class="currency">${acc.currency}</span></div>
      <div class="account-meta">
        <span>Creada: ${formatDate(acc.createdAt)}</span>
      </div>
    </div>
  `;
}

function renderPagination(data) {
  if (data.totalPages <= 1) return '';
  return `
    <div class="pagination">
      <button class="btn btn-outline btn-sm" id="prev-page" ${currentPage <= 0 ? 'disabled' : ''}>Anterior</button>
      <span class="pagination-info">Página ${data.page + 1} de ${data.totalPages} (${data.total} cuentas)</span>
      <button class="btn btn-outline btn-sm" id="next-page" ${currentPage >= data.totalPages - 1 ? 'disabled' : ''}>Siguiente</button>
    </div>
  `;
}

function setupPagination(data) {
  const prev = document.getElementById('prev-page');
  const next = document.getElementById('next-page');
  if (prev) prev.onclick = () => { currentPage--; renderAccountList(); };
  if (next) next.onclick = () => { currentPage++; renderAccountList(); };
}

function showCreateForm() {
  openModal(`
    <div class="modal-title">Nueva Cuenta</div>
    <form id="create-form">
      <div class="form-group">
        <label class="form-label" for="owner-name">Titular</label>
        <input class="form-input" id="owner-name" required placeholder="Nombre del titular">
      </div>
      <div class="form-group">
        <label class="form-label" for="currency">Divisa</label>
        <input class="form-input" id="currency" value="EUR" required placeholder="EUR">
      </div>
      <div class="form-group">
        <label class="form-label" for="initial-balance">Saldo inicial</label>
        <input class="form-input" id="initial-balance" type="number" step="0.01" min="0" value="0" placeholder="0.00">
      </div>
      <div class="form-actions">
        <button type="button" class="btn btn-outline" onclick="closeModal()">Cancelar</button>
        <button type="submit" class="btn btn-primary" id="btn-submit-create">Crear Cuenta</button>
      </div>
    </form>
  `);

  document.getElementById('create-form').onsubmit = async (e) => {
    e.preventDefault();
    const btn = document.getElementById('btn-submit-create');
    btn.disabled = true;
    btn.textContent = 'Creando...';

    try {
      await api.createAccount(
        document.getElementById('owner-name').value.trim(),
        document.getElementById('currency').value.trim().toUpperCase(),
        parseFloat(document.getElementById('initial-balance').value) || 0
      );
      closeModal();
      currentPage = 0;
      await renderAccountList();
      const { showToast } = await import('../components/toast.js');
      showToast('Cuenta creada correctamente');
    } catch (err) {
      showError(err);
      btn.disabled = false;
      btn.textContent = 'Crear Cuenta';
    }
  };
}

function formatAmount(n) {
  return Number(n).toLocaleString('es-ES', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
}

function formatDate(iso) {
  if (!iso) return '-';
  return new Date(iso).toLocaleDateString('es-ES', { day: '2-digit', month: 'short', year: 'numeric', hour: '2-digit', minute: '2-digit' });
}

function escHtml(s) {
  const d = document.createElement('div');
  d.textContent = s;
  return d.innerHTML;
}
