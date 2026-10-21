/**
 * Apex Bank - Modern Banking Management System Frontend Logic
 * Vanilla JavaScript (ES6+) with zero build step
 */

// Application State
const state = {
  currentUser: null,
  transactions: [],
  activeFilter: 'ALL',
  searchQuery: ''
};

// DOM Elements
const views = {
  auth: document.getElementById('view-auth'),
  dashboard: document.getElementById('view-dashboard')
};

const nav = {
  userSection: document.getElementById('user-nav-section'),
  userName: document.getElementById('nav-user-name'),
  userAvatar: document.getElementById('nav-avatar'),
  btnProfile: document.getElementById('btn-profile'),
  btnLogout: document.getElementById('btn-logout')
};

const modals = {
  deposit: document.getElementById('modal-deposit'),
  withdraw: document.getElementById('modal-withdraw'),
  transfer: document.getElementById('modal-transfer'),
  forgotPin: document.getElementById('modal-forgot-pin'),
  profile: document.getElementById('modal-profile')
};

// ===================================================================
// INITIALIZATION
// ===================================================================
document.addEventListener('DOMContentLoaded', () => {
  setupEventListeners();
  loadSavedAccount();
  checkExistingSession();
});

function loadSavedAccount() {
  const savedAcc = localStorage.getItem('apex_saved_acc');
  if (savedAcc) {
    const accInput = document.getElementById('login-acc');
    const rememberCheckbox = document.getElementById('remember-me');
    if (accInput) {
      accInput.value = savedAcc;
      if (rememberCheckbox) rememberCheckbox.checked = true;
      const pinInput = document.getElementById('login-pin');
      if (pinInput) pinInput.focus();
    }
  }
}

function checkExistingSession() {
  const savedUser = localStorage.getItem('apex_bank_user');
  if (savedUser) {
    try {
      const user = JSON.parse(savedUser);
      loginSuccess(user, false);
    } catch (e) {
      localStorage.removeItem('apex_bank_user');
    }
  }
}

