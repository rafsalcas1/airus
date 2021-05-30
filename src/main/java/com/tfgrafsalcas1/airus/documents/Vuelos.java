package com.tfgrafsalcas1.airus.documents;

import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vuelos")
public class Vuelos {
    
    @Id
    private String id;
    private Collection<Vuelo> flights;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Collection<Vuelo> getFlights() {
        return flights;
    }

    public void setFlights(Collection<Vuelo> flights) {
        this.flights = flights;
    }

    
}
