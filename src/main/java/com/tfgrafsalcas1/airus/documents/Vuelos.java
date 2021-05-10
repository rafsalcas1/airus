package com.tfgrafsalcas1.airus.documents;

import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vuelos")
public class Vuelos {
    
    @Id
    private String id;
    private Perfil perfil;
    private Collection<Vuelo> flights;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Collection<Vuelo> getFlights() {
        return flights;
    }

    public void setFlights(Collection<Vuelo> flights) {
        this.flights = flights;
    }

    
}
