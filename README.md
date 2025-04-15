# 💪 Membership System (Final Java Project)

> *"Code is not just syntax. It’s an intention, a pulse, a moment captured in logic. This project is the culmination of every concept learned, every error debugged, every late night turned into clarity."*

## 🌟 Overview

This **Membership Management System** is more than a console app — it's a dynamic platform designed to manage users, memberships, workout classes, and more, across distinct roles: **Admin**, **Trainer**, and **Member**. Built using **pure Java**, **JDBC**, and **PostgreSQL**, it reflects not just technical understanding but a philosophy of clean architecture, modular design, and practical application.

## 🧭 Features

### 🔐 Authentication
- Secure registration & login (with hashed passwords)
- Role-based access (Admin, Trainer, Member)

### 🧑‍💼 Admin Panel
- View, search, modify, and delete users
- Manage membership plans
- View total revenue from memberships
- See all active subscriptions

### 🧘 Member Dashboard
- View/purchase memberships
- Browse available workout classes
- Attend classes and track attendance history

### 🏋️ Trainer Tools
- Create and manage classes
- View who attended their sessions

### 🛠 Tech Stack
- 💻 **Java 17**
- 🗃️ **PostgreSQL**
- 🌿 **JDBC with HikariCP**
- 🔐 **BCrypt for password hashing**
- 📝 **Modular architecture**
- 📦 Follows SOLID principles
- ✅ Includes input validation, error handling, and session management

---

## 📁 Structure

```
src/
├── main/java/
│   ├── com.membership/Main.java          # App Entry Point
│   ├── core/                             # Menu and routing logic
│   ├── dao/                              # Data access (JDBC)
│   ├── model/                            # POJOs
│   ├── service/                          # Business logic
│   ├── menu/                             # Role-based UI layers
│   ├── util/                             # Utilities, DB, Input, Logs, etc.
├── resources/application.properties      # Database config
```

---

## 🌱 Seeding the System

Run with:
```bash
java -jar membership-system.jar --seed
```
Seeds the DB with:
- Admin, Trainer, Member users
- Sample plans
- Workout class & attendance

---

## 🎯 What I Learned

> “This wasn’t just about writing code — it was about designing a living, breathing system. One that responds, adapts, and grows.”

- Real-world application of **OOP principles**
- Clean separation of concerns using layered architecture
- JDBC + transaction handling + error recovery
- Deep understanding of **user roles and authorization**
- Design thinking: making the app **human-first**, not just function-first

---

## 🔥 Why This Project Matters

Because this isn’t just a final project.  
This is **the sum of my growth**, the **evidence of my persistence**, and **a launchpad** into whatever comes next in software development.

---

## 🙏 Final Thoughts

> “I wrote this to matter. To solve problems. To represent not just my ability, but my discipline and imagination. And if you're reading this... thank you for witnessing it.”

---

**Built with courage, coffee, and Ctrl+Z.**
