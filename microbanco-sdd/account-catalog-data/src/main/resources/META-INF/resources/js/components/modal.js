export function openModal(html) {
  document.getElementById('modal-content').innerHTML = html;
  document.getElementById('modal-overlay').classList.add('active');
}

export function closeModal() {
  document.getElementById('modal-overlay').classList.remove('active');
}

document.getElementById('modal-overlay').onclick = (e) => {
  if (e.target === e.currentTarget) closeModal();
};
