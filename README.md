# 🏋️ Membership System (Java)  
> Terminal-based Role & Membership Manager for Gyms  
[![Java](https://img.shields.io/badge/built%20with-Java%2017-blue)](https://www.oracle.com/java/)
[![PostgreSQL](https://img.shields.io/badge/database-PostgreSQL-blue)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE)

Welcome to the **Membership System**, a modern, clean, and fully-featured CLI application designed for **gym operators, trainers, and members** to manage daily operations like memberships, workout classes, and attendance — directly in the terminal.

---

## 📦 Features

✅ Role-based dashboards: **Admin**, **Trainer**, **Member**  
✅ Membership plan creation, purchase, and history  
✅ Secure login with BCrypt password hashing  
✅ Workout class scheduling and attendance  
✅ Admin analytics (revenue, memberships)  
✅ Interactive CLI with cancel/confirm logic  
✅ Full PostgreSQL backend with HikariCP pooling  
✅ Custom logging + validation system  
✅ Ready to extend into GUI or WebApp

---

## 🔧 Setup (For Developers)

```bash
git clone https://github.com/davehusk/MembershipSystem-Java.git
cd MembershipSystem-Java
mvn clean compile
```

➡️ Make sure PostgreSQL is installed and running with a `membershipsystem` database.

Edit `/src/main/resources/application.properties` with your credentials.

---

## 🚀 Run the App

To start:

```bash
mvn exec:java
```

To seed sample users, plans, and classes:

```bash
mvn exec:java -Dexec.args="--seed"
```

---

## 🧑‍💻 Developer Resources

* [🛠 DEVELOPER_MANUAL.md](https://github.com/davehusk/MembershipSystem-Java/blob/master/DEVELOPER_MANUAL.md)

  Learn how the system is built, extended, and structured.
* [🧭 DEVMENU_SYSTEM.md](https://github.com/davehusk/MembershipSystem-Java/blob/master/DEVMENU_SYSTEM.md)

  How to build and link menus like a pro.

---

## 📖 Full Documentation for Customers

* [📘 USER_GUIDE.md](https://github.com/davehusk/MembershipSystem-Java/blob/master/USER_GUIDE.md)

  For **end users** who just bought or downloaded the system.
* [📚 FULL_MANUAL.md](https://github.com/davehusk/MembershipSystem-Java/blob/master/FULL_MANUAL.md)

  A comprehensive manual that explains everything: setup, use, logic, diagrams, and more.

---

## 🔑 Default Test Accounts

> These are loaded when you run with `--seed`

| Role    | Email                                          | Password  |
| ------- | ---------------------------------------------- | --------- |
| Admin   | [admin@example.com](mailto:admin@example.com)     | admin123  |
| Trainer | [trainer@example.com](mailto:trainer@example.com) | train123  |
| Member  | [member@example.com](mailto:member@example.com)   | member123 |

---

## 🧠 Tech Stack

| Layer        | Tool/Library         |
| ------------ | -------------------- |
| Language     | Java 17              |
| Build Tool   | Maven                |
| Database     | PostgreSQL           |
| Pooling      | HikariCP             |
| Logging      | SLF4J + Logback      |
| Passwords    | BCrypt               |
| Architecture | MVC-inspired CLI App |

---

## 📂 Project Structure

```plaintext
src/
├── main/java/
│   ├── model/          → Data classes (User, Membership, etc.)
│   ├── dao/            → SQL logic + PostgreSQL interaction
│   ├── service/        → Business logic
│   ├── menu/           → Role-specific terminal menus
│   ├── core/           → Entry point + routing + factory
│   └── util/           → Logging, input, validation
└── resources/
    ├── application.properties
    └── logback.xml
```

---

## 🤝 License

Licensed under the [MIT License](https://github.com/davehusk/MembershipSystem-Java/blob/main/LICENSE).

Use it freely, extend it endlessly. Just don't forget to ⭐ the repo!

---

## 🙋 Support & Contributions

Open an [issue](https://github.com/davehusk/MembershipSystem-Java/issues) or submit a PR with improvements, fixes, or feature ideas.

Let’s build the cleanest, most powerful CLI system in the Java world 💪