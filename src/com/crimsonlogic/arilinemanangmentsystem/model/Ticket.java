package com.crimsonlogic.arilinemanangmentsystem.model;

import com.crimsonlogic.arilinemanangmentsystem.utility.IdGenerator;

public class Ticket {

    String ticketId;
    double fare;
    Seat seat;

    public Ticket(double fare, Seat seat) {
        this.fare = fare;
        this.seat = seat;
        this.ticketId= IdGenerator.generateTicketId();
    }

    public String getTicketId() {
        return ticketId;
    }


    public double getFare() {
        return fare;
    }


    public Seat getSeat() {
        return seat;
    }


}