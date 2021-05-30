package com.tfgrafsalcas1.airus.documents;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rastro")
public class Rastro {
    
    @Id
    private String id;
    private Avion avion;
    private Date starTime;
    private Date endTime;
    private String callsign;
    private Collection<WayPoint> path;
    
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
    public Date getStarTime() {
        return starTime;
    }
    public void setStarTime(Date starTime) {
        this.starTime = starTime;
    }
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public String getCallsign() {
        return callsign;
    }
    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }
    public Collection<WayPoint> getPath() {
        return path;
    }
    public void setPath(Collection<WayPoint> path) {
        this.path = path;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((avion == null) ? 0 : avion.hashCode());
        result = prime * result + ((callsign == null) ? 0 : callsign.hashCode());
        result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
        result = prime * result + ((starTime == null) ? 0 : starTime.hashCode());
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
        Rastro other = (Rastro) obj;
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
        if (endTime == null) {
            if (other.endTime != null)
                return false;
        } else if (!endTime.equals(other.endTime))
            return false;
        if (starTime == null) {
            if (other.starTime != null)
                return false;
        } else if (!starTime.equals(other.starTime))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "Rastro [avion=" + avion + ", callsign=" + callsign + ", endTime=" + endTime + ", starTime=" + starTime
                + "]";
    }
    
}
