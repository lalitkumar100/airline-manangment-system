package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.exception.RecordNotFoundException;
import com.crimsonlogic.arilinemanangmentsystem.exception.ValidationException;
import com.crimsonlogic.arilinemanangmentsystem.model.Aircraft;
import com.crimsonlogic.arilinemanangmentsystem.model.Airport;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.utility.IdGenerator;
import com.crimsonlogic.arilinemanangmentsystem.utility.InputUtil;
import com.crimsonlogic.arilinemanangmentsystem.utility.FlightValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightService {

    private final List<Flight> flightList = new ArrayList<>();

    private final AirportAndAircraftService airportAircraftService;

    private final FlightValidator validator = new FlightValidator();

    private final InputUtil input = new InputUtil();

    public FlightService(AirportAndAircraftService airportAircraftService) {

        this.airportAircraftService = airportAircraftService;
    }


    /**
     * Reads and returns an Airport object.
     *
     * @param message Input message
     * @return Airport object
     * @throws RecordNotFoundException if airport does not exist
     */
    private Airport readAirport(String message) throws RecordNotFoundException {

        String airportCode = input.getString(message);

        return airportAircraftService.getAirportByCode(airportCode);
    }

    /**
     * Reads and returns an Aircraft object.
     *
     * @param message Input message
     * @return Aircraft object
     * @throws RecordNotFoundException if aircraft does not exist
     */
    private Aircraft readAircraft(String message) throws RecordNotFoundException {

        String aircraftId = input.getString(message);

        return airportAircraftService.getAircraftById(aircraftId);
    }

    /**
     * Reads and returns a LocalDateTime.
     *
     * @param message Input message
     * @return LocalDateTime
     */
    private LocalDateTime readDateTime(String message) {

        return input.getDateTime(message);
    }
    /**
     * Returns all flights.
     *
     * @return Flight List
     */
    public List<Flight> getFlightList() {

        return flightList;
    }

    /**
     * Adds a new flight.
     */
    public void addFlight() {

        try {
              airportAircraftService.displayAllAircraft();
            System.out.println("\n========== ADD FLIGHT ==========");

            String flightId = IdGenerator.generateFlightId();

            Aircraft aircraft = readAircraft("Enter Aircraft ID : ");

            airportAircraftService.displayAllAirports();
            Airport source = readAirport("Enter Source Airport Code : ");

            Airport destination = readAirport("Enter Destination Airport Code : ");

            double basefare = input.getDouble("Enter the basefair for Flight :");

            validator.validateSourceDestination(
                    source.getAirportCode(),
                    destination.getAirportCode());

            LocalDateTime departureDateTime =
                    readDateTime("Enter Departure Date & Time");

            LocalDateTime arrivalDateTime =
                    readDateTime("Enter Arrival Date & Time");

            validator.validateDateTime(
                    departureDateTime,
                    arrivalDateTime);

            validator.validateAircraftAvailability(
                    aircraft.getAircraftId(),
                    departureDateTime,
                    arrivalDateTime,
                    flightList);

            Flight flight = new Flight(
                    flightId,
                    source,
                    destination,
                    departureDateTime,
                    arrivalDateTime,
                    aircraft,
                    basefare,
                    Flight.STATUS_SCHEDULED
            );

            flightList.add(flight);

            System.out.println("\nFlight Added Successfully.");
            System.out.println("Generated Flight ID : " + flightId);
            flight.displayInfo();

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    /**
     * Initializes demo flight data.
     */
    public void initializeDemoFlights() {

        try {

            flightList.add(
                    new Flight(
                            "FL001",
                            airportAircraftService.getAirportByCode("DEL"),
                            airportAircraftService.getAirportByCode("BOM"),
                            LocalDateTime.of(2026, 7, 30, 10, 0),
                            LocalDateTime.of(2026, 7, 30, 12, 10),
                            airportAircraftService.getAircraftById("AC001"),
                            2000.00,
                            "Scheduled"));

            flightList.add(
                    new Flight(
                            "FL002",
                            airportAircraftService.getAirportByCode("BLR"),
                            airportAircraftService.getAirportByCode("HYD"),
                            LocalDateTime.of(2026, 7, 30, 14, 0),
                            LocalDateTime.of(2026, 7, 30, 15, 30),
                            airportAircraftService.getAircraftById("AC002"),
                            3000.00,
                            "Scheduled"));

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    /**
     * Removes a flight.
     */
    public void removeFlight() {

        try {
            displayAllFlights();
            System.out.println("\n========== REMOVE FLIGHT ==========");

            String flightId = input.getString("Enter Flight ID : ");

            Flight flight = findFlightById(flightId);

            flightList.remove(flight);

            System.out.println("\nFlight removed successfully.");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    /**
     * Finds a flight using Flight ID.
     *
     * @param flightId Flight ID
     * @return Flight object
     * @throws RecordNotFoundException if flight is not found
     */
    public Flight findFlightById(String flightId)
            throws RecordNotFoundException {

        for (Flight flight : flightList) {

            if (flight.getFlightId().equalsIgnoreCase(flightId)) {
                return flight;
            }
        }

        throw new RecordNotFoundException("Flight not found.");
    }

    /**
     * Updates an existing flight.
     */
    public void updateFlight() {

        try {

            displayAllFlights();

            System.out.println("\n========== UPDATE FLIGHT ==========");

            String flightId = input.getString("Enter Flight ID : ");

            Flight flight = findFlightById(flightId);

            while (true) {

                System.out.println("\n========== UPDATE FLIGHT ==========");
                System.out.println("1. Change Aircraft");
                System.out.println("2. Change Source Airport");
                System.out.println("3. Change Destination Airport");
                System.out.println("4. Change Departure Date & Time");
                System.out.println("5. Change Arrival Date & Time");
                System.out.println("6. Change Status");
                System.out.println("0. Back");

                int choice = input.getInt("Enter Choice : ");

                switch (choice) {

                    case 1:
                        changeAircraft(flight);
                        flight.displayInfo();
                        break;

                    case 2:
                        changeSource(flight);
                        flight.displayInfo();
                        break;

                    case 3:
                        changeDestination(flight);
                        flight.displayInfo();
                        break;

                    case 4:
                        changeDeparture(flight);
                        flight.displayInfo();
                        break;

                    case 5:
                        changeArrival(flight);
                        flight.displayInfo();
                        break;

                    case 6:
                        changeStatus(flight);
                        flight.displayInfo();
                        break;

                    case 0:
                        return;

                    default:
                        System.out.println("Invalid Choice.");
                }
            }

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    void changeAircraft(Flight flight) {

        while (true) {

            try {

                airportAircraftService.displayAllAircraft();

                Aircraft aircraft = readAircraft("Enter New Aircraft ID : ");

                validator.validateAircraftAvailability(
                        aircraft.getAircraftId(),
                        flight.getDepartureDateTime(),
                        flight.getArrivalDateTime(),
//                    flight.getFlightId(),
                        flightList);

                flight.setAircraft(aircraft);

                System.out.println("Aircraft Updated Successfully.");

                return;

            } catch (Exception e) {

                System.out.println(e.getMessage());
            }
        }
    }

    void changeSource(Flight flight) {

        while (true) {

            try {

                airportAircraftService.displayAllAirports();

                Airport source = readAirport("Enter New Source Airport : ");

                validator.validateSourceDestination(
                        source.getAirportCode(),
                        flight.getDestination().getAirportCode());

                flight.setSource(source);

                System.out.println("Source Airport Updated Successfully.");

                return;

            } catch (Exception e) {

                System.out.println(e.getMessage());
            }
        }
    }

    void changeDestination(Flight flight)  {
       while(true){
           try {
               airportAircraftService.displayAllAirports();
               Airport destination = readAirport("Enter New Destination Airport : ");

               validator.validateSourceDestination(
                       flight.getSource().getAirportCode(),
                       destination.getAirportCode());

               flight.setDestination(destination);

               System.out.println("Destination Airport Updated Successfully.");
               return;
           }
           catch (Exception e){
               System.out.println(e.getMessage());
           }
       }
    }


    void changeDeparture(Flight flight)  {
       while (true){
           try {
               LocalDateTime departure =
                       readDateTime("Enter New Departure Date & Time");

               validator.validateDateTime(
                       departure,
                       flight.getArrivalDateTime());

               validator.validateAircraftAvailability(
                       flight.getAircraft().getAircraftId(),
                       departure,
                       flight.getArrivalDateTime(),
//                flight.getFlightId(),
                       flightList);

               flight.setDepartureDateTime(departure);

               System.out.println("Departure Updated Successfully.");
               return;
           }catch (Exception e){
               System.out.println(e.getMessage());
           }
       }
    }

    void changeArrival(Flight flight) {
        while (true){
            try{
                LocalDateTime arrival =
                        readDateTime("Enter New Arrival Date & Time");

                validator.validateDateTime(
                        flight.getDepartureDateTime(),
                        arrival);

                validator.validateAircraftAvailability(
                        flight.getAircraft().getAircraftId(),
                        flight.getDepartureDateTime(),
                        arrival,
//                flight.getFlightId(),
                        flightList);

                flight.setArrivalDateTime(arrival);

                System.out.println("Arrival Updated Successfully.");
                return;

            }
            catch (Exception e){

            }
        }
    }

    /**
     * Updates flight status.
     *
     * @param flight Flight object
     */
    void changeStatus(Flight flight) {

        while (true) {

            System.out.println("\n========== CHANGE STATUS ==========");
            System.out.println("1. Scheduled");
            System.out.println("2. Delayed");
            System.out.println("3. Cancelled");
            System.out.println("4. Completed");
            System.out.println("0. Back");

            int choice = input.getInt("Enter Choice : ");

            switch (choice) {

                case 1:
                    flight.setStatus(Flight.STATUS_SCHEDULED);
                    System.out.println("Status Updated Successfully.");
                    return;

                case 2:
                    flight.setStatus(Flight.STATUS_DELAYED);
                    System.out.println("Status Updated Successfully.");
                    return;

                case 3:
                    flight.setStatus(Flight.STATUS_CANCELLED);
                    System.out.println("Status Updated Successfully.");
                    return;

                case 4:
                    flight.setStatus(Flight.STATUS_COMPLETED);
                    System.out.println("Status Updated Successfully.");
                    return;

                case 0:
                    return;

                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }
    /**
     * Displays all flights.
     */
    public void displayAllFlights() {

        if (flightList.isEmpty()) {

            System.out.println("No flights available.");
            return;
        }

        System.out.println(
                "\n==================== FLIGHT LIST ====================");

        System.out.printf(
                "%-8s %-8s %-8s %-10s %-20s %-20s %-12s%n",
                "ID",
                "Source",
                "Dest",
                "Aircraft",
                "Departure",
                "Arrival",
                "Status");

        System.out.println(
                "--------------------------------------------------------------------------------------------------------------------------------");

        for (Flight flight : flightList) {
            System.out.println(flight);
        }
    }


    /**
     * Displays Flight Search Menu.
     */
    public void searchFlightMenu() {

        while (true) {

            System.out.println("\n========== SEARCH FLIGHT ==========");
            System.out.println("1. Search by Flight ID");
            System.out.println("2. Search by Source & Destination");
            System.out.println("3. Search by Source, Destination & Date");
            System.out.println("0. Back");

            int choice = input.getInt("Enter Choice : ");

            switch (choice) {

                case 1:
                    searchByFlightId();
                    break;

                case 2:
                    searchBySourceDestination();
                    break;

                case 3:
                    searchBySourceDestinationDate();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }

    /**
     * Searches a flight using Flight ID.
     */
    public void searchByFlightId() {

        try {

            displayAllFlights();

            String flightId = input.getString("Enter Flight ID : ");

            Flight flight = findFlightById(flightId);

            System.out.println("\nFlight Found Successfully.");

            flight.displayInfo();

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    /**
     * Searches flights using Source and Destination.
     */
    public void searchBySourceDestination() {

        try {

            airportAircraftService.displayAllAirports();

            String sourceCode = input.getString("Enter Source Airport Code : ");

            String destinationCode = input.getString("Enter Destination Airport Code : ");

            ArrayList<Flight> result = new ArrayList<>();

            for (Flight flight : flightList) {

                if (flight.getSource().getAirportCode().equalsIgnoreCase(sourceCode)
                        && flight.getDestination().getAirportCode().equalsIgnoreCase(destinationCode)) {

                    result.add(flight);
                }
            }

            displaySearchResult(result);

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    /**
     * Searches flights using Source, Destination and Departure Date.
     */
    public void searchBySourceDestinationDate() {

        try {

            airportAircraftService.displayAllAirports();

            String sourceCode = input.getString("Enter Source Airport Code : ");

            String destinationCode = input.getString("Enter Destination Airport Code : ");

            LocalDate date = input.getDate("Enter Departure Date");

            ArrayList<Flight> result = new ArrayList<>();

            for (Flight flight : flightList) {

                if (flight.getSource().getAirportCode().equalsIgnoreCase(sourceCode)
                        && flight.getDestination().getAirportCode().equalsIgnoreCase(destinationCode)
                        && flight.getDepartureDateTime().toLocalDate().equals(date)) {

                    result.add(flight);
                }
            }

            displaySearchResult(result);

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays all matching flights.
     *
     * @param flights List of matching flights
     * @throws RecordNotFoundException if no flights are found
     */
    private void displaySearchResult(ArrayList<Flight> flights)
            throws RecordNotFoundException {

        if (flights.isEmpty()) {

            throw new RecordNotFoundException("No flights found.");
        }

        System.out.println("\n========== SEARCH RESULT ==========");

        for (Flight flight : flights) {

            flight.displayInfo();
        }
    }



}






