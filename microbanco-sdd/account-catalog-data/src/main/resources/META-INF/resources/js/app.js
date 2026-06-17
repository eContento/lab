import { renderHeader } from './components/header.js';
import { renderAccountList } from './views/account-list.js';
import { renderAccountDetail } from './views/account-detail.js';

function route() {
  const hash = window.location.hash.replace(/^#!/, '') || '/';

  if (hash === '/') {
    renderHeader(false);
    renderAccountList();
  } else if (hash.startsWith('/accounts/')) {
    const iban = hash.replace('/accounts/', '');
    renderHeader(true);
    renderAccountDetail(iban);
  }
}

window.addEventListener('hashchange', route);
route();
