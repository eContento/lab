import * as api from '../api.js';
import { showError, showToast } from '../components/toast.js';
import { openModal, closeModal } from '../components/modal.js';

let transfersPage = 0;
const TRANSFERS_PAGE_SIZE = 20;
let currentIban = null;

export async function renderAccountDetail(iban) {
  currentIban = iban;
  transfersPage = 0;
  const main = document.getElementById('main');
  main.innerHTML = '<div class="loading"><div class="spinner"></div><p>Cargando cuenta...</p></div>';

  try {
    const [account, balance] = await Promise.all([
      api.getAccount(iban),
      api.getBalance(iban)
    ]);
    const transfers = await api.getAccountTransfers(iban, transfersPage, TRANSFERS_PAGE_SIZE);
    render(main, account, balance, transfers);
  } catch (err) {
    showError(err);
    main.innerHTML = `
      <div class="empty-state">
        <p>Error al cargar la cuenta</p>
        <button class="btn btn-primary" onclick="location.hash='#!/'">Volver</button>
      </div>
    `;
  }
}

function render(main, account, balance, transfers) {
  const statusClass = account.status === 'ACTIVE' ? 'badge-active' : 'badge-closed';

  main.innerHTML = `
    <div class="card" style="margin-bottom: 24px;">
      <div class="detail-grid">
        <div>
          <div class="detail-field">
            <div class="detail-label">Titular</div>
            <div class="detail-value">${escHtml(account.ownerName)}</div>
          </div>
          <div class="detail-field">
            <div class="detail-label">IBAN</div>
            <div class="detail-value" style="font-family: monospace; font-size: 14px;">${api.formatIban(account.iban)}</div>
          </div>
          <div class="detail-field">
            <div class="detail-label">Estado</div>
            <div><span class="badge ${statusClass}">${account.status}</span></div>
          </div>
          <div class="detail-field">
            <div class="detail-label">Divisa</div>
            <div class="detail-value">${account.currency}</div>
          </div>
        </div>
        <div style="display: flex; flex-direction: column; justify-content: center; align-items: flex-end;">
          <div class="detail-label">Saldo disponible</div>
          <div class="detail-balance">${formatAmount(balance.balance)} <span style="font-size: 16px; color: var(--text-secondary);">${balance.currency}</span></div>
          <div class="detail-actions">
            ${account.status === 'ACTIVE' ? `
              <button class="btn btn-success btn-sm" id="btn-transfer">Hacer Transferencia</button>
              <button class="btn btn-danger btn-sm" id="btn-close">Cerrar Cuenta</button>
            ` : ''}
          </div>
        </div>
      </div>
    </div>

    <div class="card">
      <div class="card-title">Historial de Transferencias</div>
      ${renderTransferTable(transfers)}
    </div>
  `;

  if (account.status === 'ACTIVE') {
    document.getElementById('btn-transfer').onclick = () => showTransferForm(account.iban);
    document.getElementById('btn-close').onclick = () => confirmClose(account.iban);
  }

  setupPagination(transfers);
}

function renderTransferTable(data) {
  const items = data.items || [];

  if (items.length === 0) {
    return '<div class="empty-state"><p>No hay transferencias</p></div>';
  }

  return `
    <div class="table-container">
      <table>
        <thead>
          <tr>
            <th>Fecha</th>
            <th>Origen</th>
            <th>Destino</th>
            <th>Importe</th>
            <th>Concepto</th>
          </tr>
        </thead>
        <tbody>
          ${items.map(t => renderTransferRow(t)).join('')}
        </tbody>
      </table>
    </div>
    ${renderPagination(data)}
  `;
}

function renderTransferRow(t) {
  const isOutgoing = t.sourceAccountIban === currentIban;
  const amountClass = isOutgoing ? 'amount-negative' : 'amount-positive';
  const sign = isOutgoing ? '-' : '+';
  return `
    <tr>
      <td>${formatDate(t.timestamp)}</td>
      <td style="font-family: monospace; font-size: 12px;">${formatIban(t.sourceAccountIban)}</td>
      <td style="font-family: monospace; font-size: 12px;">${formatIban(t.targetAccountIban)}</td>
      <td class="${amountClass}">${sign}${formatAmount(t.amount)}</td>
      <td>${t.description ? escHtml(t.description) : '-'}</td>
    </tr>
  `;
}

