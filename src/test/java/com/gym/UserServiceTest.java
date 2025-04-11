package com.gym;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserServiceTest {
    private final UserService userService = new UserService();

    @Test
    public void testPasswordHashing() {
        new User("test", "test@example.com", rawPassword, "MEMBER");
        String hashed = userService.hashPassword(rawPassword); // Ensure hashPassword method exists in UserService
        String hashed = userService.hashPassword(rawPassword);
        assertNotNull(hashed);
        assertNotEquals(rawPassword, hashed);
    }
}