// ===================================================================
// EVENT LISTENERS SETUP
// ===================================================================
function setupEventListeners() {
  // Auth Tabs Toggle
  const tabLogin = document.getElementById('tab-login');
  const tabRegister = document.getElementById('tab-register');
  const formLogin = document.getElementById('form-login');
  const formRegister = document.getElementById('form-register');

  tabLogin.addEventListener('click', () => {
    tabLogin.classList.add('active');
    tabRegister.classList.remove('active');
    formLogin.style.display = 'block';
    formRegister.style.display = 'none';
  });

  tabRegister.addEventListener('click', () => {
    tabRegister.classList.add('active');
    tabLogin.classList.remove('active');
    formLogin.style.display = 'none';
    formRegister.style.display = 'block';
  });

  // Toggle PIN visibility
  const togglePin = document.getElementById('toggle-pin-visibility');
  const pinInput = document.getElementById('login-pin');
  togglePin.addEventListener('click', () => {
    if (pinInput.type === 'password') {
      pinInput.type = 'text';
      togglePin.textContent = '🔒';
    } else {
      pinInput.type = 'password';
      togglePin.textContent = '👁️';
    }
  });

  // Auth Forms Submission
  formLogin.addEventListener('submit', handleLogin);
  formRegister.addEventListener('submit', handleRegister);

  // Nav Actions
  nav.btnLogout.addEventListener('click', handleLogout);
  nav.btnProfile.addEventListener('click', () => openModal(modals.profile));

  // Quick Action Buttons
  document.getElementById('btn-action-deposit').addEventListener('click', () => openModal(modals.deposit));
  document.getElementById('btn-action-withdraw').addEventListener('click', () => openModal(modals.withdraw));
  document.getElementById('btn-action-transfer').addEventListener('click', () => openModal(modals.transfer));
  document.getElementById('btn-action-export').addEventListener('click', exportStatementCSV);
  document.getElementById('btn-download-csv').addEventListener('click', exportStatementCSV);

  // Modal Close buttons
  document.querySelectorAll('[data-close-modal]').forEach(btn => {
    btn.addEventListener('click', () => {
      const modalId = btn.getAttribute('data-close-modal');
      const dialog = document.getElementById(modalId);
      if (dialog) closeModal(dialog);
    });
  });

  // Close modals on backdrop click
  document.querySelectorAll('dialog.fintech-modal').forEach(dialog => {
    dialog.addEventListener('click', (e) => {
      if (e.target === dialog) {
        closeModal(dialog);
      }
    });
  });

  // Quick Amount Chips (Deposit)
  document.querySelectorAll('[data-quick-amount]').forEach(chip => {
    chip.addEventListener('click', () => {
      document.getElementById('deposit-amount').value = chip.getAttribute('data-quick-amount');
    });
  });

  // Quick Amount Chips (Withdraw)
  document.querySelectorAll('[data-quick-amount-w]').forEach(chip => {
    chip.addEventListener('click', () => {
      document.getElementById('withdraw-amount').value = chip.getAttribute('data-quick-amount-w');
    });
  });

  // Transaction Filter Tabs
  document.querySelectorAll('.filter-btn').forEach(btn => {
    btn.addEventListener('click', () => {
      document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
      btn.classList.add('active');
      state.activeFilter = btn.getAttribute('data-filter');
      renderTransactions();
    });
  });

  // Transaction Search Input
  document.getElementById('tx-search-input').addEventListener('input', (e) => {
    state.searchQuery = e.target.value.toLowerCase().trim();
    renderTransactions();
  });

  // Recipient Live Verification in Transfer Modal
  const recipInput = document.getElementById('transfer-to-acc');
  const recipBadge = document.getElementById('recipient-verify-badge');
  let searchTimeout;
  recipInput.addEventListener('input', () => {
    clearTimeout(searchTimeout);
    const acc = recipInput.value.trim();
    if (!acc) {
      recipBadge.textContent = 'Enter account number';
      recipBadge.style.color = 'var(--text-muted)';
      return;
    }
    searchTimeout = setTimeout(async () => {
      try {
        const res = await fetch(`/api/account/${acc}`);
        const data = await res.json();
        if (data.success) {
          recipBadge.innerHTML = `✓ Verified: <strong>${data.account.Name}</strong> (${data.account.Acc_Type})`;
          recipBadge.style.color = 'var(--accent-primary)';
        } else {
          recipBadge.textContent = '✕ Account not found in database';
          recipBadge.style.color = 'var(--accent-rose)';
        }
      } catch (err) {
        recipBadge.textContent = 'Error checking account';
        recipBadge.style.color = 'var(--text-muted)';
      }
    }, 300);
  });

  // Form Handlers for Financial Operations
  document.getElementById('form-deposit').addEventListener('submit', handleDeposit);
  document.getElementById('form-withdraw').addEventListener('submit', handleWithdraw);
  document.getElementById('form-transfer').addEventListener('submit', handleTransfer);

  // Forgot PIN Flow
  document.getElementById('link-forgot-pin').addEventListener('click', () => {
    document.getElementById('forgot-step-1').style.display = 'block';
    document.getElementById('forgot-step-2').style.display = 'none';
    const currentAcc = document.getElementById('login-acc').value;
    if (currentAcc) document.getElementById('forgot-acc').value = currentAcc;
    openModal(modals.forgotPin);
  });
  document.getElementById('form-forgot-step-1').addEventListener('submit', handleForgotStep1);
  document.getElementById('form-forgot-step-2').addEventListener('submit', handleForgotStep2);

  // Profile and PIN Change
  document.getElementById('form-update-profile').addEventListener('submit', handleUpdateProfile);
  document.getElementById('form-change-pin').addEventListener('submit', handleChangePin);
}

// ===================================================================
// MODAL MANAGEMENT
// ===================================================================
function openModal(modal) {
  if (modal && typeof modal.showModal === 'function') {
    modal.showModal();
  }
}

