package com.gym.membership;

import java.time.LocalDate;

public class Membership {
    private int id;
    private int userId;
    private String membershipType;
    private LocalDate startDate;
    private LocalDate endDate;

    public Membership(int id, int userId, String membershipType, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.userId = userId;
        this.membershipType = membershipType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Membership(int userId, String membershipType, LocalDate startDate, LocalDate endDate) {
        this.userId = userId;
        this.membershipType = membershipType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getMembershipType() { return membershipType; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }

    public void setId(int id) { this.id = id; }
}
