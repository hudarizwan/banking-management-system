/**
 * Modern Banking Management System - Backend Server
 * Native Node.js Server with zero external dependencies
 * Uses Node v24 built-in `node:sqlite` directly against bank.sqlite
 */

const http = require('node:http');
const fs = require('node:fs');
const path = require('node:path');
const { DatabaseSync } = require('node:sqlite');

const PORT = process.env.PORT || 3000;
const DB_PATH = path.resolve(__dirname, '..', 'JAVA NetBeans Banking Management System', 'bank.sqlite');
const PUBLIC_DIR = path.join(__dirname, 'public');

// Initialize SQLite connection
let db;
try {
  db = new DatabaseSync(DB_PATH);
  console.log(`[DB] Connected to SQLite database at: ${DB_PATH}`);
  initDatabase();
} catch (err) {
  console.error('[DB] Failed to connect to SQLite:', err);
  process.exit(1);
}

function initDatabase() {
  // Ensure Transactions table exists for statement & history logging
  db.exec(`
    CREATE TABLE IF NOT EXISTS "Transactions" (
      "id" INTEGER PRIMARY KEY AUTOINCREMENT,
      "Acc" INTEGER NOT NULL,
      "Type" TEXT NOT NULL,
      "Amount" REAL NOT NULL,
      "BalanceAfter" REAL NOT NULL,
      "Description" TEXT,
      "Timestamp" TEXT DEFAULT (datetime('now', 'localtime')),
      "RecipientAcc" INTEGER
    );
  `);

  // Check if we need to seed initial transactions for existing accounts that have no records yet
  const countRow = db.prepare('SELECT count(*) as count FROM Transactions').get();
  if (countRow.count === 0) {
    console.log('[DB] Seeding initial transaction history for existing accounts...');
    const accounts = db.prepare('SELECT Acc, Name, Balance FROM Account').all();
    const insertTx = db.prepare(`
      INSERT INTO Transactions (Acc, Type, Amount, BalanceAfter, Description, Timestamp)
      VALUES (?, ?, ?, ?, ?, datetime('now', '-7 days', 'localtime'))
    `);
    
    for (const acc of accounts) {
      const cleanBal = parseFloat(String(acc.Balance).replace(/,/g, '')) || 0;
      if (cleanBal > 0) {
        insertTx.run(acc.Acc, 'DEPOSIT', cleanBal, cleanBal, 'Initial Account Opening Balance');
      }
    }
    console.log('[DB] Seeded transaction history.');
  }
}

// Helper to parse JSON request bodies
function parseBody(req) {
  return new Promise((resolve, reject) => {
    let body = '';
    req.on('data', chunk => {
      body += chunk;
      if (body.length > 1e6) { // 1MB limit
        req.destroy();
        reject(new Error('Request entity too large'));
      }
    });
    req.on('end', () => {
      try {
        resolve(body ? JSON.parse(body) : {});
      } catch (err) {
        reject(new Error('Invalid JSON'));
      }
    });
    req.on('error', reject);
  });
}

// Helper to send JSON responses
function sendJson(res, statusCode, data) {
  res.writeHead(statusCode, {
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET, POST, OPTIONS',
    'Access-Control-Allow-Headers': 'Content-Type'
  });
  res.end(JSON.stringify(data));
}

// Helper to get normalized numeric balance
function getNumericBalance(acc) {
  const row = db.prepare('SELECT Balance FROM Account WHERE Acc = ?').get(acc);
  if (!row) return 0;
  return parseFloat(String(row.Balance).replace(/,/g, '')) || 0;
}

// MIME types dictionary
const MIME_TYPES = {
  '.html': 'text/html; charset=utf-8',
  '.css': 'text/css; charset=utf-8',
  '.js': 'application/javascript; charset=utf-8',
  '.json': 'application/json; charset=utf-8',
  '.png': 'image/png',
  '.jpg': 'image/jpeg',
  '.svg': 'image/svg+xml',
  '.ico': 'image/x-icon'
};

