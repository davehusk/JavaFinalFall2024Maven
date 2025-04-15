package core;

import java.util.ArrayList;
import java.util.List;
import util.Input;
import util.Log;

public class ConsoleMenu {
    private final String title;
    private final List<MenuItem> items = new ArrayList<>();
    private boolean showBackOption = true;

    public ConsoleMenu(String title) {
        this.title = title;
    }

    public ConsoleMenu hideBackOption() {
        this.showBackOption = false;
        return this;
    }

    public void addItem(String label, Runnable action) {
        items.add(new MenuItem(label, action));
    }

    public void show() {
        while (true) {
            System.out.println("\n== " + title + " ==");
            for (int i = 0; i < items.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, items.get(i).label());
            }
            if (showBackOption) {
                System.out.println("0. Back");
            }

            try {
                int choice = Input.promptInt("Select an option");
                if (choice == 0 && showBackOption) {
                    return;
                }
                if (choice > 0 && choice <= items.size()) {
                    items.get(choice - 1).action().run();
                } else {
                    Log.warn("Invalid option selected");
                }
            } catch (Exception e) {
                Log.error("Menu error: " + e.getMessage());
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }
            }
        }
    }

    private record MenuItem(String label, Runnable action) {}
}