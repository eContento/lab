export function showToast(message, type = 'success') {
  const container = document.getElementById('toast-container');
  const toast = document.createElement('div');
  toast.className = `toast toast-${type}`;
  toast.textContent = message;
  toast.onclick = () => toast.remove();
  container.appendChild(toast);
  setTimeout(() => { if (toast.parentNode) toast.remove(); }, 5000);
}

export function showError(err) {
  showToast(err.message || 'Error inesperado', 'error');
}
