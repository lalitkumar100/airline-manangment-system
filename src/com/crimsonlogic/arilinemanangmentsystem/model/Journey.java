package com.crimsonlogic.arilinemanangmentsystem.model;
import java.time.LocalDate;

public class Journey {

    private final String sourceAirportId;
    private final String destinationAirportId;
    private final LocalDate departureDate;

    public Journey(String sourceAirportId,
                   String destinationAirportId,
                   LocalDate departureDate) {

        this.sourceAirportId = sourceAirportId;
        this.destinationAirportId = destinationAirportId;
        this.departureDate = departureDate;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (!(obj instanceof Journey))
            return false;

        Journey other = (Journey) obj;

        return sourceAirportId.equals(other.sourceAirportId)
                && destinationAirportId.equals(other.destinationAirportId)
                && departureDate.equals(other.departureDate);
    }

    @Override
    public int hashCode() {

        return java.util.Objects.hash(
                sourceAirportId,
                destinationAirportId,
                departureDate);
    }
}