function closeModal(modal) {
  if (modal && typeof modal.close === 'function') {
    modal.close();
  }
}

// ===================================================================
// AUTHENTICATION LOGIC
// ===================================================================
async function handleLogin(e) {
  e.preventDefault();
  const acc = document.getElementById('login-acc').value.trim();
  const pin = document.getElementById('login-pin').value.trim();
  const rememberMe = document.getElementById('remember-me').checked;

  try {
    const res = await fetch('/api/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ acc, pin })
    });
    const data = await res.json();

    if (data.success) {
      if (rememberMe) {
        localStorage.setItem('apex_saved_acc', acc);
      } else {
        localStorage.removeItem('apex_saved_acc');
      }
      showToast(`Welcome back, ${data.user.Name}!`, 'success');
      loginSuccess(data.user, true);
    } else {
      showToast(data.message || 'Login failed', 'error');
    }
  } catch (err) {
    showToast('Failed to connect to backend server.', 'error');
  }
}

async function handleRegister(e) {
  e.preventDefault();
  const payload = {
    name: document.getElementById('reg-name').value.trim(),
    pin: document.getElementById('reg-pin').value.trim(),
    accType: document.getElementById('reg-type').value,
    mob: document.getElementById('reg-mob').value.trim(),
    address: document.getElementById('reg-address').value.trim(),
    initialBalance: document.getElementById('reg-deposit').value,
    secQ: document.getElementById('reg-sec-q').value,
    secA: document.getElementById('reg-sec-a').value.trim()
  };

  try {
    const res = await fetch('/api/register', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });
    const data = await res.json();

    if (data.success) {
      showToast(`Account created! Your Account Number is ${data.account.acc}`, 'success');
      localStorage.setItem('apex_saved_acc', data.account.acc);
      // Auto-fill login and switch tab
      document.getElementById('login-acc').value = data.account.acc;
      document.getElementById('login-pin').value = '';
      document.getElementById('tab-login').click();
      document.getElementById('login-pin').focus();
    } else {
      showToast(data.message || 'Registration failed', 'error');
    }
  } catch (err) {
    showToast('Failed to register account.', 'error');
  }
}

function loginSuccess(user, saveToStorage = true) {
  state.currentUser = user;
  if (saveToStorage) {
    localStorage.setItem('apex_bank_user', JSON.stringify(user));
  }

  // Switch to Dashboard
  views.auth.style.display = 'none';
  views.dashboard.style.display = 'block';
  nav.userSection.style.display = 'flex';
  nav.userName.textContent = user.Name;
  nav.userAvatar.textContent = user.Name.charAt(0).toUpperCase();

  // Populate Dashboard UI
  updateDashboardDisplay(user);
  refreshAccountData();
}

function handleLogout() {
  state.currentUser = null;
  localStorage.removeItem('apex_bank_user');
  views.dashboard.style.display = 'none';
  views.auth.style.display = 'flex';
  nav.userSection.style.display = 'none';
  loadSavedAccount();
  showToast('Logged out securely.', 'info');
}

// ===================================================================
// FORGOT PIN & RECOVERY (Solves user's issue!)
// ===================================================================
let recoveryAcc = null;

async function handleForgotStep1(e) {
  e.preventDefault();
  const acc = document.getElementById('forgot-acc').value.trim();

  try {
    const res = await fetch('/api/forgot-pin/question', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ acc })
    });
    const data = await res.json();

    if (data.success) {
      recoveryAcc = data.acc;
      document.getElementById('forgot-found-name').textContent = `${data.name} (Acc #${data.acc})`;
      document.getElementById('forgot-question-text').textContent = data.question;
      document.getElementById('forgot-step-1').style.display = 'none';
      document.getElementById('forgot-step-2').style.display = 'block';
    } else {
      showToast(data.message || 'Account not found', 'error');
    }
  } catch (err) {
    showToast('Error looking up account.', 'error');
  }
}

