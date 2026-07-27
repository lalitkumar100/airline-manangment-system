package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.utility.InputUtil;
import com.crimsonlogic.arilinemanangmentsystem.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamTaskService {

    private  FlightService flightService;
    private  AirportAndAircraftService airportAircraftService;
    private PassengerService passengerService;
    private    BookingService bookingService ;

    private  RevenueReportService reportService ;
    private final InputUtil input = new InputUtil();

    public StreamTaskService(FlightService flightService,
                             AirportAndAircraftService airportAircraftService,
                              BookingService bookingService,
                              PassengerService passengerService,
                              RevenueReportService reportService) {

        this.flightService = flightService;
        this.airportAircraftService = airportAircraftService;
        this.bookingService=bookingService;
        this.passengerService=passengerService;
        this.reportService=reportService;

    }

    /**
     * Stream API Menu.
     */
    public void streamMenu() {

        while (true) {

            System.out.println("\n========== STREAM API TASKS ==========");
            System.out.println("1. List Available Flights");
            System.out.println("2. Filter Flights By Destination");
            System.out.println("3. Filter Delayed Flights");
            System.out.println("4. Filter Cancelled Bookings");
            System.out.println("5. Sort Flights By Departure Time");
            System.out.println("6. Sort Passengers By Loyalty Points");
            System.out.println("7. Top 5 Highest Revenue Flights");
            System.out.println("8. Highest Ticket Fare");
            System.out.println("9. Lowest Ticket Fare");
            System.out.println("10. Total Ticket Revenue");
            System.out.println("11. Average Ticket Fare");
            System.out.println("12. Total Refunds Issued");
            System.out.println("13. Group Flights By Destination");
            System.out.println("14. Group Passengers By Membership Tier");
            System.out.println("15. Group Bookings By Status");
            System.out.println("16. Count Bookings Per Flight");
            System.out.println("17. Count Passengers Per Route");
            System.out.println("18. Most Booked Flight");
            System.out.println("19. Highest Spending Passenger");
            System.out.println("20. Distinct Destinations");
            System.out.println("21. Distinct Aircraft Types");
            System.out.println("22. Earliest Departure");
            System.out.println("23. Latest Arrival");
            System.out.println("24. Find Overbooked Flights");
            System.out.println("25. Find Empty Flights");
            System.out.println("28. Fare Summary Statistics");
            System.out.println("29. Any seats available?");
            System.out.println("30. All Flights Departed?");
            System.out.println("31. flatMapPassengersFromBookings");
            System.out.println("0. Back");

            int choice = input.getInt("Enter Choice : ");

            switch (choice) {

                case 17:
                    countPassengersPerRoute();
                    break;
                case 28:
                    fareSummaryStatistics();
                    break;

                case 30:
                    allFlightsDeparted();
                    break;

                case 19:
                    highestSpendingPassenger();
                    break;

                case 1:
                    listAvailableFlights();
                    break;

                case 2:
                    filterByDestination();
                    break;

                case 3:
                    filterDelayedFlights();
                    break;

                case 4:
                    filterCancelledBookings();
                    break;

                case 5:
                    sortByDepartureTime();
                    break;
                case 6:
                    sortPassengersByLoyaltyPoints();
                    break;
                case 7:
                    top5HighestRevenueFlights();
                    break;

                case 10:
                    totalTicketRevenue();
                    break;

                case 12:
                    totalRefundIssued();
                    break;

                case 14:
                    groupPassengersByMembershipTier();
                    break;


                case 8:
                    highestFare();
                    break;

                case 9:
                    lowestFare();
                    break;

                case 11:
                    averageFare();
                    break;

                case 13:
                    groupByDestination();
                    break;

                case 15:
                    groupBookingsByStatus();
                    break;

                case 16:
                    countBookingsPerFlight();
                    break;

                case 18:
                    mostBookedFlight();
                    break;

                case 20:
                    distinctDestinations();
                    break;

                case 21:
                    distinctAircraftTypes();
                    break;

                case 22:
                    earliestDeparture();
                    break;

                case 23:
                    latestArrival();
                    break;

                case 24:
                    findOverBookedFlights();
                    break;

                case 25:
                    findEmptyFlights();
                    break;

                case 31:
                    flatMapPassengersFromBookings();
                    break;

                case 29:
                    anySeatsAvailable();
                    break;
                case 0:
                    return;

                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }

    /**
     * Displays the flight having the latest arrival.
     */
    public void latestArrival() {

        flightService.getFlightList()
                .stream()
                .max((f1, f2) ->
                        f1.getArrivalDateTime()
                                .compareTo(f2.getArrivalDateTime()))
                .ifPresent(flight -> {

                    System.out.println("\n========== LATEST ARRIVAL ==========");
                    System.out.println("Arrival : " + flight.getArrivalDateTime());

                    flight.displayInfo();
                });
    }

    /**
     * Displays distinct aircraft models.
     */
    public void distinctAircraftTypes() {

        System.out.println("\n========== DISTINCT AIRCRAFT TYPES ==========");

        flightService.getFlightList()
                .stream()
                .map(flight -> flight.getAircraft().getModel())
                .distinct()
                .sorted()
                .forEach(System.out::println);
    }

    /**
     * Counts passengers travelling on each route.
     */
    public void countPassengersPerRoute() {

        System.out.println("\n========== PASSENGERS PER ROUTE ==========");

        bookingService.bookingList.stream()
                .collect(Collectors.groupingBy(
                        booking -> booking.getFlightBooked()
                                .getSource().getAirportCode()
                                + " -> " +
                                booking.getFlightBooked()
                                        .getDestination().getAirportCode(),
                        Collectors.counting()))
                .forEach((route, count) ->

                        System.out.printf("%-20s : %d%n",
                                route,
                                count));
    }

    /**
     * Displays the passenger who spent the highest amount.
     */
    public void highestSpendingPassenger() {

        Map.Entry<Passenger, Double> result =
                bookingService.bookingList.stream()
                        .collect(Collectors.groupingBy(
                                Booking::getPassenger,
                                Collectors.summingDouble(
                                        Booking::getAmount)))
                        .entrySet()
                        .stream()
                        .max(Map.Entry.comparingByValue())
                        .orElse(null);

        if (result == null) {

            System.out.println("\nNo Bookings Found.");
            return;
        }

        Passenger passenger = result.getKey();

        System.out.println("\n========== HIGHEST SPENDING PASSENGER ==========");
        System.out.printf("Total Amount : %.2f%n%n", result.getValue());

        passenger.displayInfo();
    }

    /**
     * Displays all available destinations.
     */
    public void distinctDestinations() {

        System.out.println("\n========== DESTINATIONS ==========");

        airportAircraftService.displayAllAirports();
    }

    /**
     * 1. List Available Flights.
     */
    public void listAvailableFlights() {

        flightService.getFlightList()
                .stream()
                .filter(flight ->
                        !flight.getStatus().equalsIgnoreCase(Flight.STATUS_CANCELLED))
                .forEach(Flight::displayInfo);
    }

    /**
     * 2. Filter Flights By Destination.
     */
    public void filterByDestination() {

        airportAircraftService.displayAllAirports();

        while (true) {

            try {

                String code =
                        input.getString("Enter Destination Code (0 to Cancel): ");

                if (code.equals("0")) {
                    return;
                }

                Airport airport =
                        airportAircraftService.getAirportByCode(code);

                flightService.getFlightList()
                        .stream()
                        .filter(flight ->
                                flight.getDestination()
                                        .getAirportCode()
                                        .equalsIgnoreCase(airport.getAirportCode()))
                        .forEach(Flight::displayInfo);

                return;

            } catch (Exception e) {

                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 3. Filter Delayed Flights.
     */
    /**
     * Displays all delayed flights.
     */
    public void filterDelayedFlights() {

        List<Flight> delayedFlights = flightService.getFlightList()
                .stream()
                .filter(flight ->
                        flight.getStatus().equalsIgnoreCase(Flight.STATUS_DELAYED))
                .toList();

        if (((List<?>) delayedFlights).isEmpty()) {

            System.out.println("\nNo flights are delayed.");
            return;
        }

        System.out.println("\n========== DELAYED FLIGHTS ==========");

        delayedFlights.forEach(Flight::displayInfo);
    }

    /**
     * 5. Sort Flights By Departure Time.
     */
    public void sortByDepartureTime() {

        flightService.getFlightList()
                .stream()
                .sorted((f1, f2) ->
                        f1.getDepartureDateTime()
                                .compareTo(f2.getDepartureDateTime()))
                .forEach(Flight::displayInfo);
    }

    /**
     * 8. Highest Ticket Fare.
     */
    /**
     * Displays the flight having the highest ticket fare.
     */
    public void highestFare() {

        flightService.getFlightList()
                .stream()
                .max((f1, f2) ->
                        Double.compare(
                                f1.getBaseFare(),
                                f2.getBaseFare()))
                .ifPresent(flight -> {

                    System.out.println("\n========== HIGHEST TICKET FARE ==========");
                    System.out.printf("Highest Fare : %.2f%n%n",
                            flight.getBaseFare());

                    flight.displayInfo();
                });
    }

    /**
     * 9. Lowest Ticket Fare.
     */
    /**
     * Displays the flight having the lowest ticket fare.
     */
    public void lowestFare() {

        flightService.getFlightList()
                .stream()
                .min((f1, f2) ->
                        Double.compare(
                                f1.getBaseFare(),
                                f2.getBaseFare()))
                .ifPresent(flight -> {

                    System.out.println("\n========== LOWEST TICKET FARE ==========");
                    System.out.printf("Lowest Fare : %.2f%n%n",
                            flight.getBaseFare());

                    flight.displayInfo();
                });
    }

    /**
     * 11. Average Ticket Fare.
     */
    public void averageFare() {

        double average =
                flightService.getFlightList()
                        .stream()
                        .mapToDouble(Flight::getBaseFare)
                        .average()
                        .orElse(0);

        System.out.printf(
                "Average Ticket Fare : %.2f%n",
                average);
    }

    /**
     * 13. Group Flights By Destination.
     */
    public void groupByDestination() {

        flightService.getFlightList()
                .stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        flight -> flight.getDestination().getAirportCode()))
                .forEach((destination, flights) -> {

                    System.out.println("\nDestination : " + destination);

                    flights.forEach(Flight::displayInfo);
                });
    }

    /**
     * Displays all cancelled bookings.
     */
    public void filterCancelledBookings() {

        List<Booking> cancelled = bookingService.bookingList.stream()
                .filter(booking ->
                        booking.getBookingstatus()
                                .equalsIgnoreCase(Booking.STATUS_CANCELLED))
                .toList();

        if (cancelled.isEmpty()) {

            System.out.println("\nNo Cancelled Bookings Found.");
            return;
        }

        System.out.println("\n========== CANCELLED BOOKINGS ==========");

        cancelled.forEach(Booking::displayInfo);
    }

    /**
     * Groups bookings by status.
     */
    public void groupBookingsByStatus() {

        bookingService.bookingList.stream()
                .collect(Collectors.groupingBy(
                        Booking::getBookingstatus))
                .forEach((status, bookings) -> {

                    System.out.println("\n========== " + status.toUpperCase() + " ==========");

                    bookings.forEach(Booking::displayInfo);
                });
    }

    /**
     * Counts bookings of every flight.
     */
    public void countBookingsPerFlight() {

        System.out.println("\n========== BOOKINGS PER FLIGHT ==========");

        bookingService.bookingList.stream()
                .collect(Collectors.groupingBy(
                        booking -> booking.getFlightBooked().getFlightId(),
                        Collectors.counting()))
                .forEach((flightId, count) ->

                        System.out.printf("%-10s : %d%n",
                                flightId,
                                count));
    }

    /**
     * Displays most booked flight.
     */
    public void mostBookedFlight() {

        Map.Entry<String, Long> entry =
                bookingService.bookingList.stream()
                        .collect(Collectors.groupingBy(
                                booking -> booking.getFlightBooked().getFlightId(),
                                Collectors.counting()))
                        .entrySet()
                        .stream()
                        .max(Map.Entry.comparingByValue())
                        .orElse(null);

        if (entry == null) {

            System.out.println("\nNo Bookings Found.");
            return;
        }

        try {

            Flight flight =
                    flightService.findFlightById(entry.getKey());

            System.out.println("\n========== MOST BOOKED FLIGHT ==========");
            System.out.println("Total Bookings : " + entry.getValue());

            flight.displayInfo();

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays overbooked flights.
     */
    public void findOverBookedFlights() {

        List<Flight> flights = flightService.getFlightList()
                .stream()
                .filter(flight ->
                        flight.getBookings().size()
                                > flight.getAircraft().getCapacity())
                .toList();

        if (flights.isEmpty()) {

            System.out.println("\nNo Overbooked Flights Found.");
            return;
        }

        flights.forEach(flight -> {

            System.out.println("\nFlight ID : " + flight.getFlightId());
            System.out.println("Capacity  : " + flight.getAircraft().getCapacity());
            System.out.println("Bookings  : " + flight.getBookings().size());
        });
    }

    /**
     * Displays flights having no bookings.
     */
    public void findEmptyFlights() {

        List<Flight> flights = flightService.getFlightList()
                .stream()
                .filter(flight ->
                        flight.getBookings().isEmpty())
                .toList();

        if (flights.isEmpty()) {

            System.out.println("\nNo Empty Flights Found.");
            return;
        }

        System.out.println("\n========== EMPTY FLIGHTS ==========");

        flights.forEach(Flight::displayInfo);
    }


    /**
     * Displays the flight with the earliest departure.
     */
    public void earliestDeparture() {

        flightService.getFlightList()
                .stream()
                .min((f1, f2) ->
                        f1.getDepartureDateTime()
                                .compareTo(f2.getDepartureDateTime()))
                .ifPresent(flight -> {

                    System.out.println("\n========== EARLIEST DEPARTURE ==========");
                    System.out.println("Departure : " + flight.getDepartureDateTime());

                    flight.displayInfo();
                });
    }

    /**
     * Groups passengers by membership tier.
     */
    public void groupPassengersByMembershipTier() {

        passengerService.getPassengers()
                .values()
                .stream()
                .collect(Collectors.groupingBy(
                        passenger -> passenger.getLoyalty().getTier()))
                .forEach((tier, passengers) -> {

                    System.out.println("\n========== " + tier.toUpperCase() + " ==========");

                    passengers.forEach(Passenger::displayInfo);
                });
    }

    /**
     * Sorts passengers by loyalty points.
     */
    public void sortPassengersByLoyaltyPoints() {

        System.out.println("\n================ PASSENGERS BY LOYALTY POINTS ================");

        System.out.printf("%-12s %-20s %-25s %-10s %-10s%n",
                "Passenger ID",
                "Name",
                "Email",
                "Tier",
                "Points");

        System.out.println("--------------------------------------------------------------------------");

        passengerService.getPassengers()
                .values()
                .stream()
                .sorted((p1, p2) ->
                        Integer.compare(
                                p2.getLoyalty().getPoints(),
                                p1.getLoyalty().getPoints()))
                .forEach(passenger ->

                        System.out.printf("%-12s %-20s %-25s %-10s %-10d%n",
                                passenger.getPassengerId(),
                                passenger.getName(),
                                passenger.getEmail(),
                                passenger.getLoyalty().getTier(),
                                passenger.getLoyalty().getPoints()));
    }

    /**
     * Displays top 5 flights having highest revenue.
     */
    public void top5HighestRevenueFlights() {

        System.out.println("\n========== TOP 5 HIGHEST REVENUE FLIGHTS ==========");

        flightService.getFlightList()
                .stream()
                .map(flight -> {

                    double bookingRevenue = flight.getBookings()
                            .stream()
                            .mapToDouble(Booking::getAmount)
                            .sum();

                    double refundAmount = Refund.refundArrayList
                            .stream()
                            .filter(refund ->
                                    refund.getBooking()
                                            .getFlightBooked()
                                            .equals(flight))
                            .mapToDouble(Refund::getAmount)
                            .sum();

                    return new RevenueReport(
                            flight.getFlightId(),
                            bookingRevenue,
                            refundAmount);
                })
                .sorted((r1, r2) ->
                        Double.compare(
                                r2.getNetRevenue(),
                                r1.getNetRevenue()))
                .limit(5)
                .forEach(System.out::println);
    }

    /**
     * Displays total ticket revenue.
     */
    public void totalTicketRevenue() {

        double revenue = flightService.getFlightList()
                .stream()
                .flatMap(flight -> flight.getBookings().stream())
                .mapToDouble(Booking::getAmount)
                .sum();

        System.out.println("\n========== TOTAL TICKET REVENUE ==========");
        System.out.printf("Total Revenue : %.2f%n", revenue);
    }

    /**
     * Displays total refund amount issued.
     */
    public void totalRefundIssued() {

        double refundAmount = Refund.refundArrayList
                .stream()
                .mapToDouble(Refund::getAmount)
                .sum();

        long refundCount = Refund.refundArrayList
                .stream()
                .count();

        System.out.println("\n========== TOTAL REFUNDS ==========");
        System.out.println("Total Refunds Issued : " + refundCount);
        System.out.printf("Total Refund Amount  : %.2f%n", refundAmount);
    }

    /**
     * Displays fare summary statistics.
     */
    public void fareSummaryStatistics() {

        DoubleSummaryStatistics statistics =
                flightService.getFlightList()
                        .stream()
                        .mapToDouble(Flight::getBaseFare)
                        .summaryStatistics();

        System.out.println("\n========== FARE SUMMARY ==========");

        System.out.println("Number of Flights : " + statistics.getCount());
        System.out.printf("Minimum Fare      : %.2f%n", statistics.getMin());
        System.out.printf("Maximum Fare      : %.2f%n", statistics.getMax());
        System.out.printf("Average Fare      : %.2f%n", statistics.getAverage());
        System.out.printf("Total Base Fare   : %.2f%n", statistics.getSum());

        System.out.print("Total Revenue");

        reportService.totalRevenue();
    }

    /**
     * Checks whether all flights have departed.
     */
    public void allFlightsDeparted() {

        LocalDateTime now = LocalDateTime.now();

        boolean departed = flightService.getFlightList()
                .stream()
                .allMatch(flight ->
                        flight.getDepartureDateTime().isBefore(now));

        System.out.println("\n========== FLIGHT DEPARTURE STATUS ==========");
        System.out.println("Current Time : " + now);

        if (departed) {

            System.out.println("All Flights Have Departed.");

        } else {

            System.out.println("Some Flights Have Not Yet Departed.");

            System.out.println("\nUpcoming Flights:");

            flightService.getFlightList()
                    .stream()
                    .filter(flight ->
                            flight.getDepartureDateTime().isAfter(now))
                    .forEach(Flight::displayInfo);
        }
    }

    /**
     * 29. Any Seats Available?
     */
    public void anySeatsAvailable() {

        while (true) {

            try {

                flightService.displayAllFlights();

                String flightId =
                        input.getString("Enter Flight ID (0 to Cancel) : ");

                if (flightId.equals("0")) {
                    return;
                }

                Flight flight = flightService.findFlightById(flightId);

                flight.hasAvailableSeat();

                return;

            } catch (Exception e) {

                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 31. FlatMap Passengers From Bookings.
     */
    /**
     * 31. FlatMap Passengers From Bookings.
     */
    public void flatMapPassengersFromBookings() {

        System.out.println("\n================ PASSENGERS FROM BOOKINGS ================");

        System.out.printf("%-10s %-10s %-12s %-20s %-12s %-8s %-10s%n",
                "Flight",
                "Booking",
                "Passenger",
                "Name",
                "Seat",
                "Points",
                "Tier");

        System.out.println("-----------------------------------------------------------------------------------------------");

        flightService.getFlightList()
                .stream()
                .flatMap(flight -> flight.getBookings().stream())
                .forEach(booking -> {

                    Passenger passenger = booking.getPassenger();

                    System.out.printf("%-10s %-10s %-12s %-20s %-12s %-8d %-10s%n",
                            booking.getFlightBooked().getFlightId(),
                            booking.getBookingId(),
                            passenger.getPassengerId(),
                            passenger.getName(),
                            booking.getSeatType(),
                            passenger.getLoyalty().getPoints(),
                            passenger.getLoyalty().getTier());
                });
    }
}
