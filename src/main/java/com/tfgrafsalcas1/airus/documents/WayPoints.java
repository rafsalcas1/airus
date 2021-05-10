package com.tfgrafsalcas1.airus.documents;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "waypoints")
public class WayPoints {
    
    @Id
    private String id;
    private Date time;
    private Double latitude;
    private Double longitude;
    private Double baroAltitude;
    private Double trueTrack;
    private boolean onGround;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public Double getBaroAltitude() {
        return baroAltitude;
    }
    public void setBaroAltitude(Double baroAltitude) {
        this.baroAltitude = baroAltitude;
    }
    public Double getTrueTrack() {
        return trueTrack;
    }
    public void setTrueTrack(Double trueTrack) {
        this.trueTrack = trueTrack;
    }
    public boolean isOnGround() {
        return onGround;
    }
    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    
}
