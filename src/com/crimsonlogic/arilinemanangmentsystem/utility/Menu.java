package com.crimsonlogic.arilinemanangmentsystem.utility;

import com.crimsonlogic.arilinemanangmentsystem.model.RevenueReport;
import com.crimsonlogic.arilinemanangmentsystem.service.*;
import com.crimsonlogic.arilinemanangmentsystem.utility.InputUtil;

public class Menu {

    private final InputUtil input = new InputUtil();

    private final PassengerService passengerService= new PassengerService();

    private final AirportAndAircraftService airportAircraftService =
            new AirportAndAircraftService();

    private final FlightService flightService =
            new FlightService(airportAircraftService);

    private final BookingService bookingService = new BookingService(airportAircraftService,flightService,passengerService);
    private  final TicketSercive ticketSercive = new TicketSercive(flightService);
    private final RevenueReportService revenueReportService = new RevenueReportService(flightService);
    private final  StreamTaskService streamTaskService = new StreamTaskService(flightService, airportAircraftService,bookingService,passengerService,revenueReportService);


    /**
     * Starts the Airline Management System.
     */
    public void start() {

        airportAircraftService.initializeDemoData();
        flightService.initializeDemoFlights();
        passengerService.initializeDemoPassengers();
        bookingService.initializeDemoBookings();

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

        String password = input.getString("Enter Admin Password (0 to Back) : ");

        if (password.equals("0")) {
            return;
        }

        if (!password.equals("12345")) {
            System.out.println("Invalid Password. Returning to Main Menu...");
            return;
        }

        while (true) {

            System.out.println("\n========== ADMIN MENU ==========");
            System.out.println("1. Flight Management");
            System.out.println("2. Report & Booking");
            System.out.println("3. Dashboard Info");
            System.out.println("0. Back");

            int choice = input.getInt("Enter Choice : ");

            switch (choice) {

                case 1:
                    flightMenu();
                    break;

                case 2:
                    ReportandBookingMenu();
                    break;
                case 3:
                    streamTaskService.streamMenu();
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
            System.out.println("6.  Ticket Managment ");
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
                case 6:
                    ticketSercive.generateTickets();
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
            System.out.println("6. Genrate onBoarding Pass");
            System.out.println("7. check in");
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
                    bookingService.cancelBooking();
                    break;

                case 4:
                    bookingService.displayBookingById();
                    break;

                case 5:
                    passengerService.registerPassenger();
                    break;

                case 6:
                    bookingService.gernateOnboardingPass();
                    break;
                case 7:
                    bookingService.checkIn();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }
    private void ReportandBookingMenu() {

        while (true) {

            System.out.println("\n========== FLIGHT MANAGEMENT ==========");
            System.out.println("1. All Booking ");
            System.out.println("2. Booking of Flight");
            System.out.println("3. find Booking by Id ");
            System.out.println("4. Renuve Report");
            System.out.println("0. Back");

            int choice = input.getInt("Enter Choice : ");

            switch (choice) {

                case 1:
                    bookingService.displayAllBookings();
                    break;

                case 2:
                    bookingService.displayBookingsByFlight();
                    break;

                case 3:
                    bookingService.displayBookingById();
                    break;
                case 4:
                    revenueReportService.revenueMenu();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }


}