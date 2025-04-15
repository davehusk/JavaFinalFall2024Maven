# 🧭 DEV Menu System Manual  
> For the **Gym Membership CLI Application**  
> Engine: `ConsoleMenu.java`  
> Location: `src/main/java/core/ConsoleMenu.java`

---

## 📘 Overview

The system uses a **centralized menu engine** called `ConsoleMenu`.  
Every role-based screen (Admin, Trainer, Member) uses it to create interactive, scrollable menus.

Menus are:
- Dynamic
- Easy to build
- Safe from crashes
- Extensible

---

## 📐 Menu Architecture

Each menu (like `AdminMenu`, `TrainerMenu`) is backed by this structure:

```java
ConsoleMenu menu = new ConsoleMenu("Menu Title");

menu.addItem("Option Label", () -> {
    // Action logic
});

menu.show();  // Starts the loop
```

This creates a **numbered list** in the terminal with "Back" as option `0` (unless suppressed).

---

## 🧱 ConsoleMenu Anatomy

### 🧩 Constructor

```java
new ConsoleMenu("Title");
```

- Sets the header text for the CLI menu.

---

### ➕ Adding Options

```java
menu.addItem("Label", () -> {
    // code to run when selected
});
```

Each item is a pair:  
- A **label** (what user sees)  
- A **Runnable** (what happens when clicked)

---

### 🛑 Hiding the Back Option

```java
menu.hideBackOption();
```

Use this for root menus like `MainMenu`, where exiting means closing the app.

---

### ▶️ Launching the Menu

```java
menu.show();
```

This starts the input loop. It waits for input, parses it, and executes the chosen `Runnable`.

---

## 🎮 Example: Basic Menu

```java
ConsoleMenu menu = new ConsoleMenu("Trainer Dashboard");

menu.addItem("Create Class", () -> {
    trainerService.createClass();
});

menu.addItem("View Attendance", () -> {
    trainerService.viewAttendance();
});

menu.show();
```

Will display:

```
== Trainer Dashboard ==
1. Create Class
2. View Attendance
0. Back
```

---

## 🧠 Nested Menus

You can launch another menu inside a menu item:

```java
menu.addItem("Open Plan Manager", () -> {
    new PlanManagerMenu().show();
});
```

This enables **submenus**, e.g., Admin → Manage Plans → View/Create/Edit/Delete.

---

## 🧯 Input Handling

Menu input is processed by:

```java
Input.promptInt("Select an option");
```

Invalid input is caught and logged. Errors won’t crash the menu.

---

## 🔄 Menu Loop Logic

The loop inside `ConsoleMenu.show()`:

```java
while (true) {
    // Print title
    // List items
    // Ask for choice

    if (choice == 0 && showBackOption)
        return; // Exit

    if (validChoice)
        run selected action
    else
        show warning
}
```

This loop repeats until a valid selection or `0` (Back) is chosen.

---

## 🧪 Menu Test Template

Want to build a test menu?

```java
ConsoleMenu test = new ConsoleMenu("🔧 Dev Test Menu");

test.addItem("Say Hello", () -> System.out.println("Hello!"));
test.addItem("Math Test", () -> System.out.println("2 + 2 = " + (2 + 2)));
test.show();
```

---

## 🧰 Tips & Best Practices

- Keep labels concise: `"View All Classes"` not `"Show me every workout class"`
- Use `Log.success()` or `Input.prompt("Press enter...")` after long actions
- Use `Input.promptInt(...)` or `Input.prompt(...)` inside actions when you need user input
- Use `try-catch` inside every `Runnable` to prevent exceptions from crashing the menu

---

## ➕ Adding a New Menu

Let’s say you want a `SettingsMenu`:

### 1. Create Class

```java
public class SettingsMenu {
    public void show() {
        ConsoleMenu menu = new ConsoleMenu("Settings");

        menu.addItem("Change Password", () -> {
            // password logic
        });

        menu.show();
    }
}
```

### 2. Link It From Another Menu

Inside `AdminMenu.java`:

```java
menu.addItem("Settings", () -> {
    new SettingsMenu().show();
});
```

---

## 🔄 Editing Menu Items Dynamically

While `ConsoleMenu` doesn’t support live updates yet, you can re-instantiate it conditionally.

For example:

```java
public void show(boolean isAdmin) {
    ConsoleMenu menu = new ConsoleMenu("Dashboard");

    if (isAdmin) {
        menu.addItem("View All Users", this::viewUsers);
    }

    menu.addItem("Logout", Session::logout);
    menu.show();
}
```

---

## ✅ Summary

| Action               | Code Example |
|----------------------|--------------|
| Add menu item        | `menu.addItem("Label", () -> {...});` |
| Show menu            | `menu.show();` |
| Hide "Back" option   | `menu.hideBackOption();` |
| Nested menus         | `new AnotherMenu().show();` inside item |
| Handle user input    | Use `Input.promptInt`, `Input.prompt` |
| Pause screen         | `Input.prompt("Press enter to continue...");` |

---

## 🛠 Future Upgrades (Optional Ideas)

- 🔄 Dynamic menu item updates (e.g., enable/disable)
- 🧪 Unit testing for `ConsoleMenu` loop logic
- 🧼 Backstack for nested navigation
- 🎨 ANSI-based color formatting
- 🔍 Searchable menu index

---
