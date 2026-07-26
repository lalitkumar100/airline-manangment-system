package com.crimsonlogic.arilinemanangmentsystem.model;

public class Aircraft {

    private String aircraftId;

    private String model;

    private int capacity;

    public Aircraft(String aircraftId, String model, int capacity) {
        this.aircraftId = aircraftId;
        this.model = model;
        this.capacity = capacity;
    }

    public String getAircraftId() {
        return aircraftId;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }


    @Override
    public String toString() {

        return String.format("%-10s %-25s %-10d",
                aircraftId,
                model,
                capacity);
    }

    /**
     * Displays complete aircraft information.
     */
    public void displayInfo() {

        System.out.println("\n========== AIRCRAFT DETAILS ==========");
        System.out.println("Aircraft ID : " + aircraftId);
        System.out.println("Model       : " + model);
        System.out.println("Capacity    : " + capacity);
    }
}