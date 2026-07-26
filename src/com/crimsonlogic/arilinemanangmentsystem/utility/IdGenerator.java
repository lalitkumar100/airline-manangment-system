package com.crimsonlogic.arilinemanangmentsystem.utility;

public final class IdGenerator {

    private static int passengerCounter = 1001;
    private static int crewCounter = 501;
    private static int flightCounter = 101;
    private static int airportCounter = 11;
    private static int aircraftCounter = 51;
    private static int bookingCounter = 10001;
    private static int paymentCounter = 20001;
    private static int ticketCounter = 30001;
    private static int loyaltyCounter = 40001;

    private IdGenerator() {
    }

    public static String generatePassengerId() {
        return "PAS" + passengerCounter++;
    }

    public static String generateCrewId() {
        return "CRW" + crewCounter++;
    }

    public static String generateFlightId() {
        return "FLT" + flightCounter++;
    }

    public static String generateAirportId() {
        return "APT" + airportCounter++;
    }

    public static String generateAircraftId() {
        return "AIR" + aircraftCounter++;
    }

    public static String generateBookingId() {
        return "BKG" + bookingCounter++;
    }

    public static String generatePaymentId() {
        return "PAY" + paymentCounter++;
    }

    public static String generateTicketId() {
        return "TKT" + ticketCounter++;
    }

    public static String generateLoyaltyId() {
        return "LOY" + loyaltyCounter++;
    }
}