package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.exception.RecordNotFoundException;
import com.crimsonlogic.arilinemanangmentsystem.model.*;
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

        System.out.printf(
                "%-10s %-10s %-10s %-8s %-10s %-15s %-20s %-80s%n",
                "Book ID",
                "Pass ID",
                "Flight",
                "Seat",
                "Amount",
                "Status",
                "Booking Time",
                "Check In");


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

    public void displayBookingById() {

        displayAllBookings();

        while (true) {

            try {

                String bookingId =
                        input.getString("Enter Booking ID (0 to Cancel) : ");

                if (bookingId.equals("0")) {
                    return;
                }

                Booking booking = getBookingById(bookingId);

                System.out.println("\n========== BOOKING DETAILS ==========");
                booking.displayInfo();

                return;

            } catch (Exception e) {

                System.out.println(e.getMessage());
                System.out.println("Please enter a valid Booking ID.");
            }
        }
    }

    public void gernateOnboardingPass(){
        Booking booking;
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
        System.out.println("==============Booking =======================");
        booking.displayInfo();

        if (booking.getBookingstatus().equals(Booking.STATUS_WAITLIST)){
            System.out.println("Sorry!!  your are in WaitList so we can't provide you onBoarding pass");
            return;
        }
        //create good look pass
        System.out.println("Seat no :"+booking.getTicket().getSeat().getSeatNo());
    }


    /**
     * Cancels a booking.
     */
    public void cancelBooking() {

        try {

            Booking booking = readBooking();

            if (booking == null) {
                return;
            }

            Flight flight = booking.getFlightBooked();

            if (flight.getStatus().equalsIgnoreCase(Flight.STATUS_FLEW)) {

                System.out.println("Flight already Flew.");
                return;
            }

            if (flight.getStatus().equalsIgnoreCase(Flight.STATUS_CANCELLED)) {

                System.out.println("Flight is Cancelled.");
                return;
            }

            if (booking.getBookingstatus().equalsIgnoreCase("WaitList")) {

                cancelWaitingBooking(booking);

            } else {

                cancelConfirmedBooking(booking);
            }
           Refund.refundArrayList.add(new Refund(booking.getAmount(),booking));


        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    /**
     * Reads a valid booking.
     */
    private Booking readBooking() {

        displayAllBookings();

        while (true) {

            String bookingId =
                    input.getString("Enter Booking ID (0 to Cancel) : ");

            if (bookingId.equals("0")) {
                return null;
            }

            try {

                return getBookingById(bookingId);

            } catch (Exception e) {

                System.out.println(e.getMessage());
            }
        }
    }
    /**
     * Cancels waiting booking.
     */
    private void cancelWaitingBooking(Booking booking) {

        Flight flight = booking.getFlightBooked();

        flight.removeBooking(booking);

        booking.getPassenger()
                .getLoyalty()
                .update(booking.getSeatType(), false);
        booking.setBookingstatus(Booking.STATUS_CANCELLED);

        System.out.println("Booking Cancelled Successfully.");
    }
    /**
     * Cancels confirmed booking.
     */
    private void cancelConfirmedBooking(Booking booking) throws RecordNotFoundException {

        Flight flight = booking.getFlightBooked();

        Ticket oldTicket = findTicket(booking);

        flight.removeBooking(booking);

        booking.getPassenger()
                .getLoyalty()
                .update(booking.getSeatType(), false);

        Booking nextBooking =
                flight.getNextWaitingPassenger();

        if (nextBooking == null) {

            flight.removeTicket(oldTicket);

            System.out.println("Booking Cancelled.");
            System.out.println("Seat is now Empty.");

            return;
        }

        nextBooking.setBookingstatus("Confirmed");

        Ticket newTicket =
                new Ticket(
                        nextBooking.getAmount(),
                        oldTicket.getSeat());

        flight.removeTicket(oldTicket);

        flight.addTickets(newTicket);

        System.out.println("Ticket transferred successfully.");

        nextBooking.displayInfo();
    }

    /**
     * Finds ticket of a confirmed booking.
     */
    private Ticket findTicket(Booking booking)
            throws RecordNotFoundException {

        Flight flight = booking.getFlightBooked();

        for (Ticket ticket : flight.getTickets()) {

            if (ticket.getSeat().getSeatNo()
                    == booking.getTicket().getSeat().getSeatNo()) {

                return ticket;
            }
        }

        throw new RecordNotFoundException("Ticket not found.");
    }

    /**
     * Inserts demo bookings.
     */
    public void initializeDemoBookings() {

        try {

            Flight flight1 = flightService.findFlightById("FL001");
            Flight flight2 = flightService.findFlightById("FL002");

            Passenger passenger1 =
                    passengerService.getPassengerById("PAS1001");

            Passenger passenger2 =
                    passengerService.getPassengerById("PAS1002");

            Passenger passenger3 =
                    passengerService.getPassengerById("PAS1003");

            Passenger passenger4 =
                    passengerService.getPassengerById("PAS1004");

            Passenger passenger5 =
                    passengerService.getPassengerById("PAS1005");

            addDemoBooking(flight1, passenger1, "A");
            addDemoBooking(flight1, passenger2, "B");
            addDemoBooking(flight1, passenger3, "C");

            addDemoBooking(flight2, passenger4, "A");
            addDemoBooking(flight2, passenger5, "C");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates and stores a demo booking.
     */
    private void addDemoBooking(
            Flight flight,
            Passenger passenger,
            String seatType) {

        double amount;

        switch (seatType.toUpperCase()) {

            case "A":
                amount = flight.getBaseFare() * 1.50;
                break;

            case "B":
                amount = flight.getBaseFare() * 1.20;
                break;

            default:
                amount = flight.getBaseFare();
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

        bookingHashMap.put(
                booking.getBookingId(),
                booking);

        flight.addBookings(booking);

        flight.addWaitList(booking);

        passenger.getLoyalty().update(
                seatType,
                true);
    }


    public void checkIn(){
        while (true) {

            String bookingId =
                    input.getString("Enter Booking ID (0 to Cancel) : ");

            if (bookingId.equals("0")) {
                return ;
            }

            try {
                Booking booking = getBookingById(bookingId);
                 if(booking.getBookingstatus().equals(Booking.STATUS_CONFIRMED)) {

                     booking.passengerCheckIn = true;
                     System.out.println("Passenger with id" + booking.getBookingId() + " is check in");
                 }
                     else {
                     System.out.println("Passenger with id" + booking.getBookingId() + "can't is check in because ticket is not "+Booking.STATUS_CONFIRMED);
                 }
            } catch (Exception e) {

                System.out.println(e.getMessage());
            }
        }
    }
    }



