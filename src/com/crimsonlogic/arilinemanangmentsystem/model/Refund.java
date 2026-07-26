package com.crimsonlogic.arilinemanangmentsystem.model;

import com.crimsonlogic.arilinemanangmentsystem.utility.IdGenerator;

import java.util.ArrayList;

public class Refund {

   public final static ArrayList<Refund> refundArrayList = new ArrayList<>();

  private   String refundId;

  private   double amount;
  private  Booking booking;

    public Refund( double amount, Booking booking) {
        this.refundId = IdGenerator.generatePaymentId();
        this.amount = amount;
        this.booking=booking;
    }

    public String getRefundId() {
        return refundId;
    }



    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    @Override
    public String toString() {
        return "Refund{" +
                "refundId='" + refundId + '\'' +
                ", amount=" + amount +
                ", booking=" + booking.getBookingId() +
                '}';
    }

}