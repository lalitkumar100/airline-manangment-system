package com.crimsonlogic.arilinemanangmentsystem.model;


import java.time.LocalDateTime;


public class Booking implements Comparable<Booking> {

    public static final String STATUS_WAITLIST = "WaitList";
    public static final String STATUS_CONFIRMED = "Confirmed";
    public static final String STATUS_CANCELLED = "Cancelled";

    private String bookingId;

    private Passenger passenger;

     private Flight flightBooked;
     private LocalDateTime bookingDateTime;

     private String Bookingstatus;

     private Ticket ticket;

     public boolean passengerCheckIn;



    private String SeatType;
    private double amount;
    private Payment payment;

    public Booking(String bookingId, Passenger passenger, Flight flightBooked,  String seatType, double amount, Payment payment) {
        this.bookingId = bookingId;
        this.passenger = passenger;
        this.flightBooked = flightBooked;
        this.bookingDateTime = LocalDateTime.now();
        Bookingstatus =STATUS_WAITLIST;
        SeatType = seatType;
        this.amount = amount;
        this.payment = payment;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Flight getFlightBooked() {
        return flightBooked;
    }

    public void setFlightBooked(Flight flightBooked) {
        this.flightBooked = flightBooked;
    }

    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(LocalDateTime bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    public String getBookingstatus() {
        return Bookingstatus;
    }

    public void setBookingstatus(String bookingstatus) {
        Bookingstatus = bookingstatus;
    }

    public String getSeatType() {
        return SeatType;
    }

    public void setSeatType(String seatType) {
        SeatType = seatType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }




    @Override
    public int compareTo(Booking other) {

        return this.getBookingDateTime()
                .compareTo(other.getBookingDateTime());
    }

    @Override
    public String toString() {

        return String.format(
                "%-10s %-10s %-10s %-8s %-10.2f %-15s %-20s %-10sn",
                bookingId,
                passenger.getPassengerId(),
                flightBooked.getFlightId(),
                SeatType,
                amount,
                Bookingstatus,
                bookingDateTime,
                passengerCheckIn?"check-in":"check-out");
    }

    /**
     * Displays booking information.
     */
    public void displayInfo() {

        System.out.println("\n========== BOOKING DETAILS ==========");
        System.out.println("Booking ID      : " + bookingId);
        System.out.println("Passenger ID    : " + passenger.getPassengerId());
        System.out.println("Passenger Name  : " + passenger.getName());
        System.out.println("Flight ID       : " + flightBooked.getFlightId());
        System.out.println("Seat Type       : " + SeatType);
        System.out.println("Amount          : " + amount);
        System.out.println("Booking Status  : " + Bookingstatus);
        System.out.println("Booking Date    : " + bookingDateTime);
    }
}