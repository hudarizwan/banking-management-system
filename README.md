# 🏦 Apex Bank — Modern Banking Management System

[![Java](https://img.shields.io/badge/Java-8-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)
[![Node.js](https://img.shields.io/badge/Node.js-24.x-339933?style=for-the-badge&logo=nodedotjs&logoColor=white)](https://nodejs.org/)
[![SQLite](https://img.shields.io/badge/SQLite-3-003B57?style=for-the-badge&logo=sqlite&logoColor=white)](https://www.sqlite.org/)
[![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)](https://developer.mozilla.org/en-US/docs/Web/HTML)
[![CSS3](https://img.shields.io/badge/CSS3-Vanilla_Fintech_Dark-1572B6?style=for-the-badge&logo=css3&logoColor=white)](https://developer.mozilla.org/en-US/docs/Web/CSS)
[![JavaScript](https://img.shields.io/badge/JavaScript-ES6+-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)](https://developer.mozilla.org/en-US/docs/Web/JavaScript)

A full-fledged, dual-platform **Banking Management System** engineered with both a **Legacy Java Swing Desktop Client** and a modernized **Fintech Web Application** powered by a native **Node.js REST API** and a shared **SQLite** database.

## 📖 Overview

**Apex Bank** is a comprehensive banking management platform that bridges classic desktop software with modern web architecture. Built around a shared ACID-compliant SQLite database, it provides customer account lifecycle management, real-time peer-to-peer fund transfers, automated ledger auditing with balance tracking, virtual debit card visualization, and secure PIN recovery.

---

## 🏗️ Technology Stack

| Layer | Component | Technologies Used | Description |
| :--- | :--- | :--- | :--- |
| **Web Frontend** | Client Portal UI | HTML5, Vanilla CSS3 (Custom Properties), ES6+ JavaScript | Zero-framework, responsive luxury fintech design with glassmorphism, virtual debit card, and real-time updates. |
| **Web Backend** | REST API Server | Node.js (v24+), Native `node:sqlite`, HTTP/JSON | Lightweight, zero-dependency REST server directly interfacing with the SQLite database. |
| **Desktop Client** | Java GUI Application | Java 8, Java Swing, NetBeans GUI Builder, JDBC | Classic desktop client featuring `Authentication`, `Loading`, `Account`, and `MyPage` forms. |
| **Database** | Shared Persistence Layer | SQLite 3 (`bank.sqlite`) | Shared database ensuring 100% bidirectional synchronization between Desktop and Web clients. |

---

## 🗄️ Database Architecture & Schema

The system utilizes an embedded **SQLite (`bank.sqlite`)** database with three core tables.

### 1. `Account` Table (Customer Master Records)
Stores personal and identity data, credentials, and current balances for every bank customer.

| Column | Data Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| `Acc` | `INTEGER` | `PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE` | Unique numeric account number (e.g. `210`, `1572`, `3045`). |
| `Name` | `CHAR` | — | Customer's full name. |
| `DOB` | `INTEGER` / `CHAR` | — | Date of birth. |
| `Pin` | `INTEGER` | `NOT NULL` | Secret numeric security PIN for authentication and authorizations. |
| `Acc_Type` | `CHAR` | — | Account tier: `Savings`, `Current`, or `Premium`. |
| `Ethnicity` | `CHAR` | — | Demographic record. |
| `MICR_No` | `CHAR` | — | Magnetic Ink Character Recognition bank branch routing code. |
| `Gender` | `CHAR` | — | Customer gender. |
| `Mob` | `INTEGER` | — | Primary mobile phone number. |
| `Address` | `CHAR` | — | Residential mailing address. |
| `Sec_Q` | `CHAR` | — | Security question for PIN recovery. |
| `Sec_A` | `CHAR` | — | Answer to security question (verified during reset). |
| `Balance` | `INTEGER` / `REAL` | — | Current available account balance. |

```sql
CREATE TABLE "Account" (
  "Acc" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,
  "Name" CHAR,
  "DOB" INTEGER,
  "Pin" INTEGER,
  "Acc_Type" CHAR,
  "Ethnicity" CHAR,
  "MICR_No" CHAR,
  "Gender" CHAR,
  "Mob" INTEGER,
  "Address" CHAR,
  "Sec_Q" CHAR,
  "Sec_A" CHAR,
  "Balance" INTEGER
);
```

---

### 2. `Transactions` Table (Chronological Ledger)
Maintains an immutable audit log of all financial events with running balances and timestamps.

| Column | Data Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| `id` | `INTEGER` | `PRIMARY KEY AUTOINCREMENT` | Unique transaction sequence identifier (`#TX-0000X`). |
| `Acc` | `INTEGER` | `NOT NULL` | Account number associated with the transaction. |
| `Type` | `TEXT` | `NOT NULL` | Operation: `DEPOSIT`, `WITHDRAWAL`, `TRANSFER_SENT`, `TRANSFER_RECEIVED`. |
| `Amount` | `REAL` | `NOT NULL` | Transaction monetary value in USD. |
| `BalanceAfter`| `REAL` | `NOT NULL` | Account balance immediately following the transaction. |
| `Description` | `TEXT` | — | Transaction memo or description. |
| `Timestamp` | `TEXT` | `DEFAULT (datetime('now', 'localtime'))`| System date and time of event execution. |
| `RecipientAcc`| `INTEGER` | — | Target account for peer-to-peer transfers. |

```sql
CREATE TABLE "Transactions" (
  "id" INTEGER PRIMARY KEY AUTOINCREMENT,
  "Acc" INTEGER NOT NULL,
  "Type" TEXT NOT NULL,
  "Amount" REAL NOT NULL,
  "BalanceAfter" REAL NOT NULL,
  "Description" TEXT,
  "Timestamp" TEXT DEFAULT (datetime('now', 'localtime')),
  "RecipientAcc" INTEGER
);
```

---

### 3. `Balances` Table (Legacy Support)
Synchronized parallel table maintained for backward compatibility with the legacy Java NetBeans Swing client.

| Column | Data Type | Description |
| :--- | :--- | :--- |
| `Name` | `CHAR` | Account holder name. |
| `Acc` | `INTEGER` | Account number. |
| `MICR_No` | `INTEGER` | Branch routing identifier. |
| `Balance` | `INTEGER` | Account balance. |

---

## ⚡ Core Features

### 1. 🛡️ Authentication & PIN Recovery
- **Masked Credentials:** Secure numeric PIN entry with toggleable show/hide visibility.
- **Remember Account:** Browser-native local storage remembers the user's account number for subsequent sessions without exposing secret PINs.
- **2-Step Security PIN Recovery:** Built-in identity verification workflow using the registered security question and answer to securely reset forgotten PINs.

### 2. 💳 Virtual Debit Card & Financial Overview
- Interactive Platinum Debit Card with dynamic customer name, masked account number, balance, EMV chip, and NFC contact icons.
- Real-time financial metric cards tracking Available Balance, Total Inflow (Credits), Total Outflow (Debits), and Logged Transactions.

### 3. 💸 Financial Transactions
- **Deposits:** Instant balance replenishment with quick-amount selectors ($100, $500, $1,000, $5,000) or custom inputs.
- **Withdrawals:** ATM cash withdrawal with balance verification and PIN authorization.
- **Peer-to-Peer Transfers:** Instant fund transfers between accounts featuring **live recipient verification** (displays recipient name as you type the account number).

### 4. 📜 Audit Ledger & Statement Generation
- Filterable transaction history by `All`, `Deposits`, `Withdrawals`, and `Transfers`.
- Live search bar filtering transactions by description or reference ID.
- **CSV Statement Export:** Generates an official, printable `.csv` financial statement with a single click.

---

## 📂 Project Directory Structure

```
Banking-Management-System/
├── .gitignore                                 <-- Git rules (ignores build & caches)
├── README.md                                  <-- Comprehensive project documentation
├── launch-banking-web-app.bat                 <-- One-click web app launcher (starts server & opens browser)
│
├── web-banking-app/                           <-- Modern Fullstack Web Banking Application
│   ├── server.js                              <-- Native Node.js REST API with node:sqlite
│   ├── package.json                           <-- Node project definition
│   └── public/                                <-- Static Web Assets
│       ├── index.html                         <-- Semantic HTML5 layout & accessible modals
│       ├── css/
│       │   └── style.css                      <-- Vanilla CSS design system & glassmorphism
│       └── js/
│           └── app.js                         <-- Client-side state, API calls & CSV export
│
└── JAVA NetBeans Banking Management System/   <-- Original Java Swing Desktop Application
    ├── bank.sqlite                            <-- Shared SQLite database
    ├── build.xml                              <-- Ant build configuration
    ├── run.bat                                <-- Batch script to launch Java desktop GUI
    ├── manifest.mf                            <-- JAR manifest
    ├── jcalendar.jar                          <-- Swing date chooser library
    ├── JTattoo-1.6.11.jar                     <-- Swing Look & Feel library
    ├── rs2xml.jar                             <-- ResultSet to TableModel adapter
    ├── sqlitejdbc-v056.jar                    <-- SQLite JDBC driver
    ├── nbproject/                             <-- NetBeans project metadata
    ├── src/                                   <-- Java Source Files
    │   ├── Account.java & .form               <-- Account registration window
    │   ├── Authentication.java & .form        <-- Login & authentication window
    │   ├── Loading.java & .form               <-- Splash/progress bar window
    │   ├── MyPage.java & .form                <-- Main banking dashboard window
    │   └── dbconnect.java                     <-- SQLite JDBC connection provider
    └── build/                                 <-- Pre-compiled Java classes
```

---

## 🚀 Getting Started

### Prerequisites
- **Node.js**: v20+ or v24+ *(for the Web Application)*
- **Java**: Java 8 JRE or JDK *(for the Java Desktop Client)*

---

### Option A: Running the Modern Web Banking App (Recommended)

1. **One-Click Launch (Windows):**
   Double-click [`launch-banking-web-app.bat`](file:///c:/Users/dell/Desktop/ALL%20PROJECTS/oop/Banking%20Management%20System/Banking-Management-System/launch-banking-web-app.bat) in the root directory.

2. **Manual Terminal Launch:**
   ```bash
   cd web-banking-app
   node server.js
   ```
3. Open your browser and navigate to:
   ```
   http://localhost:3000
   ```

---

### Option B: Running the Java Desktop Swing App

1. **One-Click Launch (Windows):**
   Double-click [`run.bat`](file:///c:/Users/dell/Desktop/ALL%20PROJECTS/oop/Banking%20Management%20System/Banking-Management-System/JAVA%20NetBeans%20Banking%20Management%20System/run.bat) inside the `JAVA NetBeans Banking Management System` folder.

2. **Manual Terminal Launch:**
   ```powershell
   cd "JAVA NetBeans Banking Management System"
   java -cp "build\classes;sqlitejdbc-v056.jar;rs2xml.jar;jcalendar.jar;JTattoo-1.6.11.jar" Authentication
   ```

---

## 📡 REST API Reference

| Method | Endpoint | Description | Payload |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/login` | Authenticates customer credentials | `{ "acc": 210, "pin": 216 }` |
| `POST` | `/api/register` | Registers a new customer account | `{ "name": "...", "pin": 1234, ... }` |
| `POST` | `/api/forgot-pin/question` | Retrieves security question for account | `{ "acc": 210 }` |
| `POST` | `/api/forgot-pin/reset` | Verifies security answer and resets PIN | `{ "acc": 210, "answer": "...", "newPin": 5678 }` |
| `GET` | `/api/account/:acc` | Returns account details and balance | — |
| `POST` | `/api/deposit` | Deposits funds into an account | `{ "acc": 210, "amount": 500, "description": "..." }` |
| `POST` | `/api/withdraw` | Withdraws funds with PIN verification | `{ "acc": 210, "pin": 216, "amount": 200 }` |
| `POST` | `/api/transfer` | P2P transfer between two accounts | `{ "fromAcc": 210, "toAcc": 1572, "amount": 100, "pin": 216 }` |
| `GET` | `/api/transactions/:acc` | Returns full ledger with optional filtering| `?type=DEPOSIT|WITHDRAWAL|TRANSFER` |
| `POST` | `/api/profile/update` | Updates customer contact information | `{ "acc": 210, "mob": "...", "address": "..." }` |
| `POST` | `/api/change-pin` | Updates security PIN | `{ "acc": 210, "currentPin": 216, "newPin": 4321 }` |

---

## 📄 License
This project is open-source and available under the [MIT License](LICENSE).
