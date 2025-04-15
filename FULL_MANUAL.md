# 🧠 Gym Membership System – Full User Manual

> **Version:** 1.0.0  
> **Author:** David Husk  
> **Technology Stack:** Java 17, Maven, PostgreSQL  
> **Interface:** Command-Line (Terminal)  
> **License:** MIT  

---

## 📖 Table of Contents

- [🧠 Gym Membership System – Full User Manual](#-gym-membership-system--full-user-manual)
  - [📖 Table of Contents](#-table-of-contents)
  - [📘 Introduction](#-introduction)
  - [🚀 System Features](#-system-features)
  - [💾 Installation \& Setup](#-installation--setup)
    - [✅ Requirements](#-requirements)
    - [📁 Clone the Repository](#-clone-the-repository)
    - [🛠️ Database Setup](#️-database-setup)
  - [▶️ Running the Application](#️-running-the-application)
    - [Compile the system:](#compile-the-system)
    - [(Optional) Seed database with sample users/plans/classes:](#optional-seed-database-with-sample-usersplansclasses)
    - [Start the system:](#start-the-system)
  - [🧑‍💼 User Roles \& Dashboards](#-user-roles--dashboards)
  - [🧭 Menu Descriptions](#-menu-descriptions)
    - [🛡️ Admin Menu](#️-admin-menu)
    - [📚 Member Menu](#-member-menu)
    - [🏋️ Trainer Menu](#️-trainer-menu)
  - [🧱 System Architecture Overview](#-system-architecture-overview)
  - [🔗 Entity Relationships](#-entity-relationships)
  - [🧬 Database Schema](#-database-schema)
    - [Tables Created:](#tables-created)
  - [🧪 Default Test Users](#-default-test-users)
  - [🧯 Troubleshooting](#-troubleshooting)
  - [❓ FAQs](#-faqs)
  - [✅ License](#-license)

---

## 📘 Introduction

The **Gym Membership System** is a Java-based, terminal-driven application that allows gyms to manage user roles, class attendance, membership plans, and system analytics — all from a simple command-line interface.

This system is designed with usability in mind and offers a scalable foundation for future GUI or web-based extensions.

---

## 🚀 System Features

- 🔐 Secure user login & role-based access
- 👤 User account registration and management (Admin-only)
- 💳 Membership plan purchase & management
- 🧘 Workout class creation, tracking, and attendance
- 📊 Real-time revenue insights for admins
- 📅 Attendance history for members
- ✅ Validation, logging, and transaction-safe operations
- 🧪 Seeder for testing and demo purposes

---

## 💾 Installation & Setup

### ✅ Requirements

- Java 17+ installed
- Maven installed
- PostgreSQL installed and running

### 📁 Clone the Repository

```bash
git clone https://github.com/davehusk/MembershipSystem-Java.git
cd MembershipSystem-Java
```

### 🛠️ Database Setup

1. Create a PostgreSQL database:

```sql
CREATE DATABASE membershipsystem;
```

2. Make sure your database credentials match what's in `/src/main/resources/application.properties`:

```properties
db.url=jdbc:postgresql://localhost:5432/membershipsystem
db.user=postgres
db.password=Password123
```

You may customize the credentials as needed.

---

## ▶️ Running the Application

### Compile the system:

```bash
mvn clean compile
```

### (Optional) Seed database with sample users/plans/classes:

```bash
mvn exec:java -Dexec.args="--seed"
```

### Start the system:

```bash
mvn exec:java
```

---

## 🧑‍💼 User Roles & Dashboards

There are **3 distinct roles**, each with their own dashboard and capabilities:

| Role   | Description                              |
|--------|------------------------------------------|
| Admin  | Full access: manage users, plans, revenue|
| Trainer| Manage workout classes and attendance     |
| Member | Purchase/view memberships and attend classes |

---

## 🧭 Menu Descriptions

### 🛡️ Admin Menu

- View All Users
- Search for Users
- Modify or Delete Users
- View All Memberships
- View Total Revenue
- Manage Plans (Create, Edit, Delete)

### 📚 Member Menu

- View Current Membership
- Purchase New Membership
- View Available Classes
- Attend a Class
- View Attendance History

### 🏋️ Trainer Menu

- Create Workout Class
- View My Classes
- Delete a Class
- View Class Attendance

---

## 🧱 System Architecture Overview

```text
src/
├── main/
│   ├── java/
│   │   ├── core/              → Menu navigation, Role routing
│   │   ├── dao/               → Database access objects
│   │   ├── exceptions/        → Custom error handling
│   │   ├── menu/              → CLI menu implementations
│   │   ├── model/             → User, Membership, Class, etc.
│   │   ├── service/           → Business logic layer
│   │   └── util/              → Helpers: Input, Logging, DB, Validation
│   └── resources/             → Configuration files (DB, logback)
```

---

## 🔗 Entity Relationships

```plaintext
User (id)
├── Membership (user_id → id)
│   └── MembershipPlan (plan_id → id)
├── WorkoutClass (trainer_id → id)
└── ClassAttendance (user_id → id, class_id → WorkoutClass.id)
```

All data is relational and validated using DAO queries and helper methods.

---

## 🧬 Database Schema

The system includes a SQL script `schema.sql` for reference or manual resets.

### Tables Created:
- `users`
- `membership_plans`
- `memberships`
- `workout_classes`
- `class_attendance`

All foreign keys are enforced with proper constraints.

---

## 🧪 Default Test Users

After seeding with `--seed`, these accounts are available:

| Role   | Email                  | Password    |
|--------|------------------------|-------------|
| Admin  | admin@example.com      | admin123    |
| Trainer| trainer@example.com    | train123    |
| Member | member@example.com     | member123   |

---

## 🧯 Troubleshooting

- **App fails to start?**  
  Check DB config in `application.properties` and confirm PostgreSQL is running.

- **Login fails with valid credentials?**  
  Ensure the password is typed correctly — it's case-sensitive and hashed via BCrypt.

- **No plans/classes show up?**  
  Seed your database with:  
  ```bash
  mvn exec:java -Dexec.args="--seed"
  ```

- **I see weird characters in the terminal.**  
  Ensure your terminal supports ANSI escape sequences for screen clearing.

---

## ❓ FAQs

**Q: Can I add more roles?**  
Yes. Just update the `VALID_ROLES` set in `ValidationUtil` and extend logic in `RoleRouterMenu`.

**Q: Is the system secure?**  
All passwords are hashed using [BCrypt](https://github.com/jeremyh/jBCrypt). Inputs are validated.

**Q: Can this be extended into a GUI or web app?**  
Absolutely. This backend is modular and built for clean separation — making it easy to plug into a web or desktop frontend.

---

## ✅ License

This project is licensed under the MIT License. Feel free to use and expand it for your own gym or as a project portfolio piece.

---
