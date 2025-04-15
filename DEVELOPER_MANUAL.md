# 🧑‍💻 Gym Membership System – Developer Manual

> **Version:** 1.0.0  
> **Author:** David Husk  
> **Audience:** Developers, Contributors, Engineers  
> **Purpose:** To explain code structure, business logic, extensibility, and how to contribute effectively  

---

## 📖 Table of Contents

- [🧑‍💻 Gym Membership System – Developer Manual](#-gym-membership-system--developer-manual)
  - [📖 Table of Contents](#-table-of-contents)
  - [🔍 Project Overview](#-project-overview)
  - [⚙️ Tech Stack](#️-tech-stack)
  - [📁 Folder Structure](#-folder-structure)
  - [🧩 Key Components](#-key-components)
    - [`Main.java`](#mainjava)
    - [`ServiceFactory.java`](#servicefactoryjava)
    - [`Menu` Classes](#menu-classes)
    - [`DBConnection.java`](#dbconnectionjava)
  - [⚙️ Startup Lifecycle](#️-startup-lifecycle)
  - [🧠 Dependency Injection (Manual)](#-dependency-injection-manual)
  - [✍️ How to Extend Features](#️-how-to-extend-features)
    - [➕ Add a New Role (e.g., "manager")](#-add-a-new-role-eg-manager)
    - [➕ Add New Entity (e.g., Equipment)](#-add-new-entity-eg-equipment)
    - [🔄 Add a Command or Menu Item](#-add-a-command-or-menu-item)
  - [✅ Code Standards](#-code-standards)
  - [🧪 Testing \& Seeding](#-testing--seeding)
    - [Seeding](#seeding)
  - [🤝 Contributing](#-contributing)
    - [Guidelines](#guidelines)
    - [Coming Soon](#coming-soon)
    - [📩 Contact](#-contact)

---

## 🔍 Project Overview

This is a full-featured **modular, CLI-based Java application** simulating a gym system. It follows a **Service-DAO-Model architecture**, keeping logic cleanly separated from database access and presentation (menus).

> Think of it as a mini MVC for terminal apps — extensible into web frameworks like Spring Boot or Vaadin.

---

## ⚙️ Tech Stack

- **Java 17** – Core language  
- **Maven** – Build and dependency management  
- **PostgreSQL** – Persistent relational storage  
- **HikariCP** – Fast connection pooling  
- **BCrypt** – Secure password hashing  
- **SLF4J + Logback** – Logging

---

## 📁 Folder Structure

```plaintext
src/
├── main/
│   ├── java/
│   │   ├── core/              → MainMenu, RoleRouterMenu, ServiceFactory
│   │   ├── dao/               → DB access: UserDAO, PlanDAO, etc.
│   │   ├── exceptions/        → Custom exceptions (Validation, Auth, DB)
│   │   ├── menu/              → Terminal UI logic for each role
│   │   ├── model/             → Data models: User, Membership, Class
│   │   ├── service/           → Business logic per role/domain
│   │   └── util/              → Helpers (Input, Validation, DB config)
│   └── resources/
│       ├── application.properties → DB config
│       └── logback.xml           → Logging format
```

---

## 🧩 Key Components

### `Main.java`
Entry point. It initializes DB and optionally seeds the database using:

```bash
mvn exec:java -Dexec.args="--seed"
```

### `ServiceFactory.java`
Acts as a **manual dependency injector**. It builds all service classes and injects DAOs.

### `Menu` Classes
Each role has its own menu under `menu/` using `ConsoleMenu`, a reusable CLI engine.

### `DBConnection.java`
Uses **HikariCP** for efficient connection pooling.

---

## ⚙️ Startup Lifecycle

```text
Main.java
 └── DBConnection.get()
 └── Seeder.seed() [optional]
 └── new MainMenu().show()
        ├── AuthService handles login
        └── RoleRouterMenu → AdminMenu / TrainerMenu / MemberMenu
```

Each menu interacts only with its corresponding `Service`, which interacts with the proper `DAO` classes.

---

## 🧠 Dependency Injection (Manual)

Instead of using Spring, the system uses manual injection:

```java
// From ServiceFactory
private static final AuthService authService = new AuthService(userDAO);
```

If you add a new DAO or service:

1. Define the DAO in `dao/`
2. Create its corresponding service in `service/`
3. Register them in `ServiceFactory.java`
4. Use the service in any `menu/` class

---

## ✍️ How to Extend Features

### ➕ Add a New Role (e.g., "manager")

1. Add `"manager"` to `ValidationUtil.VALID_ROLES`
2. Create `ManagerMenu.java` under `menu/`
3. Create logic in `ManagerService.java`
4. Add to `RoleRouterMenu.java`:

```java
case "manager" -> new ManagerMenu().show();
```

---

### ➕ Add New Entity (e.g., Equipment)

1. Create `model/Equipment.java`  
2. Create DAO: `dao/EquipmentDAO.java`  
3. Create Service: `service/EquipmentService.java`  
4. Create Menu: `menu/EquipmentMenu.java`  
5. Link it into an existing role's dashboard or add a new role

---

### 🔄 Add a Command or Menu Item

In any `*Menu.java` file:

```java
menu.addItem("Do Something Cool", () -> {
    try {
        coolService.doSomething();
    } catch (Exception e) {
        Log.error(e.getMessage());
    }
});
```

---

## ✅ Code Standards

- Classes follow **Single Responsibility Principle**
- No static utility abuse (except for logging/input/validation)
- Clear package organization
- Logging through `Log.java` using SLF4J + Logback
- DAO pattern uses `try-with-resources` and `PreparedStatements`

---

## 🧪 Testing & Seeding

### Seeding

Use:

```bash
mvn exec:java -Dexec.args="--seed"
```

Seeds:

- 3 users (Admin, Trainer, Member)
- 2 membership plans
- 1 class
- 1 attendance log

---

## 🤝 Contributing

### Guidelines

- Fork the repo
- Branch from `dev`
- Follow existing naming patterns
- Document methods with inline comments
- Test each feature via CLI before pushing

### Coming Soon

- GitHub CI
- JUnit test coverage
- Swing-based UI prototype (optional)

---

### 📩 Contact

For questions or bugs, open a GitHub issue or contact the author directly.

> **Let’s grow this CLI into a full-stack gym powerhouse.**

---
