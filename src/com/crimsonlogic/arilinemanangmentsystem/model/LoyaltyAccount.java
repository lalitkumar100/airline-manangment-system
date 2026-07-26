package com.crimsonlogic.arilinemanangmentsystem.model;

public class LoyaltyAccount {

    public static final String DIAMOND_TIER = "Diamond";
    public static final String GOLD_TIER = "Gold";
    public static final String SILVER_TIER = "Silver";

    private int points;

    private  String tier;

    public LoyaltyAccount() {
        this.points = 0;
        this.tier = SILVER_TIER;
    }

    public LoyaltyAccount(int points, String tier) {
        this.points = points;
        this.tier = tier;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    /**
     * Updates loyalty points and membership tier.
     *
     * @param seatType Seat type (A, B or C)
     * @param isBooked true for booking, false for cancellation
     */
    public void update(String seatType, boolean isBooked) {

        int earnedPoints = 0;

        switch (seatType.toUpperCase()) {

            case "A":
                earnedPoints = 30;
                break;

            case "B":
                earnedPoints = 20;
                break;

            case "C":
                earnedPoints = 10;
                break;

            default:
                System.out.println("Invalid Seat Type.");
                return;
        }

        if (isBooked) {
            points += earnedPoints;
        } else {
            points -= earnedPoints;

            if (points < 0) {
                points = 0;
            }
        }

        if (points >= 250) {
            tier = DIAMOND_TIER;
        } else if (points >= 100) {
            tier = GOLD_TIER;
        } else {
            tier = SILVER_TIER;
        }
    }

    /**
     * Displays loyalty account information.
     */
    public void displayInfo() {

        System.out.println("\n========== LOYALTY ACCOUNT ==========");
        System.out.println("Tier   : " + tier);
        System.out.println("Points : " + points);
    }

}