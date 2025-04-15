package util;

import exceptions.AuthenticationException;
import model.User;

public class Session {
    private static User currentUser = null;

    public static void login(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
    }

    public static User currentUser() throws AuthenticationException {
        if (currentUser == null) {
            throw new AuthenticationException("No active session");
        }
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}