package com.crimsonlogic.arilinemanangmentsystem.model;

public class Airport {

    private String airportCode;
    private String airportName;
    private String city;

    public Airport(String airportCode, String airportName, String city) {
        this.airportCode = airportCode;
        this.airportName = airportName;
        this.city = city;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {

        return String.format("%-10s %-40s %-20s",
                airportCode,
                airportName,
                city);
    }

    /**
     * Displays complete airport information.
     */
    public void displayInfo() {

        System.out.println("\n========== AIRPORT DETAILS ==========");
        System.out.println("Airport Code : " + airportCode);
        System.out.println("Airport Name : " + airportName);
        System.out.println("City         : " + city);
    }
}