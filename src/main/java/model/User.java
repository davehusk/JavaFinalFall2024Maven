package model;

import util.ValidationUtil;

public class User {
    private int id;
    private String name;
    private String email;
    private String password; // hashed
    private String role;

    public User() {}

    public User(int id, String name, String email, String password, String role) {
        setId(id);
        setName(name);
        setEmail(email);
        setPassword(password);
        setRole(role);
    }

    // Getters and setters with validation
    public int getId() { return id; }
    public void setId(int id) { 
        ValidationUtil.validatePositive(id, "ID");
        this.id = id; 
    }

    public String getName() { return name; }
    public void setName(String name) {
        ValidationUtil.validateNotBlank(name, "Name");
        ValidationUtil.validateLength(name, 2, 50, "Name");
        this.name = name.trim();
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        ValidationUtil.validateEmail(email);
        this.email = email.toLowerCase().trim();
    }

    public String getPassword() { return password; }
    public void setPassword(String password) {
        ValidationUtil.validateNotBlank(password, "Password");
        this.password = password;
    }

    public String getRole() { return role; }
    public void setRole(String role) {
        ValidationUtil.validateRole(role);
        this.role = role.toLowerCase();
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", role='" + role + '\'' +
               '}';
    }
}