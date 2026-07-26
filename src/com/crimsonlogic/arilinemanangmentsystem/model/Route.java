package com.crimsonlogic.arilinemanangmentsystem.model;

public class Route {

    private final String sourceAirportId;
    private final String destinationAirportId;

    public Route(String sourceAirportId, String destinationAirportId) {
        this.sourceAirportId = sourceAirportId;
        this.destinationAirportId = destinationAirportId;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (!(obj instanceof Route))
            return false;

        Route other = (Route) obj;

        return sourceAirportId.equals(other.sourceAirportId)
                && destinationAirportId.equals(other.destinationAirportId);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(sourceAirportId, destinationAirportId);
    }
}