const server = http.createServer(async (req, res) => {
  // Handle CORS preflight
  if (req.method === 'OPTIONS') {
    res.writeHead(204, {
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'GET, POST, OPTIONS',
      'Access-Control-Allow-Headers': 'Content-Type'
    });
    return res.end();
  }

  const url = new URL(req.url, `http://${req.headers.host}`);
  const pathname = url.pathname;

  // --- REST API ENDPOINTS ---
  try {

    // 2. Login
    if (pathname === '/api/login' && req.method === 'POST') {
      const { acc, pin } = await parseBody(req);
      if (!acc || !pin) {
        return sendJson(res, 400, { success: false, message: 'Account Number and PIN are required' });
      }

      const user = db.prepare('SELECT * FROM Account WHERE Acc = ? AND Pin = ?').get(acc, pin);
      if (!user) {
        return sendJson(res, 401, { success: false, message: 'Invalid Account Number or PIN' });
      }

      // Sanitize user object for client
      const safeUser = { ...user };
      delete safeUser.Pin;
      delete safeUser.Sec_A;

      return sendJson(res, 200, { success: true, user: safeUser });
    }

    // 3. Register New Account
    if (pathname === '/api/register' && req.method === 'POST') {
      const body = await parseBody(req);
      const { name, pin, accType, dob, gender, mob, address, secQ, secA, initialBalance } = body;

      if (!name || !pin) {
        return sendJson(res, 400, { success: false, message: 'Name and PIN are required' });
      }

      const depositAmount = parseFloat(initialBalance) || 500;
      const micr = Math.floor(100 + Math.random() * 900); // 3-digit MICR

      // Generate unique random account number (e.g., 4-digit)
      let newAcc = Math.floor(1000 + Math.random() * 9000);
      let exists = db.prepare('SELECT Acc FROM Account WHERE Acc = ?').get(newAcc);
      while (exists) {
        newAcc = Math.floor(1000 + Math.random() * 9000);
        exists = db.prepare('SELECT Acc FROM Account WHERE Acc = ?').get(newAcc);
      }

      // Insert into Account
      const insertAcc = db.prepare(`
        INSERT INTO Account (Acc, Name, DOB, Pin, Acc_Type, Ethnicity, MICR_No, Gender, Mob, Address, Sec_Q, Sec_A, Balance)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
      `);
      insertAcc.run(
        newAcc,
        name,
        dob || '2000-01-01',
        pin,
        accType || 'Savings',
        body.ethnicity || 'Not specified',
        micr,
        gender || 'Male',
        mob || '0000000000',
        address || 'Standard Address',
        secQ || 'What is the name of your first pet?',
        secA || 'cat',
        depositAmount
      );

      // Also insert into Balances for legacy Java compatibility
      db.prepare('INSERT INTO Balances (Name, Acc, MICR_No, Balance) VALUES (?, ?, ?, ?)').run(
        name, newAcc, micr, depositAmount
      );

      // Record opening transaction
      db.prepare(`
        INSERT INTO Transactions (Acc, Type, Amount, BalanceAfter, Description)
        VALUES (?, 'DEPOSIT', ?, ?, 'Initial Account Opening Deposit')
      `).run(newAcc, depositAmount, depositAmount);

      return sendJson(res, 201, {
        success: true,
        message: 'Account successfully registered!',
        account: {
          acc: newAcc,
          name,
          pin,
          micr,
          balance: depositAmount
        }
      });
    }

    // 4. Forgot PIN - Step 1: Get Security Question
    if (pathname === '/api/forgot-pin/question' && req.method === 'POST') {
      const { acc } = await parseBody(req);
      const user = db.prepare('SELECT Acc, Name, Sec_Q FROM Account WHERE Acc = ?').get(acc);
      if (!user) {
        return sendJson(res, 404, { success: false, message: 'Account Number not found' });
      }
      return sendJson(res, 200, {
        success: true,
        acc: user.Acc,
        name: user.Name,
        question: user.Sec_Q || 'What is the name of your first pet?'
      });
    }

    // 5. Forgot PIN - Step 2: Verify Answer & Reset PIN
    if (pathname === '/api/forgot-pin/reset' && req.method === 'POST') {
      const { acc, answer, newPin } = await parseBody(req);
      if (!acc || !answer || !newPin) {
        return sendJson(res, 400, { success: false, message: 'Account, Answer, and New PIN are required' });
      }

      const user = db.prepare('SELECT Sec_A, Pin FROM Account WHERE Acc = ?').get(acc);
      if (!user) {
        return sendJson(res, 404, { success: false, message: 'Account not found' });
      }

      if (String(user.Sec_A).trim().toLowerCase() !== String(answer).trim().toLowerCase()) {
        return sendJson(res, 400, { success: false, message: 'Incorrect Security Answer. Please try again.' });
      }

      db.prepare('UPDATE Account SET Pin = ? WHERE Acc = ?').run(newPin, acc);
      return sendJson(res, 200, { success: true, message: 'PIN reset successfully! You can now log in.' });
    }

    // 6. Get Account Details & Balances
    if (pathname.startsWith('/api/account/') && req.method === 'GET') {
      const acc = pathname.split('/').pop();
      const user = db.prepare('SELECT * FROM Account WHERE Acc = ?').get(acc);
      if (!user) {
        return sendJson(res, 404, { success: false, message: 'Account not found' });
      }

      const safeUser = { ...user };
      delete safeUser.Pin;
      delete safeUser.Sec_A;
      safeUser.Balance = parseFloat(String(safeUser.Balance).replace(/,/g, '')) || 0;

      return sendJson(res, 200, { success: true, account: safeUser });
    }

    // 7. Deposit Funds
    if (pathname === '/api/deposit' && req.method === 'POST') {
      const { acc, amount, description } = await parseBody(req);
      const numAmount = parseFloat(amount);
      if (!numAmount || numAmount <= 0) {
        return sendJson(res, 400, { success: false, message: 'Invalid deposit amount' });
      }

      const user = db.prepare('SELECT Name, Balance FROM Account WHERE Acc = ?').get(acc);
      if (!user) {
        return sendJson(res, 404, { success: false, message: 'Account not found' });
      }

      const currentBal = parseFloat(String(user.Balance).replace(/,/g, '')) || 0;
      const newBal = currentBal + numAmount;

      // Update Account and Balances tables
      db.prepare('UPDATE Account SET Balance = ? WHERE Acc = ?').run(newBal, acc);
      db.prepare('UPDATE Balances SET Balance = ? WHERE Acc = ?').run(newBal, acc);

      // Record transaction
      db.prepare(`
        INSERT INTO Transactions (Acc, Type, Amount, BalanceAfter, Description)
        VALUES (?, 'DEPOSIT', ?, ?, ?)
      `).run(acc, numAmount, newBal, description || 'Cash / Online Deposit');

      return sendJson(res, 200, {
        success: true,
        message: `Successfully deposited $${numAmount.toLocaleString()}. New Balance: $${newBal.toLocaleString()}`,
        newBalance: newBal
      });
    }

    // 8. Withdraw Funds
    if (pathname === '/api/withdraw' && req.method === 'POST') {
      const { acc, pin, amount, description } = await parseBody(req);
      const numAmount = parseFloat(amount);
      if (!numAmount || numAmount <= 0) {
        return sendJson(res, 400, { success: false, message: 'Invalid withdrawal amount' });
      }

      const user = db.prepare('SELECT Name, Pin, Balance FROM Account WHERE Acc = ?').get(acc);
      if (!user) {
        return sendJson(res, 404, { success: false, message: 'Account not found' });
      }

      if (pin && String(user.Pin) !== String(pin)) {
        return sendJson(res, 403, { success: false, message: 'Incorrect PIN' });
      }

      const currentBal = parseFloat(String(user.Balance).replace(/,/g, '')) || 0;
      if (currentBal < numAmount) {
        return sendJson(res, 400, { success: false, message: `Insufficient balance. Available: $${currentBal.toLocaleString()}` });
      }

      const newBal = currentBal - numAmount;

      db.prepare('UPDATE Account SET Balance = ? WHERE Acc = ?').run(newBal, acc);
      db.prepare('UPDATE Balances SET Balance = ? WHERE Acc = ?').run(newBal, acc);

      db.prepare(`
        INSERT INTO Transactions (Acc, Type, Amount, BalanceAfter, Description)
        VALUES (?, 'WITHDRAWAL', ?, ?, ?)
      `).run(acc, numAmount, newBal, description || 'ATM / Branch Withdrawal');

      return sendJson(res, 200, {
        success: true,
        message: `Successfully withdrew $${numAmount.toLocaleString()}. New Balance: $${newBal.toLocaleString()}`,
        newBalance: newBal
      });
    }

    // 9. Peer-to-Peer Transfer
    if (pathname === '/api/transfer' && req.method === 'POST') {
      const { fromAcc, toAcc, amount, pin, description } = await parseBody(req);
      const numAmount = parseFloat(amount);

      if (!numAmount || numAmount <= 0) {
        return sendJson(res, 400, { success: false, message: 'Invalid transfer amount' });
      }

      if (String(fromAcc) === String(toAcc)) {
        return sendJson(res, 400, { success: false, message: 'Sender and recipient accounts cannot be the same' });
      }

      const sender = db.prepare('SELECT Name, Pin, Balance FROM Account WHERE Acc = ?').get(fromAcc);
      if (!sender) {
        return sendJson(res, 404, { success: false, message: 'Sender account not found' });
      }

      if (pin && String(sender.Pin) !== String(pin)) {
        return sendJson(res, 403, { success: false, message: 'Incorrect PIN verification' });
      }

      const recipient = db.prepare('SELECT Name, Balance FROM Account WHERE Acc = ?').get(toAcc);
      if (!recipient) {
        return sendJson(res, 404, { success: false, message: `Recipient Account #${toAcc} does not exist in the bank` });
      }

      const senderBal = parseFloat(String(sender.Balance).replace(/,/g, '')) || 0;
      if (senderBal < numAmount) {
        return sendJson(res, 400, { success: false, message: `Insufficient funds. Current balance: $${senderBal.toLocaleString()}` });
      }

      const newSenderBal = senderBal - numAmount;
      const recipBal = parseFloat(String(recipient.Balance).replace(/,/g, '')) || 0;
      const newRecipBal = recipBal + numAmount;

      // Atomic update
      db.prepare('UPDATE Account SET Balance = ? WHERE Acc = ?').run(newSenderBal, fromAcc);
      db.prepare('UPDATE Balances SET Balance = ? WHERE Acc = ?').run(newSenderBal, fromAcc);

      db.prepare('UPDATE Account SET Balance = ? WHERE Acc = ?').run(newRecipBal, toAcc);
      db.prepare('UPDATE Balances SET Balance = ? WHERE Acc = ?').run(newRecipBal, toAcc);

      // Record transaction for Sender (Debit)
      db.prepare(`
        INSERT INTO Transactions (Acc, Type, Amount, BalanceAfter, Description, RecipientAcc)
        VALUES (?, 'TRANSFER_SENT', ?, ?, ?, ?)
      `).run(fromAcc, numAmount, newSenderBal, description || `Transfer to ${recipient.Name} (Acc #${toAcc})`, toAcc);

      // Record transaction for Recipient (Credit)
      db.prepare(`
        INSERT INTO Transactions (Acc, Type, Amount, BalanceAfter, Description, RecipientAcc)
        VALUES (?, 'TRANSFER_RECEIVED', ?, ?, ?, ?)
      `).run(toAcc, numAmount, newRecipBal, `Transfer received from ${sender.Name} (Acc #${fromAcc})`, fromAcc);

      return sendJson(res, 200, {
        success: true,
        message: `Transferred $${numAmount.toLocaleString()} to ${recipient.Name} (Acc #${toAcc}) successfully.`,
        newBalance: newSenderBal
      });
    }

    // 10. Transaction History & Statements
    if (pathname.startsWith('/api/transactions/') && req.method === 'GET') {
      const acc = pathname.split('/').pop();
      const typeFilter = url.searchParams.get('type');

      let query = 'SELECT * FROM Transactions WHERE Acc = ?';
      const params = [acc];

      if (typeFilter && typeFilter !== 'ALL') {
        query += ' AND Type = ?';
        params.push(typeFilter);
      }

      query += ' ORDER BY id DESC LIMIT 100';

      const transactions = db.prepare(query).all(...params);
      return sendJson(res, 200, { success: true, transactions });
    }

    // 11. Update Profile & Security
    if (pathname === '/api/profile/update' && req.method === 'POST') {
      const { acc, mob, address, secQ, secA } = await parseBody(req);
      db.prepare(`
        UPDATE Account 
        SET Mob = ?, Address = ?, Sec_Q = COALESCE(?, Sec_Q), Sec_A = COALESCE(?, Sec_A)
        WHERE Acc = ?
      `).run(mob, address, secQ, secA, acc);

      return sendJson(res, 200, { success: true, message: 'Profile updated successfully' });
    }

    // 12. Change PIN
    if (pathname === '/api/change-pin' && req.method === 'POST') {
      const { acc, currentPin, newPin } = await parseBody(req);
      const user = db.prepare('SELECT Pin FROM Account WHERE Acc = ?').get(acc);
      if (!user) {
        return sendJson(res, 404, { success: false, message: 'Account not found' });
      }
      if (String(user.Pin) !== String(currentPin)) {
        return sendJson(res, 400, { success: false, message: 'Current PIN is incorrect' });
      }

      db.prepare('UPDATE Account SET Pin = ? WHERE Acc = ?').run(newPin, acc);
      return sendJson(res, 200, { success: true, message: 'PIN changed successfully!' });
    }

  } catch (apiErr) {
    console.error(`[API Error] ${pathname}:`, apiErr);
    return sendJson(res, 500, { success: false, message: 'Internal server error: ' + apiErr.message });
  }

  // --- STATIC FILE SERVING ---
  let filePath = path.join(PUBLIC_DIR, pathname === '/' ? 'index.html' : pathname);
  const ext = path.extname(filePath).toLowerCase();

  fs.stat(filePath, (err, stats) => {
    if (err || !stats.isFile()) {
      // Fallback to index.html for SPA routing if needed
      filePath = path.join(PUBLIC_DIR, 'index.html');
    }

    const contentType = MIME_TYPES[path.extname(filePath).toLowerCase()] || 'text/plain';
    fs.readFile(filePath, (readErr, content) => {
      if (readErr) {
        res.writeHead(404, { 'Content-Type': 'text/plain' });
        return res.end('404 Not Found');
      }
      res.writeHead(200, {
        'Content-Type': contentType,
        'Cache-Control': 'no-cache'
      });
      res.end(content);
    });
  });
});

server.listen(PORT, () => {
  console.log(`=======================================================`);
  console.log(`🚀 Modern Banking Web Application is LIVE!`);
  console.log(`🌐 Open in browser: http://localhost:${PORT}`);
  console.log(`📁 Database connected: ${DB_PATH}`);
  console.log(`=======================================================`);
});