function formatIban(iban) {
  if (!iban) return '';
  return iban.replace(/(.{4})(?=.)/g, '$1 ');
}

function renderPagination(data) {
  if (data.totalPages <= 1) return '';
  return `
    <div class="pagination">
      <button class="btn btn-outline btn-sm" id="prev-t-page" ${transfersPage <= 0 ? 'disabled' : ''}>Anterior</button>
      <span class="pagination-info">Página ${data.page + 1} de ${data.totalPages}</span>
      <button class="btn btn-outline btn-sm" id="next-t-page" ${transfersPage >= data.totalPages - 1 ? 'disabled' : ''}>Siguiente</button>
    </div>
  `;
}

function setupPagination(data) {
  const prev = document.getElementById('prev-t-page');
  const next = document.getElementById('next-t-page');
  if (prev) prev.onclick = () => { transfersPage--; renderAccountDetail(currentIban); };
  if (next) next.onclick = () => { transfersPage++; renderAccountDetail(currentIban); };
}

function showTransferForm(sourceIban) {
  openModal(`
    <div class="modal-title">Nueva Transferencia</div>
    <form id="transfer-form">
      <div class="form-group">
        <label class="form-label">Cuenta origen</label>
        <input class="form-input" value="${formatIban(sourceIban)}" disabled style="background: var(--bg); font-family: monospace; font-size: 13px;">
      </div>
      <div class="form-group">
        <label class="form-label" for="target-iban">Cuenta destino (IBAN)</label>
        <input class="form-input" id="target-iban" required placeholder="ES12 3456 7890 1234 5678 9012">
      </div>
      <div class="form-group">
        <label class="form-label" for="amount">Importe</label>
        <input class="form-input" id="amount" type="number" step="0.01" min="0.01" required placeholder="0.00">
      </div>
      <div class="form-group">
        <label class="form-label" for="description">Concepto (opcional)</label>
        <input class="form-input" id="description" placeholder="Descripción de la transferencia" maxlength="255">
      </div>
      <div class="form-actions">
        <button type="button" class="btn btn-outline" onclick="closeModal()">Cancelar</button>
        <button type="submit" class="btn btn-success" id="btn-submit-transfer">Enviar</button>
      </div>
    </form>
  `);

  const targetInput = document.getElementById('target-iban');
  targetInput.oninput = function () {
    const raw = this.value.replace(/[^A-Za-z0-9]/g, '').toUpperCase();
    const formatted = raw.replace(/(.{4})(?=.)/g, '$1 ');
    this.value = formatted;
  };

  document.getElementById('transfer-form').onsubmit = async (e) => {
    e.preventDefault();
    const btn = document.getElementById('btn-submit-transfer');
    btn.disabled = true;
    btn.textContent = 'Enviando...';

    try {
      await api.executeTransfer(
        sourceIban,
        targetInput.value.replace(/\s+/g, ''),
        parseFloat(document.getElementById('amount').value),
        document.getElementById('description').value.trim() || undefined
      );
      closeModal();
      await renderAccountDetail(currentIban);
      showToast('Transferencia realizada correctamente');
    } catch (err) {
      showError(err);
      btn.disabled = false;
      btn.textContent = 'Enviar';
    }
  };
}

function confirmClose(iban) {
  openModal(`
    <div class="modal-title">Cerrar Cuenta</div>
    <p style="margin-bottom: 20px; color: var(--text-secondary);">
      ¿Estás seguro de que deseas cerrar esta cuenta?<br>
      Solo se puede cerrar si el saldo es cero.
    </p>
    <div class="form-actions">
      <button class="btn btn-outline" onclick="closeModal()">Cancelar</button>
      <button class="btn btn-danger" id="btn-confirm-close">Cerrar Cuenta</button>
    </div>
  `);

  document.getElementById('btn-confirm-close').onclick = async () => {
    const btn = document.getElementById('btn-confirm-close');
    btn.disabled = true;
    btn.textContent = 'Cerrando...';

    try {
      await api.closeAccount(iban);
      closeModal();
      await renderAccountDetail(currentIban);
      showToast('Cuenta cerrada correctamente');
    } catch (err) {
      showError(err);
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

function shortIban(iban) {
  if (!iban) return '-';
  return iban.length > 12 ? iban.slice(0, 4) + '...' + iban.slice(-4) : iban;
}

function escHtml(s) {
  const d = document.createElement('div');
  d.textContent = s;
  return d.innerHTML;
}
