package com.crimsonlogic.arilinemanangmentsystem.utility;

import com.crimsonlogic.arilinemanangmentsystem.exception.ValidationException;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;

import java.time.LocalDateTime;
import java.util.List;

public class FlightValidator {

    /**
     * Checks whether the flight ID already exists.
     *
     * @param flightId Flight ID
     * @param flightList List of flights
     * @throws ValidationException if Flight ID already exists
     */
    public void validateFlightId(String flightId, List<Flight> flightList)
            throws ValidationException {

        for (Flight flight : flightList) {

            if (flight.getFlightId().equalsIgnoreCase(flightId)) {
                throw new ValidationException("Flight ID already exists.");
            }
        }
    }

    /**
     * Checks whether source and destination are different.
     *
     * @param source Source Airport Code
     * @param destination Destination Airport Code
     * @throws ValidationException if both are same
     */
    public void validateSourceDestination(String source, String destination)
            throws ValidationException {

        if (source.equalsIgnoreCase(destination)) {
            throw new ValidationException("Source and Destination cannot be same.");
        }
    }

    /**
     * Checks departure and arrival date-time.
     *
     * @param departure Departure Date Time
     * @param arrival Arrival Date Time
     * @throws ValidationException if arrival is before departure
     */
    public void validateDateTime(LocalDateTime departure,
                                 LocalDateTime arrival)
            throws ValidationException {

        if (!arrival.isAfter(departure)) {
            throw new ValidationException(
                    "Arrival Date Time must be after Departure Date Time.");
        }
    }

    /**
     * Checks whether an aircraft is available.
     *
     * @param aircraftId Aircraft ID
     * @param departure Departure Date Time
     * @param arrival Arrival Date Time
     * @param flightList List of flights
     * @throws ValidationException if aircraft is already assigned
     */
    public void validateAircraftAvailability(
            String aircraftId,
            LocalDateTime departure,
            LocalDateTime arrival,
            List<Flight> flightList)
            throws ValidationException {

        for (Flight flight : flightList) {

            if (!flight.getAircraft().getAircraftId()
                    .equalsIgnoreCase(aircraftId)) {
                continue;
            }

            LocalDateTime existingDeparture =
                    flight.getDepartureDateTime();

            LocalDateTime existingArrival =
                    flight.getArrivalDateTime();

            boolean overlap =
                    departure.isBefore(existingArrival)
                            && arrival.isAfter(existingDeparture);

            if (overlap) {
                throw new ValidationException(
                        "Aircraft is already assigned to another flight.");
            }
        }
    }
}