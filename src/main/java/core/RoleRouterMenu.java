package core;

import exceptions.AuthenticationException;
import exceptions.AuthorizationException;
import menu.AdminMenu;
import menu.MemberMenu;
import menu.TrainerMenu;
import model.User;
import util.Session;

public class RoleRouterMenu {
    private final AdminMenu adminMenu;
    private final MemberMenu memberMenu;
    private final TrainerMenu trainerMenu;

    public RoleRouterMenu() {
        this.adminMenu = new AdminMenu();
        this.memberMenu = new MemberMenu();
        this.trainerMenu = new TrainerMenu();
    }

    public void route() throws AuthorizationException, AuthenticationException {
        User user = Session.currentUser();
        if (user == null) {
            throw new AuthorizationException("No active session");
        }

        switch (user.getRole().toLowerCase()) {
            case "admin" -> adminMenu.show();
            case "member" -> memberMenu.show();
            case "trainer" -> trainerMenu.show();
            default -> throw new AuthorizationException("Unknown role: " + user.getRole());
        }
    }
}