async function handleForgotStep2(e) {
  e.preventDefault();
  const answer = document.getElementById('forgot-answer').value.trim();
  const newPin = document.getElementById('forgot-new-pin').value.trim();

  try {
    const res = await fetch('/api/forgot-pin/reset', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ acc: recoveryAcc, answer, newPin })
    });
    const data = await res.json();

    if (data.success) {
      showToast(data.message, 'success');
      closeModal(modals.forgotPin);
      document.getElementById('login-acc').value = recoveryAcc;
      document.getElementById('login-pin').value = '';
      document.getElementById('login-pin').focus();
    } else {
      showToast(data.message || 'PIN reset failed', 'error');
    }
  } catch (err) {
    showToast('Failed to reset PIN.', 'error');
  }
}

// ===================================================================
// DASHBOARD & FINANCIAL DATA
// ===================================================================
async function refreshAccountData() {
  if (!state.currentUser) return;
  try {
    // 1. Fetch updated account details
    const accRes = await fetch(`/api/account/${state.currentUser.Acc}`);
    const accData = await accRes.json();
    if (accData.success) {
      state.currentUser = accData.account;
      updateDashboardDisplay(accData.account);
    }

    // 2. Fetch full transactions
    const txRes = await fetch(`/api/transactions/${state.currentUser.Acc}`);
    const txData = await txRes.json();
    if (txData.success) {
      state.transactions = txData.transactions;
      renderTransactions();
      updateMetricSummaries();
    }
  } catch (err) {
    console.error('Error refreshing account data:', err);
  }
}

function updateDashboardDisplay(user) {
  const formattedBal = formatCurrency(user.Balance);
  document.getElementById('card-balance-display').textContent = formattedBal;
  document.getElementById('metric-total-bal').textContent = formattedBal;
  document.getElementById('card-holder-name').textContent = user.Name || 'CUSTOMER';
  document.getElementById('card-acc-display').textContent = `ACC •••• ${String(user.Acc).padStart(4, '0')}`;
  document.getElementById('card-acc-type').textContent = (user.Acc_Type || 'SAVINGS').toUpperCase();

  // Populate Profile Modal
  document.getElementById('prof-acc').textContent = user.Acc;
  document.getElementById('prof-type').textContent = user.Acc_Type || 'Savings';
  document.getElementById('prof-micr').textContent = user.MICR_No || 'N/A';
  document.getElementById('prof-mob').value = user.Mob || '';
  document.getElementById('prof-address').value = user.Address || '';
}

function updateMetricSummaries() {
  let inflow = 0;
  let outflow = 0;

  state.transactions.forEach(tx => {
    const amt = parseFloat(tx.Amount) || 0;
    if (tx.Type === 'DEPOSIT' || tx.Type === 'TRANSFER_RECEIVED') {
      inflow += amt;
    } else if (tx.Type === 'WITHDRAWAL' || tx.Type === 'TRANSFER_SENT') {
      outflow += amt;
    }
  });

  document.getElementById('metric-inflow').textContent = formatCurrency(inflow);
  document.getElementById('metric-outflow').textContent = formatCurrency(outflow);
  document.getElementById('metric-tx-count').textContent = state.transactions.length;
}

