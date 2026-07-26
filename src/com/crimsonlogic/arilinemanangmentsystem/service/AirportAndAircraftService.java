package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.model.Aircraft;
import com.crimsonlogic.arilinemanangmentsystem.model.Airport;

import java.util.ArrayList;
import java.util.List;

public class AirportAndAircraftService {

    private final List<Airport> airportList = new ArrayList<>();
    private final List<Aircraft> aircraftList = new ArrayList<>();

    /**
     * Loads 5 demo airports and 5 demo aircraft.
     * Call this method once when the application starts.
     */
    public void initializeDemoData() {

        // Airports
        airportList.add(new Airport("DEL", "Indira Gandhi International Airport", "Delhi"));
        airportList.add(new Airport("BOM", "Chhatrapati Shivaji Maharaj Airport", "Mumbai"));
        airportList.add(new Airport("BLR", "Kempegowda International Airport", "Bengaluru"));
        airportList.add(new Airport("MAA", "Chennai International Airport", "Chennai"));
        airportList.add(new Airport("HYD", "Rajiv Gandhi International Airport", "Hyderabad"));

        // Aircraft
        aircraftList.add(new Aircraft("AC001", "Airbus A320", 180));
        aircraftList.add(new Aircraft("AC002", "Boeing 737", 189));
        aircraftList.add(new Aircraft("AC003", "Airbus A321", 220));
        aircraftList.add(new Aircraft("AC004", "Boeing 787 Dreamliner", 290));
        aircraftList.add(new Aircraft("AC005", "Airbus A350", 315));
    }

    /**
     * Returns an Airport using airport code.
     *
     * @param airportCode Airport code (DEL, BOM...)
     * @return Airport object
     * @throws IllegalArgumentException if airport is not found
     */
    public Airport getAirportByCode(String airportCode) {

        for (Airport airport : airportList) {

            if (airport.getAirportCode().equalsIgnoreCase(airportCode)) {
                return airport;
            }
        }

        throw new IllegalArgumentException("Airport not found.");
    }

    /**
     * Returns an Aircraft using aircraft ID.
     *
     * @param aircraftId Aircraft ID
     * @return Aircraft object
     * @throws IllegalArgumentException if aircraft is not found
     */
    public Aircraft getAircraftById(String aircraftId) {

        for (Aircraft aircraft : aircraftList) {

            if (aircraft.getAircraftId().equalsIgnoreCase(aircraftId)) {
                return aircraft;
            }
        }

        throw new IllegalArgumentException("Aircraft not found.");
    }

    /**
     * Returns all airports.
     *
     * @return airport list
     */
    public List<Airport> getAirportList() {
        return airportList;
    }

    /**
     * Returns all aircraft.
     *
     * @return aircraft list
     */
    public List<Aircraft> getAircraftList() {
        return aircraftList;
    }

    /**
     * Displays all airports in table format.
     */
    public void displayAllAirports() {

        if (airportList.isEmpty()) {
            System.out.println("No airports available.");
            return;
        }

        System.out.println("\n===================== AIRPORT LIST =====================");
        System.out.printf("%-10s %-40s %-20s%n",
                "Code",
                "Airport Name",
                "City");

        System.out.println("--------------------------------------------------------------");

        for (Airport airport : airportList) {
            System.out.println(airport);
        }
    }

    /**
     * Displays all aircraft in table format.
     */
    public void displayAllAircraft() {

        if (aircraftList.isEmpty()) {
            System.out.println("No aircraft available.");
            return;
        }

        System.out.println("\n=============== AIRCRAFT LIST ===============");

        System.out.printf("%-10s %-25s %-10s%n",
                "ID",
                "Model",
                "Capacity");

        System.out.println("------------------------------------------------");

        for (Aircraft aircraft : aircraftList) {
            System.out.println(aircraft);
        }
    }
}