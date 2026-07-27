package com.crimsonlogic.arilinemanangmentsystem.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Flight {

    public static final String STATUS_SCHEDULED = "Scheduled";
    public static final String STATUS_DELAYED = "Delayed";
    public static final String STATUS_CANCELLED = "Cancelled";
    public static final String STATUS_COMPLETED = "Completed";
    public  static  final  String STATUS_FLEW = "Flew";

    private String flightId;

    private Airport source;
    private Airport destination;

    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;

    private Aircraft aircraft;

    private ArrayList<Seat> seats;
    private  final ArrayList<Booking> bookings= new ArrayList<>()  ;
    private final PriorityQueue<Booking> waitLsit = new PriorityQueue<>();
    private ArrayList<Ticket> tickets  = new ArrayList<>(); ;
    private double baseFare;
    private String status;

    public Flight(
            String flightId,
            Airport source,
            Airport destination,
            LocalDateTime departureDateTime,
            LocalDateTime arrivalDateTime,
            Aircraft aircraft,
            double baseFare,
            String status) {

        this.flightId = flightId;
        this.source = source;
        this.destination = destination;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.aircraft = aircraft;
        this.baseFare=baseFare;
        this.status = status;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public Airport getSource() {
        return source;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(double baseFare) {
        this.baseFare = baseFare;
    }

    public void setSource(Airport source) {
        this.source = source;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(LocalDateTime departureTime) {
        this.departureDateTime = departureTime;
    }

    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(LocalDateTime arrivalTime) {
        this.arrivalDateTime = arrivalTime;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public ArrayList<Seat> getSeats() {
        return seats;
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }
    public void addBookings(Booking booking) {
         bookings.add(booking);
    }

    public PriorityQueue<Booking> getWaitLsit() {
        return waitLsit;
    }
    public void addWaitList(Booking booking) {
        waitLsit.offer(booking);
    }

    public void addTickets(Ticket ticket){
        tickets.add(ticket);
    }



    public ArrayList<com.crimsonlogic.arilinemanangmentsystem.model.Ticket> getTicket() {
        return tickets;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {

        return String.format(
                "%-8s %-8s %-8s %-10s %-20s %-20s %-12s",
                flightId,
                source.getAirportCode(),
                destination.getAirportCode(),
                aircraft.getAircraftId(),
                departureDateTime,
                arrivalDateTime,
                status);
    }

    public void displayInfo() {

        System.out.println("\n========== FLIGHT DETAILS ==========");
        System.out.println("Flight ID          : " + flightId);
        System.out.println("Aircraft ID        : " + aircraft.getAircraftId());
        System.out.println("Source Airport     : " + source.getAirportCode());
        System.out.println("Destination Airport: " + destination.getAirportCode());
        System.out.println("Departure          : " + departureDateTime);
        System.out.println("Arrival            : " + arrivalDateTime);
        System.out.println("Status             : " + status);
    }

    public void  cancelTicketandNew(){

    }


    /**
     * Removes a ticket from the flight.
     */
    public void removeTicket(Ticket ticket) {

        tickets.remove(ticket);
    }

    /**
     * Removes a booking from the flight.
     */
    public void removeBooking(Booking booking) {

        bookings.remove(booking);
        waitLsit.remove(booking);
    }

    /**
     * Returns ticket having the given seat.
     */
    public Ticket getTicketBySeat(Seat seat) {

        for (Ticket ticket : tickets) {

            if (ticket.getSeat().equals(seat)) {
                return ticket;
            }
        }

        return null;
    }

    /**
     * Returns next passenger from waiting list.
     */
    public Booking getNextWaitingPassenger() {

        while (!waitLsit.isEmpty()) {

            Booking booking = waitLsit.poll();

            if (bookings.contains(booking)
                    && booking.getBookingstatus().equalsIgnoreCase("WaitList")) {

                return booking;
            }
        }

        return null;
    }

    /**
     * Checks whether seats are available in this flight.
     */
    public boolean hasAvailableSeat() {

        if (!(STATUS_COMPLETED.equalsIgnoreCase(status)
                || STATUS_FLEW.equalsIgnoreCase(status))) {

            System.out.println("Flight Status : " + status);
            System.out.println("Tickets are not generated yet.");
            return false;
        }

        int capacity = aircraft.getCapacity();
        int bookedSeats = tickets.size();
        int availableSeats = capacity - bookedSeats;

        System.out.println("\n========== SEAT AVAILABILITY ==========");
        System.out.println("Flight ID         : " + flightId);
        System.out.println("Aircraft Capacity : " + capacity);
        System.out.println("Booked Seats      : " + bookedSeats);
        System.out.println("Available Seats   : " + availableSeats);

        if (availableSeats > 0) {

            System.out.println("Seats Available.");
            return true;
        }

        System.out.println("Flight Full.");
        return false;
    }

    public boolean hasAvailableSeatbyType() {

        if (!(status.equalsIgnoreCase(STATUS_SCHEDULED)
                || status.equalsIgnoreCase(STATUS_DELAYED))) {

            System.out.println("\nFlight Status : " + status);
            System.out.println("Booking is not allowed.");
            return false;
        }

        int capacity = aircraft.getCapacity();
        int booked = bookings.size();
        int available = capacity - booked;

        System.out.println("\n========== SEAT STATUS ==========");
        System.out.println("Aircraft Capacity : " + capacity);
        System.out.println("Booked Seats      : " + booked);
        System.out.println("Available Seats   : " + available);

        if (available <= 0) {

            System.out.println("Flight is Full.");
            return false;
        }

        return true;
    }
}