// ===================================================================
// TRANSACTION LEDGER & FILTERING (User's Priority)
// ===================================================================
function renderTransactions() {
  const tbody = document.getElementById('tx-table-body');
  let list = state.transactions;

  // Category filter
  if (state.activeFilter !== 'ALL') {
    if (state.activeFilter === 'TRANSFER') {
      list = list.filter(t => t.Type.startsWith('TRANSFER'));
    } else {
      list = list.filter(t => t.Type === state.activeFilter);
    }
  }

  // Keyword search
  if (state.searchQuery) {
    list = list.filter(t => 
      String(t.id).includes(state.searchQuery) ||
      (t.Description && t.Description.toLowerCase().includes(state.searchQuery)) ||
      t.Type.toLowerCase().includes(state.searchQuery)
    );
  }

  if (list.length === 0) {
    tbody.innerHTML = `
      <tr>
        <td colspan="7" class="empty-state">
          <div class="empty-state-icon">📂</div>
          <p>No transactions match your current filter.</p>
        </td>
      </tr>
    `;
    return;
  }

  tbody.innerHTML = list.map(tx => {
    const isCredit = tx.Type === 'DEPOSIT' || tx.Type === 'TRANSFER_RECEIVED';
    const amountClass = isCredit ? 'amount-inflow' : 'amount-outflow';
    const amountPrefix = isCredit ? '+ ' : '- ';
    const badgeTypeClass = tx.Type.toLowerCase();
    const typeLabel = tx.Type.replace('_', ' ');

    return `
      <tr>
        <td class="num" style="font-family: var(--font-mono); font-size: 0.8rem; opacity: 0.7;">#TX-${String(tx.id).padStart(5, '0')}</td>
        <td style="white-space: nowrap; font-size: 0.825rem;">${formatTimestamp(tx.Timestamp)}</td>
        <td><span class="tx-type-badge ${badgeTypeClass}">${typeLabel}</span></td>
        <td style="font-weight: 500;">${tx.Description || 'Standard Transaction'}</td>
        <td class="${amountClass} num" style="font-weight: 700;">${amountPrefix}${formatCurrency(tx.Amount)}</td>
        <td class="num" style="font-weight: 600;">${formatCurrency(tx.BalanceAfter)}</td>
        <td><span style="display: inline-flex; align-items: center; gap: 0.3rem; font-size: 0.75rem; color: var(--accent-primary); font-weight: 600;">● Completed</span></td>
      </tr>
    `;
  }).join('');
}

// ===================================================================
// FINANCIAL TRANSACTIONS HANDLERS
// ===================================================================
async function handleDeposit(e) {
  e.preventDefault();
  const amount = document.getElementById('deposit-amount').value;
  const description = document.getElementById('deposit-note').value.trim();

  try {
    const res = await fetch('/api/deposit', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        acc: state.currentUser.Acc,
        amount,
        description: description || 'Cash Deposit'
      })
    });
    const data = await res.json();

    if (data.success) {
      showToast(data.message, 'success');
      closeModal(modals.deposit);
      document.getElementById('form-deposit').reset();
      refreshAccountData();
    } else {
      showToast(data.message || 'Deposit failed', 'error');
    }
  } catch (err) {
    showToast('Failed to process deposit.', 'error');
  }
}

async function handleWithdraw(e) {
  e.preventDefault();
  const amount = document.getElementById('withdraw-amount').value;
  const pin = document.getElementById('withdraw-pin').value;
  const description = document.getElementById('withdraw-note').value.trim();

  try {
    const res = await fetch('/api/withdraw', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        acc: state.currentUser.Acc,
        pin,
        amount,
        description: description || 'ATM Cash Withdrawal'
      })
    });
    const data = await res.json();

    if (data.success) {
      showToast(data.message, 'success');
      closeModal(modals.withdraw);
      document.getElementById('form-withdraw').reset();
      refreshAccountData();
    } else {
      showToast(data.message || 'Withdrawal failed', 'error');
    }
  } catch (err) {
    showToast('Failed to process withdrawal.', 'error');
  }
}

async function handleTransfer(e) {
  e.preventDefault();
  const toAcc = document.getElementById('transfer-to-acc').value;
  const amount = document.getElementById('transfer-amount').value;
  const pin = document.getElementById('transfer-pin').value;
  const description = document.getElementById('transfer-note').value.trim();

  try {
    const res = await fetch('/api/transfer', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        fromAcc: state.currentUser.Acc,
        toAcc,
        amount,
        pin,
        description: description || `Transfer to Acc #${toAcc}`
      })
    });
    const data = await res.json();

    if (data.success) {
      showToast(data.message, 'success');
      closeModal(modals.transfer);
      document.getElementById('form-transfer').reset();
      document.getElementById('recipient-verify-badge').textContent = 'Enter account number';
      document.getElementById('recipient-verify-badge').style.color = 'var(--text-muted)';
      refreshAccountData();
    } else {
      showToast(data.message || 'Transfer failed', 'error');
    }
  } catch (err) {
    showToast('Failed to process transfer.', 'error');
  }
}

