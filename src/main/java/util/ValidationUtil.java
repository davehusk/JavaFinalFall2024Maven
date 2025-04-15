package util;

import java.util.Set;
import java.util.regex.Pattern;

public class ValidationUtil {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Set<String> VALID_ROLES = Set.of("admin", "trainer", "member");

    public static void validateNotBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank");
        }
    }

    public static void validateLength(String value, int min, int max, String fieldName) {
        if (value.length() < min || value.length() > max) {
            throw new IllegalArgumentException(fieldName + " must be between " + min + " and " + max + " characters");
        }
    }

    public static void validateEmail(String email) {
        validateNotBlank(email, "Email");
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    public static void validateRole(String role) {
        validateNotBlank(role, "Role");
        if (!VALID_ROLES.contains(role.toLowerCase())) {
            throw new IllegalArgumentException("Invalid role. Must be one of: " + VALID_ROLES);
        }
    }

    public static void validatePositive(int value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " must be positive");
        }
    }

    public static void validatePrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
    }
}