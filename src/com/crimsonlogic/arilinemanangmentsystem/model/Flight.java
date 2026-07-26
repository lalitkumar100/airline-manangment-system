package com.crimsonlogic.arilinemanangmentsystem.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Flight {

    public static final String STATUS_SCHEDULED = "Scheduled";
    public static final String STATUS_DELAYED = "Delayed";
    public static final String STATUS_CANCELLED = "Cancelled";
    public static final String STATUS_COMPLETED = "Completed";

    private String flightId;

    private Airport source;
    private Airport destination;

    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;

    private Aircraft aircraft;

    private ArrayList<Seat> seats;
    private  final ArrayList<Booking> bookings= new ArrayList<>()  ;
    private final PriorityQueue<Booking> waitLsit = new PriorityQueue<>();
    private ArrayList<Ticket> tickets ;
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
}