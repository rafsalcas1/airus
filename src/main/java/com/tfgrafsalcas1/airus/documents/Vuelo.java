package com.tfgrafsalcas1.airus.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "vuelo")
public class Vuelo {
    
    @Id
    private String id;
    private Avion avion;
    private Integer firstSeen;
    private String estDepartureAirport;
    private Integer lastSeen;
    private String estArrivalAirport;
    private String callsign;
    private Double estDepartureAirportHorizDistance;
    private Double estDepartureAirportVertDistance;
    private Double estArrivalAirportHorizDistance;
    private Double estArrivalAirportVertDistance;
    private Double departureAirportCandidatesCount;
    private Double arrivalAirportCandidatesCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public Integer getFirstSeen() {
        return firstSeen;
    }

    public void setFirstSeen(Integer firstSeen) {
        this.firstSeen = firstSeen;
    }

    public String getEstDepartureAirport() {
        return estDepartureAirport;
    }

    public void setEstDepartureAirport(String estDepartureAirport) {
        this.estDepartureAirport = estDepartureAirport;
    }

    public Integer getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Integer lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getEstArrivalAirport() {
        return estArrivalAirport;
    }

    public void setEstArrivalAirport(String estArrivalAirport) {
        this.estArrivalAirport = estArrivalAirport;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public Double getEstDepartureAirportHorizDistance() {
        return estDepartureAirportHorizDistance;
    }

    public void setEstDepartureAirportHorizDistance(Double estDepartureAirportHorizDistance) {
        this.estDepartureAirportHorizDistance = estDepartureAirportHorizDistance;
    }

    public Double getEstDepartureAirportVertDistance() {
        return estDepartureAirportVertDistance;
    }

    public void setEstDepartureAirportVertDistance(Double estDepartureAirportVertDistance) {
        this.estDepartureAirportVertDistance = estDepartureAirportVertDistance;
    }

    public Double getEstArrivalAirportHorizDistance() {
        return estArrivalAirportHorizDistance;
    }

    public void setEstArrivalAirportHorizDistance(Double estArrivalAirportHorizDistance) {
        this.estArrivalAirportHorizDistance = estArrivalAirportHorizDistance;
    }

    public Double getEstArrivalAirportVertDistance() {
        return estArrivalAirportVertDistance;
    }

    public void setEstArrivalAirportVertDistance(Double estArrivalAirportVertDistance) {
        this.estArrivalAirportVertDistance = estArrivalAirportVertDistance;
    }

    public Double getDepartureAirportCandidatesCount() {
        return departureAirportCandidatesCount;
    }

    public void setDepartureAirportCandidatesCount(Double departureAirportCandidatesCount) {
        this.departureAirportCandidatesCount = departureAirportCandidatesCount;
    }

    public Double getArrivalAirportCandidatesCount() {
        return arrivalAirportCandidatesCount;
    }

    public void setArrivalAirportCandidatesCount(Double arrivalAirportCandidatesCount) {
        this.arrivalAirportCandidatesCount = arrivalAirportCandidatesCount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((arrivalAirportCandidatesCount == null) ? 0 : arrivalAirportCandidatesCount.hashCode());
        result = prime * result + ((avion == null) ? 0 : avion.hashCode());
        result = prime * result + ((callsign == null) ? 0 : callsign.hashCode());
        result = prime * result
                + ((departureAirportCandidatesCount == null) ? 0 : departureAirportCandidatesCount.hashCode());
        result = prime * result + ((estArrivalAirport == null) ? 0 : estArrivalAirport.hashCode());
        result = prime * result
                + ((estArrivalAirportHorizDistance == null) ? 0 : estArrivalAirportHorizDistance.hashCode());
        result = prime * result
                + ((estArrivalAirportVertDistance == null) ? 0 : estArrivalAirportVertDistance.hashCode());
        result = prime * result + ((estDepartureAirport == null) ? 0 : estDepartureAirport.hashCode());
        result = prime * result
                + ((estDepartureAirportHorizDistance == null) ? 0 : estDepartureAirportHorizDistance.hashCode());
        result = prime * result
                + ((estDepartureAirportVertDistance == null) ? 0 : estDepartureAirportVertDistance.hashCode());
        result = prime * result + ((firstSeen == null) ? 0 : firstSeen.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lastSeen == null) ? 0 : lastSeen.hashCode());
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
        Vuelo other = (Vuelo) obj;
        if (arrivalAirportCandidatesCount == null) {
            if (other.arrivalAirportCandidatesCount != null)
                return false;
        } else if (!arrivalAirportCandidatesCount.equals(other.arrivalAirportCandidatesCount))
            return false;
        if (avion == null) {
            if (other.avion != null)
                return false;
        } else if (!avion.equals(other.avion))
            return false;
        if (callsign == null) {
            if (other.callsign != null)
                return false;
        } else if (!callsign.equals(other.callsign))
            return false;
        if (departureAirportCandidatesCount == null) {
            if (other.departureAirportCandidatesCount != null)
                return false;
        } else if (!departureAirportCandidatesCount.equals(other.departureAirportCandidatesCount))
            return false;
        if (estArrivalAirport == null) {
            if (other.estArrivalAirport != null)
                return false;
        } else if (!estArrivalAirport.equals(other.estArrivalAirport))
            return false;
        if (estArrivalAirportHorizDistance == null) {
            if (other.estArrivalAirportHorizDistance != null)
                return false;
        } else if (!estArrivalAirportHorizDistance.equals(other.estArrivalAirportHorizDistance))
            return false;
        if (estArrivalAirportVertDistance == null) {
            if (other.estArrivalAirportVertDistance != null)
                return false;
        } else if (!estArrivalAirportVertDistance.equals(other.estArrivalAirportVertDistance))
            return false;
        if (estDepartureAirport == null) {
            if (other.estDepartureAirport != null)
                return false;
        } else if (!estDepartureAirport.equals(other.estDepartureAirport))
            return false;
        if (estDepartureAirportHorizDistance == null) {
            if (other.estDepartureAirportHorizDistance != null)
                return false;
        } else if (!estDepartureAirportHorizDistance.equals(other.estDepartureAirportHorizDistance))
            return false;
        if (estDepartureAirportVertDistance == null) {
            if (other.estDepartureAirportVertDistance != null)
                return false;
        } else if (!estDepartureAirportVertDistance.equals(other.estDepartureAirportVertDistance))
            return false;
        if (firstSeen == null) {
            if (other.firstSeen != null)
                return false;
        } else if (!firstSeen.equals(other.firstSeen))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (lastSeen == null) {
            if (other.lastSeen != null)
                return false;
        } else if (!lastSeen.equals(other.lastSeen))
            return false;
        return true;
    }

    
    
}    
