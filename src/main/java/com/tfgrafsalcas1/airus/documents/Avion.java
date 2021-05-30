package com.tfgrafsalcas1.airus.documents;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "avion")
public class Avion {
    
    @Indexed(unique=true)
    private String icao24;

    public Avion(String icao24) {
        this.icao24 = icao24;
    }

    public String getIcao24() {
        return icao24;
    }

    public void setIcao24(String icao24) {
        this.icao24 = icao24;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((icao24 == null) ? 0 : icao24.hashCode());
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
        Avion other = (Avion) obj;
        if (icao24 == null) {
            if (other.icao24 != null)
                return false;
        } else if (!icao24.equals(other.icao24))
            return false;
        return true;
    }

    
}
