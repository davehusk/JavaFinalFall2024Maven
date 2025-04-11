package com.gym.membership;

import java.sql.SQLException;
import java.time.LocalDate;

import com.gym.user.User;

public class MembershipService {
    private final MembershipDAO membershipDAO = new MembershipDAO();

    public void viewMembership(User user) throws SQLException {
        Membership membership = membershipDAO.findByUserId(user.getId());
        if (membership == null) {
            System.out.println("No membership found.");
        } else {
            System.out.printf("Membership Type: %s%nStart: %s | End: %s%n",
                membership.getMembershipType(),
                membership.getStartDate(),
                membership.getEndDate()
            );
        }
    }

    public void createDefaultMembership(User user) throws SQLException {
        LocalDate now = LocalDate.now();
        Membership membership = new Membership(user.getId(), "Standard", now, now.plusMonths(1));
        membershipDAO.create(membership);
        System.out.println("Default membership created.");
    }
}
