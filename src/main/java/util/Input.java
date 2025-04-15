package util;

import java.util.Scanner;

import exceptions.ValidationException;

public class Input {
    private static final Scanner scanner = new Scanner(System.in);

    public static String prompt(String message) throws ValidationException {
        String result = prompt(message, String::trim, "0");
        if (result == null) {
            throw new ValidationException("Input was cancelled by the user.");
        }
        return result;
    }

    public static int promptInt(String message) throws ValidationException {
        Integer result = prompt(message, input -> {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Please enter a valid integer number", e);
            }
        }, "cancel");
        if (result == null) {
            throw new ValidationException("Input was cancelled by the user.");
        }
        return result;
    }

    public static double promptDouble(String message) throws ValidationException {
        Double result = prompt(message, input -> {
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Please enter a valid decimal number", e);
            }
        }, "cancel");
        if (result == null) {
            throw new ValidationException("Input was cancelled by the user.");
        }
        return result;
    }

    private static <T> T prompt(String message, java.util.function.Function<String, T> parser, String cancelKeyword) throws ValidationException {
        while (true) {
            System.out.print(message + (cancelKeyword != null ? " (or '" + cancelKeyword + "' to cancel): " : ": "));
            String input = Input.scanner.nextLine().trim();
            
            if (cancelKeyword != null && input.equalsIgnoreCase(cancelKeyword)) {
                return null;
            }
            
            try {
                return parser.apply(input);
            } catch (Exception e) {
                System.out.println("❌ " + e.getMessage());
            }
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}