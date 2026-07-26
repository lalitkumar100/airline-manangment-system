package com.crimsonlogic.arilinemanangmentsystem.utility;

import com.crimsonlogic.arilinemanangmentsystem.service.AirportAndAircraftService;
import com.crimsonlogic.arilinemanangmentsystem.service.BookingService;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightService;
import com.crimsonlogic.arilinemanangmentsystem.service.PassengerService;
import com.crimsonlogic.arilinemanangmentsystem.utility.InputUtil;

public class Menu {

    private final InputUtil input = new InputUtil();

    private final PassengerService passengerService= new PassengerService();

    private final AirportAndAircraftService airportAircraftService =
            new AirportAndAircraftService();

    private final FlightService flightService =
            new FlightService(airportAircraftService);

    private final BookingService bookingService = new BookingService(airportAircraftService,flightService,passengerService);

    /**
     * Starts the Airline Management System.
     */
    public void start() {

        airportAircraftService.initializeDemoData();
        flightService.initializeDemoFlights();
        passengerService.initializeDemoPassengers();

        while (true) {

            System.out.println("\n==========================================");
            System.out.println("      AIRLINE MANAGEMENT SYSTEM");
            System.out.println("==========================================");
            System.out.println("1. Admin");
            System.out.println("2. Passenger");
            System.out.println("0. Exit");

            int choice = input.getInt("Enter Choice : ");

            switch (choice) {

                case 1:
                    adminMenu();
                    break;

                case 2:
                    passengerMenu();
                    break;

                case 0:
                    System.out.println("\nThank You!");
                    return;

                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }

    /**
     * Displays the Admin Menu.
     */
    private void adminMenu() {

        while (true) {

            System.out.println("\n========== ADMIN MENU ==========");
            System.out.println("1. Flight Management");
            System.out.println("2. Airport Management");
            System.out.println("3. Aircraft Management");
            System.out.println("0. Back");

            int choice = input.getInt("Enter Choice : ");

            switch (choice) {

                case 1:
                    flightMenu();
                    break;

                case 2:
                    System.out.println("Coming Soon...");
                    break;

                case 3:
                    System.out.println("Coming Soon...");
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }

    /**
     * Displays the Flight Management Menu.
     */
    private void flightMenu() {

        while (true) {

            System.out.println("\n========== FLIGHT MANAGEMENT ==========");
            System.out.println("1. Add Flight");
            System.out.println("2. Update Flight");
            System.out.println("3. Remove Flight");
            System.out.println("4. Search Flight");
            System.out.println("5. Display All Flights");
            System.out.println("0. Back");

            int choice = input.getInt("Enter Choice : ");

            switch (choice) {

                case 1:
                    flightService.addFlight();
                    break;

                case 2:
                    flightService.updateFlight();
                    break;

                case 3:
                    flightService.removeFlight();
                    break;

                case 4:
                    flightService.searchFlightMenu();
                    break;

                case 5:
                    flightService.displayAllFlights();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }

    /**
     * Displays the Passenger Menu.
     */
    private void passengerMenu() {

        while (true) {

            System.out.println("\n========== PASSENGER MENU ==========");
            System.out.println("1. Search Flights");
            System.out.println("2. Book Ticket");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Ticket");
            System.out.println("5. Register ");
            System.out.println("0. Back");

            int choice = input.getInt("Enter Choice : ");

            switch (choice) {

                case 1:
                    flightService.searchFlightMenu();
                    break;

                case 2:
                     bookingService.bookFlight();
                    break;

                case 3:
                    System.out.println("Coming Soon...");
                    break;

                case 4:
                    System.out.println("Coming Soon...");
                    break;

                case 5:
                    passengerService.registerPassenger();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }
}