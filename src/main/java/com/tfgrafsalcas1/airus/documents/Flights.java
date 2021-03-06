package com.tfgrafsalcas1.airus.documents;

import java.util.Collection;

public class Flights {
    
    private Collection<Flight> flights;

    public Collection<Flight> getFlights() {
        return flights;
    }

    public void setFlights(Collection<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((flights == null) ? 0 : flights.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Flights other = (Flights) obj;
        if (flights == null) {
            if (other.flights != null)
                return false;
        } else if (!flights.equals(other.flights))
            return false;
        return true;
    }

    
}
