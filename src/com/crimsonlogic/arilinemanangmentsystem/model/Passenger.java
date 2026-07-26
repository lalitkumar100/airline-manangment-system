package com.crimsonlogic.arilinemanangmentsystem.model;

public class Passenger {

    private  String passengerId;
    private  String name;
    private String email;
    private String phone;

    private LoyaltyAccount loyalty;

    public Passenger(String passengerId, String name, String email, String phone, LoyaltyAccount loyalty) {
        this.passengerId = passengerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.loyalty = loyalty;
    }

    public String getPassengerId() {
        return passengerId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LoyaltyAccount getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(LoyaltyAccount loyalty) {
        this.loyalty = loyalty;
    }

    /**
     * Displays passenger information.
     */
    public void displayInfo() {

        System.out.println("\n========== PASSENGER DETAILS ==========");
        System.out.println("Passenger ID : " + passengerId);
        System.out.println("Name         : " + name);
        System.out.println("Email        : " + email);
        System.out.println("Phone        : " + phone);

        loyalty.displayInfo();
    }
}