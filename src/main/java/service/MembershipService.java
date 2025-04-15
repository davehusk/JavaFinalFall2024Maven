package service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import dao.MembershipDAO;
import dao.PlanDAO;
import exceptions.AuthenticationException;
import exceptions.DatabaseException;
import exceptions.ValidationException;
import model.Membership;
import model.MembershipPlan;
import util.Input;
import util.Log;
import util.Session;

public class MembershipService {
    private final MembershipDAO membershipDAO;
    private final PlanDAO planDAO;

    public MembershipService(MembershipDAO membershipDAO, PlanDAO planDAO) {
        this.membershipDAO = membershipDAO;
        this.planDAO = planDAO;
    }

    public void viewCurrentMembership() throws AuthenticationException {
        try {
            int userId = Session.currentUser().getId();
            Optional<Membership> membership = membershipDAO.getActiveMembership(userId);

            if (membership.isEmpty()) {
                Log.info("No active membership found");
                return;
            }

            Membership m = membership.get();
            MembershipPlan plan = planDAO.getPlanById(m.getPlanId())
                .orElseThrow(() -> new DatabaseException("Associated plan not found"));

            System.out.println("\nCurrent Membership Details:");
            System.out.println("--------------------------");
            System.out.printf("Plan: %s\n", plan.getName());
            System.out.printf("Start Date: %s\n", m.getStartDate());
            System.out.printf("End Date: %s\n", m.getEndDate());
            System.out.printf("Price: $%.2f\n", plan.getPrice());
            System.out.println("--------------------------");

        } catch (DatabaseException e) {
            Log.error("Error retrieving membership: " + e.getMessage());
        }
    }

    public boolean purchaseMembership() {
        try {
            int userId = Session.currentUser().getId();
            if (membershipDAO.getActiveMembership(userId).isPresent()) {
                Log.warn("You already have an active membership");
                return false;
            }

            List<MembershipPlan> plans = planDAO.getAllPlans();
            if (plans.isEmpty()) {
                Log.warn("No membership plans available");
                return false;
            }

            System.out.println("\nAvailable Membership Plans:");
            System.out.println("--------------------------");
            plans.forEach(plan -> System.out.printf(
                "%d. %s - $%.2f (%d days)\n",
                plan.getId(),
                plan.getName(),
                plan.getPrice(),
                plan.getDurationDays()
            ));
            System.out.println("--------------------------");

            int planId = Input.promptInt("Enter plan ID to purchase (0 to cancel)");
            if (planId <= 0) {
                return false;
            }

            MembershipPlan selectedPlan = plans.stream()
                .filter(p -> p.getId() == planId)
                .findFirst()
                .orElseThrow(() -> new ValidationException("Invalid plan selection"));

            Membership newMembership = new Membership();
            newMembership.setUserId(userId);
            newMembership.setPlanId(selectedPlan.getId());
            newMembership.setStartDate(LocalDate.now());
            newMembership.setEndDate(LocalDate.now().plusDays(selectedPlan.getDurationDays()));

            boolean success = membershipDAO.save(newMembership);
            if (success) {
                Log.success("Membership purchased successfully!");
                System.out.printf("Your membership is active until %s\n", newMembership.getEndDate());
            } else {
                Log.error("Failed to purchase membership");
            }
            return success;

        } catch (ValidationException e) {
            Log.error("Validation error: " + e.getMessage());
            return false;
        } catch (DatabaseException e) {
            Log.error("Database error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            Log.error("Unexpected error during membership purchase: " + e.getMessage());
            return false;
        }
    }

    public boolean createPlan() throws ValidationException {
        try {
            String name = Input.prompt("Enter plan name");
            if (name == null) return false;

            String desc = Input.prompt("Enter description");
            if (desc == null) return false;

            double price = Input.promptDouble("Enter price");
            if (price == -1) return false;

            int durationMonths = Input.promptInt("Enter duration in months");
            if (durationMonths == -1) return false;

            MembershipPlan plan = new MembershipPlan();
            plan.setName(name);
            plan.setDescription(desc);
            plan.setPrice(price);
            plan.setDurationDays(durationMonths * 30);

            boolean success = planDAO.createPlan(plan);
            System.out.println(success ? "✅ Plan created." : "❌ Failed to create plan.");
            return success;

        } catch (DatabaseException e) {
            Log.error("Error creating plan: " + e.getMessage());
            return false;
        }
    }

    public void editPlan(int id) throws ValidationException {
        try {
            Optional<MembershipPlan> opt = planDAO.getPlanById(id);
            if (opt.isEmpty()) {
                System.out.println("❌ Plan not found.");
                return;
            }

            MembershipPlan p = opt.get();
            String name = Input.prompt("Name [" + p.getName() + "]");
            String desc = Input.prompt("Description [" + p.getDescription() + "]");
            double price = Input.promptDouble("Price [" + p.getPrice() + "]");
            int duration = Input.promptInt("Duration (days) [" + p.getDurationDays() + "]");

            p.setName(name.isBlank() ? p.getName() : name);
            p.setDescription(desc.isBlank() ? p.getDescription() : desc);
            p.setPrice(price);
            p.setDurationDays(duration);

            if (planDAO.updatePlan(p)) {
                System.out.println("✅ Plan updated.");
            } else {
                System.out.println("❌ Failed to update plan.");
            }

        } catch (DatabaseException e) {
            Log.error("Error updating plan: " + e.getMessage());
        }
    }

    public void deletePlan(int id) {
        try {
            if (planDAO.deletePlan(id)) {
                System.out.println("✅ Plan deleted.");
            } else {
                System.out.println("❌ Could not delete plan.");
            }
        } catch (DatabaseException e) {
            Log.error("Error deleting plan: " + e.getMessage());
        }
    }

    public void viewAllPlans() {
        try {
            List<MembershipPlan> plans = planDAO.getAllPlans();
            if (plans.isEmpty()) {
                System.out.println("❌ No membership plans available.");
            } else {
                System.out.println("📦 Available Plans:");
                for (MembershipPlan p : plans) {
                    System.out.printf("ID %d: %s - $%.2f (%d days)\n",
                        p.getId(), p.getName(), p.getPrice(), p.getDurationDays());
                }
            }
        } catch (DatabaseException e) {
            Log.error("Error retrieving plans: " + e.getMessage());
        }
    }
}
