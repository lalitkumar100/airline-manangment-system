package com.crimsonlogic.arilinemanangmentsystem.model;

public class RevenueReport {

    private String flightId;
    private double totalBookingAmount;
    private double totalRefundAmount;
    private double netRevenue;

    public RevenueReport(String flightId,
                         double totalBookingAmount,
                         double totalRefundAmount) {

        this.flightId = flightId;
        this.totalBookingAmount = totalBookingAmount;
        this.totalRefundAmount = totalRefundAmount;
        this.netRevenue = totalBookingAmount - totalRefundAmount;
    }

    public String getFlightId() {
        return flightId;
    }

    public double getTotalBookingAmount() {
        return totalBookingAmount;
    }

    public double getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public double getNetRevenue() {
        return netRevenue;
    }

    @Override
    public String toString() {

        return String.format(
                "%-10s %-15.2f %-15.2f %-15.2f",
                flightId,
                totalBookingAmount,
                totalRefundAmount,
                netRevenue);
    }

    public void displayInfo() {

        System.out.println("\n========== REVENUE REPORT ==========");
        System.out.println("Flight ID        : " + flightId);
        System.out.println("Booking Revenue  : " + totalBookingAmount);
        System.out.println("Refund Amount    : " + totalRefundAmount);
        System.out.println("Net Revenue      : " + netRevenue);
    }
}