// ===================================================================
// STATEMENT EXPORT (CSV)
// ===================================================================
function exportStatementCSV() {
  if (!state.currentUser || state.transactions.length === 0) {
    showToast('No transaction records available to export.', 'info');
    return;
  }

  const headers = ['Transaction ID', 'Date & Time', 'Type', 'Description', 'Amount ($)', 'Balance After ($)', 'Status'];
  const rows = state.transactions.map(t => [
    `TX-${t.id}`,
    `"${t.Timestamp}"`,
    t.Type,
    `"${t.Description || ''}"`,
    t.Amount,
    t.BalanceAfter,
    'COMPLETED'
  ]);

  const csvContent = 'data:text/csv;charset=utf-8,' + [headers.join(','), ...rows.map(e => e.join(','))].join('\n');
  const encodedUri = encodeURI(csvContent);
  const link = document.createElement('a');
  link.setAttribute('href', encodedUri);
  link.setAttribute('download', `ApexBank_Statement_Acc${state.currentUser.Acc}_${Date.now()}.csv`);
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);

  showToast('Account statement exported as CSV successfully!', 'success');
}

// ===================================================================
// PROFILE & PIN UPDATE HANDLERS
// ===================================================================
async function handleUpdateProfile(e) {
  e.preventDefault();
  const mob = document.getElementById('prof-mob').value.trim();
  const address = document.getElementById('prof-address').value.trim();

  try {
    const res = await fetch('/api/profile/update', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        acc: state.currentUser.Acc,
        mob,
        address
      })
    });
    const data = await res.json();
    if (data.success) {
      showToast('Profile contact information updated!', 'success');
      closeModal(modals.profile);
      refreshAccountData();
    } else {
      showToast(data.message, 'error');
    }
  } catch (err) {
    showToast('Failed to update profile.', 'error');
  }
}

async function handleChangePin(e) {
  e.preventDefault();
  const currentPin = document.getElementById('change-curr-pin').value;
  const newPin = document.getElementById('change-new-pin').value;

  try {
    const res = await fetch('/api/change-pin', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        acc: state.currentUser.Acc,
        currentPin,
        newPin
      })
    });
    const data = await res.json();
    if (data.success) {
      showToast(data.message, 'success');
      document.getElementById('form-change-pin').reset();
      closeModal(modals.profile);
    } else {
      showToast(data.message, 'error');
    }
  } catch (err) {
    showToast('Failed to change PIN.', 'error');
  }
}

// ===================================================================
// FORMATTING HELPERS
// ===================================================================
function formatCurrency(val) {
  const num = parseFloat(String(val).replace(/,/g, '')) || 0;
  return new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  }).format(num);
}

function formatTimestamp(ts) {
  if (!ts) return 'Just now';
  try {
    const date = new Date(ts.replace(' ', 'T'));
    if (isNaN(date.getTime())) return ts;
    return date.toLocaleDateString('en-US', {
      month: 'short',
      day: 'numeric',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  } catch (e) {
    return ts;
  }
}

// ===================================================================
// TOAST NOTIFICATION SYSTEM
// ===================================================================
function showToast(message, type = 'info') {
  const container = document.getElementById('toast-container');
  const toast = document.createElement('div');
  toast.className = `toast ${type}`;

  const icon = type === 'success' ? '✓' : type === 'error' ? '✕' : 'ℹ';
  toast.innerHTML = `<span style="font-weight: 800; font-size: 1rem;">${icon}</span><span>${message}</span>`;

  container.appendChild(toast);

  setTimeout(() => {
    toast.style.opacity = '0';
    toast.style.transform = 'translateY(10px)';
    toast.style.transition = 'all 0.3s ease';
    setTimeout(() => toast.remove(), 300);
  }, 4000);
}
