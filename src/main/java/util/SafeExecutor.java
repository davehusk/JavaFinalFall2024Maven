package util;

public class SafeExecutor {
    public static void safe(Runnable action) {
        try {
            action.run();
        } catch (Exception e) {
            System.out.println("❌ Something went wrong: " + e.getMessage());
        }
    }
}
