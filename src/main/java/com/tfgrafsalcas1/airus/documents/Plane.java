package com.tfgrafsalcas1.airus.documents;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "plane")
public class Plane {
    
    @Id
    private String id;
    private String icao24;
    private Date starTime;
    private Date endTime;
    private String callsign;
    private Collection<WayPoints> path;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getIcao24() {
        return icao24;
    }
    public void setIcao24(String icao24) {
        this.icao24 = icao24;
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
    public Collection<WayPoints> getPath() {
        return path;
    }
    public void setPath(Collection<WayPoints> path) {
        this.path = path;
    }

    
}
