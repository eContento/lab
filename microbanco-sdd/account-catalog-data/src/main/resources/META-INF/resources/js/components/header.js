export function renderHeader(showBack) {
  const header = document.getElementById('header');
  header.innerHTML = `
    <div class="header-inner">
      ${showBack ? '<button class="btn-back" id="btn-back">&larr; Volver</button>' : ''}
      <h1 id="app-title">Microbanco</h1>
    </div>
  `;
  document.getElementById('app-title').onclick = () => navigate('/');
  if (showBack) {
    document.getElementById('btn-back').onclick = () => history.back();
  }
}

function navigate(path) {
  window.location.hash = '#!' + path;
}
