package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.model.*;
import com.crimsonlogic.arilinemanangmentsystem.utility.InputUtil;

public class RevenueReportService {

    private final FlightService flightService;
    private final InputUtil input = new InputUtil();

    public RevenueReportService(FlightService flightService) {

        this.flightService = flightService;
    }

    /**
     * Displays revenue of one flight.
     */
    public void revenueByFlight() {

        try {

            flightService.displayAllFlights();

            String flightId =
                    input.getString("Enter Flight ID : ");

            Flight flight =
                    flightService.findFlightById(flightId);

            double bookingAmount =
                    flight.getBookings()
                            .stream()
                            .mapToDouble(Booking::getAmount)
                            .sum();

            double refundAmount =
                    Refund.refundArrayList
                            .stream()
                            .filter(refund ->
                                    refund.getBooking()
                                            .getFlightBooked()
                                            .getFlightId()
                                            .equalsIgnoreCase(flightId))
                            .mapToDouble(Refund::getAmount)
                            .sum();

            Route.RevenueReport report =
                    new Route.RevenueReport(
                            flightId,
                            bookingAmount,
                            refundAmount);

            report.displayInfo();

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays revenue of all flights.
     */
    public void revenueOfAllFlights() {

        System.out.println("\n================== REVENUE REPORT ==================");

        System.out.printf("%-10s %-15s %-15s %-15s%n",
                "Flight",
                "Booking",
                "Refund",
                "Revenue");

        System.out.println("---------------------------------------------------------------");

        flightService.getFlightList()
                .stream()
                .map(flight -> {

                    double bookingAmount =
                            flight.getBookings()
                                    .stream()
                                    .mapToDouble(Booking::getAmount)
                                    .sum();

                    double refundAmount =
                            Refund.refundArrayList
                                    .stream()
                                    .filter(refund ->
                                            refund.getBooking()
                                                    .getFlightBooked()
                                                    .equals(flight))
                                    .mapToDouble(Refund::getAmount)
                                    .sum();

                    return new RevenueReport(
                            flight.getFlightId(),
                            bookingAmount,
                            refundAmount);

                })
                .forEach(System.out::println);
    }

    /**
     * Displays total airline revenue.
     */
    public void totalRevenue() {

        double bookingTotal =
                flightService.getFlightList()
                        .stream()
                        .flatMap(flight ->
                                flight.getBookings().stream())
                        .mapToDouble(Booking::getAmount)
                        .sum();

        double refundTotal =
                Refund.refundArrayList
                        .stream()
                        .mapToDouble(Refund::getAmount)
                        .sum();

        System.out.println("\n========== AIRLINE REVENUE ==========");
        System.out.printf("Total Booking Revenue : %.2f%n", bookingTotal);
        System.out.printf("Total Refund Amount   : %.2f%n", refundTotal);
        System.out.printf("Net Revenue           : %.2f%n",
                bookingTotal - refundTotal);
    }
    public void revenueMenu() {

        while (true) {

            System.out.println("\n========== REVENUE REPORT ==========");
            System.out.println("1. Revenue By Flight");
            System.out.println("2. Revenue Of All Flights");
            System.out.println("3. Total Airline Revenue");
            System.out.println("0. Back");

            int choice = input.getInt("Enter Choice : ");

            switch (choice) {

                case 1:
                    revenueByFlight();
                    break;

                case 2:
                    revenueOfAllFlights();
                    break;

                case 3:
                    totalRevenue();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }
}
