const BASE = '';

async function request(method, path, body) {
  const opts = {
    method,
    headers: { 'Content-Type': 'application/json' },
  };
  if (body !== undefined) opts.body = JSON.stringify(body);
  const res = await fetch(BASE + path, opts);
  const data = res.status === 204 ? null : await res.json();
  if (!res.ok) {
    const msg = (data && data.message) || `Error ${res.status}`;
    throw new Error(msg);
  }
  return data;
}

export function listAccounts(page = 0, size = 20) {
  return request('GET', `/accounts?page=${page}&size=${size}`);
}

export function getAccount(iban) {
  return request('GET', `/accounts/${iban}`);
}

export function getBalance(iban) {
  return request('GET', `/accounts/${iban}/balance`);
}

export function createAccount(ownerName, currency, initialBalance) {
  return request('POST', '/accounts', { ownerName, currency, initialBalance });
}

export function closeAccount(iban) {
  return request('DELETE', `/accounts/${iban}`);
}

export function getAccountTransfers(iban, page = 0, size = 20) {
  return request('GET', `/accounts/${iban}/transfers?page=${page}&size=${size}`);
}

export function formatIban(iban) {
  if (!iban) return '';
  return iban.replace(/(.{4})(?=.)/g, '$1 ');
}

export function cleanIban(iban) {
  return (iban || '').replace(/\s+/g, '');
}

export function isEntityAccount(iban) {
  if (!iban || iban.length < 4) return false;
  return iban.substring(4).startsWith('00830001');
}

export function executeTransfer(sourceAccountIban, targetAccountIban, amount, description) {
  return request('POST', '/transfers', { sourceAccountIban, targetAccountIban, amount, description });
}
