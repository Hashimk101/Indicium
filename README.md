<div align="center">

<img src="src\main\resources\com\indicium\ui\Assets\Indicium_Logo_v2.png" alt="Indicium Logo" width="150" />

# INDICIUM
### Forensic Case Management System

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-25-0097A7?style=for-the-badge&logo=java&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Supabase-3ECF8E?style=for-the-badge&logo=supabase&logoColor=white)
![HikariCP](https://img.shields.io/badge/HikariCP-Pooling-FF6F00?style=for-the-badge&logoColor=white)
![Status](https://img.shields.io/badge/Status-Active-2E7D32?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-7B1FA2?style=for-the-badge)
![Contributors](https://img.shields.io/badge/Contributors-3-0097A7?style=for-the-badge&logo=github&logoColor=white)

*A full-stack desktop platform for managing forensic investigations,*
*evidence chain-of-custody, and secure administrative oversight.*

[Features](#-features) · [Screenshots](#-screenshots) · [Getting Started](#-getting-started) · [Architecture](#-architecture) · [Database Schema](#-database-schema) · [Security](#-data-persistence--security) · [Team](#-team)

</div>

---

## 📌 Overview

**Indicium** is a desktop forensic case management system built with **Java**, **JavaFX**, and **Supabase (PostgreSQL)**. It centralizes the full investigative workflow — from case creation and evidence ingestion to timeline tracking, integrity verification, and emergency system controls.

Designed with a real-world forensic environment in mind, Indicium enforces **role-based access control**, maintains a **tamper-evident audit log**, and provides administrators with powerful tools including an **emergency lockdown kill switch**.

> Built as a team project by 3 contributors as part of a software design and analysis (SDA)  course.

---

## ✨ Features

### 🗂️ Case Management
- Create, edit, lock, and archive cases with a single click
- Filter by status, priority, and date range
- Overflow context menu with role-aware actions *(admin-only lock/archive)*
- Live count badges — Active, Archived, Locked

### 🔬 Evidence Module
- Upload evidence via **drag & drop** or file browser
- **Bulk import** an entire folder in one operation
- SHA-256 hash fingerprinting on every file at ingest
- One-click **integrity verification** — detects tampering instantly
- **OS-native file viewer** — opens any file in the system's default app
- Cross-case evidence linking with a dedicated Link Manager
- Evidence statuses: `Verified` · `Collected` · `Linked` · `Archived` · `Discarded`

### 📅 Timeline
- Attach timestamped events to any case
- Chronological event log per investigation

### 🔐 Identity & Access Control
- SHA-256 password hashing
- Role-Based Access Control — `ADMIN` / `INVESTIGATOR`
- Temporary password flow for new user provisioning
- Live password strength indicator
- Admin-driven Identity Manager for user creation and role assignment

### 🛡️ Integrity Manager *(Admin Only)*
- Real-time **System Online / System Locked** status chip
- **Emergency Lockdown Kill Switch** — terminates all non-admin sessions instantly
- Secondary security code required to activate
- Full audit trail on every lockdown and lift event

### 📊 Home Dashboard
- Real-time metrics: Active Cases · Evidence Items · Timeline Events · Audit Entries
- Personalized session greeting with avatar initials

### 📋 Audit Log
- Every critical action is logged with user ID, timestamp, and category
- Categories: `SECURITY` · `EVIDENCE` · `CASE` · `ACCESS`

---

## 🖥️ Screenshots


| Home Dashboard | Case Manager |
|:-:|:-:|
| ![Home](screenshots/Home.png) | ![Cases](screenshots/Cases.png) |

| Evidence Module | Integrity Manager |
|:-:|:-:|
| ![Evidence](screenshots/Evidence.png) | ![Integrity](screenshots/Integrity.png) |

---

## 🚀 Getting Started

### Prerequisites

| Requirement | Version |
|-------------|---------|
| JDK | 21+ |
| JavaFX SDK | 25 |
| Maven | 3.8+ |
| Supabase Account | — |

### Installation

**1. Clone the repository**
```bash
git clone https://github.com/Hashimk101/indicium.git
cd indicium
```

**2. Set up your Supabase project**
- Go to [supabase.com](https://supabase.com) and create a new project
- Navigate to **Settings → Database** and copy your connection string
- It will look like:
```
jdbc:postgresql://aws-1-ap-southeast-1.pooler.supabase.com:5432/postgres
```

**3. Run the schema**
- Go to your Supabase project → **SQL Editor**
- Paste and run the contents of `schema/indicium_schema.sql`

**4. Configure your connection**


**5. Build and run**
```bash
mvn clean javafx:run
```

> ⚠️ Make sure the **PostgreSQL JDBC driver** and **HikariCP** are in your `pom.xml`:
> ```xml
> <dependency>
>     <groupId>org.postgresql</groupId>
>     <artifactId>postgresql</artifactId>
>     <version>42.7.3</version>
> </dependency>
> <dependency>
>     <groupId>com.zaxxer</groupId>
>     <artifactId>HikariCP</artifactId>
>     <version>5.1.0</version>
> </dependency>
> ```

---

## 🏗️ Architecture

```
indicium/
├── src/main/java/com/indicium/
│   ├── controllers/          # Business logic (CaseManager, EvidenceManager)
│   ├── models/               # Domain models (Case, Evidence, UserAuth...)
│   ├── repository/           # Data access layer (CaseRepository, EvidenceRepo...)
│   ├── services/             # Cross-cutting services
│   │   ├── SessionManager    # Singleton session state
│   │   ├── AccessManager     # Lockdown state management
│   │   ├── AuditLog          # Forensic audit trail
│   │   └── HashGenerator     # SHA-256 file fingerprinting
│   └── ui/                   # JavaFX controllers + FXML + CSS
│       ├── HomeController
│       ├── CaseDashBoardController
│       ├── EvidenceDashBoardController
│       ├── IntegrityManagerDashboardController
│       └── IdentityManagerDashboardController
├── src/main/resources/
│   └── com/indicium/ui/
│       ├── *.fxml            # UI layouts
│       ├── *.css             # Stylesheets
│       └── Assets/           # Icons
└── schema/
    └── indicium_schema.sql   # Full PostgreSQL schema
```

### Design Patterns Used

| Pattern | Where |
|---------|-------|
| **Singleton** | `SessionManager`, `AccessManager` |
| **Repository** | `CaseRepository`, `EvidenceRepo` |
| **MVC** | All JavaFX controllers + FXML |
| **RBAC** | Role checks on every sensitive action |

---

## 🗄️ Database Schema

Hosted on **Supabase (PostgreSQL)** via the `aws-1-ap-southeast-1` pooler endpoint. Full schema in `/schema/indicium_schema.sql`.

### Core Tables

```sql
-- Users
CREATE TABLE Users (
    UserID       SERIAL PRIMARY KEY,
    FullName     VARCHAR(255)  NOT NULL,
    Email        VARCHAR(255)  UNIQUE NOT NULL,
    PasswordHash VARCHAR(64)   NOT NULL,  -- SHA-256
    Role         VARCHAR(20)   DEFAULT 'INVESTIGATOR'
                 CHECK (Role IN ('ADMIN', 'INVESTIGATOR')),
    IsActive     BOOLEAN       DEFAULT TRUE
);

-- Cases
CREATE TABLE Cases (
    CaseID      SERIAL PRIMARY KEY,
    Title       VARCHAR(255)  NOT NULL,
    Description TEXT,
    Status      VARCHAR(20)   DEFAULT 'OPEN'
                CHECK (Status IN ('OPEN', 'ARCHIVED', 'CLOSED')),
    CreatedAt   TIMESTAMP     DEFAULT NOW()
);

-- Evidence
CREATE TABLE Evidence (
    EvidenceID   SERIAL PRIMARY KEY,
    CaseID       INT           NOT NULL REFERENCES Cases(CaseID),
    EvidenceType VARCHAR(50),
    HashValue    VARCHAR(64)   -- SHA-256 fingerprint for integrity tracking
);

-- Forensic Audit Log (append-only)
CREATE TABLE ForensicAuditLog (
    LogID     SERIAL PRIMARY KEY,
    UserID    INT           REFERENCES Users(UserID),
    Action    VARCHAR(255)  NOT NULL,
    Role      VARCHAR(20),
    Timestamp TIMESTAMP     DEFAULT (NOW() AT TIME ZONE 'Asia/Karachi')
);
```

### Entity Relationships

```
Users ──────────────────── ForensicAuditLog
  │                              │
  │                           UserID (FK)
  │
Cases ──────────────────── Evidence
  │                              │
  └──── CaseID (FK) ─────────────┘
```

---

## 🔒 Data Persistence & Security

Indicium is engineered with forensic-grade security and cloud reliability at its core.

### ☁️ Cloud Infrastructure
- Managed via **Supabase (PostgreSQL)**, utilizing the `aws-1-ap-southeast-1.pooler.supabase.com` endpoint for optimized **IPv4 connectivity** and global availability.

### ⚡ Performance
- Implemented **HikariCP connection pooling** to mitigate cloud network latency — achieving near-zero perceived UI lag despite operating against a remote hosted database.

### 🕐 Forensic Integrity
- The database schema enforces chronological accuracy by synchronizing all session audit timestamps to **`Asia/Karachi`** — ensuring a consistent, jurisdiction-aware chain of custody across all logged events.

### 🛡️ Access Control & Isolation
- Direct database access via **JDBC is restricted to the master account** only.
- **Row Level Security (RLS)** policies are explicitly configured to **deny all external web API traffic** — ensuring the database is exclusively accessible through the authorized application binary.
- No REST or GraphQL surface is exposed; Supabase's auto-generated API is fully disabled for this project.

```
┌─────────────────────────────────────────────┐
│              Login Screen                   │
│         SHA-256 Password Verification       │
└──────────────────┬──────────────────────────┘
                   │
       ┌───────────▼───────────┐
       │     Session Manager   │  ← Singleton, holds role + user ID
       └───────────┬───────────┘
                   │
      ┌────────────▼────────────┐
      │   Role-Based Access     │
      │  ADMIN  │  INVESTIGATOR │
      │─────────┼───────────────│
      │ All ops │ Read + Upload │
      └─────────────────────────┘
                   │
      ┌────────────▼────────────┐
      │    Access Manager       │  ← Lockdown state (persisted to DB)
      │  Kill Switch / Lift     │
      └─────────────────────────┘
                   │
      ┌────────────▼────────────┐
      │  Supabase PostgreSQL    │  ← HikariCP pool
      │  RLS: API blocked       │  ← JDBC only
      │  TZ: Asia/Karachi       │  ← Audit timestamps
      └─────────────────────────┘
```

---

## 🧪 Demo Data

The system ships with a curated set of real-world famous cases for demonstration:

| # | Case | Type | Status |
|---|------|------|--------|
| 1 | Zodiac Killer | Serial Murder | 🔴 Open |


---

## 🛠️ Built With

- **[Java 21](https://openjdk.org/)** — Core application language
- **[JavaFX 25](https://openjfx.io/)** — Desktop UI framework
- **[Supabase](https://supabase.com/)** — Hosted PostgreSQL backend
- **[PostgreSQL JDBC Driver](https://jdbc.postgresql.org/)** — Database connectivity
- **[HikariCP](https://github.com/brettwooldridge/HikariCP)** — High-performance connection pooling
- **[Maven](https://maven.apache.org/)** — Build and dependency management
- **[Icons8](https://icons8.com/)** — UI iconography

---

## 👥 Team

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/Hashimk101">
        <img src="https://github.com/Hashimk101.png" width="80" style="border-radius:50%" /><br/>
        <b>Hashim Khushal Khan</b>
      </a><br/>
      <a href="https://github.com/Hashimk101">@Hashimk101</a>
    </td>
    <td align="center">
      <a href="https://github.com/Hadiah-Batool">
        <img src="https://github.com/Hadiah-Batool.png" width="80" style="border-radius:50%" /><br/>
        <b>Hadiah Batool</b>
      </a><br/>
      <a href="https://github.com/Hadiah-Batool">@Hadiah-Batool</a>
    </td>
    <td align="center">
      <a href="https://github.com/bilalxfaisal">
        <img src="https://github.com/bilalxfaisal.png" width="80" style="border-radius:50%" /><br/>
        <b>Muhammad Bilal Faisal</b>
      </a><br/>
      <a href="https://github.com/bilalxfaisal">@bilalxfaisal</a>
    </td>
  </tr>
</table>

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

---

<div align="center">

*Built as a collaborative team project demonstrating full-stack Java desktop development,*
*forensic data management, and secure system design.*

⭐ **Star this repo if you found it useful!**

</div>
