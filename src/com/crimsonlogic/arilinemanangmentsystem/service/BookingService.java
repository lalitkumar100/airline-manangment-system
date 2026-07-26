package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.exception.RecordNotFoundException;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.model.Passenger;
import com.crimsonlogic.arilinemanangmentsystem.model.Payment;
import com.crimsonlogic.arilinemanangmentsystem.utility.IdGenerator;
import com.crimsonlogic.arilinemanangmentsystem.utility.InputUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class BookingService {

   final ArrayList<Booking> bookingList = new ArrayList<>();
   final HashMap<String ,Booking> bookingHashMap = new HashMap<>();
    AirportAndAircraftService airportAndAircraftService ;
    FlightService flightService;
    PassengerService passengerService ;
    InputUtil input = new InputUtil();

    public BookingService(AirportAndAircraftService airportAndAircraftService
            , FlightService flightService
            , PassengerService passengerService) {
        this.airportAndAircraftService = airportAndAircraftService;
        this.flightService = flightService;
        this.passengerService=passengerService;
    }

    /**
     * Books a flight.
     */
    public void bookFlight() {

        try {

            flightService.displayAllFlights();

            Flight flight = null;

            while (true) {

                String flightId = input.getString(
                        "Enter Flight ID (0 to Cancel) : ");

                if (flightId.equals("0")) {
                    return;
                }

                try {

                    flight = flightService.findFlightById(flightId);

                    if ((!flight.getStatus().equalsIgnoreCase(Flight.STATUS_SCHEDULED) && (!flight.getStatus().equalsIgnoreCase(Flight.STATUS_DELAYED)))) {

                        System.out.println("Booking is allowed only for Scheduled flights and .");
                        return;
                    }

                    break;

                } catch (Exception e) {

                    System.out.println(e.getMessage());
                }
            }

            flight.displayInfo();

            Passenger passenger = null;

            while (true) {

                String passengerId = input.getString(
                        "Enter Passenger ID (0 to Cancel) : ");

                if (passengerId.equals("0")) {
                    return;
                }

                try {

                    passenger = passengerService.getPassengerById(passengerId);
                    passenger.displayInfo();
                    boolean alreadyBooked = false;

                    for (Booking booking : flight.getBookings()) {

                        if (booking.getPassenger().getPassengerId()
                                .equalsIgnoreCase(passengerId)) {

                            alreadyBooked = true;
                            break;
                        }
                    }

                    if (alreadyBooked) {

                        System.out.println("Passenger has already booked this flight.");
                        continue;
                    }

                    break;

                } catch (Exception e) {

                    System.out.println(e.getMessage());
                }
            }

            System.out.println("\n========== SEAT FARE ==========");
            System.out.printf("A Class : %.2f%n", flight.getBaseFare() * 1.50);
            System.out.printf("B Class : %.2f%n", flight.getBaseFare() * 1.20);
            System.out.printf("C Class : %.2f%n", flight.getBaseFare());

            String seatType;
            double amount = 0;

            while (true) {

                seatType = input.getString(
                        "Enter Seat Type (A/B/C) : ").toUpperCase();

                switch (seatType) {

                    case "A":
                        amount = flight.getBaseFare() * 1.50;
                        break;

                    case "B":
                        amount = flight.getBaseFare() * 1.20;
                        break;

                    case "C":
                        amount = flight.getBaseFare();
                        break;

                    default:
                        System.out.println("Invalid Seat Type.");
                        continue;
                }

                break;
            }

            while (true) {

                System.out.println("\n========== PAYMENT ==========");
                System.out.println("Amount : " + amount);
                System.out.println("1. Pay");
                System.out.println("2. Cancel");

                int choice = input.getInt("Enter Choice : ");

                if (choice == 2) {
                    return;
                }

                if (choice != 1) {

                    System.out.println("Invalid Choice.");
                    continue;
                }

                Payment payment = new Payment(
                        amount,
                        true);

                Booking booking = new Booking(
                        IdGenerator.generateBookingId(),
                        passenger,
                        flight,
                        seatType,
                        amount,
                        payment);

                payment.setBooking(booking);

                bookingList.add(booking);

                bookingHashMap.putIfAbsent(booking.getBookingId(), booking);

                flight.addBookings(booking);

                flight.addWaitList(booking);

                passenger.getLoyalty().update(seatType,true);

                System.out.println("\nBooking Successful.");

                booking.displayInfo();

                payment.displayInfo();

                return;
            }

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }



    /**
     * Updates seat type of an existing booking.
     */
    public void updateSeat() {

        try {

            displayAllBookings();

            Booking booking = null;

            while (true) {

                String bookingId =
                        input.getString("Enter Booking ID (0 to Cancel) : ");

                if (bookingId.equals("0")) {
                    return;
                }

                try {

                    booking = getBookingById(bookingId);
                    break;

                } catch (Exception e) {

                    System.out.println(e.getMessage());
                }
            }

            booking.displayInfo();

            if (!booking.getFlightBooked().getStatus()
                    .equalsIgnoreCase(Flight.STATUS_SCHEDULED)) {

                System.out.println("Seat cannot be updated. Flight is not Scheduled.");
                return;
            }

            String currentSeat = booking.getSeatType().toUpperCase();
            String newSeat;

            switch (currentSeat) {

                case "C":

                    System.out.println("\nAvailable Upgrades");
                    System.out.println("1. B Class");
                    System.out.println("2. A Class");
                    System.out.println("0. Cancel");

                    while (true) {

                        int choice = input.getInt("Enter Choice : ");

                        if (choice == 0) {
                            return;
                        }

                        if (choice == 1) {
                            newSeat = "B";
                            break;
                        }

                        if (choice == 2) {
                            newSeat = "A";
                            break;
                        }

                        System.out.println("Invalid Choice.");
                    }

                    break;

                case "B":

                    System.out.println("\nAvailable Upgrades");
                    System.out.println("1. A Class");
                    System.out.println("0. Cancel");

                    while (true) {

                        int choice = input.getInt("Enter Choice : ");

                        if (choice == 0) {
                            return;
                        }

                        if (choice == 1) {
                            newSeat = "A";
                            break;
                        }

                        System.out.println("Invalid Choice.");
                    }

                    break;

                default:

                    System.out.println("Already in Highest Seat Class.");
                    return;
            }

            double newAmount =
                    calculateSeatFare(booking.getFlightBooked(), newSeat);

            double extraAmount =
                    newAmount - booking.getAmount();

            System.out.println("\n========== PAYMENT ==========");
            System.out.println("Extra Amount : " + extraAmount);
            System.out.println("1. Pay");
            System.out.println("2. Cancel");

            while (true) {

                int choice = input.getInt("Enter Choice : ");

                if (choice == 2) {
                    return;
                }

                if (choice != 1) {

                    System.out.println("Invalid Choice.");
                    continue;
                }

                booking.setSeatType(newSeat);
                booking.setAmount(newAmount);

                booking.getPayment().setAmount(newAmount);
                booking.getPayment().setPaid(true);

                System.out.println("\nSeat Updated Successfully.");

                booking.displayInfo();
                booking.getPayment().displayInfo();

                return;
            }

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    /**
     * Calculates seat fare based on seat type.
     */
    private double calculateSeatFare(Flight flight, String seatType) {

        switch (seatType.toUpperCase()) {

            case "A":
                return flight.getBaseFare() * 1.50;

            case "B":
                return flight.getBaseFare() * 1.20;

            default:
                return flight.getBaseFare();
        }
    }

    private Booking getBookingById(String bookingId) {
        return bookingHashMap.get(bookingId);
    }

    /**
     * Displays all bookings sorted by Flight ID.
     */
    public void displayAllBookings() {

        if (bookingList.isEmpty()) {

            System.out.println("\nNo Bookings Found.");
            return;
        }

        System.out.println("\n============================ ALL BOOKINGS ============================");

        System.out.printf("%-10s %-10s %-10s %-8s %-10s %-15s %-20s%n",
                "Book ID",
                "Pass ID",
                "Flight",
                "Seat",
                "Amount",
                "Status",
                "Booking Time");

        System.out.println("----------------------------------------------------------------------");

        bookingList.stream()
                .sorted((b1, b2) ->
                        b1.getFlightBooked().getFlightId()
                                .compareToIgnoreCase(
                                        b2.getFlightBooked().getFlightId()))
                .forEach(System.out::println);
    }
    /**
     * Displays all bookings of a flight sorted by Booking ID.
     */
    public void displayBookingsByFlight() {

        try {

            flightService.displayAllFlights();

            String flightId = input.getString("Enter Flight ID : ");

            Flight flight = flightService.findFlightById(flightId);

            if (flight.getBookings().isEmpty()) {

                throw new RecordNotFoundException(
                        "No bookings found for this flight.");
            }

            System.out.println("\n================ FLIGHT BOOKINGS ================");

            System.out.printf("%-10s %-10s %-10s %-8s %-10s %-15s %-20s%n",
                    "Book ID",
                    "Pass ID",
                    "Flight",
                    "Seat",
                    "Amount",
                    "Status",
                    "Booking Time");

            System.out.println("--------------------------------------------------------------------------");

            flight.getBookings()
                    .stream()
                    .sorted((b1, b2) ->
                            b1.getBookingId()
                                    .compareToIgnoreCase(b2.getBookingId()))
                    .forEach(System.out::println);

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }



}
