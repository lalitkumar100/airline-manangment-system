package com.crimsonlogic.arilinemanangmentsystem.model;

public class Seat {

    final char   SEAT_A ='A';
    final char   SEAT_B ='B';
    final char   SEAT_C ='C';

    private int seatNo;



    private char SeatType ;
    private boolean available;
    private boolean upgraded;

    public Seat(int seatNo, char seatType, boolean available, boolean upgraded) {
        this.seatNo = seatNo;
        this.SeatType = seatType;
        this.available = available;
        this.upgraded = upgraded;
    }
    public int getSeatNo() {
        return seatNo;
    }

}