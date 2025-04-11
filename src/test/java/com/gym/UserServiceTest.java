package com.gym.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    private final UserService userService = new UserService();

    @Test
    public void testPasswordHashing() {
        String rawPassword = "secure123";
        User user = new User("test", "test@example.com", rawPassword, "MEMBER");
        String hashed = userService.hashPassword(rawPassword);
        assertNotNull(hashed);
        assertNotEquals(rawPassword, hashed);
    }
}
