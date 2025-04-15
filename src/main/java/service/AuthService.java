package service;

import dao.UserDAO;
import exceptions.AuthenticationException;
import exceptions.DatabaseException;
import exceptions.ValidationException;
import model.User;
import util.Hasher;
import util.ValidationUtil;

import java.util.Optional;

public class AuthService {
    private final UserDAO userDAO;

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean register(String name, String email, String password, String role)
            throws DatabaseException, ValidationException {

        ValidationUtil.validateNotBlank(name, "Name");
        ValidationUtil.validateEmail(email);
        ValidationUtil.validateNotBlank(password, "Password");
        ValidationUtil.validateRole(role);

        if (userDAO.findByEmail(email).isPresent()) {
            throw new ValidationException("Email already registered");
        }

        String hashedPassword = Hasher.hash(password);
        User user = new User(0, name, email, hashedPassword, role.toLowerCase());

        return userDAO.save(user);
    }

    public User login(String email, String password)
            throws AuthenticationException, DatabaseException {

        Optional<User> userOpt = userDAO.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new AuthenticationException("Invalid credentials");
        }

        User user = userOpt.get();
        if (!Hasher.check(password, user.getPassword())) {
            throw new AuthenticationException("Invalid credentials");
        }

        return user;
    }
}
